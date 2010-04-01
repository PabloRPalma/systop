package com.systop.fsmis.statistics.fscase.webapp;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.statistics.StatisticsConstants;
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
	/** 登陆用户信息管理 */
	@Autowired
	private LoginUserService loginUserService;
	/** 页面选择同比或环比 */
	private String compareSort;
	/** 按年还是月 */
	private String yearOrMonth;
	/** 状态 */
	private String status;
	/** 部门编号 */
	private String deptId;
	/** 部门管理 */
	@Autowired
	private DeptManager deptManager;
	/** 类别 */
	private String types;

	/**
	 * 按事件区县统计
	 * 
	 * @return
	 */
	public String statisticByCounty() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getFsCaseCounty(beginDate, endDate, status);
			getRequest().setAttribute("csvData", cvsData);
		}
		return "fsCaseByCounty";
	}

	/**
	 * 按事件状态统计
	 * 
	 * @return
	 */
	public String statisticByStatus() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getFsCaseStatus(beginDate, endDate, county,
					deptId);
			getRequest().setAttribute("csvData", cvsData);
			// 页面回显所选部门
			setDeptName(deptId, county);
		}
		return "fsCaseByStatus";
	}

	/**
	 * 事件按年或按月同比或环比统计
	 * 
	 * @return
	 */
	public String statisticByNum() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getFsCaseByYearOrMonth(beginDate,
					yearOrMonth, county, compareSort);
			getRequest().setAttribute("csvData", cvsData);
		}
		return "fsCaseNum";
	}

	/**
	 * 按事件类别统计
	 * 
	 * @return
	 */
	public String statisticByType() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getFsCaseByType(beginDate, endDate,
					yearOrMonth, county);
			getRequest().setAttribute("csvData", cvsData);
			// 准备数据，页面图表下显示各种类别
			String graph = getManager().getGraph();
			getRequest().setAttribute("graph", graph);
			types = getManager().getTypes();
		}
		return "fsCaseByType";
	}

	/**
	 * 按事件派遣环节统计
	 * 
	 * @return
	 */
	public String statisticBySendType() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getFsCaseSendType(beginDate, endDate,
					county, deptId);
			getRequest().setAttribute("csvData", cvsData);
			// 页面回显所选部门
			setDeptName(deptId, county);
		}
		return "fsCaseBySendType";
	}

	/**
	 * 按事件来源统计
	 * 
	 * @return
	 */
	public String statisticBySource() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getFsCaseSource(beginDate, endDate, county,
					deptId);
			getRequest().setAttribute("csvData", cvsData);
			// 页面回显所选部门
			setDeptName(deptId, county);
		}
		return "fsCaseBySource";
	}

	/**
	 * 按事件时间统计
	 * 
	 * @return
	 */
	public String statisticByTime() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			String cvsData = getManager().getFsCaseByTime(beginDate, endDate,
					yearOrMonth, county, status, deptId);
			getRequest().setAttribute("csvData", cvsData);
			// 页面回显所选部门
			setDeptName(deptId, county);
		}
		return "fsCaseByTime";
	}

	/**
	 * 设置页面部门名称
	 * 
	 * @param deptId
	 * @param county
	 */
	private void setDeptName(String deptId, Dept county) {
		if (StringUtils.isNotBlank(deptId)) {
			Dept dp = deptManager.findObject("from Dept d where d.id=?", Integer
					.valueOf(deptId));
			getRequest().setAttribute("deptName", dp.getName());
		} else {
			getRequest().setAttribute("deptName", county.getName());
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

	@SuppressWarnings("unchecked")
	public Map<String, String> getCompareMap() {
		Map compareMap = new LinkedHashMap();
		compareMap.put(StatisticsConstants.SAME_COMPARE, "同比");
		compareMap.put(StatisticsConstants.LOOP_COMPARE, "环比");
		return compareMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getStatusMap() {
		Map statusMap = new LinkedHashMap();
		statusMap.put(CaseConstants.CASE_UN_RESOLVE, "未派遣");
		statusMap.put(CaseConstants.CASE_PROCESSING, "处理中");
		statusMap.put(CaseConstants.CASE_PROCESSED, "已处理");
		statusMap.put(CaseConstants.CASE_RETURNED, "已退回");
		statusMap.put(CaseConstants.CASE_CLOSED, "已核实");
		return statusMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getYearOrMonthMap() {
		Map yearOrMonthMap = new LinkedHashMap();
		yearOrMonthMap.put(StatisticsConstants.YEAR, "年");
		yearOrMonthMap.put(StatisticsConstants.MONTH, "月");
		return yearOrMonthMap;
	}

	public String getCompareSort() {
		return compareSort;
	}

	public void setCompareSort(String compareSort) {
		this.compareSort = compareSort;
	}

	public String getYearOrMonth() {
		return yearOrMonth;
	}

	public void setYearOrMonth(String yearOrMonth) {
		this.yearOrMonth = yearOrMonth;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
}
