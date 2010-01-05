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
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class AsseMemberManagerTest extends BaseTransactionalTestCase {
	@Autowired
	private AsseMemberManager assseMemberManager;
	
	@Autowired
	private ExpertManager expertManager;

	@Autowired
	private AssessmentManager assessmentManager;

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
