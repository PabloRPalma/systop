package com.systop.common.webapp.menu.impl;



import java.util.Iterator;

import com.systop.common.webapp.menu.AbstractItemRender;
import com.systop.common.webapp.menu.ItemRender;
import com.systop.common.webapp.menu.MenuException;
import com.systop.common.webapp.menu.MenuItem;
import com.systop.common.webapp.menu.UrlAccessDecisionMaker;

/**
 * folder item的JSCookMenu实现。
 * @author Sam
 *
 */
public final class FolderItemRenderJSCookMenuImpl 
  extends AbstractItemRender {
  
  /**
   * @see com.systop.common.webapp.menu.ItemRender#
   * render(MenuItem, UrlAccessDecisionMaker)
   */
  public String render(MenuItem item, UrlAccessDecisionMaker auth)
      throws MenuException {
    if (item == null) {
      throw new MenuException("Item object is null.");
    }

    if (!item.getChildren().hasNext()) {
      throw new MenuException("Folder item must have some children.");
    }
    String url = rebuildUrl(item.getUrl());
    String icon = buildIcon(item.getIcon());

    StringBuffer itemStr = new StringBuffer(MenuUtils.buildSpaces(item
        .getLevel()))
    .append("['")
    .append(icon)
    .append("','")
    .append(item.getTitle())
    .append("','")
    .append(url)
    .append("','")
    .append(item.getTarget())
    .append("','")
    .append(item.getDescription())
    .append("',\n");

    Iterator children = item.getChildren();
    while (children.hasNext()) {
      MenuItem child = (MenuItem) children.next();
      ItemRender render = ItemRenderFactory.createRender(child);
      render.setBasePath(basePath);
      itemStr.append(render.render(child, auth));
      if (children.hasNext()) {
        itemStr.append(",\n");
      } else {
        itemStr.append("\n");
      }
    }
    itemStr.append(MenuUtils.buildSpaces(item.getLevel())).append("]");

    return itemStr.toString();
  }

}
