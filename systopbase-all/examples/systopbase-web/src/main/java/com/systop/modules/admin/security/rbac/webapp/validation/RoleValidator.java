package com.systop.modules.admin.security.rbac.webapp.validation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.systop.core.webapp.struts2.validation.AbstractValidator;
import com.systop.core.webapp.struts2.validation.ActionErrors;
import com.systop.modules.admin.security.rbac.model.Role;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RoleValidator extends AbstractValidator<Role> {

  @Override
  public void doValidate(Role role, ActionErrors errors) {
    logger.debug("Do role validate.");
    /*
     * 角色名称和描述改用前台验证的方式
     */
    /**
    this.validateRequiredString(role.getName(), "角色名称不能为空", errors);
    this.validateRequiredString(role.getDescn(), "角色描述不能为空", errors);
    if (!role.getName().startsWith("ROLE_")) {
      errors.rejectDirectly("{0}", "角色名称必须以ROLE_开头");
    }*/
  }
}
