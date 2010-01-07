package com.systop.fsmis.assessment.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.model.AsseMember;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.Expert;

/**
 * 专家成员管理Manager
 * 
 * @author ShangHua
 * 
 */
@Service
public class ExpertMembersManager extends BaseGenericsManager<Expert> {
	
	/**
	 * 评估专家成员管理Manager
	 */
	@Autowired
	private AsseMemberManager asseMemberManager;
	
	/**
	 * 根据Id得到Assessment实体
	 * @param assessmentId Assessment id
	 * @return Assessment实体
	 */
	public Assessment getAssessmentById(Integer assessmentId) {
		String hql = "from Assessment ass where ass.id = ?";
		Assessment assessment = (Assessment) getDao().findObject(hql,
				new Object[] { assessmentId });
		return assessment;
	}
	
	/**
	 * 添加专家成员方法
	 * @param idSet 专家成员idSet集合
	 * @param assessmentId 风险评估Id
	 */
	@Transactional
	public void addMembers(Set<Integer> idSet, Integer assessmentId) {
		Assessment assessment = getDao().get(Assessment.class, assessmentId);
		for (Integer id : idSet) {
			Expert expert = getDao().get(Expert.class, id);
			AsseMember asseMember = new AsseMember();
			asseMember.setExpert(expert);
			asseMember.setAssessment(assessment);
			getDao().save(asseMember);
		}
	}
	
	/**
	 * 删除专家成员方法
	 * @param assessmentId 风险评估Id
	 * @param idSet 成员id的Set集合
	 */
	@Transactional
	public void removeMembers(Integer assessmentId, Set<Integer> idSet) {
		for (Integer id : idSet) {
			AsseMember asseMember = asseMemberManager.getAsseMember(assessmentId, id, AssessMentConstants.EXPERT_MEMBER);
			if (asseMember != null ){
				asseMemberManager.remove(asseMember);	
			}
		}
	}
	
	/**
	 * 得到当前评估对象下所有成员方法
	 * @param assessmentId 工程id
	 * @return 所有专家成员的Set集合
	 */
	public Set<AsseMember> getExpertMembers(Integer assessmentId) {
		Set<AsseMember> members = getDao().get(Assessment.class, assessmentId).getAsseMemberse();
		return members;
	}
	
}
