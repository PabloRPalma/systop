package quake.seismic.data.phase.dao;

import quake.DataType;
import quake.base.dao.AbstractSeismicDao;
import quake.seismic.data.phase.model.PhaseCriteria;

public abstract class AbstractPhaseDao<T> extends AbstractSeismicDao {
  /**
   * 震相数据查询在IBatis中的statementName
   */
  protected static final String SQL_ID = "cz.queryPhaseData";
  
  /**
   * 根据条件查询震相数据
   * @param criteria  封装查询条件的<code>Criteria</code>对象
   * @return
   */
  public abstract T query(PhaseCriteria criteria);
  
  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }
}
