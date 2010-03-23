package com.systop.common.security.exception;


/**
 * Thrown if an {@link Authentication} object does not hold a required
 * authority.
 */
public class AccessDeniedException extends Exception {
  /**
   * @see Exception#Exception(String)
   */
  public AccessDeniedException(final String msg) {
    super(msg);
  }
  
  /**
   * @see Exception#Exception(String, Throwable)
   */
  public AccessDeniedException(final String msg, final Throwable t) {
    super(msg, t);
  }
}
