package agileweb.support.springmvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerContext {
  private static ThreadLocal<ControllerContext> controllerContext = new ThreadLocal<ControllerContext>();
  
  public static final String HTTP_REQUEST = "_http_request"; 
  public static final String HTTP_RESPONSE = "_http_response";
  private Map<String, Object> context = new HashMap<String, Object>();
  /**
   * 缺省构造器，只有同一个package下的类可以访问
   */
  ControllerContext() {
  }
  
  static void setControllerContext(ControllerContext context) {
    controllerContext.set(context);
  }
  
  void setRequest(HttpServletRequest request) {
    context.put(HTTP_REQUEST, request);
  }
  
  void setResponse(HttpServletResponse response) {
    context.put(HTTP_RESPONSE, response);
  }
  
  public static ControllerContext getInstance() {
    return controllerContext.get();
  }
  
  public HttpServletRequest getRequest() {
    return (HttpServletRequest) context.get(HTTP_REQUEST);
  }
  
  public HttpServletResponse getResponse() {
    return (HttpServletResponse) context.get(HTTP_RESPONSE);
  }
}
