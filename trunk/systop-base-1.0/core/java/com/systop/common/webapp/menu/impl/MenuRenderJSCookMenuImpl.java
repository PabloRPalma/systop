package com.systop.common.webapp.menu.impl;

import java.util.Iterator;

import com.systop.common.webapp.menu.ItemRender;
import com.systop.common.webapp.menu.MenuException;
import com.systop.common.webapp.menu.MenuItem;
import com.systop.common.webapp.menu.MenuRender;
import com.systop.common.webapp.menu.UrlAccessDecisionMaker;

/**
 * {@link http://www.cs.ucla.edu/~heng/JSCookMenu/ThemeOffice.html}
 * 根据JsCookMenu提供的Js实现菜单.
 * @author Sam
 * 
 */
public final class MenuRenderJSCookMenuImpl implements MenuRender {
  /**
   * 菜单主题的前缀
   */
  public static final String VAR_PREFIX_THEME = "cm";

  /**
   * 菜单js、css等文件的相对位置
   */
  public static final String MENU_LOCATION = "menu/";

  /**
   * 菜单名称
   */
  private String menuName;

  /**
   * 菜单Bass URL
   */
  private String base;

  /**
   * 菜单主题
   */
  private String theme;

  /**
   * 对齐方式
   */
  private String align;

  /**
   * 权限接口
   */
  private UrlAccessDecisionMaker auth;

  /**
   * 菜单项
   */
  private Iterator<MenuItem> menus = null;

  /**
   * set base url
   */
  public void setBase(String base) {
    if (!base.endsWith("/") && !base.endsWith("\\")) {
      base += "/";
    }

    this.base = base;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public void setAuth(UrlAccessDecisionMaker auth) {
    this.auth = auth;
  }

  public void setMenu(Iterator<MenuItem> menu) {
    this.menus = menu;
  }
  
  /**
   * set the align
   */
  public void setMenuAlign(String menuAlign) {
    this.align = menuAlign;
  }

  /**
   * @see MenuRender#render()
   */
  public String render() throws MenuException {
    StringBuffer menu = new StringBuffer("<SCRIPT LANGUAGE='JavaScript' SRC='")
    .append(base)
    .append(MENU_LOCATION)
    .append("JSCookMenu.js'></SCRIPT>\n")
    .append("<LINK REL='stylesheet' HREF='")
    .append(base)
    .append(MENU_LOCATION)
    .append(theme)
    .append("/theme.css' TYPE='text/css'>\n")
    //javascript变量，用于确定图片位置
    .append("<script language='javascript'>")
    .append("var cm").append(theme).append("Base='")
    .append(base)
    .append(MENU_LOCATION)
    .append(theme)
    .append("/';")
    .append("</script>\n")
    //
    .append("<SCRIPT LANGUAGE='JavaScript' SRC='")
    .append(base)
    .append(MENU_LOCATION)
    .append(theme)
    .append("/theme.js'></SCRIPT>\n")
    .append("<DIV id='")
    .append(this.menuName)
    .append("_id'></DIV>\n")
    .append("<SCRIPT LANGUAGE='javascript'>\n")
    .append("var ")
    .append(this.menuName)
    .append("=\n[\n");
    if (menu != null) {
      while (menus.hasNext()) {
        MenuItem item = menus.next();
        ItemRender render = ItemRenderFactory.createRender(item);
        render.setBasePath(base); // 设置基础路径，这是所有URL的base

        menu.append(render.render(item, this.auth));
        if (menus.hasNext()) {
          menu.append(",\n");
        } else {
          menu.append("\n");
        }
      }
    }
    menu.append("];\n");
    //cmDraw ('myMenuID', myMenu, 'hbr', cmThemeOffice, 'ThemeOffice');
    menu.append("cmDraw ('")
    .append(this.menuName)
    .append("_id', ")
    .append(this.menuName)
    .append(",'")
    .append(this.align)
    .append("',")
    .append(VAR_PREFIX_THEME)
    .append(theme)
    .append(",'")
    .append(theme)
    .append("');\n</script>\n");
    
    return menu.toString();
  }

}
