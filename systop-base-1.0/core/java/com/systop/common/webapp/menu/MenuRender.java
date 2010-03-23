package com.systop.common.webapp.menu;

import java.util.Iterator;
/**
 * 菜单渲染接口。
 * @author Administrator
 *
 */
public interface MenuRender {
  /**
   *设置菜单项的基础URL 
   */
  public void setBase(String base);
  
  /**
   * 设置渲染主题
   */
  public void setTheme(String theme);
  
  
  /**
   * 设置权限接口的实现
   */
  public void setAuth(UrlAccessDecisionMaker auth);

  /**
   * 设置菜单描述对象
   */
  public void setMenu(Iterator<MenuItem> menu);

  /**
   * 设置菜单的显示方向
   */
  public void setMenuAlign(String align);
  
  /**
   * 渲染菜单
   */
  public String render() throws MenuException;
  /**
   * 设置菜单名称
   */
  public void setMenuName(String menuName);
}
