package com.systop.common.util;

import javax.servlet.ServletContext;

/**
 * ServletContext的工具类
 * @author Sam
 * 
 */
public final class ServletContextUtil {
  /**
   * 阻止实例化
   */
  private ServletContextUtil() {
  }

  /**
   * 从InitParameter中取得一个String
   * @param ctx 给定的ServletContext
   * @param name InitParameter名字
   * @param defaultVal 缺省值，如果值InitParameter为null,则返回defaultVal
   */
  public static String getInitParam(ServletContext ctx, String name,
      String defaultVal) {
    String val = ctx.getInitParameter(name);
    return (val == null) ? defaultVal : val;
  }

  /**
   * 从InitParameter中取得一个boolean
   * @see Boolean#parseBoolean(String)
   */
  public static boolean getInitParam(ServletContext ctx, String name,
      boolean defaultVal) {
    String val = ctx.getInitParameter(name);

    if (val == null) {
      return defaultVal;
    }
    return Boolean.parseBoolean(val);
  }

  /**
   * 从InitParameter中取得一个int
   * @see Integer#parseInt(String)
   */
  public static int getInitParam(ServletContext ctx, String name, 
      int defaultVal) {
    String val = ctx.getInitParameter(name);

    if (val == null) {
      return defaultVal;
    }
    try {
      return Integer.parseInt(val);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }

  /**
   * 从InitParameter中取得一个long
   * @see Long#parseLong(String)
   */
  public static long getInitParam(ServletContext ctx, String name,
      long defaultVal) {
    String val = ctx.getInitParameter(name);

    if (val == null) {
      return defaultVal;
    }
    try {
      return Long.parseLong(val);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }
  
}
