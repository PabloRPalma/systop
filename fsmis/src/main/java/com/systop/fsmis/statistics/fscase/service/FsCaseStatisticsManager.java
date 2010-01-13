package com.systop.fsmis.statistics.fscase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.model.FsCase;

/**
 * 事件统计管理
 * 
 * @author yj
 * 
 */
@Service
public class FsCaseStatisticsManager extends BaseGenericsManager<FsCase> {

	/**
	 * 部门管理
	 */
	@Autowired
	private DeptManager deptManager;

	/**
	 * 按事件状态进行统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFsCaseStatusStatistic(Date beginDate, Date endDate,
			Dept county) {
		List<Object[]> result = null;
		StringBuffer sql = new StringBuffer(
				"select fc.status,count(fc.status) from FsCase fc where 1=1");
		List args = new ArrayList();
		if (county.getParentDept() != null) {
			sql.append(" and fc.county.id = ?");
			args.add(county.getId());
		}
		sql.append(" and fc.caseTime between ? and ?  group by fc.status");
		args.add(beginDate);
		args.add(endDate);
		result = getDao().query(sql.toString(), args.toArray());
		// 各状态转化，为页面显示做准备
		//FIXME:这个状态的顺序调整一下
		for (Object[] o : result) {
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
		}
		StringBuffer cvsData = new StringBuffer();
		if (result.size() > 0) {
			for (Object[] objs : result) {
				cvsData.append(objs[0] + ";").append(objs[1] + "\\n");
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
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFsCaseCountyStatistic(Date beginDate, Date endDate) {
		StringBuffer cvsData = new StringBuffer();
		Dept d = deptManager.findObject("from Dept d where d.parentDept is null");
		String sql = "from Dept d where d.type = ? and d.parentDept.id = ?";
		List<Dept> depts = deptManager.query(sql, DeptConstants.TYPE_COUNTY, d
				.getId());
		for (Dept dp : depts) {
			String sqlTemp = "select fc.county.name,count(fc.id) from FsCase fc where fc.county.id=? and fc.caseTime between ? and ? group by fc.county.id ";
			List<Object[]> result = getDao().query(sqlTemp, dp.getId(), beginDate,
					endDate);
			if (result.size() > 0) {
				for (Object[] objs : result) {
					cvsData.append(objs[0] + ";").append(objs[1] + "\\n");
				}
			} else {
				cvsData.append(dp.getName() + ";").append("0" + "\\n");
			}

		}
		return cvsData.toString();
	}
}
