package com.systop.fsmis.sms.smproxy;

import java.util.List;

import com.systop.core.ApplicationException;
import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.model.SmsSend;

/**
 * 短信服务代理接口<br>
 * 定义了本接口实现类所必须实现的发送/接收短信方法<br>
 * 实现类必须根据具体的短信服务方式来实现短信的发送和接收方法.
 * 
 * @author WorkShopers
 * 
 */
public interface SmsProxy {
	

	/**
	 * 发送单个短信方法
	 * 
	 * @param smsSend
	 * @return
	 */
	int sendMessage(SmsSend smsSend) throws ApplicationException;

	/**
	 * 群发短信方法
	 * 
	 * @param smsSendList
	 * @return
	 */
	int sendMessage(List<SmsSend> smsSendList) throws ApplicationException;

	/**
	 * 接收短信方法
	 * 
	 * @return
	 */

	List<SmsReceive> receiveMessage() throws ApplicationException;

	/**
	 * 检查已返送短信的接收状态
	 */
	List<String> querySmsSendState() throws ApplicationException;

}
