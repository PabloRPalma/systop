package com.systop.fsmis.sms.smproxy.cmcc;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
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
	private static Logger logger = Logger
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
	@Deprecated
	public List<SmsReceive> receiveMessage() throws ApplicationException {
		/*
		 * 由于MAS接收短信功能暂时不能使用,此代码暂不实现,待功能能够访问时,启用代码 IfSMSService service = new
		 * IfSMSServiceProxy(); try { String[] receives =
		 * service.receive(SmsConstants.CONN_NAME, SmsConstants.CONN_PASS,
		 * SmsConstants.DEST_ADDR); if (receives != null) {
		 * 
		 * }
		 * 
		 * } catch (RemoteException ex) { logger.error(ex.getMessage()); throw new
		 * ApplicationException(ex); }
		 */
		throw new RuntimeException("方法尚未实现!");
	}

	/**
	 * @see{@link SmsProxy#sendMessage()}
	 * 
	 *            1.service.sendState()方法是必须要处理异常的,要么try-catch,要么throws
	 *            2.如果throws,throws一个RemoteException?
	 *            本来程序中的ApplicationException就是用来包装RuntimeException的
	 *            ,如果在层间传递RemoteException,那么就会污染各个层面
	 *            3.如果try-catch,那么在catch中必须有一个throw,
	 *            ,否则就必须在catch中return一个Integer,这样一来,又把返回值和异常处理耦合到了一起...
	 * 
	 *            所以当时的做法是包装到ApplicationException中throw出去了.
	 * 
	 *            
	 */
	@Override
	public Integer sendMessage(SmsSend smsSend) throws ApplicationException {
		IfSMSService service = new IfSMSServiceProxy();
		String[] destAddreses = new String[] { smsSend.getMobileNum() };
		try {

			// 如果提交成功会返回提交到Mas数据库中的主键
			int[] states = service.sendState(SmsConstants.CONN_NAME,
					SmsConstants.CONN_PASS, destAddreses, smsSend.getContent(), 1);
			//移动Mas的WebService方法用返回的Integer是否大于0来表示是否发送成功(若失败返回-1)
			if (!ArrayUtils.isEmpty(states) && states[0] > 0) {
				return states[0];
			} else {//如果发送失败(WebService方法返回小于0的Integer),封装为异常
				throw new ApplicationException("ID为:" + smsSend.getId() + ",接收号码为"
						+ smsSend.getMobileNum() + "的短信发送失败!" + "错误原因为:接收到id值" + states[0]);
			}
		} catch (RemoteException ex) {
			logger.error(ex.getMessage());
			return 0;
			//throw new RuntimeException(ex);//最终怎样处理待沟通确认
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
