package quake.seismic.instrument.stasite.dao;

import quake.DataType;
import quake.base.dao.AbstractDataShareDao;

/**
 * 场地响应查询DAO
 * @author DU
 *
 * @param <T> 查询结果的数据类型
 */
@SuppressWarnings("unchecked")
public abstract class AbstractStaSiteDao <T> extends AbstractDataShareDao {

  /**
   * 场地响应查询在IBatis中的statementName
   */
  protected static final String SQL_ID = "cz.queryStaSiteList";
  
  /**
   * 场地响应总数查询在IBatis中的statementName
   */
  protected static final String SQL_COUNT_ID = SQL_ID + "Count";
  
  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }
}
