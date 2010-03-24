package datashare.sign.prequery.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import datashare.sign.data.model.Criteria;
import datashare.sign.prequery.model.Item;
import datashare.sign.prequery.model.Node;
import datashare.sign.prequery.model.Point;
import datashare.sign.prequery.model.PrequeryCriteria;
import datashare.sign.prequery.model.Station;
import datashare.sign.prequery.util.DistUtil;
import datashare.sign.support.SignCommonDao;

/**
 * 
 * @author Lunch
 */
@SuppressWarnings("unchecked")
@Repository
public class PrepareQueryDao extends AbstractPreQueryDao {
  @Autowired(required = true)
  private SignCommonDao commonDao;
  
  @Autowired(required = true)
  private CacheManager cacheManager;
  
  /**
   * 涉密测相分量，定义在applicationContext-config.xml
   */
  @Autowired
  @Qualifier("secretItemIds")
  private ArrayList secretItemIds;
  
  /**
   * 构建台站列表。台站信息中包含测点列表，测点信息中包含测项分量列表
   * 
   * @param criteria 查询条件
   * @return
   */
  public List<Station> buildStations(PrequeryCriteria criteria) {
    
    List<Node> nodes = queryNodes(criteria);// 获得查询结果

    // 将查询结果转换成存储 Station 信息的Map
    Map<String, String> sm = DistUtil.distStationsToMap(nodes);

    // 将查询结果转换成存储 Point 信息的Map
    Map<String, String> pm = DistUtil.distPointsToMap(nodes);

    // 将查询结果转换成存储 Item 信息的Map
    Map<String, Node> im = DistUtil.itemsToMap(nodes);

    // 下面的代码主要是利用Map中key值的唯一性，将台站，测点，测项分量三级数据有机的组织在一起。
    Set<String> sidSet = sm.keySet();
    Set<String> pidSet = pm.keySet();
    Set<String> iidSet = im.keySet();

    List<Station> ss = new ArrayList<Station>();
    String[] sid_pid = null; // 台站编码,测点编码组合
    String[] sid_pid_iid = null; // 台站编码,测点编码,测项分量编码组合
    for (String sid : sidSet) {// 遍历台站
      Station s = new Station(sid, sm.get(sid));
      for (String pid : pidSet) {// 遍历测点
        sid_pid = pid.split("_");// 测点id分割成两个元素
        if (sid_pid[0].equals(s.getId())) {
          Point p = new Point(sid_pid[1], pm.get(pid));
          s.addPoint(p);
          for (String iid : iidSet) {// 遍历测点
            sid_pid_iid = iid.split("_");// 测项分量id分割成三个元素
            if (sid_pid_iid[0].equals(s.getId()) && sid_pid_iid[1].equals(p.getId())) {
              Item i = new Item(iid, im.get(iid));
              p.addItem(i);
            }
          }
        }
      }
      ss.add(s);// 向台站中增加台站信息
    }
    return ss;
  }

