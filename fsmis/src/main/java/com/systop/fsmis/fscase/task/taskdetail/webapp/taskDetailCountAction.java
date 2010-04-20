package com.systop.fsmis.fscase.task.taskdetail.webapp;

import java.util.ArrayList;
import java.util.List;

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
	    StringBuffer buf1 = new StringBuffer("from TaskDetail t where t.dept.id= ? ");
	    List<Object> args = new ArrayList<Object>();
	    args.add(loginUserService.getLoginUserDept(getRequest()).getId());
	    buf1.append(" and t.status= ?");
	    args.add(TaskConstants.TASK_DETAIL_UN_RECEIVE);
	    buf1.append(" and t.task.fsCase.isMultiple= ?");
	    args.add(FsConstants.N);
	    
	    StringBuffer buf2 = new StringBuffer("from TaskDetail t where t.dept.id= ? ");
	    List<Object> args2 = new ArrayList<Object>();
	    args2.add(loginUserService.getLoginUserDept(getRequest()).getId());
	    buf2.append(" and t.status= ?");
	    args2.add(TaskConstants.TASK_DETAIL_UN_RECEIVE);
	    buf2.append(" and t.task.fsCase.isMultiple= ?");
	    args2.add(FsConstants.Y);
	    
	    StringBuffer buf3 = new StringBuffer("from JointTaskDetail j where j.dept.id= ? ");
	    List<Object> args3 = new ArrayList<Object>();
	    args3.add(loginUserService.getLoginUserDept(getRequest()).getId());
	    buf3.append(" and j.status= ?");
	    args3.add(TaskConstants.TASK_DETAIL_UN_RECEIVE);
 
		//统计未接收单体任务
		getRequest().setAttribute("genericTaskCountNoReceive",
				String.valueOf(getManager().query(buf1.toString(),args.toArray()).size()));

		//统计未接收多体任务
		getRequest().setAttribute("multipleTaskCountNoReceive",
				String.valueOf(getManager().query(buf2.toString(),args2.toArray()).size()));
		
		//统计未接收联合整治任务
		getRequest().setAttribute("jointTaskCountNoReceive",
				String.valueOf(getManager().query(buf3.toString(),args3.toArray()).size()));

		
		return "indexTaskCount";
	}

}
