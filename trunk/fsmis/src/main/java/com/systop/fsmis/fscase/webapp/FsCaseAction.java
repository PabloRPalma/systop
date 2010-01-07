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
	private String isMultiple;
	@Autowired
	private CaseTypeManager caseTypeManager;
	@Autowired
	private LoginUserService loginUserService;

	private String typeId;

	private Integer typeoneId;

	private Integer typetwoId;

	private Integer oneId;

	private Integer twoId;

	private List typeRst;
	// 查询起始事件
	private Date caseBeginTime;
	// 查询截至事件
	private Date caseEndTime;

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
		if (StringUtils.isNotBlank(isMultiple)) {
			sql.append("and gc.isMultiple=? ");
			args.add(isMultiple);
		}
		// 根据事发时间区间查询
		if (caseBeginTime != null && caseEndTime != null) {
			sql.append("and gc.caseTime >= ? and gc.caseTime <= ? ");
			args.add(caseBeginTime);
			args.add(caseEndTime);
		}

		sql.append("order by gc.caseTime desc,gc.status");
		page = getManager().pageQuery(page, sql.toString(), args.toArray());
		restorePageData(page);

		return INDEX;
	}

	/**
	 * 接收事件信息 事件的来源有 短信举报、外网网站的在线举报、管理员手工录入
	 */
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
		getModel().setIsMultiple(FsConstants.N);
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

	public String getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
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
}
