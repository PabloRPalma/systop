package quake.seismic.station.dao;

import quake.DataType;
import quake.base.dao.AbstractSeismicDao;

/**
 * {@link StationDao}的抽象实现类。
 * TODO:继续补充文档...
 * @author DU
 *
 * @param <T> 查询结果的数据类型
 */
@SuppressWarnings("unchecked")
public abstract class AbstractStationDao <T> extends AbstractSeismicDao {

  /**
   * 测震台站查询在IBatis中的statementName
   */
  protected static final String SQL_ID = "cz.queryStation";
  
  /**
   * 测震地震计查询在IBatis中的statementName
   */
  public static final String SQL_INSTR_ID_MYSQL = "cz.queryInstrumentMySQL";
  public static final String SQL_INSTR_ID_ORACLE = "cz.queryInstrumentOracle";
  
  protected static final String SQL_STATION_ID = "cz.queryStationById";
  protected static final String SQL_CHANNEL = "cz.queryChannel";
  
  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }
}
