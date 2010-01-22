package com.systop.fsmis.statistics.fscase.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.DateUtil;
import com.systop.core.util.StringUtil;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.casetype.service.CaseTypeManager;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.SendType;
import com.systop.fsmis.statistics.StatisticsConstants;
import com.systop.fsmis.statistics.fscase.util.Util;

/**
 * 事件统计管理
 * 
 * @author yj
 * 
 */
@Service
public class FsCaseStatisticsManager extends BaseGenericsManager<FsCase> {

	/** 部门管理 */
	@Autowired
	private DeptManager deptManager;
	/** 事件类别管理 */
	@Autowired
	private CaseTypeManager caseTypeManager;
	/** 事件派遣环节管理 */
	@Autowired
	private SendTypeManager sendTypeManager;

	/**
	 * 按事件状态进行统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFsCaseStatus(Date beginDate, Date endDate, Dept county,
			String deptId) {
		List<Object[]> result = null;
		StringBuffer sql = new StringBuffer(
				"select fc.status,count(fc.status) from FsCase fc where 1=1");
		List args = new ArrayList();
		if (deptId == null || deptId.equals("")) {// 未选择部门
			if (county.getParentDept() != null) {// 不是市级，按当前登录人员所属部门
				sql.append("and fc.county.id = ? ");
				args.add(county.getId());
			}
		} else {// 选择部门
			Dept dp = deptManager.findObject("from Dept d where d.id=?", Integer
					.valueOf(deptId));
			if (dp.getParentDept() != null) {// 选择部门不是市级
				sql.append("and fc.county.id = ? ");
				args.add(Integer.valueOf(deptId));
			}
		}
		sql.append(" and fc.caseTime between ? and ?  group by fc.status");
		args.add(beginDate);
		args.add(endDate);
		result = getDao().query(sql.toString(), args.toArray());

		StringBuffer cvsData = new StringBuffer();
		if (CollectionUtils.isNotEmpty(result)) {
			for (Object[] o : result) {
				// 各状态转化，为页面显示做准备
				if (o[0].toString().equals(CaseConstants.CASE_PROCESSED)) {
					o[0] = "已处理";
				}
				if (o[0].toString().equals(CaseConstants.CASE_PROCESSING)) {
					o[0] = "处理中";
				}
				if (o[0].toString().equals(CaseConstants.CASE_UN_RESOLVE)) {
					o[0] = "未派遣";
				}
				if (o[0].toString().equals(CaseConstants.CASE_RETURNED)) {
					o[0] = "已退回";
				}
				if (o[0].toString().equals(CaseConstants.CASE_CLOSED)) {
					o[0] = "已核实";
				}
				cvsData.append(o[0] + ";").append(o[1] + "\\n");
			}
		} else {
			cvsData.append("nothing;").append("0\\n");
		}
		return cvsData.toString();
	}

	/**
	 * 按事件区县进行统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFsCaseCounty(Date beginDate, Date endDate, String status) {
		StringBuffer cvsData = new StringBuffer();
		// 市级部门
		Dept d = deptManager.findObject("from Dept d where d.parentDept is null");
		// 各区县部门
		String sql = "from Dept d where d.type = ? and d.parentDept.id = ?";
		List<Dept> depts = deptManager.query(sql, DeptConstants.TYPE_COUNTY, d
				.getId());
		for (Dept dp : depts) {
			StringBuffer sqlTemp = new StringBuffer(
					"select fc.county.name,count(fc.id) from FsCase fc where fc.county.id=?  ");
			List args = new ArrayList();
			args.add(dp.getId());
			// 事件状态
			if (StringUtils.isNotBlank(status)) {
				sqlTemp.append("and fc.status = ? ");
				args.add(status);
			}
			sqlTemp.append("and fc.caseTime between ? and ? group by fc.county.id");
			args.add(beginDate);
			args.add(endDate);
			List<Object[]> result = getDao()
					.query(sqlTemp.toString(), args.toArray());
			if (CollectionUtils.isNotEmpty(result)) {
				for (Object[] objs : result) {
					cvsData.append(objs[0] + ";").append(objs[1] + "\\n");
				}
			} else {
				cvsData.append(dp.getName() + ";").append("0" + "\\n");
			}

		}
		return cvsData.toString();
	}

	/**
	 * 按事件数量按年或按月同比或环比统计
	 * 
	 * @param beginDate
	 * @param yearOrMonth
	 *          按年或按月
	 * @param county
	 * @param compareSort
	 *          同比或环比
	 * @return
	 */
	public String getFsCaseByYearOrMonth(Date beginDate, String yearOrMonth,
			Dept county, String compareSort) {
		String dataString = null;
		if (StringUtils.isNotEmpty(yearOrMonth)) {
			if (yearOrMonth.equals(StatisticsConstants.YEAR)) {// 按年
				dataString = getByYear(beginDate, county);
			} else {// 按月
				if (compareSort.equals(StatisticsConstants.SAME_COMPARE)) {// 按同比
					dataString = getByMonthBySame(beginDate, county);
				} else {// 按环比
					dataString = getByMonthByLoop(beginDate, county);
				}
			}
		} else {
			dataString = getByYear(beginDate, county);
		}

		return dataString;
	}

