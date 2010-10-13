package quake.seismic.instrument.stasite.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.seismic.instrument.stasite.model.StaSite;

import com.systop.core.dao.support.Page;


/**
 * 场地响应查询DAO，进行数据查询。
 * @author DU
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class StaSiteDao extends AbstractStaSiteDao<Page> {

  /**
   * 根据查询条件执行场地响应数据查询。
   * @param staSite 封装查询条件的<code>StaSite</code>对象
   * @see {@link StaSite}
   * @return 查询结果对象。
   */
  public Page queryStaSite(StaSite staSite) {
    Assert.notNull(staSite, "StaSite is null.");
    int count = (Integer) (getTemplate().queryForObject(SQL_COUNT_ID, staSite));
    List<Map> rows = getTemplate().queryForList(SQL_ID, staSite, staSite.getPage()
        .getStartIndex(), staSite.getPage().getPageSize());
    staSite.getPage().setData(rows);
    staSite.getPage().setRows(count);
    return staSite.getPage();
  }
}
