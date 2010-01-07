package com.systop.fsmis.fscase.task.taskdetail.webapp;

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

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.CaseConstants;
import com.systop.fsmis.fscase.task.taskdetail.service.TaskDetailManager;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.model.TaskDetail;

@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskDetailAction extends
		ExtJsCrudAction<TaskDetail, TaskDetailManager> {
	// 是否为综合案件
	private String isMultipleCase;
	// 默认显示的Tab序号,用于在view页面默认显示哪个Tab
	private String modelId;

	// 查询起始时间
	private Date taskBeginTime;
	// 查询截至时间
	private Date taskEndTime;
	// 在页面中作为默认人员输入项
	private User user;
	// 当前区县下所有企业集合
	private Map<String, String> corps;
	// 用户选择的企业
	private Corp corp;

	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 根据当前登录人员所在的区县和部门列出任务明细
	 */
	@Override
	public String index() {
		User user = loginUserService.getLoginUser(getRequest());
		if (user == null) {
			addActionError("请先登录!");
			return INDEX;
		}
		StringBuffer buf = new StringBuffer("from TaskDetail detail where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		// 根据任务title查询
		if (getModel() != null && getModel().getTask() != null
				&& StringUtils.isNotBlank(getModel().getTask().getTitle())) {
			buf.append("and detail.task.title like ? ");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTask()
					.getTitle()));
		}

		if (StringUtils.isNotBlank(getModel().getStatus())) {
			buf.append("and detail.status = ? ");
			args.add(getModel().getStatus());
		}
		builddDispatchTimeCondition(buf, args);
		/*
		 * 带有部门的查询时,报错,可能的原因是数据库中部门信息和当前登录人员之间的关系不完善
		 * 项目组长要写初始化类来解决这个问题,待问题解决后,启用此段代码 Dept dept =
		 * loginUserService.getLoginUserCounty(getRequest()); if(dept !=null){
		 * buf.append("and td.dept.id = ?"); args.add(dept.getId()); }
		 */

		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if (dept != null) {
			buf.append("and detail.dept.id = ? ");
			args.add(dept.getId());
		}
		// 区分一般案件和综合案件
		buf.append("and detail.task.fsCase.isMultiple = ? ");
		args.add(isMultipleCase);
		getRequest().setAttribute("userId", user.getId());
		getRequest().setAttribute("userName", user.getName());

		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, buf.toString(), args.toArray());
		restorePageData(page);

		return INDEX;
	}

	/**
	 * 构建根据派发起至时间查询条件
	 * 
	 * @param buf
	 *            hql的StringBuffer
	 * @param args
	 *            参数数组
	 */
	private void builddDispatchTimeCondition(StringBuffer buf, List<Object> args) {
		if (taskBeginTime != null && taskEndTime != null) {
			buf
					.append("and detail.task.dispatchTime >=? and detail.task.dispatchTime <=? ");
			args.add(taskBeginTime);
			args.add(taskEndTime);
		}

	}

	/**
	 * 查看派遣给当前登录人员部门的任务明细方法<br>
	 * 由于查看功能使用的是在FsCase模块中统一的Tab页方式,<br>
	 * 各个子模块间就不能用"model.id"方式来传递当前实体id了,否则会引起冲突<br>
	 * 所以在各个子模块中需要通过getParameter("taskDetailId")方式来获得当前实体实例id
	 */
	@Override
	public String view() {

		String id = getRequest().getParameter("taskDetailId");
		if (StringUtils.isNotBlank(id)) {
			Integer taskDetailId = Integer.parseInt(id);
			setModel(getManager().get(taskDetailId));
			// 只有任务明细状态为"未接收"时,才涉及到改状态为"已查看"
			if (getModel().getStatus().equals(
					CaseConstants.TASK_DETAIL_UN_RECEIVE)) {
				// 置任务明细状态为"已查看"
				getModel().setStatus(CaseConstants.TASK_DETAIL_LOOK_OVERED);
				getManager().save(getModel());
			}
		}

		return VIEW;
	}

	/**
	 * 接收任务方法
	 * 
	 * @return
	 */
	public String receiveTask() {
		// 接收状态
		getModel().setStatus(CaseConstants.TASK_DETAIL_RECEIVEED);
		getManager().save(getModel());

		return SUCCESS;
	}

	/**
	 * 转发到填写退回人员/退回原因页面
	 * 
	 * @return
	 */
	public String toReturnTaskDetail() {

		return "returnTaskDetail";
	}

	/**
	 * 完成退回操作方法
	 * 
	 * @return
	 */
	public String doReturnTaskDetail() {

		getManager().doReturnTaskDetail(getModel());

		return SUCCESS;
	}

	/**
	 * 转到任务明细处理页面
	 * 
	 * @return
	 */
	public String toDealWithTaskDetail() {
		// 设定默认的任务处理结果填写人为当前登录人员
		getModel().setInputer(
				loginUserService.getLoginUser(getRequest()).getName());
		return "toDealWithTaskDetail";
	}

	/**
	 * 完成事件明细处理方法
	 * 
	 * @return
	 */
	public String doDealWithTaskDetail() {

		return SUCCESS;
	}

	/**
	 * 请求提交任务,转发到填写任务明细页面
	 * 
	 * @return
	 */
	public String toCommitTaskDetail() {
		return "toCommitTaskDetail";
	}

	/**
	 * 完成提交任务(处理完毕)方法
	 * 
	 * @return
	 */
	public String doCommitTaskDetail() {

		// 如果没有指定企业,则不设定企业关联---需要沟通确定
		if (getModel().getTask().getFsCase().getCorp().getId() == null) {
			getModel().getTask().getFsCase().setCorp(null);
		}

		getManager().doCommitTaskDetail(getModel());

		return SUCCESS;
	}

	/**
	 * 单体任务状态列表返回页面
	 */
	public Map<String, String> getStateMap() {

		Map<String, String> StateMap = new LinkedHashMap<String, String>();
		StateMap.put(CaseConstants.TASK_DETAIL_UN_RECEIVE, "未接收");
		StateMap.put(CaseConstants.TASK_DETAIL_LOOK_OVERED, "已查看");
		StateMap.put(CaseConstants.TASK_DETAIL_RECEIVEED, "已接收");
		StateMap.put(CaseConstants.TASK_DETAIL_RETURNED, "已退回 ");
		StateMap.put(CaseConstants.TASK_DETAIL_RESOLVEED, "已处理  ");

		return StateMap;
	}

	/**
	 * 查询所有当前登录人员所在区县辖区的企业,以在页面选择
	 * 
	 * @return
	 */
	public String getCurrentCuntyCorps() {

		@SuppressWarnings("unused")
		List<Corp> corpList = getManager().getDao().query(
				"from Corp c where c.dept.id = ?",
				loginUserService.getLoginUserCounty(getRequest()).getId());
		// 待本阶段结束后,将企业信息自动补全功能改为JQuery来实现,再启用本段代码
		// ReflectUtil.toMap(bean, propertyNames, containsNull)

		return JSON;
	}

	/**
	 * 根据企业编号得到企业信息
	 * 
	 * @param code
	 * @return
	 */
	public String getCorpByCode(String code) {
		corp = (Corp) getManager().getDao().findObject(
				"from Corp c where c.code = ?", code);

		return "jsonResult";
	}

	/**
	 * 打印任务明细
	 * 
	 * @return
	 */
	public String printTaskDetail() {

		return "printTaskDetail";
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Date getTaskBeginTime() {
		return taskBeginTime;
	}

	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Corp getCorp() {
		return corp;
	}

	public void setCorp(Corp corp) {
		this.corp = corp;
	}

	public Map<String, String> getCorps() {
		return corps;
	}

	public void setCorps(Map<String, String> corps) {
		this.corps = corps;
	}

	public String getIsMultipleCase() {
		return isMultipleCase;
	}

	public void setIsMultipleCase(String isMultipleCase) {
		this.isMultipleCase = isMultipleCase;
	}

}
