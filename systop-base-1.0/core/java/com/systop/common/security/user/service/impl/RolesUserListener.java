package com.systop.common.security.user.service.impl;

import java.util.Set;

import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.UserListener;
/**
 * 删除<code>User</code>与<code>Role</code>的多对多关系的
 * UserListener.
 * @author SAM
 *
 */
public class RolesUserListener implements UserListener {
  /**
   * 删除用户之前，删除用户与角色的关联关系
   */
  public void onBeforeRemove(User user) {
    Set<Role> roleSet = user.getRoles();
    if (roleSet == null || roleSet.size() < 0) {
      return;
    }
    Role[] roles = roleSet.toArray(new Role[]{});
    
    for (int i = 0; i < roles.length; i++) {
      roles[i].getUsers().remove(user);
      user.getRoles().remove(roles[i]);
    }
  }
  /**
   * 保存的时候什么也不做
   */
  public void onBeforeSave(User user) {
    ;    
  }

}
