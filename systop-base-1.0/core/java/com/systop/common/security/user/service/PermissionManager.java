package com.systop.common.security.user.service;

import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Role;
import com.systop.common.service.Manager;

/**
 * @author Administrator
 * 
 */
public interface PermissionManager extends Manager<Permission> {
  /**
   * 检查权限名是否重复，如果重复，返回true
   * @param name
   * @return
   */
  boolean isNameInUse(String name);

  /**
   * 按角色查找权限，找到的是角色拥有的权限
   * @param role 用户
   * @return 返回角色拥有的权限
   */
  Permission[] getByRole(Role role);
}
