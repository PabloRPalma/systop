package com.systop.common.webapp.menu;

/**
 * 菜单项Url访问决定器。实现者必须根据当前登录用户的权限设置判断菜单项所指向的URL
 * 是否可以被该用户访问。
 * @author Sam Lee
 *
 */
public interface UrlAccessDecisionMaker {
  
  /**
   * 如果指定的Url可以被访问，返回true，否则返回false
   * @param url 指定的URL
   */
  public boolean isAccessible(String url);
  
  /**
   * 设置安全配置上下文,可以是Spring\ServletContext之类的DD
   * @param context
   */
  public void setSecurityContext(Object context);
}
