package com.systop.fsmis;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

/**
 * 食品管理常量类
 */
public final class FsConstants {

  /** 私有构造器 */
  private FsConstants() {
  }

  /** 资源文件 */
  public static final String BUNDLE_KEY = "application";

  /** 资源绑定对象 */
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_KEY);

  /** 性别常量，M-男性 */
  public static final String GENT = "M";

  /** 性别常量，F-女性 */
  public static final String LADY = "F";

  /** 性别常量Map */
  public static final Map<String, String> SEX_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    SEX_MAP.put(GENT, "男");
    SEX_MAP.put(LADY, "女");
  }

  /** Y-是 */
  public static final String Y = "1";

  /** N-否 */
  public static final String N = "0";

  /** 是否状态Map */
  public static final Map<String, String> YN_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    YN_MAP.put(Y, "是");
    YN_MAP.put(N, "否");
  }

  /** 管理员角色 */
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  
  /** 多体任务附件上传路径 */
  public static final String MULTI_TASL_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "multi_task_file_path", "/uploadFiles/multi_task/");
  
  /** 多体任务明细上传路径 */
  public static final String MT_DETAIL_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "mt_detail_file_path", "/uploadFiles/mt_detail/");
  
  /** 联合执法任务附件上传路径 */
  public static final String ALLY_TASL_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "ally_task_file_path", "/uploadFiles/ally_task/");
  
  /** 联合执法任务明细上传路径 */
  public static final String AL_DETAIL_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "al_detail_file_path", "/uploadFiles/al_detail/");
  
  /** 风险评估上传路径 */
  public static final String RISKVE_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "riskve_file_path", "/uploadFiles/risk_ev/");
  
  /** 单体任务附件路径 */
  public static final String SINGLE_TASK_ATT_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "single_task_att_path", "/uploadFiles/singtask/att/");
  
  /** 通知附件路径 */
  public static final String NOTICE_ATT_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "notice_att_path", "/uploadFiles/notice/att/");
  /** 信息员照片上传路径 */
  public static final String SUPERVISOR_PHOTOS_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "supervisor_photos_file_path", "/uploadFiles/supervisor/");
  
  /** 企业照片上传路径 */
  public static final String COMPANY_PHOTOS_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "company_photos_file_path", "/uploadFiles/company/");
  
  /** 专家照片上传路径 */
  public static final String SPECIALIST_PHOTOS_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
	  "specailist_photos_file_path", "/uploadFiles/specialist/");
  
  /** 专家照片上传路径 */
  public static final String REGION_DEPT_IDS = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
	  "region_dept_ids", "");

}