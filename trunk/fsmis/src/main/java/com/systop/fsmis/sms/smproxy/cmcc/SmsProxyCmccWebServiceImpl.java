package com.systop.fsmis.sms.smproxy.cmcc;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.systop.core.ApplicationException;
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
	private static Logger logger = LoggerFactory
			.getLogger(SmsProxyCmccWebServiceImpl.class);

	/**
	 * @see{@link SmsProxy#querySmsSendState}
	 */
	@Override
	public List<String> querySmsSendState() {
		return null;
	}

	/**
	 * @see{@link SmsProxy#receiveMessage()}
	 */
	@Override
	public List<SmsReceive> receiveMessage() throws ApplicationException {
		logger.info("receiveMessage....");
		IfSMSService service = null;
		service = new IfSMSServiceProxy();
		try {
			String[] receives = service.receive(SmsConstants.CONN_NAME,
					SmsConstants.CONN_PASS, SmsConstants.DEST_ADDR);
			if (receives != null) {
				logger.info(receives.toString());
			}

		} catch (RemoteException ex) {
			logger.error(ex.getMessage());
			throw new ApplicationException(ex);
		}
		return null;
	}

	/**
	 * @see{@link SmsProxy#sendMessage()}
	 */
	@Override
	public Integer sendMessage(SmsSend smsSend) throws ApplicationException {
		IfSMSService service = null;
		service = new IfSMSServiceProxy();
		String[] destAddreses = new String[] { smsSend.getMobileNum() };
		int[] states = null;
		try {

			// 如果提交成功会返回提交到Mas数据库中的主键
			states = service.sendState(SmsConstants.CONN_NAME,
					SmsConstants.CONN_PASS, destAddreses, smsSend.getContent(), 1);
			if (!ArrayUtils.isEmpty(states) && states[0] > 0) {
				return states[0];
			} else {
				throw new ApplicationException("ID为:" + smsSend.getId() + ",接收号码为"
						+ smsSend.getMobileNum() + "的短信发送失败!" + "错误原因为:接收到id值" + states[0]);
			}
		} catch (RemoteException ex) {

			throw new ApplicationException("ID为:" + smsSend.getId() + ",接收号码为"
					+ smsSend.getMobileNum() + "的短信发送失败!" + "错误原因为:" + ex.getMessage());
		}

	}

	/**
	 * @see{@link SmsProxy#sendMessage()}
	 */
	@Override
	@Deprecated
	public Integer sendMessage(List<SmsSend> smsSendList)
			throws ApplicationException {
		return null;
	}

}
