package com.systop.modules.admin.security.rbac.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
import com.systop.modules.admin.security.rbac.model.Role;

/**
 * 角色管理类
 * @author DU
 * @version 3.0
 */
@Service()
public class RoleManager extends BaseGenericsManager<Role> {

  /**
   * 根据名称取得角色
   * @param roleId
   * @param roleName
   */
  @SuppressWarnings("unchecked")
  public List<Role> getRoleByName(String roleId, String roleName) {
    List<Role> list = Collections.EMPTY_LIST;
    StringBuffer hql = new StringBuffer("from Role r where r.name = ?");
    if (StringUtils.isNotBlank(roleId)) {
      hql.append(" and r.id != ?");
      list = query(hql.toString(), new Object[]{roleName, Integer.valueOf(roleId)});
    } else {
      list = query(hql.toString(), roleName);
    }
    return list;
  }
}
