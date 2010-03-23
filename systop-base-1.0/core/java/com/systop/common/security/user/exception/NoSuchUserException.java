package com.systop.common.security.user.exception;

import com.systop.common.exception.ApplicationException;

/**
 * 指定用户名不存在或密码不匹配的时候使用此异常
 * @author Sam
 *
 */
public class NoSuchUserException extends ApplicationException {
  /**
   * 用户名不存在的时候使用
   * @param loginId
   */
  public NoSuchUserException(String loginId) {
    super("security.user.noSuchUser", loginId);
  }
  
  /**
   * 密码不匹配的时候使用
   * @param errorKey
   * @param loginId
   */
  public NoSuchUserException(String password, String loginId) {
    super("security.user.mismatchPassword", loginId);
    log.info("error password:" + password);
  }
}
