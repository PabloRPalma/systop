package com.systop.common.webapp.extremecomponents;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.filter.AbstractExportFilter;
import org.extremecomponents.table.filter.ExportFilterUtils;
import org.extremecomponents.table.filter.ExportResponseWrapper;

/**
 * extremetable导出Excel Filter，用于处理与JSF等Mvc框架整合的问题
 * @author Sam
 */

public final class ExportFilter extends AbstractExportFilter {
  /**
   * Log of the class
   */
  private static Log log = LogFactory.getLog(ExportFilter.class);

  /**
   * 是否在执行doFilter方法之前设置response header
   */
  private boolean responseHeadersSetBeforeDoFilter;

  /**
   * @see javax.servlet.Filter#init(FilterConfig)
   */
  public void init(FilterConfig filterConfig) throws ServletException {
    String temp = filterConfig
        .getInitParameter("responseHeadersSetBeforeDoFilter");
    if (StringUtils.isNotBlank(temp)) {
      this.responseHeadersSetBeforeDoFilter = new Boolean(
          responseHeadersSetBeforeDoFilter).booleanValue();
    }
  }

  /**
   * ���ǣ�override��<code>AbstractExportFilter</code>��ʵ��
   */
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    Context context = new HttpServletRequestContext(
        (HttpServletRequest) request);
    boolean isExported = ExportFilterUtils.isExported(context);
    if (isExported) {
      String exportFileName = ExportFilterUtils.getExportFileName(context);
      doFilterInternal(request, response, chain, exportFileName);
      // 如果ServletResponse是经过包装之后的，那么必须取回原始的那个，否则会引起导出文件错误
      // 不过，这个处理会导致一个java.lang.IllegalStateException异常，这不会影响使用
      if (response instanceof ServletResponseWrapper) {
        log.debug("Found wrapped ServletResponse '"
            + response.getClass().getName() + "'");
        response = ((ServletResponseWrapper) response).getResponse();
      }
      handleExport((HttpServletRequest) request,
          (HttpServletResponse) response, context);
    } else {
      chain.doFilter(request, response);
    }
  }
  
  /**
   * 执行原来的doFilter方法，完成一个完整的请求
   */
  protected void doFilterInternal(ServletRequest request,
      ServletResponse response, FilterChain chain, String exportFileName)
      throws IOException, ServletException {
    if (responseHeadersSetBeforeDoFilter) {
      setResponseHeaders((HttpServletResponse) response, exportFileName);
    }
    log.debug(response.getClass().getName());

    chain.doFilter(request, new ExportResponseWrapper(
        (HttpServletResponse) response));
    // chain.doFilter(request, response);
    if (!responseHeadersSetBeforeDoFilter) {
      setResponseHeaders((HttpServletResponse) response, exportFileName);
    }
  }
  
  /**
   * @see javax.servlet.Filter#destroy() 
   */
  public void destroy() {
  }

}
