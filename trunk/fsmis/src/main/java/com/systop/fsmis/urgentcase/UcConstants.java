package com.systop.fsmis.urgentcase;


/**
 * 应急常量
 * 
 * @author yj
 * 
 */
public final class UcConstants {
	/**  类别常量：内部  */
	public static final String INNER = "1";

	/** 类别常量：外部 */
	public static final String OUTER = "0";
	
	/** 事件状态：未通过 */
	public static final String CASE_STATUS_PASSED = "0";

	/** 事件状态：未派遣 */
	public static final String CASE_STATUS_UNSEND = "1";

	/** 事件状态： 已派遣*/
	public static final String CASE_STATUS_SENDDED = "2";

	/** 事件状态：已处理 */
	public static final String CASE_STATUS_RESOLVEED = "3";
	
	/** 事件状态：已核实完成 */
	public static final String CASE_STATUS_VERIFYED = "4";
}
