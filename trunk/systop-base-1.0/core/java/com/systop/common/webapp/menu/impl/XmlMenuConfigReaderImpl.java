package com.systop.common.webapp.menu.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.systop.common.util.Dom4jUtil;
import com.systop.common.webapp.menu.MenuItem;
import com.systop.common.webapp.menu.XmlMenuConfigReader;

/**
 * 从XML配置文件中读取菜单项
 * @author Sam
 * 
 */
public final class XmlMenuConfigReaderImpl implements XmlMenuConfigReader {
  /**
   * Log of the class
   */
  private static Log log = LogFactory.getLog(XmlMenuConfigReaderImpl.class);

  // 定义XML文件的属性和element名称
  /**
   * 菜单Icon属性
   */
  public static final String NAME_ATTR_ICON = "icon";

  /**
   * 菜单标题
   */
  public static final String NAME_ATTR_TITLE = "title";

  /**
   * 菜单描述
   */
  public static final String NAME_ATTR_DESCRIPTION = "description";

  /**
   * 菜单指向的URL
   */
  public static final String NAME_ATTR_URL = "url";

  /**
   * URL执行的target，相当于HTML的target
   */
  public static final String NAME_ATTR_TARGET = "target";

  /**
   * 分割菜单项的属性名称
   */
  public static final String NAME_ATTR_SPLIT = "split";

  /**
   * 菜单项的element
   */
  public static final String NAME_MENU_ELEMENT = "menu";

  /**
   * 空白占位符
   */
  public static final String NAME_ELEMENT_SPACER = "spacer";

  /**
   * 空白占位符的属性
   */
  private String spacerIcon = "";

  /**
   * 读取菜单项
   */
  public MenuItem readItem(Element item, int level) {
    MenuItemImpl menuItem = new MenuItemImpl(level);
    menuItem.setIcon(Dom4jUtil.getString(item, NAME_ATTR_ICON, spacerIcon));
    menuItem.setDescription(Dom4jUtil
        .getString(item, NAME_ATTR_DESCRIPTION, ""));
    menuItem.setLevel(level);
    menuItem.setSplit(Dom4jUtil.getBoolean(item, NAME_ATTR_SPLIT, false));
    menuItem.setTitle(Dom4jUtil.getString(item, NAME_ATTR_TITLE, ""));
    menuItem.setUrl(Dom4jUtil.getString(item, NAME_ATTR_URL, ""));
    menuItem.setTarget(Dom4jUtil.getString(item, NAME_ATTR_TARGET, ""));
    // 迭代读取下级菜单˵�
    Iterator sub = item.elementIterator(NAME_MENU_ELEMENT);
    if (sub != null) {
      while (sub.hasNext()) {
        Element child = (Element) sub.next();
        menuItem.addChild(readItem(child, level + 1));
      }
    }

    return menuItem;
  }

  /**
   * 读取整个菜单
   */
  public List<MenuItem> readMenu(URL configFile) {
    SAXReader saxReader = new SAXReader();
    Document doc = null;
    try {
      doc = saxReader.read(configFile);
    } catch (DocumentException e) {
      log.error(e.getMessage());
    }

    List<MenuItem> menus = new ArrayList();

    if (doc != null) {
      Element root = doc.getRootElement();
      Iterator rootMenu = root.elementIterator(NAME_MENU_ELEMENT);
      spacerIcon = Dom4jUtil.getString(root.element(NAME_ELEMENT_SPACER), "");

      if (rootMenu != null) {
        while (rootMenu.hasNext()) {
          Element menu = (Element) rootMenu.next();
          MenuItem item = this.readItem(menu, 1);
          menus.add(item);
        }
      }
    }

    return menus;
  }

}
