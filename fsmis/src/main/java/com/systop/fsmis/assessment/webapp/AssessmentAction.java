package com.systop.fsmis.assessment.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.systop.fsmis.assessment.service.AssessmentAttachManager;
import com.systop.fsmis.assessment.service.AssessmentManager;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.AssessmentAttach;
import com.systop.fsmis.model.CheckResult;

/**
 * 风险评估信息管理的struts2 Action。
 * 
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
	 * 打印风险评估申请表
	 */
	public String print() {
		if (getModel().getId() != null ){
			checkResult = getManager().getCheckResult(getModel().getId());
		}
		return "print";
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
	
}
