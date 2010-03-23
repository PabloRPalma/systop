package com.systop.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * ServletRequest的帮助类
 * 
 * @author Sam
 * 
 */
public final class RequestUtil {
  /**
   * prevent from initializing
   */
  private RequestUtil() {

  }

  /**
   * 得到ServletContext的basepath
   * 
   * @return 例如http://local:8080/xx
   */
  public static String getBasePath(HttpServletRequest request) {
    String path = request.getContextPath();
    String basePath = new StringBuffer(request.getScheme()).append("://")
        .append(request.getServerName()).append(":").append(
            request.getServerPort()).append(path).append("/").toString();

    return basePath;
  }

  /**
   * 从Request中取得一个Value
   */
  public static Object getValue(HttpServletRequest request, String name,
      Object defaultValue) {
    Object obj = null;

    if ((obj = request.getParameter(name)) != null) {
      return obj;
    }

    if ((obj = request.getAttribute(name)) != null) {
      return obj;
    }

    if ((obj = request.getSession().getAttribute(name)) != null) {
      return obj;
    }

    return defaultValue;
  }

  /**
   * 删除Request中的某一个数据
   * 
   */
  public static void removeValue(HttpServletRequest request, String name) {
    if (request.getAttribute(name) != null) {
      request.removeAttribute(name);
    }

    if (request.getSession().getAttribute(name) != null) {
      request.getSession().removeAttribute(name);
    }
  }

  /**
   * 取得Request中的一个对象的属性。
   * 
   * @param name * 例如：User.name
   */
  public static Object getAttributeNested(HttpServletRequest request,
      String name) {
    if (StringUtils.isBlank(name) || request == null) {
      return null;
    }
    Object object = null;

    String[] names = name.split("\\.");
    if (names.length > 0) {
      object = request.getAttribute(names[0]);
      if (object == null) {
        object = request.getSession().getAttribute(names[0]);
      }
      if (object == null) {
        return null;
      }
    }

    for (int i = 1; i < names.length; i++) {
      object = ReflectUtil.get(object, names[i]);
    }

    return object;
  }

  /**
   * 取得PageContext中的一个对象的属性。
   * 
   * @param name 例如：User.name
   */
  public static Object getAttributeNested(PageContext pageContext, 
      String name) {
    if (StringUtils.isBlank(name) || pageContext == null) {
      return null;
    }
    Object object = null;

    String[] names = name.split("\\.");
    if (names.length > 0) {
      object = pageContext.getAttribute(names[0]);
      if (object == null) {
        object = getAttributeNested((HttpServletRequest) pageContext
            .getRequest(), name);
      }

      if (object == null) {
        return null;
      }
    }

    for (int i = 1; i < names.length; i++) {
      object = ReflectUtil.get(object, names[i]);
    }

    return object;
  }

  /**
   * 从指定的HttpServletRequest对象中取得一个parameter
   * @param request 指定的HttpServletRequest对象
   * @param name parameter的名字
   * @param defaultVal 缺省值
   */
  public static String getParam(HttpServletRequest request, String name,
      String defaultVal) {
    String val = request.getParameter(name);
    return (val == null) ? defaultVal : val;
  }
  
  /**
   * 从指定的HttpServletRequest对象中取得一个parameter,并转化为boolean
   * @param request 指定的HttpServletRequest对象
   * @param name parameter的名字
   * @param defaultVal 缺省值
   */
  public static boolean getParam(HttpServletRequest request, String name,
      boolean defaultVal) {
    String val = request.getParameter(name);
    
    return (val == null) ? defaultVal : Boolean.parseBoolean(val);
  }
  
  /**
   * 从指定的HttpServletRequest对象中取得一个parameter,并转化为int
   * @param request 指定的HttpServletRequest对象
   * @param name parameter的名字
   * @param defaultVal 缺省值
   */
  public static int getParam(HttpServletRequest request, String name,
      int defaultVal) {
    String val = request.getParameter(name);
    
    try {
      return Integer.parseInt(val);
    } catch (Exception e) {
      return defaultVal;
    }
  }
  
  /**
   * 从指定的HttpServletRequest对象中取得一个parameter,并转化为long
   * @param request 指定的HttpServletRequest对象
   * @param name parameter的名字
   * @param defaultVal 缺省值
   */
  public static long getParam(HttpServletRequest request, String name,
      long defaultVal) {
    String val = request.getParameter(name);
    
    try {
      return Long.parseLong(val);
    } catch (Exception e) {
      return defaultVal;
    }
  }
}
