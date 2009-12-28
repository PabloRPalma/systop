package com.systop.fsmis.fscase.sendtype.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.sendtype.service.CountySendTypeManager;
import com.systop.fsmis.model.CountySendType;
import com.systop.fsmis.model.FsCase;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountySendTypeAction extends
		DefaultCrudAction<CountySendType, CountySendTypeManager> {
	private FsCase fsCase;
	private List<Dept> depts;
	private LoginUserService loginUserService;

	public String send() {
		logger.info("@@@@@@@@@@@@@@@@@@22");
		logger.info(fsCase.getId() + "");
		fsCase = getManager().getDao().get(FsCase.class, fsCase.getId());
		/*Dept dept = loginUserService.getLoginUserDept(getRequest());
		logger.info(dept.getChildDepts().size()+"===000000000000");*/
		return "send";
	}

	public FsCase getFsCase() {
		return fsCase;
	}

	public void setFsCase(FsCase fsCase) {
		this.fsCase = fsCase;
	}

	public LoginUserService getLoginUserService() {
		return loginUserService;
	}

	@Autowired
	public void setLoginUserService(LoginUserService loginUserService) {
		this.loginUserService = loginUserService;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}
}
