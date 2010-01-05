package com.systop.fsmis.fscase.sendtype.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;


@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class SendTypeManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private SendTypeManager sendTypeManager;
	
	public void testOrderSendType() {
		sendTypeManager.orderSendType();
	}

}
