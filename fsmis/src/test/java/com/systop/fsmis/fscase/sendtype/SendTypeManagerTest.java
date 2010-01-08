package com.systop.fsmis.fscase.sendtype;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
import com.systop.fsmis.model.SendType;
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
		//新增类别记录
		SendType sendtype = new SendType();
		sendtype.setName("测试派遣类别");
		sendtype.setDescn("描述");
		//保存记录
		sendTypeManager.save(sendtype);
		List<SendType> sendtypes = sendTypeManager.orderSendType();
		assertTrue(sendtypes.contains(sendTypeManager.get(sendtype.getId())));
	}

}
