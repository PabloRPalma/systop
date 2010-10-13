package com.systop.cms.template;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模板常量类
 * @author Bin
 */
public final class TemConstants {
  /**
   * 私有构造器，防止常量类被实例化
   */
  private TemConstants() {
    
  }
  
  /**
   * 0-栏目模板
   */
  public static final String TEMPLATE_CATALOG = "0";

  /**
   * 1-文章模板
   */
  public static final String TEMPLATE_ARTICLE = "1";

  /**
   * 2-主页模板
   */
  public static final String TEMPLATE_INDEX = "2";
  
  /**
   * 3-公共模板
   */
  public static final String TEMPLATE_PUBLIC = "3";

  /**
   * 模板类型列表
   */
  public static final Map<String, String> TEMPLATE_TYPE_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    TEMPLATE_TYPE_MAP.put(TEMPLATE_INDEX, "首页模板");
    TEMPLATE_TYPE_MAP.put(TEMPLATE_CATALOG, "栏目模板");
    TEMPLATE_TYPE_MAP.put(TEMPLATE_ARTICLE, "文章模板");
    TEMPLATE_TYPE_MAP.put(TEMPLATE_PUBLIC, "公共模板");
  }
}
