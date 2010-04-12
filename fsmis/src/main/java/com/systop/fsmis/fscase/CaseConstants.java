package com.systop.fsmis.fscase;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

/**
 * 事件常量类
 */
public final class CaseConstants {
	
   /** 资源文件 */
   private static final String BUNDLE_KEY = "application";

  /** 资源绑定对象 */
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_KEY);

  /** 事件状态：未派遣 */
  public static final String CASE_UN_RESOLVE = "0";

  /** 事件状态：已派遣 */
  public static final String CASE_PROCESSING = "1";

  /** 事件状态：已处理 */
  public static final String CASE_PROCESSED = "2";

  /** 事件状态：退回 */
  public static final String CASE_RETURNED = "3";

  /** 事件状态：已核实结案 */
  public static final String CASE_CLOSED = "4";

  /** 事件状态：忽略 */
  public static final String CASE_IGNORED = "5";
  
  /** 事件状态：核实不通过 */
  public static final String CASE_UN_CLOSED = "6";

  /** 市级 */
  public static final String CITY = "1";

  /** 区、县级 */
  public static final String COUNTY = "2";
  
  /** 事件处理类型:任务派遣 */
  public static final String PROCESS_TYPE_TASK = "task"; 
  /** 事件处理类型:联合整治 */
  public static final String PROCESS_TYPE_JOIN_TASK = "jointask";
  
  /** 事件添加类别:普通添加单体事件 */
  public static final String CASE_SOURCE_TYPE_GENERIC = "generic";
  /** 事件添加类别:联合整治添加单体事件 */
  public static final String CASE_SOURCE_TYPE_JOINTASK = "jointask";
  /** 事件添加类别:部门上报添加单体事件 */
  public static final String CASE_SOURCE_TYPE_DEPTREPORT = "deptreport";
  /** 事件添加类别:区县上报添加单体事件 */
  public static final String CASE_SOURCE_TYPE_DISTRICT = "district";
  
  /** 市级汇总生成的多体事件标题名称 */
  public static final String CITY_MULTCASE_NAME = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "city_multcase_name", "");
  
  /** 区县级汇总生成的多体事件标题名称*/
  public static final String COUNTY_MULTCASE_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "county_multcase_name", "");
  
  /**
   * 配置类别
   */
  public static final Map<String, String> CONFIGER_CATEGORY_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    CONFIGER_CATEGORY_MAP.put(CITY, "市级");
    CONFIGER_CATEGORY_MAP.put(COUNTY, "区县级");
  }
  
  /** 事件状态常量Map ：带颜色 */
  public static final Map<String, String> CASE_COLOR_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
	  CASE_COLOR_MAP.put(CASE_UN_RESOLVE, "<font color='red'>未派遣</font>");
	  CASE_COLOR_MAP.put(CASE_PROCESSING, "<font color='#FF9D07'>已派遣</font>");
	  CASE_COLOR_MAP.put(CASE_PROCESSED, "<font color='green'>已处理</font>");
	  CASE_COLOR_MAP.put(CASE_RETURNED, "<font color='gray'>退回</font>");
	  CASE_COLOR_MAP.put(CASE_CLOSED, "<font color='blue'>已核实</font>");
	  CASE_COLOR_MAP.put(CASE_IGNORED, "<font color='gray'>忽略</font>");
	  CASE_COLOR_MAP.put(CASE_UN_CLOSED, "<font color='#18CADA'>核实不过</font>");
  }

  /** 事件状态常量Map ：不带颜色 */
  public static final Map<String, String> CASE_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
	  CASE_MAP.put(CASE_UN_RESOLVE, "未派遣");
	  CASE_MAP.put(CASE_PROCESSING, "已派遣");
	  CASE_MAP.put(CASE_PROCESSED, "已处理");
	  CASE_MAP.put(CASE_RETURNED, "退回");
	  CASE_MAP.put(CASE_CLOSED, "已核实");
	  CASE_MAP.put(CASE_IGNORED, "忽略");
	  CASE_MAP.put(CASE_UN_CLOSED, "核实不过");
  }
  /** 事件来源常量Map */
  public static final Map<String, String> CASE_SOURCE_TYPE = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
  	CASE_SOURCE_TYPE.put(CASE_SOURCE_TYPE_GENERIC, "普通事件");
  	CASE_SOURCE_TYPE.put(CASE_SOURCE_TYPE_JOINTASK, "联合整治事件");
  	CASE_SOURCE_TYPE.put(CASE_SOURCE_TYPE_DEPTREPORT, "部门上报事件");
	  
  }
}
