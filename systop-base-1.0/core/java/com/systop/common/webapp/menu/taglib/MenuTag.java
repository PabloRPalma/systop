package com.systop.common.webapp.menu.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.systop.common.util.RequestUtil;
import com.systop.common.webapp.menu.MenuConfig;
import com.systop.common.webapp.menu.MenuException;
import com.systop.common.webapp.menu.MenuFactory;
import com.systop.common.webapp.menu.MenuRender;
import com.systop.common.webapp.menu.UrlAccessDecisionMaker;

/**
 * 菜单TagLib,下面是tld配置：
 * <pre>
 * &lt;tag&gt;<BR>&nbsp;&lt;description&gt;
 * A html menu tag that supports most of browsers and uses the JSCookMenu.
 * &lt;/description&gt;<BR>&nbsp;&lt;display-name&gt;ChinaSam JSP Menu
 * &lt;/display-name&gt;<BR>&nbsp;&lt;name&gt;menu&lt;/name&gt;
 * <BR>&nbsp;&lt;tag-class&gt;com.systop.common.webapp.menu.taglib.MenuTag
 * &lt;/tag-class&gt;<BR>&nbsp;&lt;body-content&gt;JSP&lt;
 * /body-content&gt;<BR>&nbsp;&lt;attribute&gt;<BR>
 * &nbsp;&nbsp;&lt;name&gt;menuName&lt;/name&gt;<BR>
 * &nbsp;&nbsp;&lt;required&gt;true&lt;/required&gt;<BR>
 * &nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;/type&gt;<BR>
 * &nbsp;&lt;/attribute&gt;<BR>&nbsp;&lt;attribute&gt;<BR>
 * &nbsp;&nbsp;&lt;name&gt;configFile&lt;/name&gt;<BR>
 * &nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;/type&gt;<BR>
 * &nbsp;&lt;/attribute&gt;<BR>&nbsp;&lt;attribute&gt;<BR>
 * &nbsp;&nbsp;&lt;description&gt;valid value:h
 * br,hbl,hul,hur,vbr,vbl,vul,vur;default is hbr
 * &lt;/description&gt;<BR>
 * &nbsp;&nbsp;&lt;name&gt;align&lt;/name&gt;<BR>
 * &nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;/type&gt;<BR>
 * &nbsp;&lt;/attribute&gt;<BR>&nbsp;&lt;attribute&gt;<BR>
 * &nbsp;&nbsp;&lt;name&gt;theme&lt;/name&gt;<BR>
 * &nbsp;&nbsp;&lt;required&gt;false&lt;/required&gt;<BR>
 * &nbsp;&nbsp;&lt;type&gt;java.lang.String&lt;/type&gt;<BR>
 * &nbsp;&lt;/attribute&gt;<BR>&nbsp;&lt;/tag&gt;
 * </pre>
 * @author Sam
 * 
 */
public final class MenuTag extends TagSupport {
  /**
   * Log of the class
   */
  private static Log log = LogFactory.getLog(MenuTag.class);

  /**
   * 菜单名称
   */
  private String menuName = null;

  /**
   * 配置文件在ClassPath中的位置
   */
  private String configFile = "menu-config.xml";

  /**
   * 符合JsCookMenu格式的菜单对齐方式
   */
  private String align = "hbr";

  /**
   * 菜单的主题
   */
  private String theme = null;

  public String getAlign() {
    return align;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public String getConfigFile() {
    return configFile;
  }

  public void setConfigFile(String configFile) {
    this.configFile = configFile;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  /**
   * @see TagSupport#doEndTag()
   */
  public int doEndTag() throws JspException {
    super.doEndTag();
    MenuConfig cfg = MenuFactory.getMenuConfig(configFile);
    MenuRender render = MenuFactory.createMenuRender();
    // 取得Spring上下文,安全接口会使用它找到安全配置文件
    WebApplicationContext context = WebApplicationContextUtils
        .getWebApplicationContext(this.pageContext.getServletContext());
    // 取得访问决定器,并设置安全上下文
    UrlAccessDecisionMaker accessDecisionMaker = cfg
        .getUrlAccessDecisionMaker();
    accessDecisionMaker.setSecurityContext(context);

    render.setAuth(accessDecisionMaker);
    String base = cfg.getBase();
    if (base == null || base.length() == 0) {
      base = RequestUtil.getBasePath((HttpServletRequest) pageContext
          .getRequest());
    }
    render.setBase(base);

    render.setMenuAlign(align);
    if (menuName == null) {
      throw new JspException("please decleare a menu name.");
    }
    render.setMenuName(menuName);
    render.setTheme((theme == null) ? cfg.getTheme() : theme);
    try {
      render.setMenu(cfg.getMenu());
      pageContext.getOut().println(render.render());
    } catch (MenuException e) {
      log.error(e.getMessage());
      throw new JspException("Invalid Menu,please check '" + configFile + "',"
          + "message is:'" + e.getMessage() + "'");
    } catch (IOException e) {
      log.error("Io error:" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error("menu error:" + e.getMessage());
    }

    return EVAL_PAGE;
  }

  /**
   * @see TagSupport#release()
   */
  public void release() {
    super.release();
    menuName = null;
    configFile = "menu-config.xml";
    align = "hbr";
    theme = null;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

}
