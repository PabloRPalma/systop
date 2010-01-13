package com.systop.fsmis.statistics.supervisor.webapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.Supervisor;
import com.systop.fsmis.statistics.supervisor.service.SupervisorStatisticsManager;

/**
 * 信息员信息统计Action
 * 
 * @author zzg
 * 
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupervisorStatisticsAction extends
		DefaultCrudAction<Supervisor, SupervisorStatisticsManager> {

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private DeptManager deptManager;

	/**
	 * 统计信息员举报情况
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String statisticReportCount() {
		// 创建分页查询对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		// 查询举报次数大于零的信息员
		StringBuffer hql = new StringBuffer(
				"from Supervisor where reportCount > 0 ");

		// 只查询登录用户所属部门下的信息员
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if(dept == null){
			addActionError("获取用户信息失败,请重新登录!");
			return "statisticreportcount";
		}
		List args = new ArrayList();
		if (dept != null) {
			if (dept.getChildDepts().size() > 0) {
				hql.append("and dept.serialNo like ? ");
				args.add("%" + dept.getSerialNo() + "%");
			} else {
				hql.append("and dept.id = ? ");
				args.add(dept.getId());
			}
		}
		hql.append("order by reportCount desc");

		page = getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);

		// 设置统计数据
		StringBuffer cvsData = new StringBuffer();
		if (items.size() > 0) {
			// 获取元素个数计数
			int i = 0;
			for (Supervisor Sp : items) {
				if (Sp.getName() != null) {
					// 统计图最多显示10个
					if (i >= 10) {
						break;
					}
					i++;
					cvsData.append(Sp.getName()).append(";");
					cvsData.append(Sp.getReportCount().toString());
					cvsData.append("\\n");
				}
			}
		}
		getRequest().setAttribute("result", cvsData.toString());
		return "statisticreportcount";
	}

	/**
	 * 按部门统计信息员
	 */
	@SuppressWarnings("unchecked")
	public String statisticByDept() {
		StringBuffer hql = new StringBuffer("from Dept where 1=1 ");
		List args = new ArrayList();
		// 获取当前用户所属部门
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if(dept == null){
			addActionError("获取用户信息失败,请重新登录!");
			return "statisticbydept";
		}
		if (dept != null) {
			// 非顶级部门
			if (dept.getParentDept() != null) {
				if (dept.getChildDepts().size() > 0) {
					hql.append("and serialNo like ? ");
					args.add("%" + dept.getSerialNo() + "%");
				} else {
					hql.append("and id = ? ");
					args.add(dept.getId());
				}
			} else {// 顶级部门只显示直属部门
				hql.append("and id = ? ");
				args.add(dept.getId());
				if (dept.getChildDepts().size() > 0) {
					for (Dept de : dept.getChildDepts()) {
						hql.append("or id = ? ");
						args.add(de.getId());
					}
				}
			}
		}
		List<Dept> depts = deptManager.query(hql.toString(), args.toArray());
		// 设置统计数据
		StringBuffer cvsData = new StringBuffer();
		if (depts.size() > 0) {
			for (Dept dp : depts) {
				if (dp.getName() != null) {
					cvsData.append(dp.getName()).append(";");
					if (dp.getSupervisors() != null) {
						cvsData.append(dp.getSupervisors().size());
					}
					cvsData.append("\\n");
				}
			}
		}
		getRequest().setAttribute("result", cvsData.toString());
		return "statisticbydept";
	}
}
