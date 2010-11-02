package quake.special.dao;

import quake.DataType;
import quake.base.dao.AbstractSeismicDao;
@SuppressWarnings("unchecked")
public class AbstractSpecialDao<T> extends AbstractSeismicDao {

  /**
   * 地震目录查询在IBatis中
   */
  protected static final String SQL_QUERY_QC_BY_ID = "zt.queryQcById";
 
  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }

}
