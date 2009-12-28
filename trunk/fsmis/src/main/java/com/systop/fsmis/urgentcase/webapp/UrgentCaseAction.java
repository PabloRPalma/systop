package com.systop.fsmis.urgentcase.webapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.model.UrgentCase;
import com.systop.fsmis.urgentcase.service.UrgentCaseManager;

/**
 * 应急事件管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UrgentCaseAction extends ExtJsCrudAction<UrgentCase, UrgentCaseManager> {

	/**
	 * 登陆用户信息管理
	 */
	@Autowired
	private LoginUserService loginUserService;
	
	/**
	 * 应急事件查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer sql = new StringBuffer("from UrgentCase uc where 1=1 ");
		List args = new ArrayList();
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if (dept != null) {
			sql.append(" and uc.county.id = ?");
			args.add(dept.getId());
			if (StringUtils.isNotBlank(getModel().getTitle())) {
				sql.append(" and uc.title like ?");
				args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));
			}
			if (StringUtils.isNotBlank(getModel().getIsAgree())) {
				sql.append(" and uc.isAgree = ?");
				args.add(getModel().getIsAgree());
			}
			sql.append(" order by uc.status,uc.caseTime desc");
			page = getManager().pageQuery(page, sql.toString(), args.toArray());
			restorePageData(page);
		}
		return INDEX;
	}
	
	/**
	 * 保存应急事件
	 */
	@Override
	public String save() {
		try {
			Dept dept = loginUserService.getLoginUserDept(getRequest());
			if (dept == null) {
				addActionError("您所在的部门为空...");
			}
			getModel().setCounty(dept);
			getModel().setCreateTime(Calendar.getInstance().getTime());
			//事件状态
			//getModel().setStatus();
			//是否审核
			//getModel().setIsAgree(isAgree);
			getManager().save(getModel());
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}
}
