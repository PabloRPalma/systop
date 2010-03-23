package com.systop.common.webapp.menu;

import java.net.URL;
import java.util.Iterator;

/**
 * 实现者应该以线程安全的方式读取位于classpath下的配置文件
 * @author Sam
 *
 */
public interface MenuConfig {
  /**
   * 设置菜单配置文件
   */
  public void setMenuConfig(String configFile);
  
  /**
   * 从URL读取配置
   */
  public void setMenuConfig(URL configResource);
  
  /**
   * 初始化菜单描述对象
   *
   */
  public void config();
  /**
   * 返回菜单的UrlAccessDecisionMaker
   * @return
   */
  public UrlAccessDecisionMaker getUrlAccessDecisionMaker();
  /**
   * 返回第一级菜单项
   */
  public Iterator<MenuItem> getMenu() throws MenuException;
  
  /**
   * 返回资源文件(图片、主题、JavaScript文件)的存放位置
   */
  public String getBase();
  
  /**
   * 返回菜单的主题名称
   */
  public String getTheme();
}
