package com.systop.fsmis.fscase.sendtype.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.SendType;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SendTypeAction extends
		DefaultCrudAction<SendType, SendTypeManager> {
	private FsCase fsCase;
	private List<Dept> depts;
	@SuppressWarnings("unused")
	@Autowired
	private LoginUserService loginUserService;

	@Override
	public String index() {
		String hql = "from SendType s order by s.sortId";
		items = getManager().query(hql);
		return INDEX;
	}

	public String send() {
		fsCase = getManager().getDao().get(FsCase.class, fsCase.getId());

		/*
		 * 现阶段暂时不考虑市区级别问题 Dept dept =
		 * loginUserService.getLoginUserDept(getRequest());
		 * logger.info(dept.getChildDepts().size()+"===000000000000");
		 */
		return "send";
	}

	public FsCase getFsCase() {
		return fsCase;
	}

	public void setFsCase(FsCase fsCase) {
		this.fsCase = fsCase;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

}
