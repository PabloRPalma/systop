package com.systop.common.webapp.menu;

import java.net.URL;
import java.util.List;

import org.dom4j.Element;

/**
 * 读取XML配置的菜单
 *
 */
public interface XmlMenuConfigReader {
  /**
   * 读取一个菜单项极其子项
   * @param item 菜单项的XML元素
   * @param level 菜单的级别，第一级为1，第二级为2...
   * @return 根菜单项
   */
  public MenuItem readItem(Element item, int level);
  
  /**
   * 读取所有菜单项
   * @param configFile 配置文件的位置 
   * @return 所有菜单项的iterator
   */
  public List<MenuItem> readMenu(URL configFile);
}
