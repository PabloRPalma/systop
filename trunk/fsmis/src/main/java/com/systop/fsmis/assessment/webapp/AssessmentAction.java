package com.systop.fsmis.assessment.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.assessment.service.AsseMemberManager;
import com.systop.fsmis.assessment.service.AssessmentAttachManager;
import com.systop.fsmis.assessment.service.AssessmentManager;
import com.systop.fsmis.model.AsseMember;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.AssessmentAttach;
import com.systop.fsmis.model.CheckResult;

/**
 * 风险评估信息管理
 * @author ShangHua
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AssessmentAction extends
		ExtJsCrudAction<Assessment, AssessmentManager> {

	/**
	 * 评估附件管理Manager
	 */
	@Autowired
	private AssessmentAttachManager assessmentAttachManager;
	
	/**
	 * 审核管理实体类
	 */
	private CheckResult checkResult;
	
  /**
   * 缓存事件标题集合
   */
	private String[] jsonTitles;
	
	/**
	 * 评估Id
	 */
	private Integer assessmentId;
	
	/**
	 * 专家成员
	 */
	private String jsonMembers;
	
	/**
	 * 专家组长
	 */
	private String jsonLeaders;
	
	/**
	 * 评估专家成员管理Manager
	 */
	@Autowired
	private AsseMemberManager asseMemberManager;
	
  /**
   * 返回所有事件标题
   * @return 返回所有事件标题
   */
  public String getTitles() {
  	jsonTitles = getManager().getAllTitles();
    return "titles";
  }
	
	/**
	 * 重写父类的index方法，实现分页检索风险评估信息
	 */
	@Override
	public String index() {
		StringBuffer hql = new StringBuffer();
		hql.append("from Assessment ass where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if (getModel().getFsCase() != null && StringUtils.isNotBlank(getModel().getFsCase().getTitle())) {
			hql.append(" and ass.fsCase.title = ?");
			args.add(getModel().getFsCase().getTitle());
		}
		if (getModel().getProposer() != null && StringUtils.isNotBlank(getModel().getProposer().getName())) {
			hql.append(" and ass.proposer.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getProposer().getName()));
		}
		if (StringUtils.isNotBlank(getModel().getAskCause())) {
			hql.append(" and ass.askCause like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getAskCause()));
		}
		hql.append(" order by ass.askDate desc");
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return INDEX;
	}

	/**
	 * 保存评估申请信息
	 */
	@Override
	public String save() {
		try {
			//设置申请人为当前登录用户
			User user = UserUtil.getPrincipal(getRequest());
			if (user != null) {				
				getModel().setProposer(user);	
			}	
			getModel().setState(AssessMentConstants.AUDITING_WAITING);
			getManager().save(getModel());
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	/**
	 * 重写父类的remove方法，用于实现删除风险评估及其附件的功能
	 */
	@Override
	public String remove() {
		Assessment assessment = getManager().get(getModel().getId());
		Set<AssessmentAttach> attaches = assessment.getAsseAtts();
		for (AssessmentAttach assAttach : attaches) {
			String realPath = assAttach.getPath();
			if (StringUtils.isNotBlank(realPath)) {
				assessmentAttachManager.removeAttach(assAttach, getRealPath(realPath));
				return SUCCESS;
			}
		}
		return super.remove();
	}

  /**
   * 返回评估状态集合
   * @return list
   */
  public Map<String, String> getAssessmentStates() {
    return AssessMentConstants.ASSESSMENT_STATE;
  }
  
	/**
	 * 风险评估审核
	 */
	public String audit() {
		return "audit";
	}
	
	/**
	 * 保存风险评估审核记录
	 */
	public String auditSave() {
		if (getModel().getId() != null ){
			Assessment assessment = getManager().get(getModel().getId());	
			checkResult.setAssessment(assessment);
		}
		//设置审核人为当前登录用户
		User user = UserUtil.getPrincipal(getRequest());
		checkResult.setChecker(user);
		getManager().auditSave(checkResult);
		return SUCCESS;
	}
	
	/**
	 * 风险评估启动
	 */
	public String start() {
		return "start";
	}
	
	/**
	 * 保存风险评估启动记录
	 */
	public String startSave() {
		if (getModel().getId() != null ){
			Assessment assessment = getManager().get(getModel().getId());	
			assessment.setState(AssessMentConstants.EVAL_IS_START_STATE);
			getManager().save(assessment);
		}
		return SUCCESS;
	}
	/**
	 * 获取该风险评估对象所选择的专家成员信息
	 * @return
	 */
	
  public String getMembers() {
		StringBuffer members = new StringBuffer();
		if (getModel().getId() != null ){
			List<AsseMember> AsseMembers = asseMemberManager.getAsseMembers(getModel().getId(), AssessMentConstants.EXPERT_MEMBER);
	  	if (CollectionUtils.isNotEmpty(AsseMembers)){
		    for (AsseMember asseMember : AsseMembers) {
		    	members.append(asseMember.getExpert().getName());
		    	members.append(",");
		    }
		    if (members.length() > 0 && members.toString().lastIndexOf(",") > 0){
		    	jsonMembers = members.toString().substring(0, members.toString().length() -1 );
		    }
	  	}
		}
    return "members";
  }
  
	/**
	 * 获取该风险评估对象所选择的专家成员信息
	 * @return
	 */
  public String getLeaders() {
		StringBuffer leaders = new StringBuffer();
		if (getModel().getId() != null ){
			List<AsseMember> AsseMembers = asseMemberManager.getAsseMembers(getModel().getId(), AssessMentConstants.EXPERT_LEADER);
	  	if (CollectionUtils.isNotEmpty(AsseMembers)){
		    for (AsseMember asseMember : AsseMembers) {
		    	leaders.append(asseMember.getExpert().getName());
		    	leaders.append(",");
		    }
		    if (leaders.length() > 0 && leaders.toString().lastIndexOf(",") > 0){
		    	jsonLeaders = leaders.toString().substring(0, leaders.toString().length() -1 );
		    }
	  	}
		}
    return "leaders";
  }
  
	/**
	 * 风险评估上报
	 */
	public String result() {
		return "result";
	}
	
	/**
	 * 保存风险评估上报结果信息
	 */
	public String resultSave() {
		if (getModel().getId() != null ){
			Assessment assessment = getManager().get(getModel().getId());
			assessment.setState(AssessMentConstants.EVAL_IS_OVER_STATE);
			getManager().save(assessment);
		}
		return SUCCESS;
	}
	
  /**
   * 返回评估风险等级集合
   * @return list
   */
  public Map<String, String> getAssessmentLevels() {
    return AssessMentConstants.ASSESSMENT_LEVEL;
  }
  
	/**
	 * 打印风险评估申请表
	 */
	public String print() {
		if (getModel().getId() != null ){
			checkResult = getManager().getCheckResult(getModel().getId());
		}
		return "print";
	}
	
	/**
	 * 风险评估查看
	 */
	public String view() {
		return "view";
	}
	
	/**
	 * 
	 * @return
	 */
	public String[] getJsonTitles() {
		return jsonTitles;
	}

	public void setJsonTitles(String[] jsonTitles) {
		this.jsonTitles = jsonTitles;
	}

	public CheckResult getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(CheckResult checkResult) {
		this.checkResult = checkResult;
	}

	public Integer getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Integer assessmentId) {
		this.assessmentId = assessmentId;
	}

	public String getJsonMembers() {
		return jsonMembers;
	}

	public void setJsonMembers(String jsonMembers) {
		this.jsonMembers = jsonMembers;
	}

	public String getJsonLeaders() {
		return jsonLeaders;
	}

	public void setJsonLeaders(String jsonLeaders) {
		this.jsonLeaders = jsonLeaders;
	}
}
