package com.systop.fsmis.member.webapp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.member.service.MemberManager;
import com.systop.fsmis.model.Member;

/**
 * 委员会成员管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MemberAction extends ExtJsCrudAction<Member, MemberManager> {

	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 保存委员会成员信息
	 */
	@Override
	public String save() {
		getModel().setCounty(loginUserService.getLoginUserCounty(getRequest()));
		return super.save();
	}
	
	/**
	 * 企业信息查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteria());
		restorePageData(page);
		return INDEX;
	}
	
	/**
	 * 设定查询条件
	 */
	private DetachedCriteria setupDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Member.class);
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			criteria.createAlias("county", "county");
			criteria.add(Restrictions.eq("county.id", county.getId()));
		}
		if (StringUtils.isNotBlank(getModel().getName())) {
			criteria.add(Restrictions.like("name", MatchMode.ANYWHERE
					.toMatchString(getModel().getName())));
		}
		if (StringUtils.isNotBlank(getModel().getDept())) {
			criteria.add(Restrictions.like("dept", MatchMode.ANYWHERE
					.toMatchString(getModel().getDept())));
		}
		return setupSort(criteria);
	}
}
