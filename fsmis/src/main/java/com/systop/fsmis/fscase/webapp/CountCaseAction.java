package com.systop.fsmis.fscase.webapp;

import java.util.ArrayList;
import java.util.List;

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
	    StringBuffer buf1 = new StringBuffer("from FsCase s where s.county.id= ? ");
	    List<Object> args = new ArrayList<Object>();
	    args.add(loginUserService.getLoginUserCounty(getRequest()).getId());
	    buf1.append(" and s.status= ?");
	    args.add(CaseConstants.CASE_UN_RESOLVE);
	    buf1.append(" and s.isMultiple= ?");
	    args.add(FsConstants.N);
	    buf1.append(" and s.isSubmited= ?");
	    args.add(FsConstants.N);
	    buf1.append(" and s.caseSourceType <> ?");
	    args.add("'"+CaseConstants.CASE_SOURCE_TYPE_JOINTASK+"'");
	    
	    StringBuffer buf2 = new StringBuffer("from FsCase s where s.county.id= ? ");
	    List<Object> args2 = new ArrayList<Object>();
	    args2.add(loginUserService.getLoginUserCounty(getRequest()).getId());
	    buf2.append(" and s.status= ?");
	    args2.add(CaseConstants.CASE_UN_RESOLVE);
	    buf2.append(" and s.isMultiple= ?");
	    args2.add(FsConstants.Y);
	    buf2.append(" and s.isSubmited= ?");
	    args2.add(FsConstants.N);
	    buf2.append(" and s.caseSourceType <> ?");
	    args2.add("'"+CaseConstants.CASE_SOURCE_TYPE_JOINTASK+"'");
	    
	    StringBuffer buf3 = new StringBuffer("from FsCase s where s.county.id= ? ");
	    List<Object> args3 = new ArrayList<Object>();
	    args3.add(loginUserService.getLoginUserCounty(getRequest()).getId());
	    buf3.append(" and s.status= ?");
	    args3.add(CaseConstants.CASE_UN_RESOLVE);
	    buf3.append(" and s.isMultiple= ?");
	    args3.add(FsConstants.N);
	    buf3.append(" and s.isSubmited= ?");
	    args3.add(FsConstants.N);
	    buf3.append(" and s.caseSourceType = ?");
	    args3.add("'"+CaseConstants.CASE_SOURCE_TYPE_JOINTASK+"'");
	    
	  
		//统计未派遣单体案件
		getRequest().setAttribute("genericCasesCountNoSend",
				String.valueOf(getManager().query(buf1.toString(),args.toArray()).size()));

		//统计未派遣多体案件
		getRequest().setAttribute("multipleCasesCountNoSend",
				String.valueOf(getManager().query(buf2.toString(),args2.toArray()).size()));

		//统计未派遣联合整治
		getRequest().setAttribute("jointCasesCountNoSend",
				String.valueOf(getManager().query(buf3.toString(),args3.toArray()).size()));
		
		return "indexCaseCount";
	}

}
