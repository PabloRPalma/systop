package com.systop.fsmis.statistics.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.MatchMode;
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
		Dept dept;
		String dataString;
		if (deptId != null && StringUtils.isNotBlank(deptId)) {
			dept = deptManager.get(Integer.valueOf(deptId));
		} else {
			dept = county;
		}
		if (dept.getChildDepts().size() > 0) {// 区县或市
			dataString = getDataStringForCountyOrCity(dept, beginDate, endDate);
		} else {// 直属部门
			dataString = getDataStringForUser(dept, beginDate, endDate);
		}
		return dataString;
	}

	/**
	 * 针对区县或市
	 * 
	 * @param dept
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getDataStringForCountyOrCity(Dept dept, Date beginDate,
			Date endDate) {
		StringBuffer cvsData = new StringBuffer();
		List<Dept> depts = deptManager.query(
				"from Dept d where d.parentDept.id=? or d.id=?", dept.getId(), dept
						.getId());
		if (CollectionUtils.isNotEmpty(depts)) {
			for (Dept dp : depts) {
				logger.info("子部门:{}", dp.getName());
				StringBuffer sqlTemp = new StringBuffer(
						"select ulh.dept.name,count(ulh.id) from UserLoginHistory ulh where 1=1 ");
				List args = new ArrayList();
				if (dp.getType().equals(DeptConstants.TYPE_DEPT) || dp == dept) {// 区县下的部门无子部门,或者是其本身例如新华区本身
					sqlTemp.append("and ulh.dept.id = ? ");
					args.add(dp.getId());
				} else {// 市级下的区县有子部门
					sqlTemp.append("and ulh.dept.serialNo like ? ");
					args.add(MatchMode.START.toMatchString(dp.getSerialNo()));
				}
				if (beginDate != null && endDate != null) {
					sqlTemp.append("and ulh.loginTime between ? and ? ");
					args.add(beginDate);
					args.add(endDate);
				}
				sqlTemp.append(" group by ulh.dept.id,ulh.dept.name");
				logger.info("sql:{}", sqlTemp.toString());
				List<Object[]> result = getDao().query(sqlTemp.toString(),
						args.toArray());
				if (CollectionUtils.isNotEmpty(result)) {
					cvsData.append(dp.getName() + ";").append(result.get(0)[1] + "\\n");
				}
			}
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
				StringBuffer sqlTemp = new StringBuffer(
						"select ulh.user.name,count(ulh.id) from UserLoginHistory ulh where  1=1 ");
				List args = new ArrayList();
				sqlTemp.append(" and ulh.user.id=? ");
				args.add(u.getId());
				if (beginDate != null && endDate != null) {
					sqlTemp.append("and ulh.loginTime between ? and ? ");
					args.add(beginDate);
					args.add(endDate);
				}
				sqlTemp.append(" group by ulh.user.name");
				List<Object[]> result = getDao().query(sqlTemp.toString(),
						args.toArray());
				if (CollectionUtils.isNotEmpty(result)) {
					cvsData.append(u.getName() + ";").append(result.get(0)[1] + "\\n");

				}
			}
		}
		return cvsData.toString();
	}
}
