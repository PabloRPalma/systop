package datashare.seismic.data.phase.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import datashare.seismic.data.EQTimeFormat;
import datashare.seismic.data.phase.dao.AbstractPhaseDao;
import datashare.seismic.data.phase.model.PhaseCriteria;

/**
 * 查询震相数据
 * @author wbb
 */
@SuppressWarnings("unchecked")
@Repository
public class GridPhaseDao extends AbstractPhaseDao<List> {
  @Override
  public List query(PhaseCriteria criteria) {
    return EQTimeFormat.getEqTimeValue(getTemplate().queryForList(SQL_ID, criteria), "O_TIME", "O_TIME_FRAC");
  }

}
