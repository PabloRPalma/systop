package com.systop.common.security.user.service;

import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Resource;
import com.systop.common.service.Manager;

/**
 * Manager of <code>Resource</code>
 * @author Sam
 */
public interface ResourceManager extends Manager<Resource> {
 
  /**
   * 查找权限所拥有的资源
   * @param perm 权限对像
   * @return 权限下的资源
   */
  Resource[] getByPerm(Permission perm);

  /**
   * 检查资源名是否重复，如果重复，返回true
   * @param name
   * @return
   */
  boolean isNameInUse(String name);
  
}
