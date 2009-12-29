package com.systop.fsmis.fscase.sendtype.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.SendType;

/**
 * 针对SendType的操作和显示
 * @author Lunch
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SendTypeAction extends
		DefaultCrudAction<SendType, SendTypeManager> {
	
	private Integer caseId;
	
	/**
	 * 显示列表
	 */
	@Override
	public String index() {
		items = getManager().orderSendType();
		return INDEX;
	}

	public String send() {
		FsCase fsCase = getManager().getDao().get(FsCase.class, caseId);
		items = getManager().orderSendType();
		return "send";
	}
}
