package com.systop.fsmis.statistics.task.webapp;

import java.util.Date;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.statistics.task.service.TaskStatisticsManager;

/**
 * 任务统计
 * 
 * @author zzg
 * 
 */
@SuppressWarnings( { "serial" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskStatisticsAction extends
		DefaultCrudAction<Task, TaskStatisticsManager> {
	/** 查询起始时间 */
	private Date beginDate;

	/** 查询结束时间 */
	private Date endDate;
	
	/** 部门id */
	private String deptId;
	/**
	 * 登陆用户信息管理
	 */
	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private DeptManager deptManager;
	/**
	 * 按任务区县统计
	 * 
	 * @return
	 */
	public String statisticTaskCounty() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getTaskCountyStatistic(beginDate,
					endDate);
			getRequest().setAttribute("csvData", cvsData);
		}else{
			addActionError("获取用户信息失败,请重新登录!");
		}
		return "taskStatisticsCounty";
	}

	/**
	 * 按任务状态统计
	 * 
	 * @return
	 */
	public String statisticTaskStatus() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		// 根据部门关键字查询
		if (deptId != null && StringUtils.isNotBlank(deptId)) {
			county = deptManager.get(Integer.valueOf(deptId));
		} 
		if (county != null) {
			//页面回显所选部门
			getRequest().setAttribute("deptName", county.getName());
			String cvsData = getManager().getTaskStatusStatistic(beginDate,
					endDate, county);
			getRequest().setAttribute("csvData", cvsData);

		}else{
			addActionError("获取用户信息失败,请重新登录!");
		}
		return "taskStatisticsStatus";
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
