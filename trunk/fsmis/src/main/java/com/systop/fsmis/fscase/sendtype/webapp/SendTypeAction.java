package com.systop.fsmis.fscase.sendtype.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.sendtype.service.SendTypeManager;
import com.systop.fsmis.model.SendType;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SendTypeAction extends DefaultCrudAction<SendType, SendTypeManager> {

}
