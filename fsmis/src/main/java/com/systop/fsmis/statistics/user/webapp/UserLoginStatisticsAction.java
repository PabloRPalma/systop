package com.systop.fsmis.statistics.user.webapp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
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
	 * 用户登录记录统计
	 * 
	 * @return
	 */
	public String statisticUser() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			initDateRange();
			String cvsData = getManager().getUserStatistic(beginDate, endDate,
					county, deptId);
			getRequest().setAttribute("csvData", cvsData);

		}
		return "userStatistics";
	}

	/**
	 * initDateRange私有访法用于初始化时间范围
	 * 
	 */
	private void initDateRange() {
		if (beginDate == null && endDate == null) {
			beginDate = new Date();
			endDate = new Date();
		}
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
	/*	if (deptId != null) {
			Dept d = (Dept) getManager().getDao().findObject(
					"from dept d where d.id=?", deptId);
			return d.getName();
		} else {*/
			return deptId;
		//}
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
