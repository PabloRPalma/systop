package com.systop.fsmis.fscase.report.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.casetype.service.CaseTypeManager;
import com.systop.fsmis.fscase.report.service.ReportManager;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskDetail;

/**
 * 事件部门上报管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ReportAction extends ExtJsCrudAction<FsCase, ReportManager> {

	@Autowired
	private LoginUserService loginUserService;
	
	@Autowired
  private CaseTypeManager caseTypeManager;
	
	/**
	 * 查询起始事件
	 */
	private Date beginTime;
	/**
	 * 查询截止事件
	 */
	private Date endTime;
	/**
	 * 任务
	 */
	private Task task;
	/**
	 * 任务明细
	 */
	private TaskDetail taskDetail;
	/**
	 * 对应的处理企业
	 */
	private Corp corp;
	/**
	 * 企业名称
	 */
	private String corpName;
	/**
	 * 所有企业信息ajax结果
	 */
	private String[] jsonCorps;
	/**
	 * 企业信息ajax结果
	 */
	private Map corpInfo;
	
	/**
	 * 部门上报事件查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer hql = new StringBuffer("from FsCase fc where fc.reportDept.id is not null ");
		List args = new ArrayList();
		//查询事件类型是‘部门上报’
		hql.append(" and fc.caseSourceType = ?");
		args.add(CaseConstants.CASE_SOURCE_TYPE_DEPTREPORT);
		//取得登陆用户部门所属区县
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			hql.append(" and fc.county.id = ?");
			args.add(county.getId());
		}
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			hql.append(" and fc.title like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));
		}
		if (beginTime != null && endTime != null) {
			hql.append("and fc.caseTime >= ? and fc.caseTime <= ? ");
			args.add(beginTime);
			args.add(endTime);
		}
		hql.append(" order by fc.caseTime desc");
		page = getManager().pageQuery(page, hql.toString(), args.toArray());
		restorePageData(page);
		
		return INDEX;
	}
	
	/**
	 * 保存部门上报事件
	 */
	@Override
	public String save() {
		try {
			Dept county = loginUserService.getLoginUserCounty(getRequest());
			Dept dept = loginUserService.getLoginUserDept(getRequest());
			if (county == null || dept == null) {
				addActionError("您所在的区县或部门为空...");
				getRequest().setAttribute("levelone", getLevelOne());
				return INPUT;
			}
			//任务所属部门
			taskDetail.setDept(dept);
			//事件所属区县
			getModel().setCounty(county);
			//上报部门
			getModel().setReportDept(dept);
			//事件状态为‘以核实’
			getModel().setStatus(CaseConstants.CASE_CLOSED);
			//单体事件类型为‘部门上报添加的单体事件’
			getModel().setCaseSourceType(CaseConstants.CASE_SOURCE_TYPE_DEPTREPORT);
			CaseType cType = null;
	    if (typetwoId != null) {
	      cType = getManager().getDao().get(CaseType.class, typetwoId);
	    } else {
	      if (typeoneId != null) {
	        cType = getManager().getDao().get(CaseType.class, typeoneId);
	      }
	    }
	    //编辑事件类型
	    getModel().setCaseType(cType);
			getManager().saveReportInfoOfCase(getModel(), task, taskDetail, corp, corpName);
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			getRequest().setAttribute("levelone", getLevelOne());
			return INPUT;
		}
	}
	
	/**
	 * 编辑上报事件
	 */
	@Override
	public String edit() {
		initPageInfo();
		getRequest().setAttribute("levelone", getLevelOne());
    if (getModel().getId() != null) {
      //为类别赋默认值，用于编辑时显示
      CaseType caseType = getModel().getCaseType();
      if ( caseType != null) {
      	if (caseType.getCaseType() != null) {
      		oneId = caseType.getCaseType().getId();
      		twoId = caseType.getId();
      	} else {
      		oneId = caseType.getId();
      	}
      }
    }
		return super.edit();
	}
	
	/**
	 * 查看上报事件
	 */
	@Override
	public String view() {
		initPageInfo();
		return super.view();
	}
	
	/**
	 * 删除上报事件
	 */
	@Override
	public String remove() {
		getManager().removeCase(getModel().getId());
		return SUCCESS;
	}
	
	/**
	 * 初始化页面数据
	 */
	private void initPageInfo() {
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if (getModel().getId() != null) {
			//取得上报事件的任务信息
			task = getManager().getTaskOfCase(getModel().getId());
			corp = getModel().getCorp();
			if (corp != null) {
				corpName = corp.getName();
			}
			if (task != null) {
				taskDetail = getManager().getTaskDetailOfTask(task.getId(), dept.getId());
			}
		}
	}
	
	/**
   * 用于显示事件类型_一级
   */
  public List getLevelOne() {
    return caseTypeManager.getLevelOneList();
  }

  /**
   * 用于显示事件类型_二级
   */
  public String getLevelTwo() {
    typeRst = caseTypeManager.getLevelTwoList(Integer.valueOf(typeId));
    return "jsonRst";
  }
  
  /**
   * 取得区县下所有的企业
   */
  public String getCorpOfCounty() {
  	Dept county = loginUserService.getLoginUserCounty(getRequest());
  	if (county != null) {
  		jsonCorps = getManager().getCorpOfCounty(county.getId());
  	}
  	
  	return "jsonCorp";
  }
  
  /**
   * 取得企业信息
   */
  public String getCorpByName() {
  	Dept county = loginUserService.getLoginUserCounty(getRequest());
  	if (county != null) {
  		corpInfo = getManager().getCorpMapByName(corpName, county.getId());;
  	}
  	
  	return "jcorp";
  }
  
	public Date getBeginTime() {
  	return beginTime;
  }

	public void setBeginTime(Date beginTime) {
  	this.beginTime = beginTime;
  }

	public Date getEndTime() {
  	return endTime;
  }

	public void setEndTime(Date endTime) {
  	this.endTime = endTime;
  }
	
	public Task getTask() {
  	return task;
  }

	public void setTask(Task task) {
  	this.task = task;
  }

	public TaskDetail getTaskDetail() {
  	return taskDetail;
  }

	public void setTaskDetail(TaskDetail taskDetail) {
  	this.taskDetail = taskDetail;
  }
	
	public Corp getCorp() {
  	return corp;
  }

	public void setCorp(Corp corp) {
  	this.corp = corp;
  }
	
	public String getCorpName() {
  	return corpName;
  }

	private String typeId;

  private Integer typeoneId;

  private Integer typetwoId;

  private Integer oneId;

  private Integer twoId;

  private List typeRst;
  
	public void setCorpName(String corpName) {
  	this.corpName = corpName;
  }
	
	public String getTypeId() {
  	return typeId;
  }

	public void setTypeId(String typeId) {
  	this.typeId = typeId;
  }

	public Integer getTypeoneId() {
  	return typeoneId;
  }

	public void setTypeoneId(Integer typeoneId) {
  	this.typeoneId = typeoneId;
  }

	public Integer getTypetwoId() {
  	return typetwoId;
  }

	public void setTypetwoId(Integer typetwoId) {
  	this.typetwoId = typetwoId;
  }

	public Integer getOneId() {
  	return oneId;
  }

	public void setOneId(Integer oneId) {
  	this.oneId = oneId;
  }

	public Integer getTwoId() {
  	return twoId;
  }

	public void setTwoId(Integer twoId) {
  	this.twoId = twoId;
  }

	public List getTypeRst() {
  	return typeRst;
  }

	public void setTypeRst(List typeRst) {
  	this.typeRst = typeRst;
  }

	public String[] getJsonCorps() {
  	return jsonCorps;
  }

	public void setJsonCorps(String[] jsonCorps) {
  	this.jsonCorps = jsonCorps;
  }
	
	public Map getCorpInfo() {
  	return corpInfo;
  }

	public void setCorpInfo(Map corpInfo) {
  	this.corpInfo = corpInfo;
  }
}
