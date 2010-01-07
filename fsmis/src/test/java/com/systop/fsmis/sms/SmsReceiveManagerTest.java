package com.systop.fsmis.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTestCase;
import com.systop.fsmis.model.SmsReceive;

@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class SmsReceiveManagerTest extends BaseTestCase {
	@Autowired
	private SmsReceiveManager smsReceiveManager;

	public void testSave() throws Exception {
		SmsReceive smsReceive = new SmsReceive();
		smsReceive.setContent("测试短信>>>>>>");
		smsReceive.setMobileNum("15100150783");
		smsReceive.setIsNew(SmsConstants.SMS_SMS_SEND_IS_NEW);
		smsReceive.setIsReport(SmsConstants.SMS_IS_REPORT_Y);

		smsReceiveManager.save(smsReceive);
	}
}