	/**
	 * 按事件数量月同比
	 * 
	 * @param date
	 * @param county
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String getByMonthBySame(Date date, Dept county) {
		StringBuffer cvsData = new StringBuffer();
		int year = Util.getYear(date);
		int month = Util.getMonth(date);
		// 查询用户选定的月
		List<Object[]> userCheckedResult = getResultByMonth(year, month + 1, county);
		// 查询同比的月
		List<Object[]> sameResult = getResultByMonth(year - 1, month + 1, county);
		if (CollectionUtils.isNotEmpty(sameResult)) {
			cvsData.append(
					(year - 1) + "年-" + StringUtil.zeroPadding(month + 1, 2) + "月;")
					.append(sameResult.get(0)[1] + "\\n");
			if (CollectionUtils.isNotEmpty(userCheckedResult)) {
				cvsData.append(
						year + "年-" + StringUtil.zeroPadding(month + 1, 2) + "月;").append(
						userCheckedResult.get(0)[1] + "\\n");
			} else {
				cvsData.append(year + "年-" + StringUtil.zeroPadding(month + 1, 2)
						+ "月;0\\n");
			}
		} else {
			cvsData.append((year - 1) + "年-" + StringUtil.zeroPadding(month + 1, 2)
					+ "月;0\\n");
			if (CollectionUtils.isNotEmpty(userCheckedResult)) {
				cvsData.append(
						year + "年-" + StringUtil.zeroPadding(month + 1, 2) + "月;").append(
						userCheckedResult.get(0)[1] + "\\n");
			} else {
				cvsData.append(year + "年-" + StringUtil.zeroPadding(month + 1, 2)
						+ "月;0\\n");
			}
		}
		return cvsData.toString();
	}

	/**
	 * 按事件数量月环比
	 * 
	 * @param date
	 * @param county
	 * @return
	 */
	private String getByMonthByLoop(Date date, Dept county) {
		StringBuffer cvsData = new StringBuffer();
		int year = Util.getYear(date);
		int month = Util.getMonth(date);
		// 小一个月的那个日期
		Date d = com.systop.core.util.DateUtil.add(date, Calendar.MONTH, -1);
		int yearNew = Util.getYear(d);
		int monthNew = Util.getMonth(d);
		logger.info("环比年月{},{}", year, month);
		logger.info("环比新年月{},{}", yearNew, monthNew);
		// 查询用户选定的年月
		List<Object[]> userCheckedResult = getResultByMonth(year, month + 1, county);
		// 查询环比的年月
		List<Object[]> loopResult = getResultByMonth(yearNew, monthNew + 1, county);
		if (CollectionUtils.isNotEmpty(loopResult)) {
			cvsData.append(
					yearNew + "年-" + StringUtil.zeroPadding(monthNew + 1, 2) + "月;")
					.append(loopResult.get(0)[1] + "\\n");
			if (CollectionUtils.isNotEmpty(userCheckedResult)) {
				cvsData.append(
						year + "年-" + StringUtil.zeroPadding(month + 1, 2) + "月;").append(
						userCheckedResult.get(0)[1] + "\\n");
			} else {
				cvsData.append(year + "年-" + StringUtil.zeroPadding(month + 1, 2)
						+ "月;0\\n");
			}
		} else {
			cvsData.append(yearNew + "年-" + StringUtil.zeroPadding(monthNew + 1, 2)
					+ "月;0\\n");
			if (CollectionUtils.isNotEmpty(userCheckedResult)) {
				cvsData.append(
						year + "年-" + StringUtil.zeroPadding(month + 1, 2) + "月;").append(
						userCheckedResult.get(0)[1] + "\\n");
			} else {
				cvsData.append(year + "年-" + StringUtil.zeroPadding(month + 1, 2)
						+ "月;0\\n");
			}
		}
		return cvsData.toString();
	}

