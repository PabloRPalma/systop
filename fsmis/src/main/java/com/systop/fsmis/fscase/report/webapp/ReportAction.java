package com.systop.fsmis.fscase.report.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.systop.fsmis.fscase.report.service.ReportManager;
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
	
	/**
	 * 查询起始事件
	 */
	private Date beginTime;
	
	/**
	 * 查询截止事件
	 */
	private Date endTime;

	private Task task;
	
	private TaskDetail taskDetail;
	
	private Corp corp;
	
	private String corpName;
	
	/**
	 * 部门上报事件查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer hql = new StringBuffer("from FsCase fc where fc.reportDept.id is not null ");
		List args = new ArrayList();
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
				return INPUT;
			}
			//任务所属部门
			taskDetail.setDept(dept);
			//事件所属区县
			getModel().setCounty(county);
			//getModel().getCorp().setDept(county);
			//上报部门
			getModel().setReportDept(dept);
			//事件状态为‘以核实’
			getModel().setStatus(CaseConstants.CASE_CLOSED);
			getManager().saveReportInfoOfCase(getModel(), task, taskDetail, corp, corpName);
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	/**
	 * 编辑上报事件
	 */
	@Override
	public String edit() {
		initPageInfo();
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
			if (getModel().getCorp() != null) {
				corp = getModel().getCorp();
				if (corp != null) {
					corpName = corp.getName();
				}
			}
			if (task != null) {
				taskDetail = getManager().getTaskDetailOfTask(task.getId(), dept.getId());
			}
		}
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

	public void setCorpName(String corpName) {
  	this.corpName = corpName;
  }
}
