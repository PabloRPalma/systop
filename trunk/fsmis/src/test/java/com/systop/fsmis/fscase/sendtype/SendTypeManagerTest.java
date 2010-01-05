package com.systop.fsmis.fscase.sendtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;


@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class SendTypeManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private SendTypeManager sendTypeManager;
	
	public void testOrderSendType() {
		sendTypeManager.orderSendType();
	}

}
