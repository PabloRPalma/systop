package com.systop.fsmis.fscase.sendtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
/**
 * 派遣类别管理测试类
 * @author shaozhiyuan
 *
 */

@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class SendTypeManagerTest extends BaseTransactionalTestCase {

	//注入派遣类别manager
	@Autowired
	private SendTypeManager sendTypeManager;
	
	/**
	 * 测试得到类别list列表方法
	 */
	public void testOrderSendType() {
		sendTypeManager.orderSendType();
	}

}
