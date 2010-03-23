package com.systop.common.webapp.struts2.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.systop.common.dao.impl.BaseHibernateDAO;
/**
 * Dwr{@link http://getahead.ltd.uk/dwr/} action superclass.
 * @author Sam Lee
 *
 */
public class BaseDwrAjaxAction {
  /**
   * 为子类提供Log功能，方便子类使用
   */
  protected Log log = LogFactory.getLog(getClass());

  /**
   * <tt>dao</tt>对象是通往持久层的捷径.
   */
  private BaseHibernateDAO dao;
  /**
   * @see {@link WebContext}
   * @see {@link WebContextFactory}
   */
  public final WebContext getWebContext() {
    return WebContextFactory.get();
  }
  
  /**
   * @return instance of HttpServletRequest.
   */
  public final HttpServletRequest getRequest() {
    return getWebContext().getHttpServletRequest();
  }
  
  /** 
   * @return instance of ServletContext.
   */
  public final ServletContext getServletContext() {
    return getWebContext().getServletContext();
  }
  /**
   * @return instance of HttpServletResponse.
   */
  public final HttpServletResponse getResponse() {
    return getWebContext().getHttpServletResponse();
  }
  /**
   *  @return HttpSession
   */
  public final HttpSession getSession() {
    return getRequest().getSession();
  }

  /**
   * @return the dao
   */
  public BaseHibernateDAO getDao() {
    return dao;
  }

  /**
   * @param dao the dao to set
   */
  public void setDao(BaseHibernateDAO dao) {
    this.dao = dao;
  }
}
