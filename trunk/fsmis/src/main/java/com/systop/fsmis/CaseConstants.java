package com.systop.fsmis;

/*
 * 事件常量类
 */
public final class CaseConstants {

	/** 事件状态：未派遣 */
	public static final String CASE_STATUS_RESOLVEUN = "0";

	/** 事件状态：已派遣 */
	public static final String CASE_STATUS_RESOLVEING = "1";

	/** 事件状态：已处理 */
	public static final String CASE_STATUS_RESOLVEED = "2";
	
	/** 事件状态：回退 */
	public static final String CASE_STATUS_RETURNED = "3";

	/** 事件状态：已核实完成 */
	public static final String CASE_STATUS_VERIFYED = "4";
	
	/** 事件状态：忽略 */
	public static final String CASE_STATUS_PASSED = "5";

	/** 任务是否牵头部门状态：是 */
	public static final String IS_DEPT_LEADER = "1";

	/** 任务是否牵头部门状态：否 */
	public static final String NO_DEPT_LEADER = "0";

	/** 任务详细状态：未接收 */
	public static final String TASK_DETAIL_UN_RECEIVE = "0";

	/** 任务详细状态：已查看 */
	public static final String TASK_DETAIL_LOOK_OVERED = "1";

	/** 任务详细状态：已接收 */
	public static final String TASK_DETAIL_RECEIVEED = "2";

	/**  任务详细状态：已退回  */
	public static final String TASK_DETAIL_RETURNED = "3";

	/** 任务详细状态：已处理 */
	public static final String TASK_DETAIL_RESOLVEED = "4";
	
	/** 任务状态：未接收 */
	public static final String TASK_STATUS_UN_RECEIVE = "0";
	
	/** 任务状态：已派遣 */
	public static final String TASK_STATUS_RESOLVEING = "1";
	
	/** 任务状态：已处理 */
	public static final String TASK_STATUS_RESOLVEED = "2";
	
	/**  任务状态：已退回  */
	public static final String TASK_STATUS_RETURNED = "3";

	/** 发送通知内容 */
	public static final String SEND_CONTENT = "请核实事件";

	/** 发送核实短信状态1：发送  */
	public static final String SENDED = "1";
	/** 网站举报事件状态1：最新  */
	public static final String IS_NEW = "1";
	/** 网站举报事件状态0：已查看  */
	public static final String IS_OLD = "0";
	/** 事件是否上报市级 状态0：未上报  */
	public static final String IS_NOSUBSJ = "0";
	/** 事件是否上报市级 状态1：已上报  */
	public static final String IS_SUBSJ = "1";
	
}
