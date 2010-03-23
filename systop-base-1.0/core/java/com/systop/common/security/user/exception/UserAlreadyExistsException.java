package com.systop.common.security.user.exception;

import com.systop.common.exception.ApplicationException;

/**
 * 用户的login_id是唯一的,如果出现重复,则抛出此异常
 * @author Sam
 */
public class UserAlreadyExistsException extends ApplicationException {
  /**
   * @param loginId 用户输入的LoginId
   */
  public UserAlreadyExistsException(String loginId) {
    super("error.duplicate_loginid", loginId);
  }
}
