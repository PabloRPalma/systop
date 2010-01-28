package com.systop.fsmis.corp;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.corp.service.CorpManager;
import com.systop.fsmis.model.Corp;

/**
 * 企业管理测试类
 * @author DU
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class CorpManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private CorpManager corpManager;
	
	@Autowired
	private DeptManager deptManager;
	
	/**
	 * 所属区县
	 */
	private Dept county;
	
	/**
	 * 准备数据
	 */
	protected void setUp() throws Exception {
		county = deptManager.findObject("from Dept d where d.name like ? ",
				MatchMode.ANYWHERE.toMatchString("裕华区"));
	}
	
	/**
	 * 测试保存企业信息
	 */
	public void testSave() {
		Corp corp = generateCorp("Wifi Corporation", "ShiJiazhuang HeBei China", county);
		corpManager.save(corp);
		
		assertNotNull(corpManager.get(corp.getId()));
	}
	
	/**
	 * 测试删除企业信息
	 */
	public void testRemove() {
		Corp corp = generateCorp("Wifi Corporation of DU", "ShiJiazhuang HeBei China", county);
		corpManager.save(corp);
		corpManager.remove(corp, "c:\\");
		
		assertNull(corpManager.get(corp.getId()));
	}
	
	/**
	 * 生成企业测试数据
	 * @param name 名称
	 * @param address 地址
	 */
	private Corp generateCorp(String name, String address, Dept county) {
		Corp corp = new Corp();
		corp.setName(name);
		corp.setAddress(address);
		corp.setBusinessLicense("20100128");
		corp.setOperateDetails("综合");
		corp.setDept(county);
		
		return corp;
	}
}
