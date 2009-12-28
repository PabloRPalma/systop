package com.systop.fsmis.fscase.casetype.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.model.CaseType;


@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class CastTypeManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private CaseTypeManager caseTypeManager;
	
	
	public void testGetLevelOneMap() {
		caseTypeManager.getLevelOneMap();
	}

	public void testSaveCaseType() {
		CaseType casetype = new CaseType();
		casetype.setName("测试类别");
		casetype.setDescn("测试类别描述");
		
		caseTypeManager.save(casetype);
	}

	

	public void testGetLevelOneList() {
		caseTypeManager.getLevelOneList();
	}

	

}
