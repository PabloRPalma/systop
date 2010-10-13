package quake.seismic.data.seed.dao;

import quake.DataType;
import quake.base.dao.AbstractDataShareDao;

/**
 * SeedDao抽象父类
 * @author dhm
 */
public abstract class AbstractSeedDao<T> extends AbstractDataShareDao {

  protected static final String SQL_SATNAME_ID = "cz.queryStaName";
  
  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }
}
