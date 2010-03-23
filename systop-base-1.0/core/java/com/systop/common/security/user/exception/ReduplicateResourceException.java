package com.systop.common.security.user.exception;

import com.systop.common.exception.ApplicationException;

/**
 * 资源名称重复异常
 * @author Sam
 */
public class ReduplicateResourceException extends ApplicationException {
  /**
   * @see {@link ApplicationException#ApplicationException(String)}
   */
  public ReduplicateResourceException(String resName) {
    super("resource.reduplicate", resName);
  }
}
