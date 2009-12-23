package com.systop.fsmis.genericcase.webapp;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.CaseConstants;
import com.systop.fsmis.casetype.service.CaseTypeManager;
import com.systop.fsmis.genericcase.service.GenericCaseManager;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.GenericCase;



/**
 * 一般事件处理
 * 
 * @author shaozhiyuan
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericCaseAction extends
      DefaultCrudAction<GenericCase, GenericCaseManager>{
	
	@Autowired
	private CaseTypeManager caseTypeManager;
	
	private String typeId;
	
	private Integer typeoneId;
	
	private Integer typetwoId;
	
	private List typeRst;
	
	

	/**
	 * 查询获得一般事件信息列表，分页查询
	 */
	public String index() {
		Page page = new Page(Page.start(getPageNo(), getPageSize()),
				getPageSize());
		String sql = "from GenericCase gc where isSubmited=0 ";
		List args = new ArrayList();
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			sql = sql + "and gc.title like ? ";
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));
			
		}
		if (StringUtils.isNotBlank(getModel().getStatus())) {
			sql = sql + "and gc.status = ? ";
			args.add("'" + getModel().getStatus() + "'");
		}
		if (StringUtils.isNotBlank(getModel().getCode())) {
			sql = sql + "and gc.code = ? ";
			args.add("'" + getModel().getCode() + "'");
		}
		Date eventDate = new Date();
		if(StringUtils.isNotBlank(getRequest().getParameter("eventDate"))){
			try {
				eventDate = DateUtils.parseDate(getRequest().getParameter("eventDate"),
							new String[] { "yyyy-MM-dd HH:mm:ss" });
	 
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//根据事发时间查询
		if(StringUtils.isNotBlank(getRequest().getParameter("eventDate"))){
			sql =  sql +  " and gc.eventDate <= ?";
			args.add(eventDate);
			
		}
		sql += " order by gc.eventDate desc,gc.status";
		page = getManager().pageQuery(page, sql,args.toArray());
		restorePageData(page);
		return INDEX;
	}	
	
	
	/**
	 * 接收事件信息 事件的来源有 短信举报、外网网站的在线举报、管理员手工录入
	 */
	public String save() {
	
	
		if (getModel().getId() == null || getModel().getId().equals("")) {
			if(getManager().getDao().exists(getModel(), "code")){
				addActionError("事件编号已存在！");
				getRequest().setAttribute("levelone", getLevelOne());
				return INPUT;
			}
			getModel().setSubmitTime(new Date());
			getModel().setStatus(CaseConstants.CASE_STATUS_RESOLVEUN);
		}
		
		
		if (getModel().getDept() == null
				|| getModel().getDept().getId() == null) {
			addActionError("请选择事件所属区县.");
			getRequest().setAttribute("levelone", getLevelOne());
			return INPUT;
		}

		if (getRequest().getParameter("isDone") != null
				&& getRequest().getParameter("isDone").equals(Constants.YES)) {
			getModel().setClosedTime(new Date());
			getModel().setStatus(CaseConstants.CASE_STATUS_VERIFYED);
		}
		
		CaseType cType = null;
		if (typetwoId != null) {
			cType = getManager().getDao().get(
					CaseType.class, typetwoId);	
		}else{
			if(typeoneId != null){
				cType = getManager().getDao().get(
						CaseType.class, typeoneId);
			}
		}
		
		getModel().setCaseType(cType);
		getModel().setIsSubmited(Character.valueOf((char) 0));
		getManager().getDao().clear();
		getManager().save(getModel());
		return SUCCESS;
	}
	
	
	/**
	 * 用于显示一级主题类，用于edit.jsp显示
	 */
	public List getLevelOne() {
		return caseTypeManager.getLevelOneList();
	}
	
	/**
	 * 根据一级ID显示二级主题类，用于edit.jsp显示
	 */
	public String getLevelTwo() {
		typeRst = Collections.EMPTY_LIST;
		typeRst = caseTypeManager.getLevelTwoList(Integer.valueOf(typeId));
		return "jsonRst";
	}
	
	/**
	 * 重写父类编辑方法传类别参数，用于edit.jsp显示
	 */
	@Override
	public String edit() {
		getRequest().setAttribute("levelone", getLevelOne());
		return super.edit();
	}
	
	/**
	 * 单体事件状态列表返回页面
	 */
	public Map getStateMap() {
		Map StateMap = new LinkedHashMap() ;
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEUN, "未派遣");
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEING, "已派遣");
		StateMap.put(CaseConstants.CASE_STATUS_VERIFYED, "已核实");
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEED, "已处理");
		return StateMap;
	}

	public List getTypeRst() {
		return typeRst;
	}


	public void setTypeRst(List typeRst) {
		this.typeRst = typeRst;
	}
	
	public String getTypeId() {
		return typeId;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	public Integer getTypeoneId() {
		return typeoneId;
	}


	public void setTypeoneId(Integer typeoneId) {
		this.typeoneId = typeoneId;
	}


	public Integer getTypetwoId() {
		return typetwoId;
	}


	public void setTypetwoId(Integer typetwoId) {
		this.typetwoId = typetwoId;
	}
}
