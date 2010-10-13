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
 * @author DU
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class GridStationDao extends AbstractStationDao {
  
  /**
   * 根据查询条件执行测震台站数据查询。
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  public List<Map> queryStation(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_ID, criteria);
  }
  
  /**
   * 根据查询条件执行仪器数据查询。
   * @param instrDic 封装查询条件的<code>InstrDic</code>对象
   * @see {@link InstrDic}
   * @return 查询结果对象。
   */
  public List<Map> getAllInstrument(InstrDic instrDic, String sqlId) {
    List<Map> result = new ArrayList();
    List<Map> rows = getTemplate().queryForList(sqlId, instrDic);
    for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if(row == null) {
        continue;
      } 
      Map item = new HashMap(); 
      item.put("instrCode", row.get("ID"));
      item.put("instrName", row.get("INSTR_MODEL"));
      /*logger.info("仪器ID: " + row.get("ID") + "  仪器类型: " 
          + row.get("INSTR_TYPE") + "  仪器型号： " + row.get("INSTR_MODEL"));*/
      result.add(item);
    }
    return result;
  }  
}
