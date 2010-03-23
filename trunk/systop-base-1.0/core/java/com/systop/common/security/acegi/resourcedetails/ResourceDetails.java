package com.systop.common.security.acegi.resourcedetails;

import java.io.Serializable;

import org.acegisecurity.GrantedAuthority;

/**
 * 提供资源信息
 *
 * @author cac
 */
public interface ResourceDetails extends Serializable {
  /**
   * 函数资源
   */
  public static final String RES_TYPE_FUNCTION = "1";
  /**
   * URL资源
   */
  public static final String RES_TYPE_URL = "0";
	/**
	 * 资源串
	 */
	String getResString();

	/**
	 * 资源类型,如<code>RES_TYPE_FUNCTION</code>,<code>RES_TYPE_URL</code>
   * @see RES_TYPE_FUNCTION
   * @see RES_TYPE_URL
	 */
	String getResType();

	/**
	 * 返回属于该resource的authorities
	 */
	GrantedAuthority[] getAuthorities();
}
