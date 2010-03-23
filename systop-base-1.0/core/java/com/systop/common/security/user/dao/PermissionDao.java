package com.systop.common.security.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.systop.common.dao.impl.BaseGenericsDAO;
import com.systop.common.security.user.model.Permission;

/**
 * @author Sam
 * 
 */
public class PermissionDao extends BaseGenericsDAO<Permission> {
  /**
   * 根据组合条件查询<code>Permission</code>
   */
  public List<Permission> findBy(Map filter) {
    Criteria criteria = getEntityCriteria();

    String id = (String) filter.get("id");
    if (StringUtils.isNotEmpty(id)) {
      criteria.add(Restrictions.eq("id", new Integer(id)));
    }

    String name = (String) filter.get("name");
    if (StringUtils.isNotEmpty(name)) {
      criteria.add(Restrictions.like("name", "%" + name + "%"));
    }

    String status = (String) filter.get("status");
    if (StringUtils.isNotEmpty(status)) {
      criteria.add(Restrictions.eq("status", status));
    }
    
    String sysname = (String) filter.get("operation");
    //all表示全部操作
    if (StringUtils.isNotEmpty(sysname) && !"all".equals(sysname)) {
      criteria.add(Restrictions.eq("operation", sysname));
    }
    criteria.addOrder(org.hibernate.criterion.Order.asc("name"));

    return criteria.list();
  }
}
