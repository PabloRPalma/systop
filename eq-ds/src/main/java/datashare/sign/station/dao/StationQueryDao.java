package datashare.sign.station.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import datashare.admin.ds.service.DataSourceManager;
import datashare.admin.method.model.Method;
import datashare.admin.subject.model.Subject;
import datashare.base.model.PageSchemaAware;
import datashare.sign.overview.dao.OverviewDao;
import datashare.sign.prequery.model.Point;
import datashare.sign.prequery.model.Station;
import datashare.sign.station.model.SignStationCriteria;
import datashare.sign.support.SignCommonDao;

/**
 * 前兆台站基本信息查询
 * @author DU
 */
@SuppressWarnings("unchecked")
@Repository
public class StationQueryDao extends AbstractStaQueryDao {
  @Autowired(required = true)
  private SignCommonDao commonDao;
  
  @Autowired(required = true)
  private OverviewDao overviewDao;
  
  /** 用于获取Schema */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;
  
  /**
   * 涉密测相分量，定义在applicationContext-config.xml
   */
  @Autowired
  @Qualifier("secretItemIds")
  private ArrayList secretItemIds;
  
  /**
   * 构建台站列表，台站信息中包含测点列表
   * 根据学科查询该学科所对应的测项，然后根据测项信息查询包含这些测项的台站信息。
   * 解析所查询出的台站信息及台站所对应的测点信息。
   * 构建树形结构所需要的数据格式
   * @param subject 学科
   * @param criteria 查询条件
   */
  public List<Station> buildStations(Subject subject, SignStationCriteria criteria) {
    //得到台站与测点Tree只保存着台站与测点ID
    if (subject != null) {
      Method list[] = new Method[]{};
      list = subject.getMethods().toArray(list);
      StringBuffer sb = new StringBuffer();
      if (list != null && list.length > 0) {
        int j = list.length;
        for (int i = 0; i < j - 1; i++) {
          sb.append('\''+list[i].getId()+'\'');
          sb.append(",");
        }
        sb.append('\''+list[j-1].getId()+'\'');
      }
      //logger.info("学科对应的两位测项代码：" + sb.toString());
      criteria.setSubjectItemIds(sb.toString());
    }
    Map<String, List<String>> treeMap = queryNodes(criteria, SQL_STA_POI_ID);
    //前台树列表数据
    List<Station> tree = new ArrayList<Station>();
    for (String stationId : treeMap.keySet()) {
      //解析台站信息
      Station station = new Station();
      station.setId(stationId);
      station.setName(commonDao.getStationName(stationId));
      //解析台站下的测点
      for (String pointId : treeMap.get(stationId)) {
        Point point = new Point();
        point.setId(pointId);
        point.setName(commonDao.getPointName(stationId, pointId));
        station.addPoint(point);
      }
      tree.add(station);
    }
    return tree;
  }
  
  /**
   * 查询台站列表,台站信息中包含测点列表.
   * 构建台站信息与对应测点信息的数据
   * @param criteria 查询条件
   * @param querySqlId sqlMap中对应的查询ID
   * @return Map<台站,List<测点>>
   */
  public Map<String, List<String>> queryNodes(SignStationCriteria criteria, String querySqlId) {
    //得到所有“台站ID,测点ID”
    List<Map<String, String>> rows = getTemplate().queryForList(querySqlId, criteria);
    Map<String, List<String>> treeMap = new TreeMap<String, List<String>>();
    //构建与树状，一个台站有多个测点
    for (Map<String, String> row : rows) {
      List<String> node = treeMap.get(row.get("STATIONID"));
      if (node == null) {
        node = new ArrayList<String>();
        node.add(row.get("POINTID"));
        treeMap.put(row.get("STATIONID"), node);
      } else {
        node.add(row.get("POINTID"));
      }
    }
    return treeMap;
  }
  
  /**
   * 得到前兆数据库中所有仪器
   */
  public List<Map<String, String>> queryInstruments() {
    PageSchemaAware schema = new PageSchemaAware();
    //设置前兆数据库
    schema.setSchema(dataSourceManager.getQzSchema());
    return getTemplate().queryForList(SQL_INST_ID, schema);
  }
  
