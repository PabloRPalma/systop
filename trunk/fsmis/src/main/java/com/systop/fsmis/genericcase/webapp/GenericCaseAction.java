package com.systop.fsmis.genericcase.webapp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.ApplicationException;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.CaseConstants;
import com.systop.fsmis.casetype.service.CaseTypeManager;
import com.systop.fsmis.genericcase.service.GenericCaseManager;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.GenericCase;



/**
 * 一般事件处理
 * 
 * @author shaozhiyuan
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericCaseAction extends
      DefaultCrudAction<GenericCase, GenericCaseManager>{
	
	@Autowired
	private CaseTypeManager caseTypeManager;
	/**
	 * 查询获得一般事件信息列表，分页查询
	 */
	public String index() {
		Page page = new Page(Page.start(getPageNo(), getPageSize()),
				getPageSize());
		String sql = "from GenericCase gc where isSubmitSj=0 ";
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			sql = sql + "and gc.title like '%" + getModel().getTitle() + "%' ";
		}
		if (StringUtils.isNotBlank(getModel().getStatus())) {
			sql = sql + "and gc.status = '" + getModel().getStatus() + "' ";
		}
		if (StringUtils.isNotBlank(getModel().getCode())) {
			sql = sql + "and gc.code = '" + getModel().getCode() + "' ";
		}
		sql += " order by gc.eventDate desc,gc.status";
		page = getManager().pageQuery(page, sql);
		
		//从短信接收表中查询已核实单体事件短信信息,设置单体事件的核实状态 yanjg add
		items = page.getData();
		/*for(GenericCase se:items){			
			Long hasCheckedCount= mesReceiveManager.getCheckedMsgCountBySingleEvId(se.getId());
			if(hasCheckedCount != null && hasCheckedCount > 0){
				se.setMsgCheckedFlag("1");//设置短信核实标识
			}
		}	*/	
		restorePageData(page);
		return INDEX;
	}	
	
	
	/**
	 * 接收事件信息 事件的来源有 短信举报、外网网站的在线举报、管理员手工录入
	 */
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.title", message = "请填写事件标题."),
			@RequiredStringValidator(fieldName = "model.descn", message = "请填写事件描述."),
			@RequiredStringValidator(fieldName = "model.address", message = "请填写事件发生地点."),
			@RequiredStringValidator(fieldName = "model.code", message = "请填写事件编号.") })
	public String save() {
	
	
		if (getModel().getId() == null) {
			if(getManager().getDao().exists(getModel(), "code")){
				addActionError("事件编号已存在！");
				return INPUT;
			}
			getModel().setSubmitTime(new Date());
			getModel().setStatus(CaseConstants.CASE_STATUS_RESOLVEUN);
		}

		if (getModel().getCaseType() == null
				|| getModel().getCaseType().getId() == null) {
			addActionError("请选择事件类别.");
			return INPUT;
		}
		
		if (getModel().getDept() == null
				|| getModel().getDept().getId() == null) {
			addActionError("请选择事件所属区县.");
			return INPUT;
		}

		if (getRequest().getParameter("isDone") != null
				&& getRequest().getParameter("isDone").equals("1")) {
			getModel().setClosedTime(new Date());
			getModel().setStatus(CaseConstants.CASE_STATUS_VERIFYED);
		}
		CaseType cType = getManager().getDao().get(
				CaseType.class, getModel().getCaseType().getId());
		getModel().setCaseType(cType);
		getModel().setIsSubmitSj(Character.valueOf((char) 0));
		getManager().getDao().clear();
		getManager().save(getModel());
		return SUCCESS;
	}
	
	/**
	 * 查看信息
	 */
	public String look() {
		return "look";
	}
	
	/**
	 * 用于显示一级主题类，用于edit.jsp显示
	 */
	public Map getLevelOne() {
		return caseTypeManager.getLevelOneMap();
	}
	
	/**
	 * 单体事件状态列表返回页面
	 */
	public Map getStateMap() {
		Map StateMap = new HashMap();
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEUN, "未派遣");
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEING, "已派遣");
		StateMap.put(CaseConstants.CASE_STATUS_VERIFYED, "已核实");
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEED, "已处理");
		StateMap.put("", "状态选择");
		return StateMap;
	}
	/**
	 * 添加事件时获得用户以显示该用户的所在区县
	 */
	public String input(){
		//获得当前登录用户
		User user = UserUtil.getPrincipal(getRequest());
		if (user == null) {
			throw new ApplicationException("未登录，请登录后访问本页面。");
			
		}
		user = getManager().getDao().get(User.class, user.getId());
		return INPUT;
	}
	
}
