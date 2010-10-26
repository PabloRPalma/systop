package quake.special.dao;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import quake.special.model.Criteria;


public class SpecialDao extends AbstractSpecialDao {
  /**
   * 根据查询条件执行地震目录数据查询。
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  @SuppressWarnings("unchecked")
  public List<Map> queryQc(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_ID, criteria);
  }
  
}
