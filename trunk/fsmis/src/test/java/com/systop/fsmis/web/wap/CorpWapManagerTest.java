package com.systop.fsmis.web.wap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.corp.service.CorpManager;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.web.wap.service.CorpWapManager;

/**
 * WAP企业管理测试类
 * @author DU
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class CorpWapManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private CorpWapManager corpWapManager;
	
	@Autowired
	private CorpManager corpManager;
	
	/**
	 * 测试根据企业名称列出企业信息
	 */
	@SuppressWarnings("unchecked")
  public void testGetAllCorps() {
		Corp corp1 = generateCorp("EMS Corperation", "ShiJiazhuang HeBei China");
		corp1.setBusinessLicense("200912010085");
		Corp corp2 = generateCorp("EMS Corp", "ShiJiazhuang HeBei China");
		corpManager.save(corp1);
		corpManager.save(corp2);
		List corps = corpWapManager.getAllCorps("EMS", 5, 0);

		assertNotNull(corps);
		assertTrue(corps.size() >= 2);
	}
	
	/**
	 * 测试根据企业名称查询符合条件的记录数量
	 */
	public void testGetCorpCount() {
		Corp corp1 = generateCorp("EMS Corperation", "ShiJiazhuang HeBei China");
		corp1.setBusinessLicense("200912010085");
		Corp corp2 = generateCorp("EMS Corp", "ShiJiazhuang HeBei China");
		corpManager.save(corp1);
		corpManager.save(corp2);
		int corps = corpWapManager.getCorpCount("EMS");
		
		assertNotNull(corps);
	}
	
	/**
	 * 测试查看企业详情
	 */
	public void testViewCorp() {
		Corp corp = generateCorp("EMS Corperation", "ShiJiazhuang HeBei China");
		corp.setBusinessLicense("200912010085");
		corpManager.save(corp);
		
		assertNotNull(corpWapManager.viewCorp(corp.getId()));
	}
	
	/**
	 * 生成企业测试数据
	 * @param name 名称
	 * @param address 地址
	 */
	private Corp generateCorp(String name, String address) {
		Corp corp = new Corp();
		corp.setName(name);
		corp.setAddress(address);
		corp.setBusinessLicense("20100128");
		corp.setOperateDetails("综合");
		
		return corp;
	}
}
