package com.systop.common.webapp.menu.impl;

/**
 * Utils of menu
 * @author Sam
 *
 */
public final class MenuUtils {
  /**
   * private constructor
   *
   */
  private MenuUtils() {
    
  }
  
  /**
   * 根据菜单级别构建菜单中的空格
   */
  static String buildSpaces(int level) {
    StringBuffer buf = new StringBuffer();
    for (int i = 1; i <= level; i++) {
      buf.append("  ");
    }
    return buf.toString();
  }
}
