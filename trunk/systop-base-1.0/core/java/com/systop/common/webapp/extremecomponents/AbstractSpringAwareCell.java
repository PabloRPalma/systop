/**
 * 
 */
package com.systop.common.webapp.extremecomponents;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 可以从Spring的ApplicationContext中获得Bean的Cell基类. 
 * 子类调用{@link #getBean(String, TableModel)}方法，可以得到Spring管理的Bean.
 * @author Sam
 * 
 */
public abstract class AbstractSpringAwareCell extends AbstractCell {
  /**
   * Log for the class
   */
  protected Log log = LogFactory.getLog(getClass());

  /**
   * @see AbstractCell#getCellValue(TableModel, Column)
   */
  @Override
  protected abstract String getCellValue(TableModel model, Column column);

  /**
   * Get spring managed bean.
   * @param beanName bean id.
   * @param model 如果不为null,则从ServletContext中获得ApplicationContext，否则
   *          使用ClassPathXmlApplicationContext加载ApplicationContext
   * @return bean instance.
   */
  protected final Object getBean(String beanName, TableModel model) {
    ServletContext servletCtx = null;
    if (model != null) {
      servletCtx = getServletContext(model);
    }

    ApplicationContext appCtx = null;

    if (servletCtx != null) {
      if (log.isDebugEnabled()) {
        log.debug("Get ApplicationContext from ServletContext.");
      }
      appCtx = WebApplicationContextUtils.getWebApplicationContext(servletCtx);
    } else {
      if (log.isDebugEnabled()) {
        log.debug("Get ApplicationContext from '"
            + "classpath*:context/applicationContext-*.xml'.");
      }
      appCtx = new ClassPathXmlApplicationContext(
          "classpath*:context/applicationContext-*.xml");
    }
    return appCtx.getBean(beanName);
  }

  /**
   * 从TableModel对象中获得ServletContext
   * @param model 给定的TableModel
   * @return 如果extremetable的context从PageContext获得，返回ServletContext 否则，null
   */
  private ServletContext getServletContext(TableModel model) {
    Object object = model.getContext().getContextObject();
    ServletContext servletCtx = null;

    if (object instanceof PageContext) {
      servletCtx = ((PageContext) object).getServletContext();
    }

    return servletCtx;
  }
}
