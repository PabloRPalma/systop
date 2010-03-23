package com.systop.common.security.acegi.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.userdetails.UserDetails;

import com.systop.common.Constants;
import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.UserManager;

/**
 * 把User变量放入http session中,key为Constants.USER_IN_SESSION
 * 
 * @author cac,sam
 */
public class UserAuthenticationProcessingFilter extends
    AuthenticationProcessingFilter {
  /**
   * Manager for User
   */
  private UserManager userManager;

  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }
  /**
   * @see {@link org.acegisecurity.ui.
   *   AbstractProcessingFilter#requiresAuthentication}
   */
  protected boolean requiresAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    boolean requiresAuth = super.requiresAuthentication(request, response);
    HttpSession httpSession = null;
    try {
      httpSession = request.getSession(false);
    } catch (IllegalStateException ignored) {
    }
    if (httpSession != null) {
      if (httpSession.getAttribute(Constants.USER_IN_SESSION) == null) {
        if (!requiresAuth) {
          SecurityContext sc = SecurityContextHolder.getContext();
          Authentication auth = sc.getAuthentication();
          if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails ud = (UserDetails) auth.getPrincipal();
            User user = userManager.getUserByLoginIdAndPassword(ud
                .getUsername(), ud.getPassword());
            httpSession.setAttribute(Constants.USER_IN_SESSION, user);
          }
        }

      }
    }
    return requiresAuth;
  }
}
