package com.systop.common.security.user.webapp;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.service.PermissionManager;
import com.systop.common.webapp.struts2.action.BaseModelAction;

/**
 * @author Administrator
 *
 */
public class PermissionAction extends
    BaseModelAction<Permission, PermissionManager> {
  /**
   * 检查权限名是否重复，如果重复，返回true
   * @param name 权限名
   * @return true:权限名已被使用;false:权限名未被使用
   */
  public boolean isNameInUse(String name) {
    return getManager().isNameInUse(name);
  }

  /**
   * 查找角色拥有的权限
   * @param role 角色
   * @return 角色拥有的权限
   */
  public Permission[] getByRole(Role role) {
    return getManager().getByRole(role);
  }
  
  /**
   * @see BaseModelAction#setupDetachedCriteria()
   */
  @Override
  protected DetachedCriteria setupDetachedCriteria() {
  	DetachedCriteria dc = DetachedCriteria.forClass(Permission.class).add(
        Restrictions.like("name", model.getName(), MatchMode.ANYWHERE));
  	if (!model.getStatus().equals("")) {
  		dc.add(Restrictions.eq("status", model.getStatus()));
  	}
  	if (!model.getOperation().equals("")) {
  		dc.add(Restrictions.eq("operation", model.getOperation()));
  	}
    return dc;
  }

}
