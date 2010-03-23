package com.systop.common.security;

import java.io.Serializable;

/**
 * 用于描述可以赋予的权限
 * @author Cats_Tiger
 *
 */
public interface Authority extends Serializable {
  /**
   * 返回以<code>String</code>描述的权限
   */
  public String getAuthority();
}
