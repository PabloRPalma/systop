package com.systop.common.webapp.struts2.action;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.directwebremoting.WebContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.systop.common.security.user.UserUtil;
import com.systop.common.security.user.model.User;
import com.systop.common.service.Manager;

/**
 * BaseAction of the struts2
 * @param <E> Action所使用的Manager
 * @author Sam
 * 
 */
public abstract class BaseAction<E extends Manager> extends ActionSupport {
  /**
   * 为子类提供Log功能，方便子类使用
   */
  protected Log log = LogFactory.getLog(getClass());

  /**
   * struts2的ActionContext
   */
  private ActionContext actionContext;

  /**
   * 待注入的Manager
   */
  private E manager;

  /**
   * 获得struts2的ActionContext
   */
  protected final ActionContext getActionContext() {
    return (actionContext == null) ? actionContext = ActionContext.getContext()
        : actionContext;
  }

  /**
   * 获得当前Action的名称
   */
  protected final String getActionName() {
    return getActionContext().getName();
  }

  /**
   * 获得当前Http Servlet Request
   */
  protected final HttpServletRequest getHttpServletRequest() {
    HttpServletRequest request = null;
    if (getActionContext() != null) { //从Struts2中取得Request
      request = (HttpServletRequest) getActionContext().get(
          StrutsStatics.HTTP_REQUEST);
    }
    if (request  == null) { //从DWR中取得Request
      request = WebContextFactory.get().getHttpServletRequest();
    }
    
    return request;    
  }

  /**
   * 获得当前HttpServletResponse.
   */
  protected final HttpServletResponse getHttpServletResponse() {
    HttpServletResponse response = null;
    if (getActionContext() != null) { //从Struts2中取得Response
      response = (HttpServletResponse) getActionContext().get(
          StrutsStatics.HTTP_REQUEST);
    }
    if (response  == null) { //从DWR中取得Response
      response = WebContextFactory.get().getHttpServletResponse();
    }
    
    return response;    
  }
  /**
   * 返回Spring的ApplicationContext实例。
   * @return
   */
  protected ApplicationContext getApplicationContext() {
  	ServletContext ctx = null;
  	
  	if (getActionContext() != null) {
  	  ctx =	
  	  	(ServletContext) getActionContext().get(StrutsStatics.SERVLET_CONTEXT);
  	} else {
  		ctx = WebContextFactory.get().getServletContext();
  	}
  	return WebApplicationContextUtils.getWebApplicationContext(ctx);
  }

  protected final E getManager() {
    return manager;
  }

  public final void setManager(E manager) {
    this.manager = manager;
  }
  /**
   * 
   * @return ServletContext
   */
  protected ServletContext getServletContext() {
    return (ServletContext) getActionContext().get(
        StrutsStatics.SERVLET_CONTEXT);
  }
  /**
   * 在Action函数return之前调用，可以防止浏览器缓存页面，从而使页面失效。
   */
  protected final void setPageExpired() {
    getHttpServletResponse().addHeader("Cache-Control", "no-cache");
    getHttpServletResponse().addHeader("Expires",
        "Thu, 01 Jan 1970 00:00:01 GMT");
  }

  /**
   * Application范围
   */
  protected static final String SCOPE_APPLICATION = "application";

  /**
   * Session 范围
   */
  protected static final String SCOPE_SESSION = "session";

  /**
   * Request范围
   */
  protected static final String SCOPE_REQUEST = "request";

  /**
   * 缺省范围，相当于<code>SCOPE_REQUEST</code>
   */
  protected static final String SCOPE_DEFAULT = SCOPE_SESSION;

  /**
   * 向web环境添加一个对象
   * @param name 对象的名称
   * @param object 被添加的对象
   * @param scope 范围，可以是 <code>SCOPE_REQUEST</code>,
   *          <code>SCOPE_SESSION</code> ,<code>SCOPE_APPLICATION</code>,
   *          <code>SCOPE_DEFAULT</code>中的一个.
   */
  protected final void addObject(String name, Object object, String scope) {
    if (StringUtils.isBlank(name)) {
      log.warn("You should indicate a name for the object");
      return;
    }

    if (StringUtils.isBlank("scope") || scope.equalsIgnoreCase(SCOPE_REQUEST)) {
      getHttpServletRequest().setAttribute(name, object);
    } else if (scope.equalsIgnoreCase(SCOPE_SESSION)) {
      Map sessionMap = getActionContext().getSession();
      sessionMap.put(name, object);
      getActionContext().setSession(sessionMap);
    } else if (scope.equalsIgnoreCase(SCOPE_APPLICATION)) {
      Map appMap = getActionContext().getApplication();
      appMap.put(name, object);
      getActionContext().setSession(appMap);
    } else {
      getHttpServletRequest().setAttribute(name, object);
    }
  }

  /**
   * 在request范围内添加一个对象. 相当于<code>addObject(name, object, null)</code>
   * @param name 被添加对象的名称.
   * @param object 被添加的对象。
   */
  protected final void addObject(String name, Object object) {
    addObject(name, object, SCOPE_DEFAULT);
  }
  /**
   * 获取当前登录的用户信息
   */
  public User getUserPrincipal() {
    return UserUtil.getPrincipal(getHttpServletRequest());
  }
}
