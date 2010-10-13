package com.systop.common.modules.security.user;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;

import com.systop.common.modules.security.user.model.User;

/**
 * 用户工具类
 * @author Sam Lee
 * 
 */
public final class UserUtil {
   /**
   * 从HttpServletRequest中获得当前登录用户。返回的<code>User</code>
   * 并未关联Hibnerate的Session，如果需要获取其角色、部门等关联实体信息，则
   * 必须重新与Session建立关联。
   * @param request 给定<code>HttpServletRequest</code>对象
   * @return <code>User</code> or <code>null</code> if no user login.
   */
  public static User getPrincipal(final HttpServletRequest request) {
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
  
  /**
   * 私有构造器，防止实例化
   */
  private UserUtil() {
  }


}
