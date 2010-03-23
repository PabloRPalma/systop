package agileweb.taglib;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 从Spring上下文中得到一个Bean，并且将它设置为一个Page/Request/Session
 * 的Attribute。Attribute的名字为spring的bean id。如果给定的bean不存在，则不做任何操作。
 * @author SAM Lee
 *
 */
public class SpringTag extends TagSupport {
  private static Logger logger = LoggerFactory.getLogger(SpringTag.class);
  
  public static final String SCOPE_PAGE = "page";
  public static final String SCOPE_REQUEST = "request";
  public static final String SCOPE_SESSION = "session";
  /**
   * Spring bean id
   */
  private String beanId;
  
  /**
   * 给出范围，page,request,session
   */
  private String scope = SCOPE_REQUEST;

  @Override
  public int doEndTag() throws JspException {
    if(beanId == null || "".equals(beanId)) {
      return SKIP_BODY;
    }
    //得到Spring Context
    ServletContext servletCtx = pageContext.getServletContext();
    ApplicationContext ctx = WebApplicationContextUtils
        .getWebApplicationContext(servletCtx);
    
    if(ctx != null) {
      Object bean = null;
      try {
        bean = ctx.getBean(beanId);
      } catch (BeansException e) {
        logger.error("沒有找到需要的bean, id 是 {}", beanId);
      }
      if(bean != null) {
        //放在pageContext中
        if(scope.equalsIgnoreCase(SCOPE_PAGE)) {
          pageContext.setAttribute(beanId, bean);
        } else if(scope.equalsIgnoreCase(SCOPE_SESSION)) {//Session中
          pageContext.getSession().setAttribute(beanId, bean);
        } else {//Reqeust attribute
          pageContext.getRequest().setAttribute(beanId, bean);
        }
      }
    }
    return SKIP_BODY;
  }

  @Override
  public void release() {
    beanId = null;
    scope = SCOPE_REQUEST;
    super.release();
  }
  /**
   * @return the beanId
   */
  public String getBeanId() {
    return beanId;
  }

  /**
   * @param beanId the beanId to set
   */
  public void setBeanId(String beanId) {
    this.beanId = beanId;
  }

  /**
   * @return the scope
   */
  public String getScope() {
    return scope;
  }

  /**
   * @param scope the scope to set
   */
  public void setScope(String scope) {
    this.scope = scope;
  }
}
