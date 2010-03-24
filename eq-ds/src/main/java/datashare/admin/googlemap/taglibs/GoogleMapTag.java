package datashare.admin.googlemap.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import datashare.admin.googlemap.service.GoogleMapManager;

/**
 * GoogleMap密钥标签类
 * @author dhm
 *
 */
@SuppressWarnings("serial")
public class GoogleMapTag extends BodyTagSupport {

  @Override
  public int doEndTag() throws JspException {
    ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
    //获取GoogleMap密钥manager
    GoogleMapManager googleMapManager = (GoogleMapManager)ctx.getBean("googleMapManager");
    //获取已设置密钥，若未设置，返回no key has set
    String googleMapId = googleMapManager.get() == null ? "no key has set" : googleMapManager.get().getGoogleMapId();
    try {
      //将key写入页面
      pageContext.getOut().print(googleMapId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return EVAL_PAGE;
  }
  
  
}
