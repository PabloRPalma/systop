package com.systop.fsmis.fscase.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.service.FsCaseCountyViewManager;
import com.systop.fsmis.model.FsCase;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FsCaseCountyViewAction extends ExtJsCrudAction<FsCase, FsCaseCountyViewManager> {
	private List<Dept> deptList;
	private String isMultipleCase;
	@SuppressWarnings("unused")
	@Autowired
	private DeptManager deptManager;

	@Autowired
	private LoginUserService loginUserService;

	// 查询起始时间
	private Date caseBeginTime;
	// 查询截至时间
	private Date caseEndTime;
	// 是否联合整治案件
	private String isJointCase;

	/**
	 * 列出所有市级以下区县方法
	 */
	@SuppressWarnings("unchecked")
	public String listAllDepts() {
		String hql = "from Dept d where d.type = '1' and d.parentDept <> null order by d.deptSort";
		deptList = getManager().getDao().query(hql);

		return "listAllDepts";
	}

	/**
	 * 得到指定区县下所有食品安全事件方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String index() {
		if (loginUserService == null
				|| loginUserService.getLoginUser(getRequest()) == null) {
			addActionError("请先登录!");
			return INDEX;
		}
		Page page = new Page(Page.start(getPageNo(), getPageSize()),
				getPageSize());
		StringBuffer hql = new StringBuffer(
				"from FsCase fc where fc.county.id = ? ");
		List args = new ArrayList();
		args.add(getModel().getCounty().getId());

		if (StringUtils.isNotBlank(getModel().getTitle())) {
			hql.append("and fc.title like ? ");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));

		}
		if (StringUtils.isNotBlank(getModel().getStatus())) {
			hql.append("and fc.status = ? ");
			args.add(getModel().getStatus());
		}
		if (StringUtils.isNotBlank(getModel().getCode())) {
			hql.append("and fc.code = ? ");
			args.add(getModel().getCode());
		}
		// 区分一般/综合(单体/多体)案件
		if (StringUtils.isNotBlank(isMultipleCase)) {
			hql.append("and fc.isMultiple=? ");
			args.add(isMultipleCase);
		}

		// 根据事发时间区间查询
		if (caseBeginTime != null && caseEndTime != null) {
			hql.append("and fc.caseTime >= ? and fc.caseTime <= ? ");
			args.add(caseBeginTime);
			args.add(caseEndTime);
		}
		// 查是否是上报案件
		if (StringUtils.isNotBlank(getModel().getIsSubmited())) {
			hql.append("and fc.isSubmited = ? ");
			args.add(getModel().getIsSubmited());
		}
		// 查只是联合案件
		if (StringUtils.isNotBlank(isJointCase)
				&& isJointCase.equals(FsConstants.Y)) {
			hql.append("and fc.caseSourceType=? ");
			args.add(CaseConstants.CASE_SOURCE_TYPE_JOINTASK);
		}
		// 查非联合案件
		if (StringUtils.isNotBlank(isJointCase)
				&& isJointCase.equals(FsConstants.N)) {
			hql.append("and fc.caseSourceType != ? ");
			args.add(CaseConstants.CASE_SOURCE_TYPE_JOINTASK);
		}
		hql.append("order by fc.caseTime desc,fc.status");
		page = getManager().pageQuery(page, hql.toString(), args.toArray());

		restorePageData(page);

		items = page.getData();

		return INDEX;
	}

	/**
	 * 列出当前区县下各类食品安全事件方法
	 * 
	 * @return
	 */
	public String deptCaseCount() {
		String baseHql = "from FsCase fc where fc.county.id = ? and fc.isSubmited = ? ";

		// 单体/多体
		String simpleHql = baseHql
				+ "and fc.isMultiple = ? and fc.caseSourceType <> ? ";
		// 联合整治
		String jionHql = baseHql
				+ "and fc.isMultiple = ? and fc.caseSourceType = ? ";

		// 单体
		getRequest().setAttribute(
				"singleFsCaseCount",
				getManager().query(
						simpleHql,
						new Object[] { getModel().getCounty().getId(),
								FsConstants.N, FsConstants.N,
								CaseConstants.CASE_SOURCE_TYPE_JOINTASK })
						.size());
		// 多体
		getRequest().setAttribute(
				"multipleFsCaseCount",
				getManager().query(
						simpleHql,
						new Object[] { getModel().getCounty().getId(),
								FsConstants.N, FsConstants.Y,
								CaseConstants.CASE_SOURCE_TYPE_JOINTASK })
						.size());
		// 联合整治
		getRequest().setAttribute(
				"joinFsCaseCount",
				getManager().query(
						jionHql,
						new Object[] { getModel().getCounty().getId(),
								FsConstants.N, FsConstants.N,
								CaseConstants.CASE_SOURCE_TYPE_JOINTASK })
						.size());

		return "deptCaseCount";
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	/**
	 * 单体事件状态列表返回页面:无颜色
	 */
	@SuppressWarnings("unchecked")
	public Map getStateMap() {
		Map StateMap = new LinkedHashMap();
		StateMap.putAll(CaseConstants.CASE_MAP);

		return StateMap;
	}

	/**
	 * 单体事件状态列表返回页面:带颜色
	 */
	@SuppressWarnings("unchecked")
	public Map getStateColorMap() {
		Map StateColorMap = new LinkedHashMap();
		StateColorMap.putAll(CaseConstants.CASE_COLOR_MAP);

		return StateColorMap;
	}

	public String getIsMultipleCase() {
		return isMultipleCase;
	}

	public void setIsMultipleCase(String isMultipleCase) {
		this.isMultipleCase = isMultipleCase;
	}
}
