package com.systop.common.webapp.upload;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HttpServletRequest Wrapper类，用于转化Multipart的Request
 * @author Sam
 * 
 */
public class PlainHttpServletRequest extends HttpServletRequestWrapper {
  /**
   * 用于存放Parameters的Map
   */
  private Map params;
  
  /**
   * 构造器
   */
  public PlainHttpServletRequest(HttpServletRequest request) {
    super(request);
    params = new HashMap();
  }

  /**
   * 添加一个Param参数
   * @param name
   * @param value
   */
  public void addParameter(String name, String value) {
    params.put(name, value);
  }

  /**
   * 添加一个Param参数
   * @param name
   * @param value
   */
  public void addParameters(String name, String[] value) {
    params.put(name, value);
  }

  /**
   * @see ServletRequestWrapper#getParameter(java.lang.String)
   */
  @Override
  public String getParameter(String name) {
    Object value = params.get(name);
    return (value == null) ? null : value.toString();
  }

  /**
   * @see ServletRequestWrapper#getParameterMap()
   */
  @Override
  public Map getParameterMap() {
    return params;
  }

  /**
   * @see ServletRequestWrapper#getParameterNames()
   */
  @Override
  public Enumeration getParameterNames() {
    Set<String> set = params.keySet();
    Vector v = new Vector();
    for (String key : set) {
      v.add(key);
    }
    return v.elements();
  }

  /**
   * @see ServletRequestWrapper#getParameterValues(java.lang.String)
   */
  @Override
  public String[] getParameterValues(String name) {
    Object obj = params.get(name);
    if (obj == null) {
      return null;
    }

    if (obj.getClass().isArray()) {
      return (String[]) obj;
    } else {
      return new String[] { obj.toString() };
    }
  }

  /**
   *
   * @see javax.servlet.ServletRequestWrapper#getContentType()
   */
  @Override
  public String getContentType() {
    return "text/html";
  }

}
