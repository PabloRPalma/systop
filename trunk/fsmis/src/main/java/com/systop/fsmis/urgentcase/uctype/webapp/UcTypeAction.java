package com.systop.fsmis.urgentcase.uctype.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.UrgentType;
import com.systop.fsmis.urgentcase.uctype.service.UcTypeManager;

/**
 * 应急事件类别action
 * @author yj
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UcTypeAction extends DefaultCrudAction<UrgentType, UcTypeManager> {
	
}
