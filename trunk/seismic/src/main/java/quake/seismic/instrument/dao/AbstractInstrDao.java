package quake.seismic.instrument.dao;

import quake.DataType;
import quake.base.dao.AbstractSeismicDao;
import quake.seismic.instrument.model.Instrument;

/**
 * 仪器查询Dao抽象实现类
 * @author DU
 */
public abstract class AbstractInstrDao<T> extends AbstractSeismicDao {

  /**
   * 仪器查询在IBatis中的statementName
   */
  protected static final String SQL_ID = "cz.queryInstr";
  
  protected static final String SQL_COUNT_ID = "cz.queryInstrCount";
  
  /**
   * 根据条件查询仪器信息
   * @param criteria  封装查询条件的<code>Criteria</code>对象
   * @return
   */
  public abstract T query(Instrument instrument);
  
  @Override
  protected DataType getDataType() {
    return DataType.SIGN;
  }
}
