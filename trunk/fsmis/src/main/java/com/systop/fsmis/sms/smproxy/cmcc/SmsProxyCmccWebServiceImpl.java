package com.systop.fsmis.sms.smproxy.cmcc;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.systop.core.util.DateUtil;
import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.model.SmsSend;
import com.systop.fsmis.sms.SmsConstants;
import com.systop.fsmis.sms.smproxy.SmsProxy;
import com.systop.fsmis.sms.smproxy.cmcc.webservice.IfSMSService;
import com.systop.fsmis.sms.smproxy.cmcc.webservice.IfSMSServiceProxy;

/**
 * SMSProxy接口实现类<br>
 * 本类以访问中国移动WebServcie方式实现SMSProxy接口所规定的发送和接收短信方法<br>
 * 其他短信服务代理的实现类似,但必须以@Service("smsProxy")在Spring容器中注册,以
 * 
 * @author WorkShopers
 * 
 */
@Service("smsProxy")
public class SmsProxyCmccWebServiceImpl implements SmsProxy {
	private static Logger logger = Logger
			.getLogger(SmsProxyCmccWebServiceImpl.class);

	/**
	 * @see{@link SmsProxy#querySmsSendState}
	 */
	@Override
	public List<String> querySmsSendState() throws Exception {
		return null;
	}

	/**
	 * 
	 * 发送单个短信方法
	 * 
	 * @param smsSend
	 *            要发送的短信实体实例
	 * @return 发送到短信系统中的短信id
	 * @throws Exception
	 *             短信移动短信系统的WebService会抛出RemoteException.<br>
	 *             这个异常无法指望本系统进行处理,也不应该由本系统进行处理<br>
	 *             所以对于本方法的:throws Exception,只管在方法中继续throws Exception
	 */
	@Override
	public Integer sendMessage(SmsSend smsSend) throws Exception {
		IfSMSService service = new IfSMSServiceProxy();
		String[] destAddreses = new String[] { smsSend.getMobileNum() };
		try {

			// 如果提交成功会返回提交到Mas数据库中的主键
			int[] states = service.sendState(SmsConstants.CONN_NAME,
					SmsConstants.CONN_PASS, destAddreses, smsSend.getContent(),
					1);
			// 移动Mas的WebService方法用返回的Integer是否大于0来表示是否发送成功(若失败返回-1)
			if (!ArrayUtils.isEmpty(states) && states[0] > 0) {
				return states[0];
			} else {// 如果发送失败(WebService方法返回小于0的Integer),封装为异常
				StringBuffer buf = new StringBuffer();
				buf.append("ID为:").append(smsSend.getId()).append(",接收号码为")
						.append(smsSend.getMobileNum()).append("的短信发送失败!")
						.append("错误原因为:接收到id值").append(states[0]);
				logger.error(buf.toString());
				throw new Exception(buf.toString());
			}
		} catch (RemoteException ex) {
			logger.error(ex.getMessage());
			throw new Exception(ex);
		}
	}

	/**
	 * 解析Mas返回的字符串数组,得到SmsReceive实例
	 * 
	 * @return
	 */
	private SmsReceive parse(String[] receiveStrings) {
		SmsReceive smsReceive = null;
		if (!ArrayUtils.isEmpty(receiveStrings)) {
			if (receiveStrings.length == 3) {
				smsReceive = new SmsReceive();
				smsReceive.setMobileNum(receiveStrings[0]);
				smsReceive.setContent(receiveStrings[1]);
				try {
					smsReceive.setSendTime(DateUtil
							.convertStringToDate(receiveStrings[2]));
				} catch (ParseException e) {
					logger.error("转换短信发送时间错误{}" + e.getMessage(), e);
				}
				smsReceive.setReceiveTime(new Date());
				smsReceive.setIsNew(SmsConstants.SMS_SMS_SEND_IS_NEW);
			}
		}

		return smsReceive;
	}

	/**
	 * @see{@link SmsProxy#sendMessage()}
	 */
	@Override
	public Integer sendMessage(List<SmsSend> smsSendList) throws Exception {
		throw new Exception("方法尚未实现,移动Mas没有提供发送多条短信方法");
	}

	@Override
	public SmsReceive receiveMessage() throws Exception {
		/*
		 * 由于MAS接收短信功能暂时不能使用,此代码暂不实现,待功能能够访问时,删除此注释
		 */
		IfSMSService service = new IfSMSServiceProxy();
		SmsReceive smsReceive = null;
		try {
			String[] receiveStrings = service.receive(SmsConstants.CONN_NAME,
					SmsConstants.CONN_PASS, SmsConstants.DEST_ADDR);

			smsReceive = parse(receiveStrings);

		} catch (RemoteException ex) {
			logger.error(ex.getMessage());
			throw new Exception(ex);
		}

		return smsReceive;
	}

	@Override
	public List<SmsReceive> receiveMessages() throws Exception {

		throw new Exception("方法尚未实现,移动Mas没有提供接收多条短信方法");
	}

}
