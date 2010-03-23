package com.systop.common.webapp.menu;

/**
 * 用于标记菜单异常
 * @author Sam
 *
 */
public class MenuException extends Exception {
  
  /**
   * @see Exception#Exception()
   *
   */
  public MenuException() {
    super();
  }
  
  /**
   * @see Exception#Exception(String)
   */
  public MenuException(String msg) {
    super(msg);
  }
  
  /**
   * @see Exception #Exception(String, Throwable)
   */
  public MenuException(String msg, Throwable t) {
    super(msg, t);
  }
}
