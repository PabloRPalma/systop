package com.systop.fsmis.fscase.task.taskdetail.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.task.TaskConstants;
import com.systop.fsmis.fscase.task.taskdetail.service.TaskDetailManager;
import com.systop.fsmis.model.TaskDetail;


/**
 * 接收任务统计
 * 
 * @author shaozhiyuan
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class taskDetailCountAction extends ExtJsCrudAction<TaskDetail, TaskDetailManager> {
	
	@Autowired
	private LoginUserService loginUserService;
	  
	public String getTaskDetailCount(){
		// 获得当前登录用户
	    if (loginUserService == null
	            || loginUserService.getLoginUser(getRequest()) == null) {
	          addActionError("请先登录!");
	          return INDEX;
	    }
		
	    String sql = "from TaskDetail t where t.dept.id="+loginUserService.getLoginUserDept(getRequest()).getId()+" and t.status="+TaskConstants.TASK_DETAIL_UN_RECEIVE+" and t.task.fsCase.isMultiple="+FsConstants.N;
	    String sql2 = "from TaskDetail t where t.dept.id="+loginUserService.getLoginUserDept(getRequest()).getId()+" and t.status="+TaskConstants.TASK_DETAIL_UN_RECEIVE+" and t.task.fsCase.isMultiple="+FsConstants.Y;

		//统计未接收单体任务
		getRequest().setAttribute("genericTaskCountNoReceive",
				String.valueOf(getManager().query(sql).size()));

		//统计未接收多体任务
		getRequest().setAttribute("multipleTaskCountNoReceive",
				String.valueOf(getManager().query(sql2).size()));

		
		return "indexTaskCount";
	}

}
