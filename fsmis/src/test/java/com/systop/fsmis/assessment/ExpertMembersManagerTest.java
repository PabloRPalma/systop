package com.systop.fsmis.assessment;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.assessment.service.AssessmentManager;
import com.systop.fsmis.assessment.service.ExpertMembersManager;
import com.systop.fsmis.expert.service.ExpertManager;
import com.systop.fsmis.model.AsseMember;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.Expert;

/**
 * 专家成员管理测试类
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class ExpertMembersManagerTest extends BaseTransactionalTestCase {
	//注入专家组管理类
	@Autowired
	private ExpertMembersManager expertMembersManager;
	//注入评估管理类
	@Autowired
	private AssessmentManager assessmentManager;
	//注入专家管理类
	@Autowired
	private ExpertManager expertManager;
	
	//测试根据Id得到Assessment实体
	public void testGetAssessmentById(){
		Assessment sssessment = new Assessment();
		sssessment.setResult("result");
		assessmentManager.save(sssessment);
		Assessment am = expertMembersManager.getAssessmentById(sssessment.getId());
		assertEquals("result", am.getResult()); 
	}
	
	//测试添加专家成员方法
	public void testAddMembers(){
		Expert expert = new Expert();
		expert.setName("name");
		expertManager.save(expert);
		
		Assessment sssessment = new Assessment();
		sssessment.setResult("result");
		assessmentManager.save(sssessment);
		
		Set<Integer> idSet = new HashSet<Integer>();
		idSet.add(expert.getId());
		
		expertMembersManager.addMembers(idSet, sssessment.getId());

		AsseMember asseMember = (AsseMember)expertMembersManager.getDao()
				.findObject("from AsseMember am where am.expert.id = ?", expert.getId());
		
		assertEquals("name", asseMember.getExpert().getName());
	}
	
	//测试删除专家成员方法removeMembers
	public void testRemoveMembers(){
		Expert expert = new Expert();
		expert.setName("name");
		expertManager.save(expert);
		
		Assessment sssessment = new Assessment();
		sssessment.setResult("result");
		assessmentManager.save(sssessment);
		
		Set<Integer> idSet = new HashSet<Integer>();
		idSet.add(expert.getId());
		
		expertMembersManager.addMembers(idSet, sssessment.getId());
		expertMembersManager.removeMembers(sssessment.getId(), idSet);
	}
}
