package com.systop.common.webapp.menu;

/**
 * 根据菜单项的描述对象和权限控制对象，渲染(遵循某种规则)菜单项
 * 
 * @author Sam
 * 
 */
public interface ItemRender {
  /**
   * Base path 属性是菜单中所有URL的基础
   */
  public void setBasePath(String basePath);
  
  /**
   * 返回Url Base
   */
  public String getBasePath();
  /**
   * 根据给定的菜单项描述对象和权限控制对象渲染菜单。
   * 
   * @param item 给定的菜单项
   * @param auth 权限控制对象
   * @return 符合渲染规则的文本
   */
  public String render(MenuItem item, UrlAccessDecisionMaker auth)
      throws MenuException;
}
