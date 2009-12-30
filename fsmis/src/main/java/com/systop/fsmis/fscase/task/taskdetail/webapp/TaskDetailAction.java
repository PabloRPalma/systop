package com.systop.fsmis.fscase.task.taskdetail.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TaskDetailAction extends
		DefaultCrudAction<TaskDetail, TaskDetailManager> {
	@SuppressWarnings("unused")
	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer buf = new StringBuffer("from TaskDetail td where 1 = 1 ");
		List<Object> args = new ArrayList<Object>();

		/*
		 * 带有部门的查询时,报错,可能的原因是数据库中部门信息和当前登录人员之间的关系不完善 项目组长要写初始化类来解决这个问题,待问题解决后,启用此段代码
		 * Dept dept = loginUserService.getLoginUserCounty(getRequest()); if(dept
		 * !=null){ buf.append("and td.dept.id = ?"); args.add(dept.getId()); }
		 */

		page = getManager().pageQuery(page, buf.toString(), args.toArray());
		restorePageData(page);

		return INDEX;
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
}
