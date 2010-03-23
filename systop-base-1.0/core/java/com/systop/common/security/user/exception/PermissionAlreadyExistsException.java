package com.systop.common.security.user.exception;

import com.systop.common.exception.ApplicationException;

/**
 * 权限名称重复异常
 * @author han
 *
 */
public class PermissionAlreadyExistsException extends ApplicationException {
  /**
   * 权限名称必须唯一，出现重复则抛出异常
   * @param rolename
   */
  public PermissionAlreadyExistsException(String name) {
    super("error.duplicate_permissionName", name);
  }
}