  /**
   * 根据本地mysql数据库中学科与测项（两位代码）的对应关系，取得三位代码的测项
   * 将查询出来的三位代码的测项排序
   * @param subject 学科
   * @param criteria 测项查询条件
   */
  public List queryMethodsByTwoCode(Subject subject, SignStationCriteria criteria) {
    List methods = new LinkedList();
    //根据本地mysql数据库中学科与测项（两位代码）的对应关系，取得三位代码的测项
    for (Method method : subject.getMethods()) {
      criteria.setItemId(method.getId());
      //logger.info("两位测项代码:" + criteria.getItemId());
      List<Map> rows = getTemplate().queryForList(SQL_MSD_ID, criteria);
      //logger.info("三位测项数量:" + rows.size());
      for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
        Map row = itr.next();
        if(row == null) {
          continue;
        } 
        //屏蔽涉密测项分量
        if(isSecrectItem(row.get("METHODID").toString())) {
          continue;
        }
        Map data = new HashMap();
        data.put("id", row.get("METHODID"));
        data.put("name", row.get("METHODNAME")+"(" + row.get("METHODID") + ")");
        data.put("method", row.get("METHODID")); //用于排序
        methods.add(data);
      }
    }
    //排序，By Sam 2009-7-10
    logger.debug("测项分量排序...");
    Collections.sort(methods, new Comparator<Map>() {
      @Override
      public int compare(Map arg0, Map arg1) {
        String method0 = (String) arg0.get("method");
        String method1 = (String) arg1.get("method");
        
        if(method0 == null || method1 == null) {
          return 0;
        }
        return method0.compareTo(method1);
      }
      
    });
    return methods;
  }
  
  /**
   * 根据本地mysql数据库中学科（两位代码），取得对应的仪器
   * @param subject 学科
   * @param criteria 查询条件
   */
  public List queryInstrsByTwoCode(Subject subject, SignStationCriteria criteria) {
    List instrs = new ArrayList();
    //根据本地mysql数据库中学科（两位代码），取得对应的仪器
    for (Method method : subject.getMethods()) {
      criteria.setItemId(method.getId());
      //logger.info("两位学科代码:" + criteria.getItemId());
      List<Map> rows = getTemplate().queryForList(SQL_SUBINSTRS_ID, criteria);
      //logger.info("对应仪器数量:" + rows.size());
      for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
        Map row = itr.next();
        if(row == null) {
          continue;
        } 
        //屏蔽涉密测项分量
        if(isSecrectItem(row.get("INSTRCODE").toString().substring(0, 3))) {
          continue;
        }
        Map data = new HashMap();
        data.put("id", row.get("INSTRCODE"));
        data.put("name", row.get("INSTRTYPE") + "_" + row.get("INSTRNAME")+"(" + row.get("INSTRCODE") + ")");
        instrs.add(data);
      }
    }
    
    return instrs;
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
   * 根据本地mysql数据库中测项代码查询对应仪器
   * @param method 测项
   * @param criteria 查询条件
   * @return
   */
  public List queryInstrsByMethod(String methodId,SignStationCriteria criteria){
    List instrs = new ArrayList();
    criteria.setItemId(methodId);
    
    List<Map> rows = getTemplate().queryForList(SQL_SUBINSTRS_ID, criteria);
    for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if(row == null) {
        continue;
      } 
      Map data = new HashMap();
      data.put("id", row.get("INSTRCODE"));
      data.put("name", row.get("INSTRTYPE") + "_" + row.get("INSTRNAME")+"(" + row.get("INSTRCODE") + ")");
      instrs.add(data);
    }
    
    return instrs;
  }
  
  /**
   * 查询台站信息，用于显示台站分布图的XML
   * @param subject 学科
   * @param criteria 查询条件
   * @param schema 
   */
  public List<Map> queryStationForList(Subject subject, SignStationCriteria criteria, String schema) {
    if (subject != null) {
      Method list[] = new Method[]{};
      list = subject.getMethods().toArray(list);
      StringBuffer sb = new StringBuffer();
      if (list != null && list.length > 0) {
        int j = list.length;
        for (int i = 0; i < j - 1; i++) {
          sb.append('\''+list[i].getId()+'\'');
          sb.append(",");
        }
        sb.append('\''+list[j-1].getId()+'\'');
      }
      //logger.info("学科对应的两位测项代码：" + sb.toString());
      criteria.setSubjectItemIds(sb.toString());
    }
    List<Map> staList = new ArrayList();
    Set staSets = new HashSet();
    List<Map> rows = getTemplate().queryForList(SQL_STA_POI_ID, criteria);
    for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if(row == null) {
        continue;
      }
      String staId = (String) row.get("STATIONID");
      staSets.add(staId);
    }
    for(Iterator<String> sSet = staSets.iterator(); sSet.hasNext();) {
      String staId = sSet.next();
      SignStationCriteria signStationCriteria = new SignStationCriteria();
      signStationCriteria.setSchema(schema);
      signStationCriteria.setStationId(staId);
      List<Map> staRows = getTemplate().queryForList(SQL_STATION_ID, signStationCriteria);
      staList.addAll(staRows);
    }
    
    return staList;
  }
  
  /**
   * 查询前兆台站，用于页面上台站选择
   */
  public List<Map> getAllStations(String schema) {
    return overviewDao.getStations(schema);
  }
  
  /**
   * 构建台站列表。台站信息中包含测点列表_按台站查询
   * @param criteria 查询条件
   */
  public List<Station> buildStationsForSta(SignStationCriteria criteria) {
    //设置逗号分隔的台站字符串
    criteria.setStationItemIds(criteria.getStationId());
    logger.debug("台站ID字符串：" + criteria.getStationId());
    Map<String, List<String>> treeMap = queryNodes(criteria, SQL_STA_POI_FORSTA_ID);
    logger.debug("-----------------:" + treeMap.size());
    //前台树列表数据
    List<Station> tree = new ArrayList<Station>();
    for (String stationId : treeMap.keySet()) {
      //解析台站信息
      Station station = new Station();
      station.setId(stationId);
      station.setName(commonDao.getStationName(stationId));
      //解析台站下的测点
      for (String pointId : treeMap.get(stationId)) {
        if(pointId != null) {
          Point point = new Point();
          point.setId(pointId);
          point.setName(commonDao.getPointName(stationId, pointId));
          station.addPoint(point);
        }
      }
      tree.add(station);
    }
    logger.debug("台站个数：" + tree.size());
    return tree;
  }
  
  /**
   * 查询台站信息_按台站查询，用于显示台站分布图的XML
   * @param subject 学科
   * @param criteria 查询条件
   * @param schema 
   */
  public List<Map> queryStationForStaList(SignStationCriteria criteria, String schema) {
    logger.debug("所选择的台站：" + criteria.getStationId());
    //设置逗号分隔的台站字符串
    criteria.setStationItemIds(criteria.getStationId());
    List<Map> staList = new ArrayList();
    Set staSets = new HashSet();
    List<Map> rows = getTemplate().queryForList(SQL_STA_POI_FORSTA_ID, criteria);
    for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if(row == null) {
        continue;
      }
      String staId = (String) row.get("STATIONID");
      staSets.add(staId);
    }
    for(Iterator<String> sSet = staSets.iterator(); sSet.hasNext();) {
      String staId = sSet.next();
      SignStationCriteria signStationCriteria = new SignStationCriteria();
      signStationCriteria.setSchema(schema);
      signStationCriteria.setStationId(staId);
      List<Map> staRows = getTemplate().queryForList(SQL_STATION_ID, signStationCriteria);
      staList.addAll(staRows);
    }
    
    return staList;
  }
  
  /**
   * 查询台站信息
   * @param criteria 查询条件
   */
  public Object queryStationInfo(SignStationCriteria criteria) {
    return getTemplate().queryForObject(SQL_STATION_ID, criteria);
  }
  
  /**
   * 查询台站测点信息
   * @param criteria 查询条件
   */
  public Object queryPointInfo(SignStationCriteria criteria) {
    return id2Name((Map) getTemplate().queryForObject(SQL_POINT_ID, criteria));
  }
  
  /**
   * 查询台站测点仪器运行信息
   * @param criteria 查询条件
   */
  public List queryInstrInfo(SignStationCriteria criteria) {
    return batchId2Name(getTemplate().queryForList(SQL_INSTR_ID, criteria));
  }
  
  /**
   * 查询台站测点地磁方位标
   * @param criteria 查询条件
   */
  public List queryGeomagAziFlag(SignStationCriteria criteria) {
    return batchId2Name(getTemplate().queryForList(SQL_GFLAG_ID, criteria));
  }
  
  /**
   * 查询台站测点地磁墩差
   * @param criteria 查询条件
   */
  public List queryGeomagDiff(SignStationCriteria criteria) {
    return batchId2Name(getTemplate().queryForList(SQL_GDIFF_ID, criteria));
  }
  
  /**
   * 查询台站测点测项分量
   * @param criteria 查询条件
   */
  public List queryStaItems(SignStationCriteria criteria) {
    List list = getTemplate().queryForList(SQL_ITEMS_ID, criteria);
    return batchId2Name(list);
  }
  
  /**
   * 查询台站测点仪器参数
   * @param criteria 查询条件
   */
  public List queryInstrParams(SignStationCriteria criteria) {
    return batchId2Name(getTemplate().queryForList(SQL_INSTRPARAMS_ID, criteria));
  }
  
  /**
   * 查询台站测点洞体
   * @param criteria 查询条件
   */
  public List queryStaCave(SignStationCriteria criteria) {
    return batchId2Name(getTemplate().queryForList(SQL_CAVE_ID, criteria));
  }
  
  /**
   * 查询台站测点井泉信息
   * @param criteria 查询条件
   */
  public List queryStaWell(SignStationCriteria criteria) {
    return batchId2Name(getTemplate().queryForList(SQL_WELL_ID, criteria));
  }
  
  /**
   * 查询台站相关洞体信息
   * @param criteria 查询条件
   */
  public List queryStatCaveInfo(SignStationCriteria criteria) {
    return getTemplate().queryForList(SQL_STAT_CAVE_ID, criteria);
  }
  
  /**
   * 查询台站相关井信息
   * @param criteria 查询条件
   */
  public List queryStatWellInfo(SignStationCriteria criteria) {
    return getTemplate().queryForList(SQL_STAT_WELL_ID, criteria);
  }
  
  /**
   * 查询台站相关泉信息
   * @param criteria 查询条件
   */
  public List queryStatSpringInfo(SignStationCriteria criteria) {
    return getTemplate().queryForList(SQL_STAT_SPRING_ID, criteria);
  }
  
  /**
   * 查询台站相关断层信息
   * @param criteria 查询条件
   */
  public List queryStatFaultsInfo(SignStationCriteria criteria) {
    return getTemplate().queryForList(SQL_STAT_FAULTS_ID, criteria);
  }
  
  /**
   * 根据仪器代码查询仪器名称
   */
  public String getInstrNameByCode(final String instrCode) {
    Map args = new HashMap() {{
      put("schema", dataSourceManager.getQzSchema());
      put("instrCode", instrCode);
    }};
    
    return (String) getTemplate().queryForObject("qz.queryInstrName", args);
  }
  
  /**
   * 将List中所有的代码转换为名称，比如将台站代码转换为台站名称
   */
  public List<Map> batchId2Name(List<Map> list) {
    if(CollectionUtils.isEmpty(list)) {
      return Collections.EMPTY_LIST;
    }
    for(Map row : list) {
      id2Name(row);
    }
    
    return list;
  }
  
  
  public Map id2Name(Map row) {
  //转换台站代码
    if(row.containsKey(STATIONID)) {
      row.put("STATIONNAME", commonDao.getStationName((String) row.get(STATIONID)));
    }
    //转换测相分量代码
    if(row.containsKey(ITEMID)) {
      row.put("ITEMNAME", commonDao.getItemName((String) row.get(ITEMID)));
    }
    //转换采样率代码
    if(row.containsKey(SAMPLERATE)) {
      row.put("SAMPLERATENAME", commonDao.getSampleRateName((String) row.get(SAMPLERATE)));
    }
    //转换仪器代码
    if(row.containsKey(INSTRCODE)) {
      row.put("INSTRNAME", getInstrNameByCode((String) row.get(INSTRCODE)));
    }
    //是否为主标志
    if(row.containsKey(ISMAINAZIFLAG)) {
      row.put("ISMAINAZIFLAG_CN", S_MAP.get((String) row.get(ISMAINAZIFLAG)));
    }
    //是否为标准墩
    if(row.containsKey(ISSTANDARDSTACK)) {
      row.put("ISSTANDARDSTACK_CN", S_MAP.get((String) row.get(ISSTANDARDSTACK)));
    }
    //观测方法
    if(row.containsKey(ANALOGORDIGITAL)) {
      row.put("ANALOGORDIGITAL_CN", ANALOGORDIGITAL_MAP.get((String) row.get(ANALOGORDIGITAL)));
    }
    //转换接收单位代码
    if(row.containsKey(DCUNITCODE)) {
      row.put("DCUNITNAME", commonDao.getStationName((String) row.get(DCUNITCODE)));
    }
    return row;
  }
  
  private static final String STATIONID = "STATIONID";
  private static final String ITEMID = "ITEMID";
  private static final String SAMPLERATE = "SAMPLERATE";
  private static final String INSTRCODE = "INSTRCODE";
  private static final String ISSTANDARDSTACK = "ISSTANDARDSTACK";
  private static final String ISMAINAZIFLAG = "ISMAINAZIFLAG";
  private static final String ANALOGORDIGITAL = "ANALOGORDIGITAL";
  private static final String DCUNITCODE = "DCUNITCODE";
  
  private static final String S_YES = "1";
  private static final String S_NO = "0";
  public static final Map<String, String> S_MAP = new HashMap<String, String>();
  static {
    S_MAP.put(S_YES, "是");
    S_MAP.put(S_NO, "否");
  }
  public static final Map<String, String> ANALOGORDIGITAL_MAP = new HashMap<String, String>();
  static {
    ANALOGORDIGITAL_MAP.put(S_YES, "数字化观测");
    ANALOGORDIGITAL_MAP.put(S_NO, "模拟观测");
  }
}
