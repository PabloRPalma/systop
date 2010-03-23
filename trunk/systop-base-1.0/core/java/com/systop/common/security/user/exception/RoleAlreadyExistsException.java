package com.systop.common.security.user.exception;

import com.systop.common.exception.ApplicationException;

/**
 * 如角色名称重复，则抛出异常
 * @author han
 *
 */
public class RoleAlreadyExistsException extends ApplicationException {
  /**
   * 用户角色名称必须唯一，出现重复则抛出异常
   * @param rolename
   */
  public RoleAlreadyExistsException(String rolename) {
    super("error.duplicate_roleName", rolename);
  }
}
