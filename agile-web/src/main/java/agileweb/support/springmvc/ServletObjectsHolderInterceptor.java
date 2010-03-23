package agileweb.support.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ServletObjectsHolderInterceptor extends HandlerInterceptorAdapter {
  private static Logger logger = LoggerFactory.getLogger(ServletObjectsHolderInterceptor.class);
   
  /**
   * @see HandlerInterceptorAdapter#preHandle(HttpServletRequest, HttpServletResponse, Object)
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    logger.debug("Do preHandler,putting Servlet Object to thread local.");
    ControllerContext ctx = new ControllerContext();
    ctx.setRequest(request);
    ctx.setResponse(response);
    ControllerContext.setControllerContext(ctx);
    return true;
  }

}
