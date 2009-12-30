package com.systop.fsmis.sms;

import java.util.List;

import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.model.SmsSend;
import com.systop.fsmis.sms.SmsConstants;

@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class SMSManagerTest extends BaseTransactionalTestCase {
	public void testWebService() throws Exception {
		SmsSend smsSend = new SmsSend();
		smsSend.setContent("测试短信>>>>>>");
		smsSend.setMobileNum("15100150783");
		smsSend.setIsNew(SmsConstants.SMS_SMS_SEND_IS_NEW);

		SmsSendManager sm = (SmsSendManager) applicationContext
				.getBean("smsSendManager");
		sm.save(smsSend);
		List<SmsSend> list = sm.get();

		SmsManager service = (SmsManager) applicationContext.getBean("smsManager");

		service.sendMessages();
		assertEquals(1, list.size());

	}

}
