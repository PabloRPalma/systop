package com.systop.fsmis.web.wap;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Complain;
import com.systop.fsmis.web.wap.service.ComplainManager;

/**
 * 举报投诉管理测试类
 * @author DU
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class ComplainManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private ComplainManager complainManager;
	
	/**
	 * 测试保存举报投诉内容，并发送短息
	 */
	public void testSaveComplain() {
		Complain complain = generateComplain("吃面条拉肚子", "Sam");
		complainManager.saveComplain(complain);
		
		assertNotNull(complainManager.get(complain.getId()));
	}
	
	/**
	 * 测试举报投诉身份确认
	 */
	public void testIdentitComp() {
		Complain complain = generateComplain("超市有过期食品", "Pailn");
		complainManager.saveComplain(complain);
		complainManager.identitComp(complain.getSecureStr());
		
		assertEquals(FsConstants.Y, complainManager.get(complain.getId()).getIsValidated());
	}
	
	/**
	 * 生成举报投诉测试数据
	 * @param title 标题
	 * @param reporter 投诉人
	 */
	private Complain generateComplain(String title, String reporter) {
		Complain complain = new Complain();
		complain.setTitle(title);
		complain.setReporter(reporter);
		complain.setReportTime(new Date());
		complain.setDescn("descn");
		complain.setAddr("石家庄职业技术学院");
		//俺滴号码
		complain.setPhoneNo("13933067201");
		
		return complain;
	}
}
