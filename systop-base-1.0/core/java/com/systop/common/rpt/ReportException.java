package com.systop.common.rpt;

import com.systop.common.exception.ApplicationException;
/**
 * 报表异常，使用者可以将解析报表过程中发生的异常以<code>ReportException</code>
 * 重新抛出。<code>ReportException</code>继承{@link ApplicationException}
 * 异常，是一个RuntimeException。
 * @author SAM
 *
 */
public class ReportException extends ApplicationException {
  /**
   * @see RuntimeException#RuntimeException(Throwable)
   */
  public ReportException(Throwable cause) {
    super(cause);
  }

  /**
   * 报告错误原因
   */
   public ReportException(String errorMsg) {
     super(errorMsg);
   }
}
