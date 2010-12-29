package com.systop.cms.catalog;

import java.util.List;

/**
 * 栏目常量表
 * @author yun
 * 
 */
public final class CatalogConstants {

  /**
   * 顶级栏目ID
   */
  public static final Integer TOP_CATALOG_ID = 0;

  /**
   * 第一个栏目的编号
   */
  public static final String FIRST_CATALOG_NO = "01";
  
  /**
   * 第一个栏目的名称
   */
  public static final String FIRST_CATALOG_NAME = "测震数据服务省级网站平台";

  /**
   * 栏目类别(内部栏目)
   */
  public static final String CATALOG_TYPE_INNER = "1";

  /**
   * 栏目类别(外部栏目)
   */
  public static final String CATALOG_TYPE_EXTERNAL = "2";
  
  /**
   * 栏目列表
   */
  @SuppressWarnings("unchecked")
  public static  List CATALOG_LIST = null;

  /**
   * private constructor
   */
  private CatalogConstants() {

  }
}
