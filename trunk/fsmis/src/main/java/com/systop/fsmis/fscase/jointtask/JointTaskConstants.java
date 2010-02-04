package com.systop.fsmis.fscase.jointtask;

import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;


/*
 * 联合整治模块常量类
 */
public final class JointTaskConstants {

	/** 任务详细状态：未接收 */
	public static final String TASK_DETAIL_UN_RECEIVE = "0";

	/** 任务详细状态：已查看 */
	public static final String TASK_DETAIL_LOOK_OVERED = "1";

	/** 任务详细状态：已接收 */
	public static final String TASK_DETAIL_RECEIVEED = "2";

	/** 任务详细状态：已退回 */
	public static final String TASK_DETAIL_RETURNED = "3";

	/** 任务详细状态：已处理 */
	public static final String TASK_DETAIL_RESOLVEED = "4";
	
	/** 审核通过状态 */
	public static final String AUDITING_PASSED_STATE = "1";

	/** 审核未通过状态 */
	public static final String AUDITING_REJECT_STATE = "2";
	
	public static final String MSG_JOINT_TASK_SMSSEND = ResourceBundleUtil.getString(ResourceBundle.getBundle("application"),
			"joint_task_smsSend","您好！您有一条联合整治任务！");
	

}
