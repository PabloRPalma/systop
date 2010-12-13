package quake.special.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import quake.seismic.data.EQTimeFormat;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.phase.model.PhaseCriteria;

import com.systop.core.dao.support.Page;

@SuppressWarnings("unchecked")
@Repository
public class SpecialDao extends AbstractSpecialDao<Page> {
  /**
   * 根据查询条件执行地震目录数据查询。
   * 
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link QcCriteria}
   * @return 查询结果对象。
   */
  public Page queryQcForObeject(Criteria criteria) {
    // TODO Auto-generated method stub

    List<Map> list = getTemplate().queryForList(SQL_QUERY_QC_BY_ID, criteria);
    criteria.getPage().setData(EQTimeFormat.getEqTimeValue(list, "O_TIME", "O_TIME_FRAC"));
    return criteria.getPage();
  }
  /**
   * 查询震相数据
   */
  public List queryPhaseByCatalogId(PhaseCriteria criteria) {  
    return EQTimeFormat.getEqTimeValue(getTemplate().queryForList(SQL_PHASE, criteria), "O_TIME", "O_TIME_FRAC");
  }
}
