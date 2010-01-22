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
	 * 
	 * 发送单个短信方法
	 * 
	 * @param smsSend
	 * @return
	 * @throws ApplicationException
	 */
	Integer sendMessage(SmsSend smsSend) throws Exception;

	/**
	 * 
	 * 群发短信方法
	 * 
	 * @param smsSendList
	 *            要发送的短信实体集合
	 * @return 返回短信在短信服务端的id
	 * @throws ApplicationException
	 */
	Integer sendMessage(List<SmsSend> smsSendList) throws Exception;

	/**
	 * 接收短信方法
	 * 
	 * @return 得到的短信实体List集合
	 * @throws ApplicationException
	 */

	List<SmsReceive> receiveMessages() throws Exception;

	/**
	 * 接收短信方法
	 * 
	 * @return 得到的短信实体
	 * @throws ApplicationException
	 */
	SmsReceive receiveMessage() throws Exception;

	/**
	 * 
	 * 检查已发送短信的接收状态
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	void querySmsSendState(SmsSend smsSend) throws Exception;

}
