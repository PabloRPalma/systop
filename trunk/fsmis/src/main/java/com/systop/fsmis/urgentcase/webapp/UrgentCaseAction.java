package com.systop.fsmis.urgentcase.webapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
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
	 * json返回结果
	 */
	private Map<String, String> checkResult;

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
		Dept dept = loginUserService.getLoginUserCounty(getRequest());
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
			Dept dept = loginUserService.getLoginUserCounty(getRequest());
			if (dept == null) {
				addActionError("您所在的区县为空...");
			}
			getModel().setCounty(dept);
			getModel().setCreateTime(Calendar.getInstance().getTime());
			getManager().save(getModel());
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	/**
	 * 保存应急事件审核结果
	 */
	public String saveCheckResult() {
		checkResult = Collections.synchronizedMap(new HashMap<String, String>());
		String caseId = getRequest().getParameter("caseId").toString();
		logger.info("应急事件ID：{}", caseId);
		String isAgree = getRequest().getParameter("isAgree").toString();
		logger.info("是否同意：{}", isAgree);
		String reason = getRequest().getParameter("reason").toString();
		logger.info("审核意见：{}", reason);
		User checker = loginUserService.getLoginUser(getRequest());
		if (checker != null) {
			getManager().saveCheckResult(caseId, isAgree, reason, checker);
			checkResult.put("result", "success");
		} else {
			checkResult.put("result", "error");
		}
				
		return "jsonRst";
	}
	
	/**
	 * 查询应急事件的审核记录
	 */
	public String listCheckResult() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer sql = new StringBuffer("from CheckResult cr where 1=1 ");
		sql.append(" and cr.urgentCase.id = ?");
		sql.append(" order by cr.isAgree,cr.checkTime desc");
		page = getManager().pageQuery(page, sql.toString(), getModel().getId());
		restorePageData(page);
		
		return "listCheckRst";
	}
	
	public Map<String, String> getIsAgreeMap() {
		return FsConstants.YN_MAP;
	}
	
	public Map<String, String> getCheckResult() {
  	return checkResult;
  }

	public void setCheckResult(Map<String, String> checkResult) {
  	this.checkResult = checkResult;
  }
}
