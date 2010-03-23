package com.systop.common.security.user.service.impl;

import java.util.Set;

import com.systop.common.security.acegi.cache.AcegiCacheManager;
import com.systop.common.security.user.dao.PermissionDao;
import com.systop.common.security.user.exception
  .PermissionAlreadyExistsException;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Resource;
import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.service.PermissionManager;
import com.systop.common.service.BaseManager;

/**
 * Implementor of <code>PermissionManager</code>.
 * @see {@link PermissionManager}
 * @author han
 *
 */
public class PermissionManagerImpl 
    extends BaseManager<Permission> implements PermissionManager {
  /** 权限Dao,此属性需要Spring初始化 */
  private PermissionDao permDao;
  /**
   * 用于同步缓存
   */
  private AcegiCacheManager acegiCacheManager;

  public PermissionDao getPermDao() {
    return permDao;
  }

  /**
   * 设置Dao，同时设置父类的sessionFactory
   * @param permDao the permissionDao to set
   */
  public void setPermDao(PermissionDao permDao) {
    this.permDao = permDao;
    if (getSessionFactory() == null) {
      setSessionFactory(permDao.getSessionFactory());
    }
  }

  /**
   * @see PermissionManager#getByRole(Role)
   */
  @SuppressWarnings("unchecked")
  public Permission[] getByRole(Role role) {
    Set permSet = role.getPermissions();
    Permission[] retPerms = new Permission[permSet.size()];
    permSet.toArray(retPerms);
    return retPerms;
  }

  /**
   * @see PermissionManager#isNameInUse(java.lang.String)
   */
  public boolean isNameInUse(String name) {
    Permission perm = new Permission();
    perm.setName(name);
    if (log.isDebugEnabled()) {
      log.debug("check for permission name '" + name + "'");
    }
    return exists(perm, "name"); // 如果给定name不唯一，则表示正在使用
  }

  /**
   * @see PermissionManager#savePerm(Permission)
   */
  @Override
  public void save(Permission perm) {
    if (perm == null) {
      return;
    }
    if (!exists(perm, "name")) {
      super.save(perm);
      if (acegiCacheManager != null) {
        acegiCacheManager.onPermissionChanged(perm);
      }
    } else {
      throw new PermissionAlreadyExistsException(perm.getName());
    }
    
  }

  /**
   * @return the acegiCacheManager
   */
  public AcegiCacheManager getAcegiCacheManager() {
    return acegiCacheManager;
  }

  /**
   * @param acegiCacheManager the acegiCacheManager to set
   */
  public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
    this.acegiCacheManager = acegiCacheManager;
  }

  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(Permission perm) {
    if (perm == null || perm.getId() == null) {
      return;
    }
    // 删除与资源的关联关系
    Set<Resource> resSet = perm.getResources();
    Resource[] resources = resSet.toArray(new Resource[] {});
    log.debug("Remove the relations between Persmission and Resource");
    for (int i = 0; i < resources.length; i++) {
      resources[i].getPermissions().remove(perm);
      perm.getResources().remove(resources[i]);
      saveObject(resources[i]);
    }
    // 删除与角色的关联关系
    Set<Role> roleSet = perm.getRoles();
    Role[] roles = roleSet.toArray(new Role[] {});
    log.debug("Remove the relations between Persmission and Roles");
    for (int i = 0; i < roles.length; i++) {
      roles[i].getPermissions().remove(perm);
      perm.getRoles().remove(roles[i]);
      saveObject(roles[i]);
    }
    // 同步缓存
    if (acegiCacheManager != null) {
      acegiCacheManager.beforePermissionRemoved(perm);
    }    
    
    super.remove(perm);
  }

  
}
