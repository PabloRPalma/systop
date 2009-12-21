package com.systop.fsmis.sms;

import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

/**
 * 短信管理常量类
 * 
 * @author WorkShopers
 * 
 */
public final class SMSConstants {
	/** 资源文件 */
	public static final String BUNDLE_KEY = "sms";

	/** 资源绑定对象 */
	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_KEY);
	/** 短信发送功能中一次从数据库中提取要发送短信的记录数 */
	public static final int SMS_SMS_SEND_COUNT = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "sms.SmsSend.sendCount", 20);
	/** 新短信 */
	public static final String SMS_SMS_SEND_IS_NEW = "1";
	/** 不是新短信 */
	public static final String SMS_SMS_SEND_IS_NOT_NEW = "0";

	/** WebService是否可用 */
	public static boolean IS_ENABLE = true;

	/** 服务启动异常原因 */
	public static String EXCEPTION_MSG = null;

	public final static String Y = "1";

	public final static String N = "0";

	/** 举报短信头 */
	public final static String QUOTE_STRING = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "sms.SmsReceive.quoteString", "1,A,a");

	/** 反馈短信头 */
	public final static String FEEDBACK_STRING = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "sms.SmsReceive.feedbackString", "2,B,b");

	/** 连接用户名 */
	public final static String CONN_NAME = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "sms.websconnName", "admin");

	/** 连接扩密码 */
	public final static String CONN_PASS = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "sms.websconnPass", "100258");

	/** 接收码 */
	public final static String DEST_ADDR = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "sms.websDestAddr", "106573113888");

	/** 连接URL */
	public final static String CONN_URL = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "sms.websconnUrl",
			"http://61.233.42.4:8080/masproxy/services/IfSMSService?WSDL");
}
