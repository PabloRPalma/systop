package com.systop.fsmis.urgentcase.uctype.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentType;
import com.systop.fsmis.urgentcase.ucgroup.service.UcGroupManager;
import com.systop.fsmis.urgentcase.uctype.service.UcTypeManager;

/**
 * 应急事件类别action
 * 
 * @author yj
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UcTypeAction extends DefaultCrudAction<UrgentType, UcTypeManager> {
	/**
	 * 登陆用户信息管理
	 */
	@Autowired
	private LoginUserService loginUserService;
	/**
	 * 组管理
	 */
	@Autowired
	private UcGroupManager ucGroupManager;

	/**
	 * 保存类别
	 */
	@Override
	public String save() {
		try {
			Dept dept = loginUserService.getLoginUserCounty(getRequest());
			if (dept == null) {
				addActionError("当前用户部门为空");
			}
			getModel().setCounty(dept);
			getManager().save(getModel());
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}

	/**
	 * 类别查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer sql = new StringBuffer("from UrgentType ut where ");
		Dept dept = loginUserService.getLoginUserCounty(getRequest());
		if (dept != null) {
			sql.append(" ut.county.id = ?");
			page = getManager().pageQuery(page, sql.toString(), dept.getId());
			restorePageData(page);
		}
		return INDEX;
	}

	/**
	 * 删除，会级联删除该类别下的组
	 */
	public String remove() {
		List<UrgentGroup> ugList = ucGroupManager.query(
				"from UrgentGroup ug where ug.urgentType.id = ? ", getModel().getId());

		for (UrgentGroup ug : ugList) {
			ucGroupManager.remove(ug);
		}
		getManager().remove(getModel());
		return SUCCESS;
	}
}
