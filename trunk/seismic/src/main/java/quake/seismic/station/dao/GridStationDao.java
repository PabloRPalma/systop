package quake.seismic.station.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.seismic.station.model.Criteria;
import quake.seismic.station.model.InstrDic;

/**
 * 测震台站据查询Dao。
 * 
 * @author DU
 * 
 */
@SuppressWarnings("unchecked")
@Repository
public class GridStationDao extends AbstractStationDao {

  /**
   * 根据查询条件执行测震台站数据查询。
   * 
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  public List<Map> queryStation(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria);
    return setStaChannels(rows ,criteria);
  }

  /**
   * 根据台站查询通道
   * 
   * @param rows
   */

  private List<Map> setStaChannels(List<Map> rows,Criteria c) {
    Criteria cc =new Criteria();
    cc.setSchema(c.getSchema());
    for (Map sta : rows) {
      String netCode = (String) sta.get("NET_CODE");
      String staCode = (String) sta.get("STA_CODE");
      cc.setNetCode(netCode);
      cc.setStaCode(staCode);
      sta.put("staChannels", queryChannel(cc));
    }
    return rows;
  }

  /**
   * 根据查询条件执行通道数据查询。
   * 
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  public List<Map> queryChannel(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_CHANNEL, criteria);
  }

  /**
   * 根据查询条件执行仪器数据查询。
   * 
   * @param instrDic 封装查询条件的<code>InstrDic</code>对象
   * @see {@link InstrDic}
   * @return 查询结果对象。
   */
  public List getInstrumentByType(InstrDic instrDic, String sql) {
    List<Map> result = new ArrayList();
    List<Map> rows = getTemplate().queryForList(sql, instrDic);
    for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if (row == null) {
        continue;
      }
      Map item = new HashMap();
      item.put("instrModel", row.get("INSTR_MODEL"));
      result.add(item);
    }
    return result;
  }

}
