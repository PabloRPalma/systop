package com.systop.common.security.user.service.impl;

import java.util.Set;

import com.systop.common.security.acegi.cache.AcegiCacheManager;
import com.systop.common.security.user.dao.ResourceDao;
import com.systop.common.security.user.exception.ReduplicateResourceException;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Resource;
import com.systop.common.security.user.service.ResourceManager;
import com.systop.common.service.BaseManager;

/**
 * Implementor of <code>ResourceManager</code>.
 * @see {@link ResourceManager}
 * @author Sam
 * 
 */
public class ResourceManagerImpl extends BaseManager<Resource> implements
    ResourceManager {
  /**
   * Data access object of <code>Resource</code>.
   */
  private ResourceDao resourceDao;

  /**
   * <code>AcegiCacheManager</code>用于同步缓存
   */
  private AcegiCacheManager acegiCacheManager;

  /**
   * @return the resourceDao
   */
  public ResourceDao getResourceDao() {
    return resourceDao;
  }

  /**
   * 设置Dao，同时设置父类的sessionFactory
   * @param resourceDao the resourceDao to set
   */
  public void setResourceDao(ResourceDao resourceDao) {
    this.resourceDao = resourceDao;
    if (getSessionFactory() == null) {
      setSessionFactory(resourceDao.getSessionFactory());
    }
  }

  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(Resource resource) {
    if (resource == null || resource.getId() == null) {
      return;
    }
    // 删除权限关联
    Set<Permission> permsSet = resource.getPermissions();
    Permission[] perms = permsSet.toArray(new Permission[] {});
    log.debug("Remove the relations between Persmission and Resource");
    for (int i = 0; i < perms.length; i++) {
      perms[i].getResources().remove(resource);
      resource.getPermissions().remove(perms[i]);
      saveObject(perms[i]);
    }

    String old = resource.getResString();
    super.remove(resource);
    if (acegiCacheManager != null) {
      acegiCacheManager.onResourceChanged(null, old);
    }
  }

  /**
   * @see BaseManager#save(java.lang.Object)
   */
  @Override
  public void save(Resource object) {
    if (object == null) {
      return;
    }

    if (isAlreadyExists(object, "resString")) {
      throw new ReduplicateResourceException(object.getResString());
    }

    String oldString = null; // 修改之前的resString
    Resource old = object;
    if (object.getId() != null) {
      old = get(object.getId());
      // 因为有Version字段，所以要复制属性
      oldString = old.getResString();
      old.setName(object.getName());
      old.setResType(object.getResType());
      old.setDescn(object.getDescn());
      old.setResString(object.getResString());
    }

    super.save(old); // 更新Resource

    if (oldString == null) {
      oldString = object.getResString();
    }

    if (acegiCacheManager != null) { // 同步缓存
      acegiCacheManager.onResourceChanged(old, oldString);
    }

  }

  /**
   * @see ResourceManager#isNameInUse(java.lang.String)
   */
  public boolean isNameInUse(String name) {
    Resource res = new Resource();
    res.setName(name);
    if (log.isDebugEnabled()) {
      log.debug("check for resource name '" + name + "'");
    }
    return exists(res, "name"); // 如果给定name不唯一，则表示正在使用
  }

  /**
   * @see ResourceManager#getByPerm(Permission)
   */
  @SuppressWarnings("unchecked")
  public Resource[] getByPerm(Permission perm) {
    Set resSet = perm.getResources();
    Resource[] retResources = new Resource[resSet.size()];
    resSet.toArray(retResources);
    return retResources;
  }

  /**
   * @return the acegeCacheManager
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
}
