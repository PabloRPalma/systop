package com.systop.fsmis.sms;

import java.util.List;

import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.model.SmsSend;
import com.systop.fsmis.sms.SMSConstants;
import com.systop.fsmis.sms.SMSManager;
import com.systop.fsmis.sms.service.SmsSendManager;

@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class SMSManagerTest extends BaseTransactionalTestCase {
	public void testWebService() {
		SmsSend smsSend = new SmsSend();
		smsSend.setContent("测试短信>>>>>>");
		smsSend.setMobileNum("13932159399");
		smsSend.setIsNew(SMSConstants.SMS_SMS_SEND_IS_NEW);

		SmsSendManager sm = (SmsSendManager) applicationContext
				.getBean("smsSendManager");
		sm.save(smsSend);
		List<SmsSend> list = sm.get();

		SMSManager service = (SMSManager) applicationContext.getBean("smsManager");

		service.sendMessages();
		assertEquals(1, list.size());

	}

	public void testReceiveMessages() {

		SMSManager service = (SMSManager) applicationContext.getBean("smsManager");

		service.receiveMessages();

	}
}
