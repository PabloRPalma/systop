package com.systop.fsmis.assessment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.assessment.service.AssessmentManager;
import com.systop.fsmis.assessment.service.ExpertMembersManager;
import com.systop.fsmis.model.Assessment;

/**
 * 专家成员管理测试类
 * @author zzg
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class ExpertMembersManagerTest extends BaseTransactionalTestCase {
	
	//注入专家组管理类
	@Autowired
	private ExpertMembersManager expertMembersManager;
	
	//注入评估管理类
	@Autowired
	private AssessmentManager assessmentManager;
	
	/**
	 * 测试根据Id得到Assessment实体
	 * 新增评估后保存
	 * 验证是否保存成功
	 */
	public void testGetAssessmentById() {
		
		//向数据库里新增一条评估记录
		Assessment sssessment = new Assessment();
		sssessment.setResult("result");
		assessmentManager.save(sssessment);
		
		//测试根据Id得到Assessment实体
		Assessment am = expertMembersManager.getAssessmentById(sssessment.getId());
		
		//验证是否成功获取
		assertEquals("result", am.getResult()); 
	}
}