	/**
	 * 按事件数量年统计
	 * 
	 * @param date
	 * @param county
	 * @return 数据字符串
	 */
	private String getByYear(Date date, Dept county) {
		StringBuffer cvsData = new StringBuffer();
		int year = Util.getYear(date);
		// 查询用户选定的年
		List<Object[]> userCheckedResult = getResultByYear(year, county);
		// 查询同比的年
		List<Object[]> sameResult = getResultByYear(year - 1, county);
		if (CollectionUtils.isNotEmpty(sameResult)) {
			cvsData.append((year - 1) + "年;").append(sameResult.get(0)[1] + "\\n");
			if (CollectionUtils.isNotEmpty(userCheckedResult)) {
				cvsData.append(year + "年;").append(userCheckedResult.get(0)[1] + "\\n");
			} else {
				cvsData.append(year + "年;0\\n");
			}
		} else {
			cvsData.append((year - 1) + "年;0\\n");
			if (CollectionUtils.isNotEmpty(userCheckedResult)) {
				cvsData.append(year + "年;").append(userCheckedResult.get(0)[1] + "\\n");
			} else {
				cvsData.append(year + "年;0\\n");
			}
		}
		return cvsData.toString();
	}

	/**
	 * 按事件数量年统计-- 针对年
	 * 
	 * @param year
	 * @param county
	 * @return 数据集合
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getResultByYear(int year, Dept county) {
		StringBuffer sql = new StringBuffer(
				" select year(fc.caseTime), count(fc.id) from FsCase fc where year(fc.caseTime)= ? ");
		List args = new ArrayList();
		args.add(year);
		if (county.getParentDept() != null) {// 区县
			sql.append(" and fc.county.id= ? ");
			args.add(county.getId());
		}
		sql.append(" group by year(fc.caseTime)");
		return getDao().query(sql.toString(), args.toArray());
	}

	/**
	 * 按事件数量月统计--针对月
	 * 
	 * @param year
	 * @param month
	 * @param county
	 * @return 数据集合
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getResultByMonth(int year, int month, Dept county) {
		StringBuffer sql = new StringBuffer(
				" select month(fc.caseTime),count(fc.id) from FsCase fc where year(fc.caseTime)=? and month(fc.caseTime)=? ");
		List args = new ArrayList();
		args.add(year);
		args.add(month);
		if (county.getParentDept() != null) {// 区县
			sql.append(" and fc.county.id=? ");
			args.add(county.getId());
		}
		sql.append(" group by year(fc.caseTime)");
		return getDao().query(sql.toString(), args.toArray());

	}

	/**
	 * 事件按类别，按年或按月同比或环比统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param yearOrMonth
	 * @param county
	 * @return
	 */
	public String getFsCaseByType(Date beginDate, Date endDate,
			String yearOrMonth, Dept county) {
		String dataString = null;
		if (StringUtils.isNotEmpty(yearOrMonth)) {
			if (yearOrMonth.equals(StatisticsConstants.YEAR)) {// 按年
				dataString = getByYearByType(beginDate, endDate, county);
			} else {// 按月
				dataString = getByMonthByType(beginDate, endDate, county);
			}
		} else {// 默认
			dataString = getByYearByType(beginDate, endDate, county);
		}
		return dataString;
	}

