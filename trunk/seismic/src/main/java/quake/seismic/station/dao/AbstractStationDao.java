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
   
  protected static final String SQL_STATION_ID = "cz.queryStationById";
  protected static final String SQL_CHANNEL = "cz.queryChannel";
  protected static final String SQL_CHANNEL_ID = "cz.queryChannelById";
  protected static final String SQL_DIGITIZER = "cz.queryDigitizer";
  protected static final String SQL_LOC = "cz.queryLoc";
  public static final String SQL_INSTR_TYPE = "cz.queryInstrByType";
  
  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }
}