  /**
   * 根据条件从数据中查询[台站、测点、测项分量]组合无重复的信息。并将这些信息封装到Node中。并以List形式返回。 在具体查询过程中，将会在所有符合条件并且存在数据表中进修查询。
   * 
   * @param criteria 查询条件
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<Node> queryNodes(PrequeryCriteria criteria) {

    // 首先获得符合条件的表名称
    List<String> tabNames = queryTableNames(criteria);

    List<Node> nodes = new LinkedList();
    for (String tableName : tabNames) {
      criteria.setQueryTableName(tableName);
      List<Node> tempNodes = getTemplate().queryForList(SQL_QUERY_NODE_ID, criteria);
      for (Node node : tempNodes) {
        if(!isSecrectItem(node.getItemId())) { //检查测相分量是否涉密
          // 告诉本条记录是从哪个数据表中查询出来的。
          node.setTableName(tableName);
          nodes.add(node);
        }
      }
      //因为需要检查每个测相分量的涉密情况，所以不能用addAll了
      // 将每个表中查询的Node信息都添加到列表中
      //nodes.addAll(tempNodes);
    }

    String stationName = StringUtils.EMPTY;
    String itemName = StringUtils.EMPTY;
    String pointName = StringUtils.EMPTY;
    Node temNode = null;
    // 进行下一步查询以获得台站名称，测点名称，测项分量名称，以及数据开始截至时间
    for (Node n : nodes) {
      
      // 设置查询条件stationId
      criteria.setStationId(n.getStationId());
      // 查询台站名称
      stationName = commonDao.getStationName(criteria.getStationId());
      n.setStationName(stationName);

      // 设置测点编码:pointId,此查询还需要stationId,前面已经经赋值
      criteria.setPointId(n.getPointId());
      // 查询测点名称
      pointName = commonDao.getPointName(criteria.getStationId(), criteria.getPointId());
      n.setPointName(pointName);

      // 设置测项分量编码:itemId
      criteria.setItemId(n.getItemId());
      // 查询测项分量名称
      itemName = commonDao.getItemName(criteria.getItemId());
      n.setItemName(itemName);

      // 设置从哪个表进行查询,此时还需要itemId,前面已经赋值
      criteria.setQueryTableName(n.getTableName());
      //查询本条node对应数据的开始时间、截至时间
      temNode = getMaxDateAndMinDate(criteria);
      n.setStartDate((temNode == null) ? "" : temNode.getStartDate());
      n.setEndDate((temNode == null) ? "" : temNode.getEndDate());
    }
    logger.debug("查询出的记录数量为:" + nodes.size());
    return nodes;
  }

  private Node getMaxDateAndMinDate(PrequeryCriteria criteria) {
     String key = new StringBuffer(200).append(criteria.getQueryTableName())
     .append(criteria.getSchema()).append(criteria.getStationId())
     .append(criteria.getPointId()).append(criteria.getItemId()).toString();
     //Ehcache in classpath:ehcache.xml
     Ehcache cache = cacheManager.getCache("maxDateMinDateInSign");
     Element ele = cache.get(key);
     Node result = null;
     if(ele == null) {
       Node node = (Node) getTemplate().queryForObject(SQL_QUERY_START_END_DATE_ID, criteria);
       logger.debug("Got maxDate minDate from database.{}", key);
       if(node != null) {
         cache.put(new Element(key, node));
         result = node;
       }
     } else {
       result = (Node) ele.getValue();
       logger.debug("Got maxDate minDate from cache.{}", key);
     }
     return result;
  }
  
  /**
   * 判断一个测相分量是否涉密，涉密的测相分量定义在applicationContext-config.xml中,
   * 可以只定义测相分量的前3位或者前两位。
   * @see @{link #secretItemIds}
   * @param itemId 给出被检测的测向分量
   * @return 如果涉密，返回true,否则，false
   */
  private boolean isSecrectItem(String itemId) {
    if(StringUtils.isBlank(itemId)) {
      return false;
    }
    if(CollectionUtils.isEmpty(secretItemIds)) {
      return false; //如果没有定义涉密测相分量，返回false
    }
    
    for(Iterator itr = secretItemIds.iterator(); itr.hasNext();) {
      String sItemId = (String) itr.next();
      //只有涉密测相分量不为空，并且长度>=2,才有判断价值
      if(StringUtils.isNotBlank(sItemId) && sItemId.length() >= 2) {
        if(itemId.startsWith(sItemId)) {
          logger.debug("测相分量{},涉密。", itemId);
          return true;
        }
      }
    }
    
    return false;
  }
  
  /**
   * 根据Criteria中的属性：[schema,tableCategory,sampleRate].查询数据库存在并且符合条件的表名。
   * 
   * @param criteria 查询条件
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<String> queryTableNames(Criteria criteria) {
    // 查询数据库存在并且符合条件的表名
    List<String> rows = getTemplate().queryForList(SQL_TABLENAME_ID, criteria);
    if (rows == null || rows.isEmpty()) {// 如果查询结果为空，返回EMPTY_LIST
      return Collections.EMPTY_LIST;
    }
    List<String> tableNames = new ArrayList();
    String mids = criteria.getMethodId();
    if (mids.indexOf(",") == -1) {
      mids = mids + ",";
    }
    // 分割mids,取得测项代码数组,mids由多个测项代码组成，彼此间有[,]隔开。
    String[] mIdArray = mids.split(",");
    for (String mid : mIdArray) {
      for (String tabName : rows) {
        if (tabName.indexOf("_" + mid) != -1) {// 包含测项代码的表名是符合要求的
          tableNames.add(tabName);
        }
      }
    }
    return tableNames;
  }

}
