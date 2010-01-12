package com.systop.fsmis.assessment.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.model.AsseMember;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.sms.SmsSendManager;

/**
 * 风险评估专家成员管理类
 * 
 * @author ShangHua
 * 
 */
@Service
public class AsseMemberManager extends BaseGenericsManager<AsseMember> {

	@Autowired
	private SmsSendManager smsSendManager;

	/**
	 * 根据Id得到AsseMember实体
	 * @param assessmentId 风险评估Id
	 * @param expertId 专家Id
	 * @return AsseMember实体
	 */
	public AsseMember getAsseMember(Integer assessmentId, Integer expertId,
			String type) {
		String hql = "from AsseMember ass where ass.assessment.id = ? and ass.expert.id = ? and ass.type = ? ";
		return (AsseMember) getDao().findObject(hql,
				new Object[] { assessmentId, expertId, type });
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
		return getDao().query(hql, new Object[] { assessmentId, type });
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
		return getDao().query(hql, new Object[] { assessmentId, type });
	}

	/**
	 * 获取该风险评估对象所选择的专家组长信息
	 * @param assessmentId 风险评估Id
	 * @return
	 */
	public String getLeaders(Integer assessmentId) {
		String jsonLeaders = StringUtils.EMPTY;
		StringBuffer leaders = new StringBuffer();
		if (assessmentId != null) {
			List<AsseMember> asseMembers = this.getAsseMembers(assessmentId,
					AssessMentConstants.EXPERT_LEADER);
			if (CollectionUtils.isNotEmpty(asseMembers)) {
				for (AsseMember asseMember : asseMembers) {
					leaders.append(asseMember.getExpert().getName());
					leaders.append(",");
				}
				if (leaders.length() > 0 && leaders.lastIndexOf(",") > 0) {
					jsonLeaders = leaders.substring(0, leaders.length() - 1);
				}
			}
		}
		return jsonLeaders;
	}

	/**
	 * 获取该风险评估对象所选择的专家成员信息
	 * @param assessmentId 风险评估Id
	 * @return
	 */
	public String getMembers(Integer assessmentId) {
		String jsonMembers = StringUtils.EMPTY;
		StringBuffer members = new StringBuffer();
		if (assessmentId != null) {
			List<AsseMember> asseMembers = this.getAsseMembers(assessmentId,
					AssessMentConstants.EXPERT_MEMBER);
			if (CollectionUtils.isNotEmpty(asseMembers)) {
				for (AsseMember asseMember : asseMembers) {
					members.append(asseMember.getExpert().getName());
					members.append(",");
				}
				if (members.length() > 0 && members.lastIndexOf(",") > 0) {
					jsonMembers = members.substring(0, members.length() - 1);
				}
			}
		}
		return jsonMembers;
	}

	/**
	 * 给该条评估申请所选择的专家发送短信
	 * @param assessment 风险评估对象
	 * @return
	 */
	@Transactional
	public void sendTaskMessage(Assessment assessment) {
		Set<AsseMember> asseMembers = assessment.getAsseMemberse();
		for (AsseMember asseMember : asseMembers) {
			StringBuffer asseBuf = new StringBuffer();
			asseBuf.append(asseMember.getExpert().getName()).append(
					AssessMentConstants.MSG_EXPERT_SMSSEND);
			// 给该条评估申请所选择的专家发送短信
			smsSendManager.addMessage(asseMember.getExpert().getMobile(), asseBuf.toString());
		}
	}
}
