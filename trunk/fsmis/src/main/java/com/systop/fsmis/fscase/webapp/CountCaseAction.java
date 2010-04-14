package com.systop.fsmis.fscase.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.model.FsCase;


/**
 * 事件统计
 * 
 * @author shaozhiyuan
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountCaseAction extends ExtJsCrudAction<FsCase, FsCaseManager> {
	
	@Autowired
	private LoginUserService loginUserService;
	  
	public String getCaseCount(){
		// 获得当前登录用户
	    if (loginUserService == null
	            || loginUserService.getLoginUser(getRequest()) == null) {
	          addActionError("请先登录!");
	          return INDEX;
	    }
		
	    String sql = "from FsCase s where s.county.id="+loginUserService.getLoginUserCounty(getRequest()).getId()+" and s.status="+CaseConstants.CASE_UN_RESOLVE+" and s.isMultiple="+FsConstants.N+" and s.isSubmited="+FsConstants.N+" and s.caseSourceType <>'"+CaseConstants.CASE_SOURCE_TYPE_JOINTASK+"'";
		String sql2 = "from FsCase s where s.county.id="+loginUserService.getLoginUserCounty(getRequest()).getId()+" and s.status= "+CaseConstants.CASE_UN_RESOLVE+" and s.isMultiple="+FsConstants.Y+" and s.isSubmited="+FsConstants.N+" and s.caseSourceType <>'"+CaseConstants.CASE_SOURCE_TYPE_JOINTASK+"'";
		String sql3 = "from FsCase s where s.county.id="+loginUserService.getLoginUserCounty(getRequest()).getId()+" and s.status="+CaseConstants.CASE_UN_RESOLVE+" and s.isMultiple="+FsConstants.N+" and s.isSubmited="+FsConstants.N+" and s.caseSourceType ='"+CaseConstants.CASE_SOURCE_TYPE_JOINTASK+"'";
		//统计未派遣单体案件
		getRequest().setAttribute("genericCasesCountNoSend",
				String.valueOf(getManager().query(sql).size()));

		//统计未派遣多体案件
		getRequest().setAttribute("multipleCasesCountNoSend",
				String.valueOf(getManager().query(sql2).size()));

		//统计未派遣联合整治
		getRequest().setAttribute("jointCasesCountNoSend",
				String.valueOf(getManager().query(sql3).size()));
		
		return "indexCaseCount";
	}

}
