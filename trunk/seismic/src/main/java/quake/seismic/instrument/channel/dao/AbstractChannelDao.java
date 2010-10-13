package quake.seismic.instrument.channel.dao;

import quake.DataType;
import quake.base.dao.AbstractDataShareDao;

/**
 * 测震通道参数查询DAO
 * @author DU
 *
 * @param <T> 查询结果的数据类型
 */
@SuppressWarnings("unchecked")
public abstract class AbstractChannelDao<T> extends AbstractDataShareDao {

  /**
   * 测震通道参数查询在IBatis中的statementName
   */
  protected static final String SQL_ID = "cz.queryChannelList";
  
  /**
   * 测震通道参数总数查询在IBatis中的statementName
   */
  protected static final String SQL_COUNT_ID = "cz.queryChannelListCount";
  
  /**
   * 测震台站列表查询在IBatis中的statementName
   */
  protected static final String SQL_STA_ID = "cz.queryStaList";
  
  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }
}
