package datashare.seismic.data.seed.dao;

import datashare.DataType;
import datashare.base.dao.AbstractDataShareDao;

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
