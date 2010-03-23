package com.systop.common.webapp.menu.impl;

import com.systop.common.webapp.menu.AbstractItemRender;
import com.systop.common.webapp.menu.MenuException;
import com.systop.common.webapp.menu.MenuItem;
import com.systop.common.webapp.menu.UrlAccessDecisionMaker;

/**
 * 渲染“叶”菜单
 * @author Sam
 *
 */
public final class LeafItemRenderJSCookMenuImpl extends AbstractItemRender {
  /**
   * 根据给定的MenuItem和UrlAccessDecisionMaker渲染叶菜单
   */
  public String render(MenuItem item, UrlAccessDecisionMaker auth)  
    throws MenuException {
    if (item == null) {
      throw new MenuException("Item object is null.");
    }
    
    if (item.getChildren().hasNext()) {
      throw new MenuException("Leaf item must not have any children.");
    }
    //权限检查
    if (auth != null && !auth.isAccessible(item.getUrl())) {
      return "";
    }
    //检查URL
    
    if (item.getUrl().length() == 0) {
      throw new MenuException("Leaf item must have a URL.");
    }
    String url = rebuildUrl(item.getUrl());
    
    //检查icon
    String icon = buildIcon(item.getIcon());
    
    //['icon', 'title', 'url', 'target', 'description']
    StringBuffer itemStr = new StringBuffer(
        MenuUtils.buildSpaces(item.getLevel()))
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
    .append("']");
    
    return itemStr.toString();
  }

}
