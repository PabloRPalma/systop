package com.systop.cms.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * CMS的帮助类，抽象一些小功能
 * 
 * @author Lunch
 */
public final class CmsUtil {

  /**
   * 得到ServletContext的basepath
   * 
   * @return 例如http://local:8080/xx
   */
  public static String getBasePath(HttpServletRequest request) {
    String path = request.getContextPath();
    String basePath = new StringBuffer(request.getScheme()).append("://").append(
        request.getServerName()).append(":").append(request.getServerPort()).append(path).append(
        "/").toString();

    return basePath;
  }

}
