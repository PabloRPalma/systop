package com.systop.fsmis.expert;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 专家模块常量类
 */
public final class ExpertConstants {

	public static final String EXPERT_LEVEL_PROVINCIAL = "1";//省级
	
	public static final String EXPERT_LEVEL_MUNICIPAL = "2";//市级

  /**
   * 专家级别
   */
  public static final Map<String, String> EXPERT_LEVELS = 
    Collections.synchronizedMap(new LinkedHashMap<String, String>());
  static {
  	EXPERT_LEVELS.put(EXPERT_LEVEL_PROVINCIAL, "省级");
  	EXPERT_LEVELS.put(EXPERT_LEVEL_MUNICIPAL, "市级");
  }
	
}
