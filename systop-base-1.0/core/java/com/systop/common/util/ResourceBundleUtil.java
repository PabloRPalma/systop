package com.systop.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;

/**
 * ResourceBundle的帮助类
 * @author Sam
 * 
 */
public final class ResourceBundleUtil {
  /**
   * prevent from initializing.
   */
  private ResourceBundleUtil() {

  }

  /**
   * 以List的形式返回指定的ResourceBundle的所有key
   */
  public static List getKeys(ResourceBundle rb) {
    Enumeration en = rb.getKeys();
    List list = new ArrayList();
    while (en.hasMoreElements()) {
      list.add(en.nextElement());
    }

    return list;
  }

  /**
   * 返回指定ResourceBundle的所有value
   */
  public static String[] getValues(ResourceBundle rb) {
    List keys = getKeys(rb);
    String[] strs = new String[keys.size()];
    Iterator itr = keys.iterator();
    int index = 0;
    while (itr.hasNext()) {
      strs[index] = rb.getString(itr.next().toString());
      index++;
    }

    return strs;
  }

  /**
   * 将ResourceBundle转为Map,其"key-value"对应关系不变
   */
  public static Map convert2Map(ResourceBundle rb) {
    Map map = new HashMap();

    for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
      String key = (String) keys.nextElement();
      map.put(key, rb.getString(key));
    }

    return map;
  }

  /**
   * Utility method to populate a javabean-style object with values from a
   * Properties file
   */
  public static Object populate(ResourceBundle rb, Object obj)
      throws IllegalAccessException, InvocationTargetException {
    BeanUtils.copyProperties(obj, convert2Map(rb));
    return obj;
  }

  /**
   * 返回定义在ResourceBundle中的int值
   * @param defaultValue 缺省值，如果出现任何异常，都返回该值
   * @return
   */
  public static int getInt(ResourceBundle rb, String key, int defaultValue) {
    String value = null;
    try {
      value = rb.getString(key);
    } catch (Exception e) {
      return defaultValue;
    }

    if (value == null) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * 返回定义在ResourceBundle中的String值
   * @param defaultValue 缺省值，如果出现任何异常，都返回该值
   * 
   */
  public static String getString(ResourceBundle rb, String key,
      String defaultValue) {
    String value = null;
    try {
      value = rb.getString(key);
    } catch (Exception e) {
      return defaultValue;
    }

    return (value == null) ? defaultValue : value;
  }

  /**
   * 返回定义在ResourceBundle中的boolean值
   * @param defaultValue 缺省值，如果出现任何异常，都返回该值
   * @return
   */
  public static boolean getBoolean(ResourceBundle rb, String key,
      boolean defaultValue) {
    String value = null;
    try {
      value = rb.getString(key);
    } catch (Exception e) {
      return defaultValue;
    }

    if (value == null) {
      return defaultValue;
    }

    return Boolean.valueOf(value);
  }
}
