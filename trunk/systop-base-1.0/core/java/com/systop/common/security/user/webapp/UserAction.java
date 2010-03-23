package com.systop.common.security.user.webapp;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.UserManager;
import com.systop.common.webapp.struts2.action.BaseModelAction;

/**
 * struts2 Action of User.
 * @author Sam
 * 
 */
public class UserAction extends BaseModelAction<User, UserManager> {
  /**
   * 提供密码加密服务
   */
  private PasswordEncoder passwordEncoder;

  /**
   * 加密密码，然后保存
   * @see com.systop.common.webapp.struts2.action.BaseModelAction#save()
   */
  @Override
  public String save() {
    
    if (passwordEncoder != null) {
      String oldPwd = model.getPassword();
      if (oldPwd == null) {
        model.setPassword(StringUtils.EMPTY);
      }
      
      model.setPassword(passwordEncoder.encodePassword(oldPwd, null));
      log.debug("Password is " + oldPwd + " encode '" 
          + model.getPassword() + "'");
    }
    
    return super.save();
  }

  /**
   * 检查用户名是否重复，如果重复，返回true
   */
  public boolean isLoginIdInUse(String loginId) {
    return getManager().isLoginIdInUse(loginId);
  }

  /**
   * @see BaseModelAction#setupDetachedCriteria()
   */
  @Override
  protected DetachedCriteria setupDetachedCriteria() {
  	DetachedCriteria dc = DetachedCriteria.forClass(User.class).add(
				Restrictions.like("loginId", model.getLoginId(),
						MatchMode.ANYWHERE))
				.add(Restrictions.like("name", model.getName(), 
						MatchMode.ANYWHERE));
		if (!model.getStatus().equals("")) {
			dc.add(Restrictions.eq("status", model.getStatus()));
		}
		return dc;
  }

  /**
   * @param passwordEncoder the passwordEncoder to set
   */
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
  
}
