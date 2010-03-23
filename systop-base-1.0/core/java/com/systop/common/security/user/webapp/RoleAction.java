package com.systop.common.security.user.webapp;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.service.RoleManager;
import com.systop.common.webapp.struts2.action.BaseModelAction;

/**
 * struts2 Action of Role
 * @author han
 * 
 */
public class RoleAction extends BaseModelAction<Role, RoleManager> {

  /**
   * @see BaseModelAction#setupDetachedCriteria()
   */
  @Override
  protected DetachedCriteria setupDetachedCriteria() {
    return DetachedCriteria.forClass(Role.class).add(
        Restrictions.like("name", model.getName(), MatchMode.ANYWHERE));
  }
}
