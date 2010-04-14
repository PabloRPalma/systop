package com.systop.fsmis.fscase.jointtask.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.fscase.jointtask.service.JointTaskDetailManager;
import com.systop.fsmis.fscase.task.TaskConstants;
import com.systop.fsmis.model.JointTaskDetail;


/**
 * 联合未接收任务统计
 * 
 * @author shaozhiyuan
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JointTaskCountAction extends ExtJsCrudAction<JointTaskDetail, JointTaskDetailManager> {
	
	@Autowired
	private LoginUserService loginUserService;
	  
	public String getJointTaskCount(){
		// 获得当前登录用户
	    if (loginUserService == null
	            || loginUserService.getLoginUser(getRequest()) == null) {
	          addActionError("请先登录!");
	          return INDEX;
	    }
		
	    String sql = "from JointTaskDetail j where j.dept.id="+loginUserService.getLoginUserDept(getRequest()).getId()+" and j.status="+TaskConstants.TASK_DETAIL_UN_RECEIVE;
	  
		//统计未接收联合整治任务
		getRequest().setAttribute("jointTaskCountNoReceive",
				String.valueOf(getManager().query(sql).size()));


		
		return "indexJointTaskCount";
	}

}
