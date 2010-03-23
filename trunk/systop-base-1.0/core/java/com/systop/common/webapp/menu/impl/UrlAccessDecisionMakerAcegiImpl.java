package com.systop.common.webapp.menu.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.systop.common.security.AccessDecisionManager;
import com.systop.common.security.exception.AccessDeniedException;
import com.systop.common.security.impl.AccessDecisionManagerAcegiImpl;
import com.systop.common.webapp.menu.UrlAccessDecisionMaker;

/**
 * 读取Acegi的权限配置，实现对URL(菜单指向的链接)的访问控制
 * @author Sam
 * 
 */
public final class UrlAccessDecisionMakerAcegiImpl
  implements UrlAccessDecisionMaker {
  /**
   * Log of the class
   */
  private static Log log = LogFactory
      .getLog(UrlAccessDecisionMakerAcegiImpl.class);
  
  /**
   * 根据Spring acegi实现的AccessDecisionManager接口
   */
  private AccessDecisionManager acegi = new AccessDecisionManagerAcegiImpl();
  
  /**
   * Default constructor
   *
   */
  public UrlAccessDecisionMakerAcegiImpl() {
    if (acegi == null) {
      acegi = new AccessDecisionManagerAcegiImpl();
    }
  }
  
  /**
   * @see UrlAccessDecisionMaker#isAccessible(String)
   */
  public boolean isAccessible(String url) {

    try {
      acegi.decide(null, url);
    } catch (AccessDeniedException e) {
      if (log.isDebugEnabled()) {
        log.debug("Menu item with url '" + url + "' can't be accessed.");
      }
      return false;
    }

    return true;
  }
  
  /**
   * @see UrlAccessDecisionMaker#setSecurityContext(Object)
   */
  public void setSecurityContext(Object context) {
    acegi.setSecurityContext(context);
  }

}
