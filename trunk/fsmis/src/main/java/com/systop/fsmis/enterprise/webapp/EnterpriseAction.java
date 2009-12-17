package com.systop.fsmis.enterprise.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.enterprise.service.EnterpriseManager;
import com.systop.fsmis.model.Enterprise;

/**
 * 企业信息管理的struts2 Action。
 * @author DU
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EnterpriseAction extends ExtJsCrudAction<Enterprise, EnterpriseManager>{

}
