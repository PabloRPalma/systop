package com.systop.common.security.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.systop.common.dao.impl.BaseGenericsDAO;
import com.systop.common.security.user.model.Resource;

/**
 * Data access object for <code>Resource</code>
 * @author Sam
 */
public class ResourceDao extends BaseGenericsDAO<Resource> {
  /**
   * 组合条件查询
   * @param filter 查询条件，以<code>Resource</code>的字段名和期望值为key和value
   * @return List of <code>Resource</code>...or Empty list.
   */
  public List<Resource> findBy(Map filter) {
    Criteria criteria = getEntityCriteria();

    String id = (String) filter.get("id");
    if (StringUtils.isNotEmpty(id)) {
      criteria.add(Restrictions.eq("id", new Integer(id)));
    }

    String name = (String) filter.get("name");
    if (StringUtils.isNotEmpty(name)) {
      criteria.add(Restrictions.like("name", "%" + name + "%"));
    }

    String resString = (String) filter.get("resString");
    if (StringUtils.isNotEmpty(resString)) {
      criteria.add(Restrictions.like("resString", "%" + resString + "%"));
    }

    String resType = (String) filter.get("resType");
    if (StringUtils.isNotEmpty(resType) && !"all".equals(resType)) {
      criteria.add(Restrictions.eq("resType", resType));
    }

    criteria.addOrder(org.hibernate.criterion.Order.asc("id"));

    return criteria.list();
  }
}
