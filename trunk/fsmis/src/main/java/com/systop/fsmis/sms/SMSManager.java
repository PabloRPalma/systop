package com.systop.fsmis.sms;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.systop.core.ApplicationException;
import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.model.SmsSend;

import com.systop.fsmis.sms.smproxy.SmsProxy;

/**
 * 短信管理类,短信模块的顶层类<br>
 * 本类依赖:<br>
 * <ul>
 * <li>SMSProxy完成短信的收发操作
 * <li>SmsSendManager完成发送短信的持久化操作
 * <li>SmsReceiveManager完成接收短信的持久化操作
 * </ul>
 * 
 * @author WorkShopers
 * 
 */
@Service("smsManager")
public class SMSManager {
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 依赖短信服务代理接口,用于完成特定的短信平台的短信发送/接收/查询操作
	 */
	private SmsProxy smsProxy;
	/**
	 * 依赖发送短信Manager,用于进行发送短信的数据库操作
	 */
	private SmsSendManager smsSendManager;
	/**
	 * 依赖接收短信Manager,用于进行接收短信的数据库操作
	 */
	private SmsReceiveManager smsReceiveManager;

	public SmsSendManager getSmsSendManager() {
		return smsSendManager;
	}

	public void setSmsSendManager(SmsSendManager smsSendManager) {
		this.smsSendManager = smsSendManager;
	}

	public SmsReceiveManager getSmsReceiveManager() {
		return smsReceiveManager;
	}

	public void setSmsReceiveManager(SmsReceiveManager smsReceiveManager) {
		this.smsReceiveManager = smsReceiveManager;
	}

	public SmsProxy getSmsProxy() {
		return smsProxy;
	}

	@Autowired(required = true)
	public void setSmsProxy(@Qualifier("smsProxy") SmsProxy smsProxy) {
		this.smsProxy = smsProxy;
	}

	/**
	 * @see {@link SmsProxy#querySmsSendState()}
	 */

	public void querySmsSendState() {
		// TODO Auto-generated method stub

	}

	public void receiveMessages() {
		try {
			List<SmsReceive> smsReceiveList = smsProxy.receiveMessage();
			for (SmsReceive smsReceive : smsReceiveList) {

				getSmsReceiveManager().save(smsReceive);
			}
		} catch (ApplicationException ex) {

		}
	}

	public void sendMessage() {
		System.out.println(">>>>>>>");
	}

	public void sendMessages() {
		logger.info("SMSManager.sendMessages()-->");

		// 得到数据库中需要发送的短信列表
		List<SmsSend> smsSendList = getSmsSendManager().getNewSmsSends();

		// 遍历列表
		for (SmsSend smsSend : smsSendList) {
			if (smsSend == null) {
				continue;
			}
			if (smsSend.getMobileNum() == null
					|| smsSend.getMobileNum().length() < 11) {
				logger.error(sf.format(new java.util.Date()) + " ID为:"
						+ smsSend.getId() + "的短信,接收手机号[" + smsSend.getMobileNum()
						+ "]有误,发送失败!");

				continue;
			}
			try {
				// 调用代理的发送功能
				int state = getSmsProxy().sendMessage(smsSend);

				if (state > 0) {// 如果发送成功
					// 更新数据库中短信的状态,不为新短信,短信发送时间
					smsSend.setIsNew(SmsConstants.SMS_SMS_SEND_IS_NOT_NEW);
					smsSend.setSendTime(new java.util.Date());
					getSmsSendManager().update(smsSend);

					logger.info(sf.format(new java.util.Date()) + " Id为: "
							+ smsSend.getId() + " 的短信发送成功");
				}
			} catch (ApplicationException ex) {
				logger.error(sf.format(new java.util.Date()) + "ID为: "
						+ smsSend.getId() + "接收手机号为:" + smsSend.getMobileNum()
						+ "的短信发送失败!  错误原因:" + ex.getMessage());
			}

		}
		getSmsProxy().receiveMessage();
	}

}
