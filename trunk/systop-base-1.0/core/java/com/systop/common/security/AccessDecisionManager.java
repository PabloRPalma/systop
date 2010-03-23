package com.systop.common.security;

import com.systop.common.security.exception.AccessDeniedException;

/**u
 * 访问决定管理器。 
 * @author Cats_Tiger
 */

public interface AccessDecisionManager {
  /**
   * 判定具备某种授权的调用者是否可以访问指定的对象
   * @param auth 调用者所具有的授权
   * @param secured 被访问的对象
   * @throws AccessDeniedException 如果没有相应的授权
   */
  public void decide(Authentication auth, Object secured) 
    throws AccessDeniedException;
  
  /**
   * 设置安全上下文，通常是用于取得安全策略的配置信息
   * @param context 安全上下文，可以是HttpServletRequest\Spring ApplicationContext等等
   */
  public void setSecurityContext(Object context);
}