	/**
	 * 事件按类别,按月 分级别 2级的累加到1级上 -针对月
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getByMonthByType(Date beginDate, Date endDate, Dept county) {
		// 所有类别
		List<CaseType> caseTypes = caseTypeManager
				.query("from CaseType where caseType.id is null");
		// 获得开始日期与结束日期时间段的月份
		List<String> months = getDateMonth(beginDate, endDate);
		List<Object[]> result = new ArrayList();
		for (String month : months) {// month 形如2009-01
			logger.info("统计年月：{}", month);
			Date d = null;
			try {
				d = DateUtil.convertStringToDate("yyyy-MM", month);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date eDate = Util.getLastDayOfMonth(d);
			Date sDate = Util.getFirstDayOfMonth(d);
			for (CaseType ct : caseTypes) {
				StringBuffer sql = new StringBuffer(
						" select count(fc.id),fc.caseType.id from FsCase fc where 1=1 ");
				List args = new ArrayList();
				if (county.getParentDept() != null) {// 区县
					sql.append(" and fc.county.id= ? ");
					args.add(county.getId());
				}
				sql.append(" and (fc.caseType.id =? ");
				args.add(ct.getId());
				// 2级类别发生的事件归属1级类别
				List<CaseType> cs = caseTypeManager.query(
						"from CaseType where caseType.id =?", ct.getId());
				if (CollectionUtils.isNotEmpty(cs)) {
					for (CaseType c : cs) {
						sql.append(" or fc.caseType.id =?  ");
						args.add(c.getId());
					}
				}
				sql.append(" )and fc.caseTime between ? and ?");
				args.add(sDate);
				args.add(eDate);
				List<Object[]> r = getDao().query(sql.toString(), args.toArray());
				Object[] data = new Object[3];
				data[1] = month;
				data[2] = ct.getId();
				if (CollectionUtils.isNotEmpty(r)) {
					data[0] = r.get(0)[0];
				} else {
					data[0] = 0;
				}
				result.add(data);
			}
		}
		// 组装数据
		HashMap<String, ArrayList> yearData = new HashMap<String, ArrayList>();
		for (Object[] o : result) {
			if (yearData == null || !(yearData.containsKey(o[1].toString()))) {
				ArrayList<Object[]> alo = new ArrayList<Object[]>();
				alo.add(o);
				yearData.put(o[1].toString(), alo);
			} else {
				ArrayList<Object[]> tempalo = yearData.get(o[1].toString());
				tempalo.add(o);
				yearData.put(o[1].toString(), tempalo);
			}
		}
		StringBuffer cvsData = new StringBuffer();
		for (String year : yearData.keySet()) {
			ArrayList<Object[]> al = yearData.get(year);
			cvsData.append(year).append("月;").append(praseString(al, caseTypes))
					.append("\\n");
		}
		logger.info("年度月{}", cvsData.toString());
		return cvsData.toString();
	}

	/**
	 * 按类别按年统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getByYearByType(Date beginDate, Date endDate, Dept county) {
		List<CaseType> caseTypes = caseTypeManager
				.query("from CaseType where caseType.id is null");
		// 所有按年按类别数据
		List<Object[]> result = getResultByYearByType(county, caseTypes);
		List<Object[]> tempResult;
		// 用户选择年数据
		tempResult = this.limitYear(beginDate, endDate, result);
		// 年-数据(各个类别)
		HashMap<String, ArrayList> yearData = new HashMap<String, ArrayList>();
		for (Object[] o : tempResult) {
			if (yearData == null || !(yearData.containsKey(o[1].toString()))) {
				ArrayList<Object[]> alo = new ArrayList<Object[]>();
				alo.add(o);
				yearData.put(o[1].toString(), alo);
			} else {
				ArrayList<Object[]> tempalo = yearData.get(o[1].toString());
				tempalo.add(o);
				yearData.put(o[1].toString(), tempalo);
			}
		}
		StringBuffer cvsData = new StringBuffer();
		for (String year : yearData.keySet()) {
			ArrayList<Object[]> al = yearData.get(year);
			cvsData.append(year).append("年;").append(praseString(al, caseTypes))
					.append("\\n");
		}
		logger.info("年度{}", cvsData.toString());
		return cvsData.toString();
	}

	/**
	 * 按年类别查询 分级别 2级的累加到1级上 -针对年
	 * 
	 * @param county
	 * @param caseTypes
	 * @return 数据集合
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getResultByYearByType(Dept county,
			List<CaseType> caseTypes) {
		List<Object[]> result = new ArrayList();
		for (CaseType ct : caseTypes) {
			StringBuffer sql = new StringBuffer(
					" select count(fc.id),year(fc.caseTime),fc.caseType.id from FsCase fc where 1=1 ");
			List args = new ArrayList();
			if (county.getParentDept() != null) {// 区县
				sql.append(" and fc.county.id= ? ");
				args.add(county.getId());
			}
			sql.append(" and (fc.caseType.id =? ");
			args.add(ct.getId());
			List<CaseType> cs = caseTypeManager.query(
					"from CaseType where caseType.id =?", ct.getId());
			if (CollectionUtils.isNotEmpty(cs)) {
				for (CaseType c : cs) {
					sql.append(" or fc.caseType.id =?  ");
					args.add(c.getId());
				}
			}
			sql.append(" )group by year(fc.caseTime)");
			List<Object[]> r = getDao().query(sql.toString(), args.toArray());
			if (CollectionUtils.isNotEmpty(r)) {
				result.add(r.get(0));
			}
		}
		logger.info("长度：{}", result.size());
		return result;
	}

	/**
	 * 针对按类别页面需要的字符串
	 * 
	 * @param result
	 * @param caseTypes
	 * @return
	 */
	private String praseString(List<Object[]> result, List<CaseType> caseTypes) {
		String returnString = "";
		for (CaseType temp : caseTypes) {
			String valueString = "0;";		
				for (Object[] o : result) {
					if (o[2].toString().equals(temp.getId().toString())) {
						valueString = o[0] + ";";
						break;
					}
				}
			returnString += valueString;
		}
		returnString = returnString.substring(0, returnString.length() - 1);
		return returnString;
	}

