package com.systop.fsmis.fscase;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 事件常量类
 */
public final class CaseConstants {

  /** 事件状态：未派遣 */
  public static final String CASE_UN_RESOLVE = "0";

  /** 事件状态：处理中 */
  public static final String CASE_PROCESSING = "1";

  /** 事件状态：已处理 */
  public static final String CASE_PROCESSED = "2";

  /** 事件状态：退回 */
  public static final String CASE_RETURNED = "3";

  /** 事件状态：已核实结案 */
  public static final String CASE_CLOSED = "4";

  /** 事件状态：忽略 */
  public static final String CASE_IGNORED = "5";

  /** 市级 */
  public static final String CITY = "1";

  /** 区、县级 */
  public static final String COUNTY = "2";
  
  /** 事件处理类型:任务派遣 */
  public static final String PROCESS_TYPE_TASK = "0"; 
  /** 事件处理类型:联合整治 */
  public static final String PROCESS_TYPE_JOIN_TASK = "1";
  
  /** 事件添加类别:普通添加单体事件 */
  public static final String CASE_SOURCE_TYPE_GENERIC = "0";
  /** 事件添加类别:联合整治添加单体事件 */
  public static final String CASE_SOURCE_TYPE_JOINTASK = "1";
  /** 事件添加类别:部门上报添加单体事件 */
  public static final String CASE_SOURCE_TYPE_DEPTREPORT = "2";
  /**
   * 配置类别
   */
  public static final Map<String, String> CONFIGER_CATEGORY_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    CONFIGER_CATEGORY_MAP.put(CITY, "市级");
    CONFIGER_CATEGORY_MAP.put(COUNTY, "区县级");
  }

}
