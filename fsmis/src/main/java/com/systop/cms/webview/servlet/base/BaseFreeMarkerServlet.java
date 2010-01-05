package com.systop.cms.webview.servlet.base;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.systop.common.modules.template.freemarker.servlet.FreeMarkerStringTemplateViewServlet;
import com.systop.core.webapp.context.ApplicationContextAwareObject;

/**
 * FreemarkerServlet通过数据库模板生成静态页基础类
 * 
 * @author lunch
 */
@SuppressWarnings("serial")
public class BaseFreeMarkerServlet extends FreeMarkerStringTemplateViewServlet implements
    ApplicationContextAwareObject {

  /**
   * @see com.systop.common.core.webapp.context.ApplicationContextAwareObject
   *      #getBean(java.lang.String)
   */
  public Object getBean(String beanName) {
    ServletContext servletCtx = getServletContext();
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletCtx);
    return ctx.getBean(beanName);
  }

}
