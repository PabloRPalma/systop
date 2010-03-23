package com.systop.common.webapp.menu.impl;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

import com.systop.common.util.Dom4jUtil;
import com.systop.common.util.ReflectUtil;
import com.systop.common.webapp.menu.MenuConfig;
import com.systop.common.webapp.menu.MenuException;
import com.systop.common.webapp.menu.MenuItem;
import com.systop.common.webapp.menu.UrlAccessDecisionMaker;
import com.systop.common.webapp.menu.XmlMenuConfigReader;

/**
 * 菜单配置
 * @author Sam
 * 
 */
public final class MenuConfigImpl implements MenuConfig {
  /**
   * Log for the class
   */
  private static Log log = LogFactory.getLog(MenuConfigImpl.class);

  /**
   * 权限接口element
   */
  public static final String NAME_ELEMENT_AUTH_IMPL = "auth-impl";

  /**
   * 菜单的base url
   */
  public static final String NAME_ELEMENT_BASE = "base";

  /**
   * JSCookMenu的主题
   */
  public static final String NAME_ELEMENT_THEME = "theme";

  /**
   * 菜单项Element
   */
  public static final String NAME_ELEMENT_MENU = "menu";

  /**
   * 菜单配置文件
   */
  private URL resource;

  /**
   * 权限接口
   */
  private UrlAccessDecisionMaker accessdecisionMaker;

  /**
   * 菜单URL的基础
   */
  private String base = "";

  /**
   * JSCookMenu主题
   */
  private String theme = "";

  /**
   * MenuItems
   */
  private List<MenuItem> menu = null;

  /**
   * 是否已经初始化
   */
  private boolean hasConfiged = false;

  /**
   * 初始化菜单配置文件
   */
  public MenuConfigImpl(URL resource) {
    this.resource = resource;
  }

  /**
   * 设置菜单配置文件的位置
   */
  public void setMenuConfig(String configFile) {
    try {
      this.resource = ResourceUtils.getURL(configFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置菜单配置文件的URL位置
   */
  public void setMenuConfig(URL configResource) {
    this.resource = configResource;
  }

  public UrlAccessDecisionMaker getUrlAccessDecisionMaker() {
    return accessdecisionMaker;
  }

  /**
   * 返回菜单项
   */
  public Iterator<MenuItem> getMenu() throws MenuException {
    return (menu == null) ? null : menu.iterator();
  }

  public String getBase() {
    return base;
  }

  public String getTheme() {
    return theme;
  }
  
  /**
   * 初始化菜单
   */
  public void config() {
    if (hasConfiged) {
      return;
    }

    SAXReader sax = new SAXReader();
    Document doc = null;
    try {
      doc = sax.read(resource);
    } catch (DocumentException e) {
      log.error(e.getMessage());
    }

    Element root = doc.getRootElement();
    // Ȩ������
    Element eleAuth = root.element(NAME_ELEMENT_AUTH_IMPL);
    if (eleAuth != null) {
      String authImpl = Dom4jUtil.getString(eleAuth, null);
      try {
        accessdecisionMaker = (UrlAccessDecisionMaker) ReflectUtil
            .classForName(authImpl).newInstance();
      } catch (Exception e) {
        log.warn("UrlAccessDecisionMaker is null.");
      }
    }

    // base
    Element eleBase = root.element(NAME_ELEMENT_BASE);
    if (eleBase != null) {
      base = Dom4jUtil.getString(eleBase, "");
    }
    // theme
    Element eleTheme = root.element(NAME_ELEMENT_THEME);
    if (eleTheme != null) {
      theme = Dom4jUtil.getString(eleTheme, "");
    }
    // menu
    XmlMenuConfigReader reader = new XmlMenuConfigReaderImpl();
    menu = reader.readMenu(resource);

    hasConfiged = true;
  }

}
