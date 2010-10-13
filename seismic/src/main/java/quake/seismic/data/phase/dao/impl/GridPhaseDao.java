package quake.seismic.data.phase.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import quake.seismic.data.EQTimeFormat;
import quake.seismic.data.phase.dao.AbstractPhaseDao;
import quake.seismic.data.phase.model.PhaseCriteria;


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
