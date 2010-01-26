package com.systop.fsmis.statistics.corp.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.statistics.corp.service.CorpStatisticsManager;

/**
 * 企业信息统计Action
 * 
 * @author zzg
 * 
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CorpStatisticsAction extends
		DefaultCrudAction<Corp, CorpStatisticsManager> {

	@Autowired
	private LoginUserService loginUserService;
	
	@Autowired
	private DeptManager deptManager;

	/** 查询起始时间 */
	private Date beginDate;

	/** 查询结束时间 */
	private Date endDate;

	/** 部门查询关键字 */
	private String deptId;

	/** 显示数目 */
	private Integer displayNum;
	/**
	 * 企业涉及案件情况统计
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String statisticFsCaseCount() {
		// 只查询登录用户所属部门下的企业
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if(dept == null){
			addActionError("获取用户信息失败,请重新登录!");
			return "statisticfscasecount";
		}
		// 创建分页查询对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		List args = new ArrayList();
		StringBuffer hql = new StringBuffer(
				"select cp from Corp cp, FsCase fs where cp.id = fs.corp ");
		// 根据时间查询
		if (beginDate != null && endDate != null) {
			hql.append("and fs.caseTime between ? and ? ");
			args.add(beginDate);
			args.add(endDate);
		}
		// 根据部门关键字查询
		if (deptId != null && StringUtils.isNotBlank(deptId)) {
			dept = deptManager.get(Integer.valueOf(deptId));
			getRequest().setAttribute("deptName", dept.getName());
		}
		if (dept != null) {
			if (dept.getChildDepts().size() > 0) {
				hql.append("and cp.dept.serialNo like ? ");
				args.add(MatchMode.START.toMatchString(dept.getSerialNo()));
			} else {
				hql.append("and cp.dept.id = ? ");
				args.add(dept.getId());
			}
		}
		hql.append("group by cp.id order by count(*) desc");

		page = getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);

		// 设置统计数据
		StringBuffer cvsData = new StringBuffer();
		if (items != null && items.size() > 0) {
			// 默认显示10个
			if (displayNum == null) {
				displayNum = 10;
			}
			List<Corp> corps = new ArrayList();
			corps.addAll(items);
			for (int i = 0; (i < corps.size() && i < displayNum); i++) {
				if (corps.get(i).getName() != null
						&& corps.get(i).getFsCases() != null
						&& corps.get(i).getFsCases().size() > 0) {
					cvsData.append(corps.get(i).getName()).append(";");
					if (corps.get(i).getFsCases() != null) {
						cvsData.append(corps.get(i).getFsCases().size());
					}
					cvsData.append("\\n");
				}
			}
		}else{
			cvsData.append("无数据;0\\n");
		}
		getRequest().setAttribute("result", cvsData.toString());
		return "statisticfscasecount";
	}
	
	/**
	 * 按部门统计企业
	 */
	@SuppressWarnings("unchecked")
	public String statisticByDept() {
		// 获取当前用户所属部门
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if (dept == null) {
			addActionError("获取用户信息失败,请重新登录!");
			return "statisticbydept";
		}
		
		StringBuffer hql = new StringBuffer("from Dept where 1=1 ");
		List args = new ArrayList();
	
		if (dept != null) {
			// 非顶级部门
			if (dept.getParentDept() != null) {
				if (dept.getChildDepts().size() > 0) {
					hql.append("and serialNo like ? ");
					args.add(MatchMode.START.toMatchString(dept.getSerialNo()));
				} else {
					hql.append("and id = ? ");
					args.add(dept.getId());
				}
			} else {// 顶级部门显示区县
				hql.append("and type = ? and parentDept.id = ? ");
				args.add(DeptConstants.TYPE_COUNTY);
				args.add(dept.getId());
			}
		}
		
		List<Dept> depts = deptManager.query(hql.toString(), args.toArray());
		// 设置统计数据
		StringBuffer cvsData = new StringBuffer();
		if (depts != null && depts.size() > 0) {
			for (Dept dp : depts) {
				if (dp.getName() != null) {
					cvsData.append(dp.getName()).append(";");
					if (dp.getCorps() != null) {
						cvsData.append(dp.getCorps().size());
					}
					cvsData.append("\\n");
				}
			}
		}else{
			cvsData.append("无数据;0\\n");
		}
		getRequest().setAttribute("result", cvsData.toString());
		return "statisticbydept";
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

	public Integer getDisplayNum() {
		return displayNum;
	}

	public void setDisplayNum(Integer displayNum) {
		this.displayNum = displayNum;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
