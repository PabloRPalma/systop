package com.systop.fsmis.fscase.casetype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.casetype.service.CaseTypeManager;
import com.systop.fsmis.model.CaseType;
/**
 * 事件类别管理测试类
 * @author shaozhiyuan
 *
 */

@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class CastTypeManagerTest extends BaseTransactionalTestCase {

	//注入事件类别manager
	@Autowired
	private CaseTypeManager caseTypeManager;
	
	/**
	 * 测试得到一级类别map列表方法
	 */
	public void testGetLevelOneMap() {
		caseTypeManager.getLevelOneMap();
		
	}

	/**
	 * 测试保存类别方法
	 */
	public void testSaveCaseType() {
		//新增类别记录
		CaseType casetype = new CaseType();
		casetype.setName("测试类别");
		casetype.setDescn("测试类别描述");
		//保存记录
		caseTypeManager.save(casetype);
		assertEquals("测试类别",caseTypeManager.get(casetype.getId()).getName());
	}

	
	/**
	 * 测试得到一级类别list列表方法
	 */
	@SuppressWarnings("unchecked")
	public void testGetLevelOneList() {
		//新增类别记录
		CaseType casetype = new CaseType();
		casetype.setName("测试类别");
		casetype.setDescn("测试类别描述");
		caseTypeManager.save(casetype);
		//得到一级类别list
		List<CaseType> caseTypes = caseTypeManager.getLevelOneList();
		assertTrue(caseTypes.contains(caseTypeManager.get(casetype.getId())));
	}

	

}
