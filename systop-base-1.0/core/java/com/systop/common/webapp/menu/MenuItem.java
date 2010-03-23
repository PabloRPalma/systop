package com.systop.common.webapp.menu;

import java.util.Iterator;

/**
 * 菜单项接口
 * @author Sam
 *
 */
public interface MenuItem extends java.io.Serializable {
  /**
   * 返回菜单项的级别，第一级为1，第二级为2...
   */
  public int getLevel();

  /**
   * 返回子菜单的Iterator，如果当前菜单项不包括子菜单，则返回Empty Iterator
   */
  public Iterator<MenuItem> getChildren();
  
  /**
   * 添加一个子菜单
   */
  public void addChild(MenuItem child);
  
  /**
   * 如果当前菜单是分隔线，返回true,否则返回false
   */
  public boolean isSplit();
  
  /**
   * 返回菜单的Icon,如果没有icon，则返回null
   */
  public String getIcon();
  
  /**
   * 返回菜单的Title,如果是分隔项(不需要title)，返回null
   */
  public String getTitle();
  
  /**
   * 返回菜单的描述，如果没有则返回null
   */
  public String getDescription();
  
  /**
   * 返回菜单的URL，如果没有则返回null
   */
  public String getUrl();
  
  /**
   * 返回链接的target属性，如果没有，则返回"_self"
   */
  public String getTarget();
}
