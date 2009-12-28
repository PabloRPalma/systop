package com.systop.fsmis.fscase.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.casetype.service.CaseTypeManager;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.FsCase;

@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class FsCaseManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private FsCaseManager fsCaseManager;
	
	
	public void testSaveFsCase() {
		FsCase fscase = new FsCase();
		CaseType casetype = new CaseType();
	
		fscase.setTitle("测试事件");
		fscase.setAddress("测试地址");
		fscase.setBeginDate(new Date());
		fscase.setCaseTime(new Date());
		fscase.setCaseType(casetype);
		fscase.setCode("002003005");
		fscase.setDescn("测试描述");
		fscase.setStatus("0");
		fscase.setEndDate(new Date());
		fscase.setClosedTime(new Date());
		fscase.setInformer("测试发布人");
		fscase.setInformerPhone("13685245126");
	
		fsCaseManager.save(fscase);
	}

}