	/**
	 * 返回所有一级类别字符串 页面使用
	 * 
	 * @return
	 */
	public String getGraph() {
		int index = 0;
		String graph = "";
		List<CaseType> caseTypes = caseTypeManager
				.query("from CaseType where caseType.id is null");
		for (CaseType ct : caseTypes) {
			graph += "<graph gid='" + index + "'>" + "<axis>left</axis>" + "<title>"
					+ ct.getName() + "</title>" + "<color>"
					// +getRandColor(index, 255).getRGB()
					+ "</color><color_hover>FF0F00</color_hover><unit></unit></graph>";
			index++;
		}
		return graph;
	}

	/**
	 * 事件按时间统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param yearOrMonth
	 * @param county
	 * @param status
	 * @param deptId
	 * @return
	 */
	public String getFsCaseByTime(Date beginDate, Date endDate,
			String yearOrMonth, Dept county, String status, String deptId) {
		String dataString = null;
		if (StringUtils.isNotBlank(yearOrMonth)) {
			if (yearOrMonth.equals(StatisticsConstants.MONTH)) {// 按月
				dataString = getByMonth(beginDate, endDate, county, status, deptId);
			} else {// 按年
				dataString = getByYear(beginDate, endDate, county, status, deptId);
			}
		} else {// 默认
			dataString = getByYear(beginDate, endDate, county, status, deptId);
		}
		return dataString;
	}

