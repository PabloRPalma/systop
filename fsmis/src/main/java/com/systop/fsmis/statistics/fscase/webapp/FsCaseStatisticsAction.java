package com.systop.fsmis.statistics.fscase.webapp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.statistics.fscase.service.FsCaseStatisticsManager;

/**
 * 事件统计
 * 
 * @author yj
 * 
 */
@SuppressWarnings( { "serial" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FsCaseStatisticsAction extends
		DefaultCrudAction<FsCase, FsCaseStatisticsManager> {
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
	 * 按事件区县统计
	 * 
	 * @return
	 */
	public String statisticFsCaseCounty() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			initDateRange();
			String cvsData = getManager().getFsCaseCountyStatistic(beginDate,
					endDate);
			getRequest().setAttribute("csvData", cvsData);
		}
		return "fsCaseStatisticsCounty";
	}

	/**
	 * 按事件状态统计
	 * 
	 * @return
	 */
	public String statisticFsCaseStatus() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			initDateRange();
			String cvsData = getManager().getFsCaseStatusStatistic(beginDate,
					endDate, county);
			getRequest().setAttribute("csvData", cvsData);

		}
		return "fsCaseStatisticsStatus";
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
}
