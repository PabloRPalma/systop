package com.systop.fsmis.assessment;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

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

	public static final Map<String, String> ASSESSMENT_LEVEL = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		ASSESSMENT_LEVEL.put("一般", "一般");
		ASSESSMENT_LEVEL.put("较大", "较大");
		ASSESSMENT_LEVEL.put("重大", "重大");
		ASSESSMENT_LEVEL.put("特大", "特大");
	}

	/** UPLOAD_ALLOWED_FILE_TYPES:允许上传的文件类型 **/
	public static final String[] UPLOAD_ALLOWED_FILE_TYPES = new String[]{"doc","docx"};
	
	/** UPLOAD_ALLOWED_FILE_SIZE 允许上传的文件大小，默认10MB*/
	public static final long UPLOAD_ALLOWED_FILE_SIZE = getAllowedFileSize();
	
	/** MSG_RISK_EVAL :	向专家组成员发送的风险评估任务短消息，消息格式为：{0}专家，您好！您有一条风险评估任务！*/
	public static final String MSG_EXPERT_SMSSEND = ResourceBundleUtil.getString(ResourceBundle.getBundle("application"),
			"assessment_expert_smsSend","专家，您好！您有一条风险评估任务！");
	
	/**
	 * 获得风险评估结果附件允许上传的最大文件大小,默认10MB
	 * @return 允许上传的风险评估附件的大小	long
	 */
	private static final long getAllowedFileSize() {
		String sizeStr = ResourceBundleUtil.getString(ResourceBundle.getBundle("application"),
				"assessment_file_max_size", "10240000");
		long size = 0L;
		try{
			size = Long.parseLong(sizeStr);
		}catch(Exception e){
			size = 10240000;
		}
		return size;
	}
}
