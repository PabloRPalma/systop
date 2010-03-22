package com.systop.modules.admin.security.rbac.webapp.validation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.systop.core.webapp.struts2.validation.ActionErrors;
import com.systop.core.webapp.struts2.validation.AbstractValidator;
import com.systop.modules.admin.security.rbac.model.User;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserValidator extends AbstractValidator<User> {

  @Override
  public void doValidate(User target, ActionErrors errors) {
    logger.debug("Do user validate.");
    this.validateRequired(null, "yyyy", errors);
  }

}
