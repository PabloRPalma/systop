package com.systop.common.security.user;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;

import com.systop.common.security.user.model.User;
/**
 * 用户工具类
 * @author SAM
 *
 */
public final class UserUtil {
  /**
   * 防止实例化
   *
   */
  private UserUtil() {    
  }
  /**
   * 从HttpServletRequest中获得当前登录用户
   * @param request 给定<code>HttpServletRequest</code>对象
   * @return <code>User</code> or <code>null</code> if no user login.
   */
  public static final User getPrincipal(HttpServletRequest request) {
    Principal principal = request.getUserPrincipal();
    if (principal != null) {
      if (principal instanceof UsernamePasswordAuthenticationToken) {
        Object p = ((UsernamePasswordAuthenticationToken) principal)
          .getPrincipal();
        if (p instanceof User) {
          return (User) p;
        }
      }
    }
    return null;    
  }
}
