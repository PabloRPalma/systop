package com.systop.fsmis.assessment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.AsseMember;

/**
 * 风险评估专家成员管理类
 * 
 * @author ShangHua
 * 
 */
@Service
public class AsseMemberManager extends BaseGenericsManager<AsseMember> {
	
	/**
	 * 根据Id得到AsseMember实体
	 * @param assessmentId 风险评估Id
	 * @param expertId 专家Id
	 * @return AsseMember实体
	 */
	public AsseMember getAsseMember(Integer assessmentId, Integer expertId, String type) {
		String hql = "from AsseMember ass where ass.assessment.id = ? and ass.expert.id = ? and ass.type = ? ";
		AsseMember asseMember = (AsseMember) getDao().findObject(hql, new Object[]{assessmentId, expertId, type} );
		return asseMember;
	}
	
	/**
	 * 根据风险评估Id和类型获取该类型下的专家信息
	 * @param assessmentId 风险评估Id
	 * @param type 专家类型
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<AsseMember> getAsseMembers(Integer assessmentId, String type) {
		String hql = "from AsseMember ass where ass.assessment.id = ? and ass.type = ? ";
		List asseMembers = this.getDao().query(hql, new Object[]{assessmentId, type} );
		return asseMembers;
	}
	
	/**
	 * 根据风险评估Id和类型获取除了该类型下的其它类型的专家信息
	 * @param assessmentId 风险评估Id
	 * @param type 专家类型
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<AsseMember> getAsseOtherMembers(Integer assessmentId, String type) {
		String hql = "from AsseMember ass where ass.assessment.id = ? and ass.type <> ? ";
		List asseMembers = this.getDao().query(hql, new Object[]{assessmentId, type} );
		return asseMembers;
	}
	
}
