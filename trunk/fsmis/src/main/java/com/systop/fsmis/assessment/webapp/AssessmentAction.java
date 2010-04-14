package com.systop.fsmis.assessment.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.util.ResourceBundleUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.assessment.service.AsseMemberManager;
import com.systop.fsmis.assessment.service.AssessmentAttachManager;
import com.systop.fsmis.assessment.service.AssessmentManager;
import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.model.AsseMember;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.AssessmentAttach;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.FsCase;

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
	 * 事件管理Manager
	 */
	@Autowired
	private FsCaseManager fsCaseManager;
	
	@Autowired
	private LoginUserService loginUserService;
	
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
		Dept dept = loginUserService.getLoginUserCounty(getRequest());
		List<Object> args = new ArrayList<Object>();
		if (dept != null) {
			hql.append(" and ass.fsCase.county.id = ?");
			args.add(dept.getId());
	  }
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
	 * 编辑评估申请信息
	 */
	@Override
	public String edit() {
		if (getModel().getFsCase() != null) {
			FsCase fsCase = fsCaseManager.get(getModel().getFsCase().getId());
			getModel().setFsCase(fsCase);
		}
		return super.edit();
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
			if (getModel().getFsCase() != null) {
				FsCase fsCase = fsCaseManager.get(getModel().getFsCase().getId());
				getModel().setFsCase(fsCase);
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
		Set<AsseMember> asseMembers = assessment.getAsseMemberse();
		//删除评估附件信息
		for (AssessmentAttach assAttach : attaches) {
			String realPath = assAttach.getPath();
			if (StringUtils.isNotBlank(realPath)) {
				assessmentAttachManager.removeAttach(assAttach, getRealPath(realPath));
			}
		}
		
		//删除评估审核信息		
		getManager().delCheckResults(assessment);
		
		//删除评估所选专家信息
		for (AsseMember asseMember : asseMembers) {
			asseMemberManager.remove(asseMember);
		}		
		
		//删除评估对象信息
		getManager().remove(assessment);
		return SUCCESS;
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
		if (user != null ) {
			checkResult.setChecker(user);
			getManager().auditSave(checkResult);
		}
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
		  //给该条评估申请所选择的专家发送短信
			asseMemberManager.sendTaskMessage(assessment);
		}
		return SUCCESS;
	}

	/**
	 * 获取该风险评估对象所选择的专家组长信息
	 * @return
	 */
  public String getLeaders() {
		if (getModel().getId() != null ){
			jsonLeaders = asseMemberManager.getLeaders(getModel().getId());
		}
    return "leaders";
  }
  
	/**
	 * 获取该风险评估对象所选择的专家成员信息
	 * @return
	 */
  public String getMembers() {
		if (getModel().getId() != null ){
			jsonMembers = asseMemberManager.getMembers(getModel().getId());
		}
    return "members";
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
	 * 查询风险评估的审核记录
	 */
	public String listCheckResult() {
		if (assessmentId != null ) {
			Page page = PageUtil.getPage(getPageNo(), getPageSize());
			StringBuffer sql = new StringBuffer("from CheckResult cr where 1=1 ");
			sql.append(" and cr.assessment.id = ?");
			sql.append(" order by cr.isAgree,cr.checkTime desc");
			page = getManager().pageQuery(page, sql.toString(), assessmentId);
			restorePageData(page);
		}
		
		return "listCheckRst";
	}
	
	/**
	 * 查询风险评估的评估进度
	 */
	public String process() {
		return "process";
	}
	
	/**
	 * WelCome页面显示最新的5条风险评估信息
	 */
	public String viewWelcome() {
		List args = new ArrayList();
		Dept dept = loginUserService.getLoginUserCounty(getRequest());
		StringBuffer hql = new StringBuffer("from Assessment ass where 1=1 ");
		if (dept != null) {
			hql.append(" and ass.fsCase.county.id = ?");
			args.add(dept.getId());
		}
		hql.append(" order by ass.askDate desc");
		String pageSize = ResourceBundleUtil.getString(ResourceBundle.getBundle("application"), "welcome.assessment.pageSize", "5");
		Page page = PageUtil.getPage(1, Integer.valueOf(pageSize));
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return "viewWelcome";
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
