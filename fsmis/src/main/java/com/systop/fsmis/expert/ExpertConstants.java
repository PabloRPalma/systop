package com.systop.fsmis.expert;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 专家模块常量类
 */
public final class ExpertConstants {

  /**
   * 专家级别
   */
  public static final Map<String, String> EXPERT_LEVELS = 
    Collections.synchronizedMap(new LinkedHashMap<String, String>());
  static {
  	EXPERT_LEVELS.put("省级", "省级");
  	EXPERT_LEVELS.put("市级", "市级");
  }
	
}
