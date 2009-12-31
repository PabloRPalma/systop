package com.systop.fsmis.assessment;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 风险评估模块常量类
 */
public final class AssessMentConstants {
	
	public static final String EXPERT_LEADER = "1";
	
	public static final String EXPERT_MEMBER = "2";

	/** AUDITING_WAITING_STATE → "0" 评估申请状态 */
	public static final String AUDITING_WAITING = "0";

	/** AUDITING_PASSED_STATE → "1" 审核通过状态 */
	public static final String AUDITING_PASSED_STATE = "1";

	/** AUDITING_REJECT_STATE → "2" 审核未通过状态 */
	public static final String AUDITING_REJECT_STATE = "2";

	/** EVAL_IS_START_STATE → "3" 评估启动状态 */
	public static final String EVAL_IS_START_STATE = "3";
	
	/** EVAL_IS_OVER_STATE → "3" 评估完毕状态 */
	public static final String EVAL_IS_OVER_STATE = "4";

	public static final Map<String, String> ASSESSMENT_STATE = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		ASSESSMENT_STATE.put(AUDITING_WAITING, "待审核");
		ASSESSMENT_STATE.put(AUDITING_PASSED_STATE, "审核通过");
		ASSESSMENT_STATE.put(AUDITING_REJECT_STATE, "审核未通过");
		ASSESSMENT_STATE.put(EVAL_IS_OVER_STATE, "评估完毕");
	}

}