	/**
	 * 按年统计 返回页面组织数据
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @param status
	 * @param deptId
	 * @return
	 */
	private String getByYear(Date beginDate, Date endDate, Dept county,
			String status, String deptId) {
		// 未做时间处理的数据
		List<Object[]> result = this.statisticCountByYear(county, status, deptId);
		List<Object[]> tempResult;
		// 用户选择的年内数据
		tempResult = this.limitYear(beginDate, endDate, result);
		StringBuffer cvsData = new StringBuffer();
		for (Object[] o : tempResult) {
			cvsData.append(o[1] + "年;" + o[0] + "\\n");
		}
		return cvsData.toString();
	}

	/**
	 * 按月统计 返回页面组织数据
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @param status
	 * @param deptId
	 * @return
	 */
	private String getByMonth(Date beginDate, Date endDate, Dept county,
			String status, String deptId) {
		List<Object[]> result = this.statisticCountByMonth(beginDate, endDate,
				county, status, deptId);
		StringBuffer cvsData = new StringBuffer();
		if (CollectionUtils.isNotEmpty(result)) {
			for (Object[] o : result) {
				cvsData.append(o[1] + "月;" + o[0] + "\\n");
			}
		} else {
			cvsData.append("nothing;").append("0\\n");
		}
		return cvsData.toString();
	}

	/**
	 * 按年统计事件发生次数
	 * 
	 * @param county
	 * @param status
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> statisticCountByYear(Dept county, String status,
			String deptId) {
		StringBuffer sql = new StringBuffer(
				" select count(fc.id), year(fc.caseTime) from FsCase fc where 1=1 ");
		List args = new ArrayList();
		logger.info("所选区县：{}", deptId);
		if (deptId == null || deptId.equals("")) {// 未选择部门
			if (county.getParentDept() != null) {// 不是市级，按当前登录人员所属部门
				sql.append("and fc.county.id = ? ");
				args.add(county.getId());
			}
		} else {// 选择部门
			Dept dp = deptManager.findObject("from Dept d where d.id=?", Integer
					.valueOf(deptId));
			if (dp.getParentDept() != null) {// 选择部门不是市级
				sql.append("and fc.county.id = ? ");
				args.add(Integer.valueOf(deptId));
			}
		}
		// 事件状态
		if (StringUtils.isNotBlank(status)) {
			sql.append("and fc.status = ? ");
			args.add(status);
		}
		sql.append(" group by year(fc.caseTime) order by year(fc.caseTime)");
		return getDao().query(sql.toString(), args.toArray());
	}

	/**
	 * 时间段(年)内数据
	 * 
	 * @param sDate
	 *          开始年
	 * @param eDate
	 *          结束年
	 * @param result
	 *          查询数据
	 * @return
	 */
	private List<Object[]> limitYear(Date sDate, Date eDate, List<Object[]> result) {
		// 获得开始，结束时间段的年
		int sYear = Util.getYear(sDate);
		int eYear = Util.getYear(eDate);

		ArrayList<Integer> year = new ArrayList<Integer>();
		List<Object[]> returnValue = new ArrayList<Object[]>();
		// 存储时间段内的各年
		if ((eYear - sYear) > 0) {
			for (int temp = sYear; temp <= eYear; temp++) {
				year.add(temp);
			}
		} else {
			year.add(eYear);
		}
		// 查找符合条件的数据
		for (Integer i : year) {
			if (CollectionUtils.isNotEmpty(result)) {
				for (Object[] o : result) {
					Integer tempYear = null;
					try {
						tempYear = Integer.parseInt(o[1].toString());
						logger.info("年:{},{}", i, tempYear);
					} catch (Exception e) {
						break;
					}
					if (i.intValue() == tempYear) {
						returnValue.add(o);
					}
				}
			} else {
				Object[] data = new Object[3];
				data[0] = 0;
				data[1] = i;
				data[2] = "";
				returnValue.add(data);
			}
		}
		return returnValue;
	}

