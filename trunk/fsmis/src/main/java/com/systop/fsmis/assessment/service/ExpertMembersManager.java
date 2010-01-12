package com.systop.fsmis.assessment.service;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
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
	 * 根据Id得到Assessment实体
	 * @param assessmentId Assessment id
	 * @return Assessment实体
	 */
	public Assessment getAssessmentById(Integer assessmentId) {
		String hql = "from Assessment ass where ass.id = ?";
		return (Assessment) getDao().findObject(hql,
				new Object[] { assessmentId });
	}

}
