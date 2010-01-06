package com.systop.fsmis.assessment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.assessment.service.AsseMemberManager;
import com.systop.fsmis.assessment.service.AssessmentManager;
import com.systop.fsmis.expert.service.ExpertManager;
import com.systop.fsmis.model.AsseMember;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.Expert;
/**
 * 评估成员管理测试类
 * @author zzg
 *
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class AsseMemberManagerTest extends BaseTransactionalTestCase {
	//注入评估成员管理类
	@Autowired
	private AsseMemberManager assseMemberManager;
	//注入专家管理类
	@Autowired
	private ExpertManager expertManager;
	//注入评估表管理类
	@Autowired
	private AssessmentManager assessmentManager;
	//测试根据Id、评估类型得到AsseMember实体方法
	public void testgetAsseMember() {
		Expert expert = new Expert();
		expert.setName("专家");
		expertManager.save(expert);

		Assessment assessment = new Assessment();
		assessment.setState("0");
		assessmentManager.save(assessment);

		AsseMember asseMember = new AsseMember();
		asseMember.setDescn("描述");
		asseMember.setType("1");
		asseMember.setExpert(expert);
		asseMember.setAssessment(assessment);
		assseMemberManager.save(asseMember);

		AsseMember aM = assseMemberManager.get(asseMember.getId());
		AsseMember aMRet = assseMemberManager.getAsseMember(aM.getAssessment()
				.getId(), aM.getExpert().getId(), "1");

		assertEquals("专家", aMRet.getExpert().getName());
		assertEquals("0", aMRet.getAssessment().getState());
		assertEquals("描述", aMRet.getDescn());
		assertEquals("1", aMRet.getType());
	}
	
	//测试获取评估成员List方法
	public void testgetAsseMembers() {
		Assessment assessment = new Assessment();
		assessment.setState("1");
		assessmentManager.save(assessment);

		AsseMember asseMember = new AsseMember();
		asseMember.setDescn("新描述");
		asseMember.setType("0");
		asseMember.setAssessment(assessment);
		assseMemberManager.save(asseMember);

		List<AsseMember> asseMembers = assseMemberManager.getAsseMembers(asseMember
				.getAssessment().getId(), "0");
		
		assertTrue(asseMembers.contains(assseMemberManager.get(asseMember.getId())));
	}
}
