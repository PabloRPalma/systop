package com.systop.fsmis.urgentcase;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 应急常量
 * 
 * @author yj
 * 
 */
public final class UcConstants {

	/** 事件状态：未通过 */
	public static final String CASE_STATUS_PASSED = "0";

	/** 事件状态：未派遣 */
	public static final String CASE_STATUS_UNSEND = "1";

	/** 事件状态： 已派遣 */
	public static final String CASE_STATUS_SENDDED = "2";

	/** 事件状态：已处理 */
	public static final String CASE_STATUS_RESOLVEED = "3";

	/** 事件状态：已核实完成 */
	public static final String CASE_STATUS_VERIFYED = "4";

	/** 指挥部 */
	public static final String LEADERSHIP = "Leadership";

	/** 办公室 */
	public static final String OFFICE = "Office";

	/** 事故调查处理 */
	public static final String ACCIDENT_HANDLE = "AccidentHandle";

	/** 善后处理 */
	public static final String AFTER_HANDLE = "AfterHandle";

	/** 警戒保卫 */
	public static final String DEFEND = "Defend";

	/** 技术专家 */
	public static final String EXPERT_TECHNOLOGY = "ExpertTechnology";

	/** 医疗救护 */
	public static final String MEDICAL_RESCUE = "MedicalRescue";

	/** 新闻报道 */
	public static final String NEWS_REPORT = "NewsReport";

	/** 后勤保障 */
	public static final String REAR_SERVICE = "RearService";

	/** 接待 */
	public static final String RECEIVE = "Receive";
	
	/** 应急指挥组是否是原始数据 1:是*/
	public static final String GROUP_ORIGINAL_YES = "1";
	/** 前台提交排序时使用*/
	public static final int LENGTH = 2;
	/**
	 * 组类别
	 */
	public static final Map<String, String> GROUP_CATEGORY_MAP = Collections
	    .synchronizedMap(new LinkedHashMap<String, String>());
	static {
		GROUP_CATEGORY_MAP.put(LEADERSHIP, "指挥部");
		GROUP_CATEGORY_MAP.put(OFFICE, "办公室");
		GROUP_CATEGORY_MAP.put(DEFEND, "警戒保卫");
		GROUP_CATEGORY_MAP.put(MEDICAL_RESCUE, "医疗救护");
		GROUP_CATEGORY_MAP.put(AFTER_HANDLE, "善后处理");
		GROUP_CATEGORY_MAP.put(REAR_SERVICE, "后勤保障");
		GROUP_CATEGORY_MAP.put(ACCIDENT_HANDLE, "事故调查处理");
		GROUP_CATEGORY_MAP.put(NEWS_REPORT, "新闻报道");
		GROUP_CATEGORY_MAP.put(EXPERT_TECHNOLOGY, "技术专家");
		GROUP_CATEGORY_MAP.put(RECEIVE, "接待");
	}
}
