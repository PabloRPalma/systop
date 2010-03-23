package com.systop.common.security;

import java.security.Principal;

/**
 * <code>Authentication</code>接口用于描述某种Principal对象的授权情况。
 * @author Cats_Tiger
 */
public interface Authentication extends Principal {
  /**
   * 返回Principal对象所具有的权限
   */
  public Authority[] getGrantedAuthorities();  
}
