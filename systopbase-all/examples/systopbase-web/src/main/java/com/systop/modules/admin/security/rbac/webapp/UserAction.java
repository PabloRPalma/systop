package com.systop.modules.admin.security.rbac.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;

import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.modules.admin.security.rbac.model.User;
import com.systop.modules.admin.security.rbac.service.UserManager;
import com.systop.modules.admin.security.rbac.webapp.validation.UserValidator;

/**
 * 用戶管理Action类
 * @author Sam Lee
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserAction extends ExtJsCrudAction<User, UserManager> {
  private UserValidator userValidator;
  
  @Override
  public Validator getValidator() {
    logger.debug(userValidator.toString());
    return userValidator;
  }

  @Autowired
  public void setUserValidator(UserValidator userValidator) {
    this.userValidator = userValidator;
  }
}
