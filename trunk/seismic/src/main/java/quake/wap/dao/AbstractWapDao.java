package quake.wap.dao;

import quake.DataType;
import quake.base.dao.AbstractSeismicDao;

public abstract class AbstractWapDao<T> extends AbstractSeismicDao {
  protected static final String SQL_CAT_COUNT = "cz.queryCatCount";
  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }
}
