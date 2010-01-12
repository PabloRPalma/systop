package com.systop.fsmis.fscase.webapp;

/**
 * 问题:
 * 判断是否是市级人员登录,本逻辑需要确认
 */
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.CaseConstants;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.casetype.service.CaseTypeManager;
import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.sms.SmsReceiveManager;
import com.systop.fsmis.supervisor.service.SupervisorManager;

/**
 * 一般事件处理
 * 
 * @author shaozhiyuan
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FsCaseAction extends DefaultCrudAction<FsCase, FsCaseManager> {
	// 是否综合(多体)案件
	private String isMultipleCase;
	@Autowired
	private CaseTypeManager caseTypeManager;
	@Autowired
	private LoginUserService loginUserService;
	/** 监管员信息Manager */
	@Autowired
	private SupervisorManager supervisorManager;
	
	//注入短信接收类
	@Autowired
	private SmsReceiveManager smsReceiveManager;

	private String typeId;

	private Integer typeoneId;

	private Integer typetwoId;

	private Integer oneId;

	private Integer twoId;

	private List typeRst;
	// 查询起始时间
	private Date caseBeginTime;
	// 查询截至时间
	private Date caseEndTime;

	private Integer smsReceiveId;
	
	//短信息接收人姓名
	private String supervisorName;

	//短信息接收人电话
	private String supervisorMobile;
	
	//短信息内容
	private String msgContent;

	/**
	 * 查询获得一般事件信息列表，分页查询
	 */
	public String index() {
		if (loginUserService == null
				|| loginUserService.getLoginUser(getRequest()) == null) {
			addActionError("请先登录!");
			return INDEX;
		}

		Page page = new Page(Page.start(getPageNo(), getPageSize()),
				getPageSize());
		StringBuffer sql = new StringBuffer(
				"from FsCase gc where isSubmitSj=0 ");
		List args = new ArrayList();
		// 判断是否是市级人员登录,如果不是,则需要添加根据本区县查询案件的查询条件,本逻辑需要确认
		if (loginUserService.getLoginUserCounty(getRequest()).getParentDept() != null) {
			sql.append("and gc.county.id = ? ");
			args.add(loginUserService.getLoginUserCounty(getRequest()).getId());
		}

		if (StringUtils.isNotBlank(getModel().getTitle())) {
			sql.append("and gc.title like ? ");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));

		}
		if (StringUtils.isNotBlank(getModel().getStatus())) {
			sql.append("and gc.status = ? ");
			args.add(getModel().getStatus());
		}
		if (StringUtils.isNotBlank(getModel().getCode())) {
			sql.append("and gc.code = ? ");
			args.add(getModel().getCode());
		}
		// 区分一般/综合(单体/多体)案件
		if (StringUtils.isNotBlank(isMultipleCase)) {
			sql.append("and gc.isMultiple=? ");
			args.add(isMultipleCase);
		}
		// 根据事发时间区间查询
		if (caseBeginTime != null && caseEndTime != null) {
			sql.append("and gc.caseTime >= ? and gc.caseTime <= ? ");
			args.add(caseBeginTime);
			args.add(caseEndTime);
		}

		sql.append("order by gc.caseTime desc,gc.status");
		page = getManager().pageQuery(page, sql.toString(), args.toArray());
		
		//从短信接收表中查询已核实单体事件短信信息,设置单体事件的核实状态 
		items = page.getData();
		for(FsCase ca:items){			
			Long hasCheckedCount= smsReceiveManager.getCheckedMsgCountByFscaseId(ca.getId());
			if(hasCheckedCount != null && hasCheckedCount > 0){
				ca.setMsgCheckedFlag(FsConstants.Y);//设置短信核实标识
			}
		}
		restorePageData(page);

		return INDEX;
	}

	/**
	 * 接收事件信息 事件的来源有 短信举报、外网网站的在线举报、管理员手工录入
	 */
	@Transactional
	public String save() {

		if (getModel().getId() == null) {
			if (getManager().getDao().exists(getModel(), "code")) {
				addActionError("事件编号已存在！");
				getRequest().setAttribute("levelone", getLevelOne());
				return INPUT;
			}
			getModel().setCaseTime(new Date());
			getModel().setStatus(CaseConstants.CASE_STATUS_RESOLVEUN);
		}

		if (getModel().getCounty() == null
				|| getModel().getCounty().getId() == null) {
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
			cType = getManager().getDao().get(CaseType.class, typetwoId);
		} else {
			if (typeoneId != null) {
				cType = getManager().getDao().get(CaseType.class, typeoneId);
			}
		}

		getModel().setCaseType(cType);
		getModel().setIsSubmitSj(CaseConstants.IS_NOSUBSJ);
		if (StringUtils.isBlank(getModel().getIsMultiple())) {
			getModel().setIsMultiple(FsConstants.N);
		}
		getManager().getDao().clear();
		getManager().save(getModel());
		
		//如果是通过短信添加的案件,则为短信添加案件关联
		if (smsReceiveId != null && smsReceiveId.intValue() != 0) {
			SmsReceive smsReceive = (SmsReceive) getManager().getDao().findObject("from SmsReceive sr where sr.id = ?", smsReceiveId);
			smsReceive.setFsCase(getModel());
			getManager().getDao().save(smsReceive);
		}
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
		if (getModel().getId() != null) {
			// 为类别赋默认值，用于编辑时显示
			if (getModel().getCaseType().getCaseType() != null
					&& getModel().getCaseType().getCaseType().getId() != null) {
				oneId = getModel().getCaseType().getCaseType().getId();
				twoId = getModel().getCaseType().getId();
			} else {
				oneId = getModel().getCaseType().getId();

			}
		}

		return super.edit();
	}

	/**
	 * 通用于整个FsCase功能模块的查看方法<br>
	 * 由于需要在其他模块中被访问,<br>
	 * 所以需要在本方法中getRequest().getParameter("fsCaseId");<br>
	 * 而不是getModel().getId();来获得FsCase实例的id<br>
	 * 本方法的跳转页面需要根据数据关联情况将FsCase/Task/TaskDetail等信息逐级显示
	 */
	@Override
	public String view() {
		String id = getRequest().getParameter("fsCaseId");
		if (StringUtils.isNotBlank(id)) {
			Integer fsCaseId = Integer.parseInt(id);
			setModel(getManager().get(fsCaseId));
		}

		return VIEW;
	}

	/**
	 * 根据收到的举报短信编辑案件信息
	 * 
	 * @return
	 */
	public String editFsCaseBySmsReceive() {
		// 根据短信id得到短信实体实例
		SmsReceive smsReceive = getReceiveById(smsReceiveId);
		getModel().setDescn(smsReceive.getContent());// 案件描述
		getModel().setInformerPhone(smsReceive.getMobileNum());// 举报人电话
		// 根据举报人电话得到举报人姓名,并赋值给案件举报人属性
		if (supervisorManager.getSupervisorByMobile(smsReceive.getMobileNum()) != null) {
			getModel().setInformer(
					supervisorManager.getSupervisorByMobile(
							smsReceive.getMobileNum()).getName());
		}

		return edit();
	}

	/**
	 * 根据id得到短信实体实例
	 * 
	 * @param smsReceiveId 短信id
	 * @return
	 */
	private SmsReceive getReceiveById(Integer smsReceiveId) {
		return getManager().getDao().get(SmsReceive.class, smsReceiveId);
	}

	/**
	 * 单体事件状态列表返回页面
	 */
	public Map getStateMap() {
		Map StateMap = new LinkedHashMap();
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEUN, "未派遣");
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEING, "已派遣");
		StateMap.put(CaseConstants.CASE_STATUS_VERIFYED, "已核实");
		StateMap.put(CaseConstants.CASE_STATUS_RESOLVEED, "已处理");

		return StateMap;
	}
	
	/**
	 * 编写为核实事件给信息员发送的信息
	 */
	public String addSendMsg() {
		if (getModel().getId() != null) {
			FsCase fsCase = getManager().get(getModel().getId());
			supervisorName = fsCase.getInformer();
			supervisorMobile = fsCase.getInformerPhone();
		}
		return "addSendMsg";
	}
	
	/**
	 * 给信息员发送信息核实事件
	 */
	public String sendMsg() {
		if (getModel().getId() != null) {
			FsCase fsCase = getManager().get(getModel().getId());
			getManager().sendMsg(fsCase, supervisorName, supervisorMobile,
					msgContent);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 跳转到短信核实确认页面 
	 */
	public String confirmBackMsg(){
		return "confirmBackMsg";	
	}

	/**
	 * 确认核实信息
	 * 
	 */
	public String confirmCheckedMsg(){
		//添加或更新单体事件的反馈确认信息	
		if(getRequest().getParameter("checked")!= null
			&& getRequest().getParameter("checked").equals(Constants.YES)){
			getModel().setClosedTime(new Date());
			getModel().setStatus(CaseConstants.CASE_STATUS_VERIFYED);
		}else if(getRequest().getParameter("checked")!= null
				&& getRequest().getParameter("checked").equals(Constants.NO)){
			getModel().setStatus(CaseConstants.CASE_STATUS_RESOLVEUN);
		}
		
		//此处应调用多体汇总方法
		getManager().save(getModel());
		return SUCCESS;
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

	public Integer getOneId() {
		return oneId;
	}

	public void setOneId(Integer oneId) {
		this.oneId = oneId;
	}

	public Integer getTwoId() {
		return twoId;
	}

	public void setTwoId(Integer twoId) {
		this.twoId = twoId;
	}

	public Date getCaseBeginTime() {
		return caseBeginTime;
	}

	public void setCaseBeginTime(Date caseBeginTime) {
		this.caseBeginTime = caseBeginTime;
	}

	public Date getCaseEndTime() {
		return caseEndTime;
	}

	public void setCaseEndTime(Date caseEndTime) {
		this.caseEndTime = caseEndTime;
	}

	public LoginUserService getLoginUserService() {
		return loginUserService;
	}

	public void setLoginUserService(LoginUserService loginUserService) {
		this.loginUserService = loginUserService;
	}

	public String getIsMultipleCase() {
		return isMultipleCase;
	}

	public void setIsMultipleCase(String isMultipleCase) {
		this.isMultipleCase = isMultipleCase;
	}

	public Integer getSmsReceiveId() {
		return smsReceiveId;
	}

	public void setSmsReceiveId(Integer smsReceiveId) {
		this.smsReceiveId = smsReceiveId;
	}

}
