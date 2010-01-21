package com.systop.fsmis.statistics.user.webapp;

import java.util.Date;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.model.UserLoginHistory;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.statistics.user.service.UserStatisticsManager;

/**
 * 用户登录记录统计
 * 
 * @author yj
 * 
 */
@SuppressWarnings( { "serial" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserLoginStatisticsAction extends
		DefaultCrudAction<UserLoginHistory, UserStatisticsManager> {
	/** 查询起始时间 */
	private Date beginDate;

	/** 查询结束时间 */
	private Date endDate;
	/** 部门编号 */
	private String deptId;

	/**
	 * 登陆用户信息管理
	 */
	@Autowired
	private LoginUserService loginUserService;
	/**
	 * 部门管理
	 */
	@Autowired
	private DeptManager deptManager;

	/**
	 * 用户登录记录统计
	 * 
	 * @return
	 */
	public String statisticUser() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getUserStatistic(beginDate, endDate,
					county, deptId);
			getRequest().setAttribute("csvData", cvsData);
			//页面回显所选部门
			if (StringUtils.isNotBlank(deptId)) {
				Dept dp = deptManager.findObject("from Dept d where d.id=?", Integer
						.valueOf(deptId));
				getRequest().setAttribute("deptName", dp.getName());
			} else {
				getRequest().setAttribute("deptName", county.getName());
			}
		}
		return "userStatistics";
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
