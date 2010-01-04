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
	public void testgetAsseMember(){
		Expert expert = new Expert();
		expert.setName("专家");
		expertManager.save(expert);
		String hql = "from Expert where name = '专家'";
		Expert et = expertManager.findObject(hql);
		
		Assessment assessment = new Assessment();
		assessment.setState("0");
		assessmentManager.save(assessment);
		hql = "from Assessment where state = '0'";
		Assessment asm = assessmentManager.findObject(hql);
		
		AsseMember asseMember = new AsseMember();
		asseMember.setDescn("描述");
		asseMember.setType("1");
		asseMember.setExpert(et);
		asseMember.setAssessment(asm);
		assseMemberManager.save(asseMember);
		hql = "from AsseMember am where am.descn = '描述' and am.type = '1' and am.expert.name = '专家'";
		AsseMember aM = assseMemberManager.findObject(hql);
		
		if(aM != null && et != null && asm != null){
			@SuppressWarnings("unused")
			AsseMember aMRet = assseMemberManager.getAsseMember(aM.getAssessment().getId(), aM.getExpert().getId(), "1");
		}
	}
	
	public void testgetAsseMembers(){
		Assessment assessment = new Assessment();
		assessment.setState("1");
		assessmentManager.save(assessment);
		String hql = "from Assessment where state = '1'";
		Assessment asm = assessmentManager.findObject(hql);
		
		AsseMember asseMember = new AsseMember();
		asseMember.setDescn("新描述");
		asseMember.setType("0");
		asseMember.setAssessment(asm);
		assseMemberManager.save(asseMember);
		hql = "from AsseMember am where am.descn = '新描述' and am.type = '0'";
		AsseMember aM = assseMemberManager.findObject(hql);
		
		if(aM != null && asm != null){
			@SuppressWarnings("unused")
			List<AsseMember> asseMembers = assseMemberManager.getAsseMembers(aM.getAssessment().getId(), "0");
			System.out.println(asseMembers.get(0).getDescn());
		}
	}
}
