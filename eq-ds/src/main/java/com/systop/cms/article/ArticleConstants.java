package com.systop.cms.article;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 文章常量
 * @author zhangwei
 */
public final class ArticleConstants {
  
  /**
   * 资源文件.
   */
  public static final String BUNDLE_KEY = "application";
  
  /**
   * 资源绑定对象
   */
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle(BUNDLE_KEY);
  
  /**
   * 私有构造器，防止常量类被实例化
   */
  private ArticleConstants() {
    
  }
  /**
   * 审核是否通过常量：0－待审批
   */
  public static final String WAITCHECK = "0";

  /**
   * 审核是否通过常量：1-审核通过
   */
  public static final String PASS = "1";

  /**
   * 审核常量Map
   */
  public static final Map<String, String> STATE_PASS_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    STATE_PASS_MAP.put(WAITCHECK, "待审核");
    STATE_PASS_MAP.put(PASS, "审核通过");
  }
  
}
