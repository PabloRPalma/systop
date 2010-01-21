package com.systop.fsmis.statistics.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.model.UserLoginHistory;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.core.service.BaseGenericsManager;

/**
 * 用户登录记录统计管理
 * 
 * @author yj
 * 
 */
@Service
public class UserStatisticsManager extends
		BaseGenericsManager<UserLoginHistory> {
	/** 部门管理 */
	@Autowired
	private DeptManager deptManager;
	/** 用户管理 */
	@Autowired
	private UserManager userManager;

	/**
	 * 按登录历史记录统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 *          当前用户的区县
	 * @param deptId
	 *          页面选择的区县
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getUserStatistic(Date beginDate, Date endDate, Dept county,
			String deptId) {
		if (beginDate == null && endDate == null) {
			beginDate = new Date();
			endDate = new Date();
		}
		String dataString = null;
		if (deptId == null || deptId.equals("")) {// 未选择
			if (county.getParentDept() != null) {// 区县
				dataString = getDataStringForCounty(county.getId(), beginDate, endDate);
			} else {// 市级
				dataString = getDataStringForCity(beginDate, endDate);
			}
		} else {// 选择部门
			Dept dt = deptManager.findObject("from Dept d where d.id =?", Integer
					.valueOf(deptId));
			if (dt.getParentDept() != null) {// 选择区县或直属部门
				if (dt.getType().equals(DeptConstants.TYPE_COUNTY)) {// 区县
					dataString = getDataStringForCounty(dt.getId(), beginDate, endDate);
				} else {// 直属部门
					dataString = getDataStringForUser(dt, beginDate, endDate);
				}
			} else {// 选择市级
				dataString = getDataStringForCity(beginDate, endDate);
			}
		}
		logger.info("拼接数据{}", dataString);
		return dataString;
	}

	/**
	 * 拼接数据 针对区县
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getDataStringForCounty(Integer deptId, Date beginDate,
			Date endDate) {
		StringBuffer cvsData = new StringBuffer();
		List<Dept> depts = deptManager.query(
				"from Dept d where d.parentDept.id=? or d.id=?", deptId, deptId);
		for (Dept dp : depts) {
			String sqlTemp = "select ulh.dept.name,count(ulh.id) from UserLoginHistory ulh where ulh.dept.id=? and ulh.loginTime between ? and ?  group by ulh.dept.id ";
			List<Object[]> result = getDao().query(sqlTemp, dp.getId(), beginDate,
					endDate);
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
	 * 拼接数据 针对市
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getDataStringForCity(Date beginDate, Date endDate) {
		Dept d = deptManager.findObject("from Dept d where d.parentDept is null");
		List<Dept> depts = deptManager.query(
				"from Dept d where d.parentDept.id = ?", d.getId());// 所有部门，包括区县以及直属部门
		List<Object[]> r = new ArrayList();
		for (Dept dp : depts) {
			if (dp.getType().equals(DeptConstants.TYPE_DEPT)) {// 直属部门
				String sqlTemp = "select ulh.dept.name,count(ulh.id) from UserLoginHistory ulh where ulh.dept.id=? and ulh.loginTime between ? and ?  group by ulh.dept.id ";
				List<Object[]> result = getDao().query(sqlTemp, dp.getId(), beginDate,
						endDate);
				Object[] o = new Object[2];
				o[0] = dp.getName();
				if (CollectionUtils.isNotEmpty(result)) {
					o[1] = result.get(0)[1];
				} else {// 该直属部门下无人员登录
					o[1] = 0;
				}
				r.add(o);
			} else {// 区县 记录该区县下的所有部门人员的登录次数，一并归为这个区县下的
				List<Dept> ds = deptManager.query(
						"from Dept d where d.parentDept.id=? or d.id=?", dp.getId(), dp
								.getId());
				Object[] o = new Object[2];
				o[0] = dp.getName();
				int count = 0;
				for (Dept dTemp : ds) {// 区县下的各个部门
					String sqlTemp = "select ulh.dept.name,count(ulh.id) from UserLoginHistory ulh where ulh.dept.id=? and ulh.loginTime between ? and ?  group by ulh.dept.id ";
					List<Object[]> result = getDao().query(sqlTemp, dTemp.getId(),
							beginDate, endDate);
					if (CollectionUtils.isNotEmpty(result)) {// 累加
						count += Integer.valueOf(result.get(0)[1].toString());
					}
				}
				o[1] = count;
				r.add(o);
			}
		}
		StringBuffer cvsData = new StringBuffer();
		if (CollectionUtils.isNotEmpty(r)) {
			for (Object[] objs : r) {
				cvsData.append(objs[0] + ";").append(objs[1] + "\\n");
			}
		} else {
			cvsData.append("nothing;").append("0\\n");
		}
		return cvsData.toString();
	}

	/**
	 * 查看某个部门登录记录对应的是人员的登录情况
	 * 
	 * @param dt
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getDataStringForUser(Dept dt, Date beginDate, Date endDate) {
		List<User> us = userManager.query("from User u where u.dept.id=?", dt
				.getId());
		StringBuffer cvsData = new StringBuffer();
		if (CollectionUtils.isNotEmpty(us)) {
			for (User u : us) {
				String sqlTemp = "select ulh.user.name,count(ulh.id) from UserLoginHistory ulh where ulh.user.id=? and ulh.loginTime between ? and ?  group by ulh.user.id ";
				List<Object[]> result = getDao().query(sqlTemp, u.getId(), beginDate,
						endDate);
				if (CollectionUtils.isNotEmpty(result)) {
					for (Object[] objs : result) {
						cvsData.append(objs[0] + ";").append(objs[1] + "\\n");
					}
				} else {
					cvsData.append(u.getName() + ";").append("0" + "\\n");
				}
			}
		} else {// 部门下无人员
			cvsData.append("nothing;").append("0\\n");
		}
		return cvsData.toString();
	}
}
