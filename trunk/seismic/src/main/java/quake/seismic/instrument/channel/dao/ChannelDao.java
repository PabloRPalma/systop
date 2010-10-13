package quake.seismic.instrument.channel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.seismic.instrument.channel.model.Channel;
import quake.seismic.station.model.Criteria;

import com.systop.core.dao.support.Page;


/**
 * 测震通道参数查询DAO，进行数据查询。
 * @author DU
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class ChannelDao extends AbstractChannelDao<Page> {

  /**
   * 根据查询条件执行测震通道参数数据查询。
   * @param channel 封装查询条件的<code>Channel</code>对象
   * @see {@link Channel}
   * @return 查询结果对象。
   */
  public Page queryChannelInfo(Channel channel) {
    Assert.notNull(channel, "Channel is null.");
    int count = (Integer) (getTemplate().queryForObject(SQL_COUNT_ID, channel));
    logger.debug("台站总数：" + count);
    List<Map> rows = getTemplate().queryForList(SQL_ID, channel, channel.getPage()
        .getStartIndex(), channel.getPage().getPageSize());
    channel.getPage().setData(rows);
    channel.getPage().setRows(count);
    return channel.getPage();
  }
  
  /**
   * 根据查询条件执行测震台站数据查询，返回List类型数据
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  public List<Map> getAllStationInfo(Criteria criteria) {
    List<Map> result = new ArrayList();
    List<Map> rows = getTemplate().queryForList(SQL_STA_ID, criteria);
    for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if(row == null) {
        continue;
      } 
      Map item = new HashMap(); 
      item.put("staCode", row.get("STA_CODE"));
      item.put("staName", row.get("STA_CNAME"));
      /*logger.info("台站代码: " + row.get("STA_CODE") + "  台站名称: " 
          + row.get("STA_CNAME");*/
      result.add(item);
    }
    return result;
  }
  
  /**
   * 根据查询条件执行测震台站数据查询,返回Map类型数据
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  public Map<String, String> getStationInfoMap(Criteria criteria) {
    Map staMap = new HashMap<String, String>();
    List<Map> rows = getTemplate().queryForList(SQL_STA_ID, criteria);
    for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if(row == null) {
        continue;
      }
      staMap.put(row.get("STA_CODE"), row.get("STA_CNAME"));
      /*logger.info("台站代码: " + row.get("STA_CODE") + "  台站名称: " 
          + row.get("STA_CNAME"));*/
    }
    return staMap;
  }
}
