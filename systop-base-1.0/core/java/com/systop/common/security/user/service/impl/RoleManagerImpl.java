/**
 * 
 */
package com.systop.common.security.user.service.impl;

import java.util.Set;

import com.systop.common.security.acegi.cache.AcegiCacheManager;
import com.systop.common.security.user.dao.RoleDao;
import com.systop.common.security.user.exception.RoleAlreadyExistsException;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.RoleManager;
import com.systop.common.service.BaseManager;

/**
 * @author Administrator
 * 
 */
public class RoleManagerImpl extends BaseManager<Role> implements RoleManager {
  /** 角色Dao,此属性需要Spring初始化 */
  private RoleDao roleDao;
  /**
   * <code>AcegiCacheManager</code>用于同步缓存
   */
  private AcegiCacheManager acegiCacheManager;

  /**
   * @see RoleManager#getRolesByUser(User)
   */
  @SuppressWarnings("unchecked")
  public Role[] getRolesByUser(User user) {
    Set roleSet = user.getRoles();
    Role[] retRoles = new Role[roleSet.size()];
    roleSet.toArray(retRoles);
    return retRoles;
  }

  /**
   * @see RoleManager#isRoleNameInUse(java.lang.String)
   */
  public boolean isRoleNameInUse(String rolename) {
    Role role = new Role();
    role.setName(rolename);
    if (log.isDebugEnabled()) {
      log.debug("check for role name '" + rolename + "'");
    }
    return exists(role, "name"); // 如果给定name不唯一，则表示正在使用
  }

  /**
   * 
   */
  @Override
  public void save(Role role) {
    if (!exists(role, "name")) {
      super.save(role);
      if (acegiCacheManager != null) {
        acegiCacheManager.onRoleChanged(role);
      }
    } else {
      throw new RoleAlreadyExistsException(role.getName());
    }
  }

  /**
   * 角色dao,一般情况会由spring初始化
   * @return 角色dao
   */
  public RoleDao getRoleDao() {
    return roleDao;
  }

  /**
   * 角色dao,一般情况会由spring初始化
   * @param roleDao 传进来的角色dao
   */
  public void setRoleDao(RoleDao roleDao) {
    this.roleDao = roleDao;
    // BaseManager可以和UserDao使用同一个SessionFactory，这样就避免了在
    // applicationContext中重复设置sessionFactory属性了
    if (getSessionFactory() == null) {
      setSessionFactory(roleDao.getSessionFactory());
    }
  }

  /**
   * @return the acegiCacheManager
   */
  public AcegiCacheManager getAcegiCacheManager() {
    return acegiCacheManager;
  }

  /**
   * @see com.systop.common.service.BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(Role role) {
    if (role == null || role.getId() == null) {
      return;
    }
    //删除与用户之间的关联关系
    Set<User> userSet = role.getUsers();
    User[] users = userSet.toArray(new User[]{});
    log.debug("Remove the relations between Role and User");
    for (int i = 0; i < users.length; i++) {
      users[i].getRoles().remove(role);
      role.getUsers().remove(users[i]);
      saveObject(users[i]);
    }
   //  删除与权限之间的关联关系
    Set<Permission> permSet = role.getPermissions();
    Permission[] perms = permSet.toArray(new Permission[]{});
    log.debug("Remove the relations between Persmission and Role");
    for (int i = 0; i < perms.length; i++) {
      perms[i].getRoles().remove(role);
      role.getPermissions().remove(perms[i]);
      saveObject(perms[i]);
    }
    //同步缓存
    if (acegiCacheManager != null) {
      acegiCacheManager.beforeRoleRemoved(role);
    }
    
    super.remove(role);
  }

 
  /**
   * @param acegiCacheManager the acegiCacheManager to set
   */
  public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
    this.acegiCacheManager = acegiCacheManager;
  }

}
