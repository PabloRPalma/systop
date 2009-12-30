package com.systop.fsmis.fscase.task.taskdetail.webapp;

import java.text.ParseException;
import java.util.ArrayList;
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

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.CaseConstants;
import com.systop.fsmis.fscase.task.taskdetail.service.TaskDetailManager;
import com.systop.fsmis.model.TaskDetail;

@Controller
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskDetailAction extends
		DefaultCrudAction<TaskDetail, TaskDetailManager> {
	private Date taskBeginTime;
	private Date taskEndTime;
	@SuppressWarnings("unused")
	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 
	 */
	@Override
	public String index() {

		StringBuffer buf = new StringBuffer("from TaskDetail detail where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		// 根据任务title查询
		if (getModel() != null && getModel().getTask() != null
				&& StringUtils.isNotBlank(getModel().getTask().getTitle())) {
			buf.append("and detail.task.title like ? ");
			args.add(MatchMode.ANYWHERE
					.toMatchString(getModel().getTask().getTitle()));
		}

		if (StringUtils.isNotBlank(getModel().getStatus())) {
			buf.append("and detail.status = ? ");
			args.add(getModel().getStatus());
		}
		builddDispatchTimeCondition(buf, args);
		/*
		 * 带有部门的查询时,报错,可能的原因是数据库中部门信息和当前登录人员之间的关系不完善 项目组长要写初始化类来解决这个问题,待问题解决后,启用此段代码
		 * Dept dept = loginUserService.getLoginUserCounty(getRequest()); if(dept
		 * !=null){ buf.append("and td.dept.id = ?"); args.add(dept.getId()); }
		 */
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, buf.toString(), args.toArray());
		restorePageData(page);
		return INDEX;
	}

	/**
	 * 构建根据派发起至时间查询条件
	 * 
	 * @param buf hql的StringBuffer
	 * @param args 参数数组
	 */
	private void builddDispatchTimeCondition(StringBuffer buf, List<Object> args) {
		if (StringUtils.isNotBlank(getRequest().getParameter("taskBeginTime"))) {
			try {
				taskBeginTime = DateUtils.parseDate(getRequest().getParameter(
						"taskBeginTime"), new String[] { "yyyy-MM-dd HH:mm:ss" });
				buf.append("and detail.task.dispatchTime >=? ");
				args.add(taskBeginTime);
			} catch (ParseException e) {
				addActionError("查询的派发开始时间错误!");
				logger.error(e.getMessage());
			}
		}
		if (StringUtils.isNotBlank(getRequest().getParameter("taskEndTime"))) {
			try {
				taskEndTime = DateUtils.parseDate(getRequest().getParameter(
						"taskEndTime"), new String[] { "yyyy-MM-dd HH:mm:ss" });
				buf.append("and detail.task.dispatchTime <=? ");
				args.add(taskEndTime);
			} catch (ParseException e) {
				addActionError("查询的派发开始时间错误!");
				logger.error(e.getMessage());
			}
		}

	}

	/**
	 * 查看派遣给当前登录人员部门的任务明细方法<br>
	 * 
	 */
	@Override
	public String view() {
		String id = getRequest().getParameter("taskDetailId");
		if (StringUtils.isNotBlank(id)) {
			Integer taskDetailId = Integer.parseInt(id);
			setModel(getManager().get(taskDetailId));
			// 只有任务明细状态为"未接收"时,才涉及到改状态为"已查看"
			if (getModel().getStatus().equals(CaseConstants.TASK_DETAIL_UN_RECEIVE)) {
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
	public String toReturnTask() {

		return "returnTask";
	}

	/**
	 * 完成退回操作
	 * 
	 * @return
	 */
	public String doReturnTask() {

		
		// 退回状态
		getModel().setStatus(CaseConstants.CASE_STATUS_RETURNED);
		getManager().save(getModel());

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
}