	/**
	 * 按月为单位发生数量的统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @param status
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> statisticCountByMonth(Date beginDate, Date endDate,
			Dept county, String status, String deptId) {
		// 获得开始日期与结束日期时间段的月份
		List<String> months = getDateMonth(beginDate, endDate);
		List<Object[]> result = new ArrayList();
		for (String month : months) {
			logger.info("统计年月：{}", month);
			Date d = null;
			try {
				d = DateUtil.convertStringToDate("yyyy-MM", month);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date eDate = Util.getLastDayOfMonth(d);
			Date sDate = Util.getFirstDayOfMonth(d);
			StringBuffer sql = new StringBuffer(
					" select year(fc.caseTime),count(fc.id) from FsCase fc where 1=1 ");
			List args = new ArrayList();
			if (deptId == null || deptId.equals("")) {// 未选择部门
				if (county.getParentDept() != null) {// 不是市级 按当前登录人员所属部门
					sql.append("and fc.county.id = ? ");
					args.add(county.getId());
				}
			} else {// 选择部门
				Dept dp = deptManager.findObject("from dept d where d.id=?", deptId);
				if (dp.getParentDept() != null) {// 不是市级
					sql.append("and fc.county.id = ? ");
					args.add(Integer.valueOf(deptId));
				}
			}
			// 事件状态
			if (StringUtils.isNotBlank(status)) {
				sql.append("and fc.status = ? ");
				args.add(status);
			}

			sql.append(" and fc.caseTime between ? and ?");
			args.add(sDate);
			args.add(eDate);
			List<Object[]> r = getDao().query(sql.toString(), args.toArray());
			Object[] data = new Object[2];
			data[1] = StringUtils.substring(month, 2);
			if (CollectionUtils.isNotEmpty(r)) {
				data[0] = r.get(0)[1];
			} else {
				data[0] = 0;
			}
			result.add(data);
		}
		return result;
	}

	/**
	 * 计算两个日期间的所有年月
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return 例如 2009-01，2009-02
	 */
	private List<String> getDateMonth(Date beginDate, Date endDate) {
		// 开始,结束时间的年
		int sYear = Util.getYear(beginDate);
		int eYear = Util.getYear(endDate);
		// 开始,结束时间的月
		int sMonth = Util.getMonth(beginDate);
		int eMonth = Util.getMonth(endDate);
		List<String> month = new ArrayList<String>();
		if ((eYear - sYear) > 0) {// 结束年比开始年大
			for (int temp = sMonth; temp < 12; temp++) {// 开始年剩余月份
				month.add(String.valueOf(sYear) + "-"
						+ StringUtil.zeroPadding(temp + 1, 2));
			}
			for (int temp = sYear + 1; temp <= eYear - 1; temp++) {// 开始,结束年之间的月份
				for (int i = 0; i < 12; i++) {
					month.add(String.valueOf(temp) + "-"
							+ StringUtil.zeroPadding(i + 1, 2));
				}
			}
			for (int temp = 0; temp <= eMonth; temp++) {// 结束年月份
				month.add(String.valueOf(eYear) + "-"
						+ StringUtil.zeroPadding(temp + 1, 2));
			}
		} else if ((eYear - sYear) < 0) {// 结束年比开始年小
			for (int temp = eMonth; temp < 12; temp++) {// 开始年剩余月份
				month.add(String.valueOf(eYear) + "-"
						+ StringUtil.zeroPadding(temp + 1, 2));
			}

			for (int temp = eYear + 1; temp <= sYear - 1; temp++) {// 开始,结束年之间的月份
				for (int i = 0; i < 12; i++) {
					month.add(String.valueOf(eYear) + "-"
							+ StringUtil.zeroPadding(i + 1, 2));
				}
			}
			for (int temp = 0; temp <= eMonth; temp++) {// 结束年月份
				month.add(String.valueOf(sYear) + "-"
						+ StringUtil.zeroPadding(temp + 1, 2));
			}

		}
		if (eYear == sYear) {// 同一年
			if ((eMonth - sMonth) > 0) {
				for (int temp = sMonth; temp <= eMonth; temp++) {
					month.add(String.valueOf(eYear) + "-"
							+ StringUtil.zeroPadding(temp + 1, 2));
				}
			} else if ((eMonth - sMonth) < 0) {
				for (int temp = eMonth; temp <= sMonth; temp++) {
					month.add(String.valueOf(eYear) + "-"
							+ StringUtil.zeroPadding(temp + 1, 2));
				}
			}
			if (eMonth == sMonth) {// 一个月
				month.add(String.valueOf(eYear) + "-"
						+ StringUtil.zeroPadding(sMonth + 1, 2));
			}
		}
		return month;
	}

