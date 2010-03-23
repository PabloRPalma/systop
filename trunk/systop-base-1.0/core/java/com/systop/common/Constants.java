package com.systop.common;

import java.util.ResourceBundle;

import com.systop.common.util.ResourceBundleUtil;

/**
 * 系统常量类.
 * @author  Sam
 *
 */
public final class Constants {
  /**
   * Prevent from initializing.
   */
  private Constants() {
  }
  
  /**
   * 字符串表示的true
   */
  public static final String CHAR_TRUE = "Y";
  
  /**
   * 字符串表示的false
   */
  public static final String CHAR_FALSE = "N";
  
  /**
   * 资源文件.
   */
  public static final String BUNDLE_KEY = "application";
  
  /**
   * 资源绑定对象
   */
  public static final ResourceBundle RESOURCE_BUNDLE = 
    ResourceBundle.getBundle(BUNDLE_KEY);
  
  
  /**
   * 错误信息文件.
   */
  public static final String ERROR_BUNDLE_KEY = "errors";
  
  /**
   * 缺省的分页容量
   */
  public static final int DEFAULT_PAGE_SIZE = 
    ResourceBundleUtil.getInt(RESOURCE_BUNDLE, "default.pagesize", 20);
  
  /**
   * 第一页的页码,缺省是0
   */
  public static final int DEFAULT_FIRST_PAGE_NO = 
    ResourceBundleUtil.getInt(RESOURCE_BUNDLE, "defalut.firstPageNo", 0);
  /**
   * Freemark模板中dataModel的名字
   */
  public static final String DEFAULT_FREEMARKER_DATAMODEL_NAME = 
    ResourceBundleUtil.getString(RESOURCE_BUNDLE,
        "global.freemarker.dataModelName", "data");
  /**
   * 缺省的模板目录名称
   */
  public static final String DEFAULT_TEMPLATE_DIR = 
    ResourceBundleUtil.getString(RESOURCE_BUNDLE,
        "global.templates.defaultDir", "classpath:templates");
  /**
   * 缺省的模板主题
   */
  public static final String DEFAULT_TEMPLATE_THEME = 
    ResourceBundleUtil.getString(RESOURCE_BUNDLE,
        "global.freemarker.defaultTheme", "simple");
  /**
   * 模板中，用于列表的数据的名字
   */
  public static final String DEFAULT_LIST_NAME = 
    ResourceBundleUtil.getString(RESOURCE_BUNDLE,
        "global.freemarker.listName", "list");
  /**
   * 状态可用
   */
  public static final String STATUS_AVAILABLE =
    ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "global.available", "1");
  /**
   * 状态不可用
   */
  public static final String STATUS_UNAVAILABLE = 
    ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "global.unavailable", "0");
  /**
   * 用户信息在Session中的名字
   */
  public static final String USER_IN_SESSION = "userInSession";
  /**
   * captcha 显示的字符
   */
  public static final String CAPTCHA_STRING = 
    ResourceBundleUtil.getString(RESOURCE_BUNDLE,
        "captcha.string", "023456789");
  /**
   * captcha 最多显示多少个
   */
  public static final int CAPTCHA_MAX_WORDS = 
    ResourceBundleUtil.getInt(RESOURCE_BUNDLE, "captcha.maxWords", 5);
  /**
   * captcha 最少显示多少个
   */
  public static final int CAPTCHA_MIN_WORDS = 
    ResourceBundleUtil.getInt(RESOURCE_BUNDLE, "captcha.minWords", 4);
  /**
   * captcha 图片宽度
   */
  public static final int CAPTCHA_IMG_WIDTH = 
    ResourceBundleUtil.getInt(RESOURCE_BUNDLE, "captcha.imgWidth", 100);
  /**
   * captcha 图片高度
   */
  public static final int CAPTCHA_IMG_HEIGHT = 
    ResourceBundleUtil.getInt(RESOURCE_BUNDLE, "captcha.imgHeight", 40);
  /**
   * captcha 最大字体
   */
  public static final int CAPTCHA_MAX_FONT_SIZE = 
    ResourceBundleUtil.getInt(RESOURCE_BUNDLE, "captcha.maxFontSize", 22);
  /**
   * captcha 最小字体
   */
  public static final int CAPTCHA_MIN_FONT_SIZE = 
    ResourceBundleUtil.getInt(RESOURCE_BUNDLE, "captcha.minFontSize", 20);
}
