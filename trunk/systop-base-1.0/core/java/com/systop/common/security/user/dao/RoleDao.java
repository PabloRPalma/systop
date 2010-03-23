package com.systop.common.security.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.systop.common.Constants;
import com.systop.common.dao.impl.BaseGenericsDAO;
import com.systop.common.security.user.model.Resource;
import com.systop.common.security.user.model.Role;

/**
 * Data access object of <code>Role</code>
 * @author Sam
 * 
 */
public class RoleDao extends BaseGenericsDAO<Role> {
  /**
   * 查询指定角色(<code>Role</code>)被授权的资源(<code>Resource</code>)
   * @param role 指定的角色，id不能为null
   * @return List of <code>Resource</code>,如果不存在，返回Empty List
   */
  public List<Resource> findResourcesByRole(Role role) {
    String hql = "select resc from Role role left "
        + "join role.permissions permi left join permi.resources resc "
        + "where permi.status='"
        + Constants.STATUS_AVAILABLE 
        + "' and role=?";
    return getHibernateTemplate().find(hql, role);
  }

  /**
   * 根据组合条件查询Roles
   * @param filter 查询条件，是<code>Role</code>的字段名和预期值的key-value Map
   * @return List of <code>Role</code>,如果不存在，返回Empty List
   */
  public List<Role> findBy(Map filter) {
    Criteria criteria = getEntityCriteria();

    String id = (String) filter.get("id");
    if (StringUtils.isNotEmpty(id)) {
      criteria.add(Restrictions.eq("id", new Integer(id)));
    }

    String name = (String) filter.get("name");
    if (StringUtils.isNotEmpty(name)) {
      criteria.add(Restrictions.like("name", "%" + name + "%"));
    }

    String descn = (String) filter.get("descn");
    if (StringUtils.isNotEmpty(descn)) {
      criteria.add(Restrictions.like("descn", "%" + descn + "%"));
    }

    criteria.addOrder(org.hibernate.criterion.Order.asc("name"));

    return criteria.list();
  }
}
