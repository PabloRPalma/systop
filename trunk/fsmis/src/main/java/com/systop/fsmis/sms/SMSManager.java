package com.systop.fsmis.sms;

public interface SMSManager {
	/**
	 * 发送单条短信
	 * 
	 */
	void sendMessage();

	/**
	 * 群发短信
	 * 
	 */
	void sendMessages();

	/**
	 * 接收短信<br>
	 * 这里是接收多条短信,以兼容有可能一次接收多条短信的多种短信代理实现
	 * 
	 */
	void receiveMessages();

	/**
	 * 检查已返送短信的接收状态
	 */
	void querySmsSendState();

}
