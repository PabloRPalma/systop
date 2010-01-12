package com.systop.fsmis.statistics.webapp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.statistics.service.FsCaseStatisticsManager;

/**
 * 事件统计
 * 
 * @author yj
 * 
 */
public class FsCaseStatisticsAction extends
		ExtJsCrudAction<FsCase, FsCaseStatisticsManager> {
	/** 查询起始时间 */
	private Date beginDate;

	/** 查询结束时间 */
	private Date endDate;
	/**
	 * 登陆用户信息管理
	 */
	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 按事件状态统计
	 * 
	 * @return
	 */
	public String statisticFsCaseStatus() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county == null) {
			addActionError("当前用户部门为空");
		}
		List<Object[]> statResult = getManager().getFsCaseStatusStatistic(
				beginDate, endDate,county);
		
		
		
		return null;
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
}
