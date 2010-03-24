package datashare.sign.overview.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import datashare.DataType;
import datashare.base.dao.AbstractDataShareDao;
import datashare.sign.data.model.Criteria;

@Repository
public class OverviewDao extends AbstractDataShareDao {
 
  /**
   * 查询QZ_DICT_STATIONITEMS表
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Map> query(Criteria criteria) {
    return getTemplate().queryForList("ov.overview", criteria);
  }
  
  /**
   * 返回Station列表，用于页面显示Combox
   */
  @SuppressWarnings("unchecked")
  public List<Map> getStations(String schema) {
    Map param = new HashMap();
    param.put("schema", schema);
    return getTemplate().queryForList("ov.stations", param);
  }
  
  @Override
  protected DataType getDataType() {
    return DataType.SIGN;
  }

}