	/**
	 * 按事件派遣环节统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFsCaseSendType(Date beginDate, Date endDate, Dept county,
			String deptId) {
		List<Object[]> result = new ArrayList();
		List<SendType> sts = sendTypeManager.orderSendType();
		for (SendType st : sts) {
			StringBuffer sql = new StringBuffer(
					"select fc.sendType.name,count(fc.id) from FsCase fc where 1=1");
			List args = new ArrayList();
			if (deptId == null || deptId.equals("")) {// 未选择部门
				if (county.getParentDept() != null) {// 不是市级，按当前登录人员所属部门
					sql.append("and fc.county.id = ? ");
					args.add(county.getId());
				}
			} else {// 选择部门
				Dept dp = deptManager.findObject("from Dept d where d.id=?", Integer
						.valueOf(deptId));
				if (dp.getParentDept() != null) {// 选择部门不是市级
					sql.append("and fc.county.id = ? ");
					args.add(Integer.valueOf(deptId));
				}
			}
			sql.append(" and fc.sendType.id =?");
			args.add(st.getId());
			sql.append(" and fc.caseTime between ? and ? ");
			args.add(beginDate);
			args.add(endDate);
			List<Object[]> r = getDao().query(sql.toString(), args.toArray());
			Object[] data = new Object[2];
			data[0] = st.getName();
			if (CollectionUtils.isNotEmpty(r)) {
				data[1] = r.get(0)[1];
			} else {
				data[1] = 0;
			}
			result.add(data);
		}
		StringBuffer cvsData = new StringBuffer();
		if (CollectionUtils.isNotEmpty(result)) {
			for (Object[] o : result) {
				cvsData.append(o[0] + ";").append(o[1] + "\\n");
			}
		} else {
			cvsData.append("nothing;").append("0\\n");
		}
		return cvsData.toString();
	}

	/**
	 * 按事件信息来源统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFsCaseSource(Date beginDate, Date endDate, Dept county,
			String deptId) {
		List<Object[]> result = null;
		StringBuffer sql = new StringBuffer(
				"select fc.caseSourceType,count(fc.id) from FsCase fc where 1=1");
		List args = new ArrayList();
		if (deptId == null || deptId.equals("")) {// 未选择部门
			if (county.getParentDept() != null) {// 不是市级，按当前登录人员所属部门
				sql.append("and fc.county.id = ? ");
				args.add(county.getId());
			}
		} else {// 选择部门
			Dept dp = deptManager.findObject("from Dept d where d.id=?", Integer
					.valueOf(deptId));
			if (dp.getParentDept() != null) {// 选择部门不是市级
				sql.append("and fc.county.id = ? ");
				args.add(Integer.valueOf(deptId));
			}
		}
		sql.append(" and fc.caseTime between ? and ?  group by fc.caseSourceType");
		args.add(beginDate);
		args.add(endDate);
		result = getDao().query(sql.toString(), args.toArray());

		StringBuffer cvsData = new StringBuffer();
		if (CollectionUtils.isNotEmpty(result)) {
			for (Object[] o : result) {
				// 各状态转化，为页面显示做准备
				if (o[0].toString().equals(CaseConstants.CASE_SOURCE_TYPE_DEPTREPORT)) {
					o[0] = "部门上报";
				}
				if (o[0].toString().equals(CaseConstants.CASE_SOURCE_TYPE_GENERIC)) {
					o[0] = "普通";
				}
				if (o[0].toString().equals(CaseConstants.CASE_SOURCE_TYPE_JOINTASK)) {
					o[0] = "联合整治";
				}
				cvsData.append(o[0] + ";").append(o[1] + "\\n");
			}
		} else {
			cvsData.append("nothing;").append("0\\n");
		}
		return cvsData.toString();
	}

}
