package com.systop.common.webapp.menu.impl;

import com.systop.common.webapp.menu.AbstractItemRender;
import com.systop.common.webapp.menu.MenuException;
import com.systop.common.webapp.menu.MenuItem;
import com.systop.common.webapp.menu.UrlAccessDecisionMaker;

/**
 * JsCookMenu风格的菜单分割线
 * @author Sam
 * 
 */
public final class SplitItemRenderJSCookMenuImpl extends AbstractItemRender {

  /**
   * @see com.systop.common.webapp.menu.ItemRender#render(MenuItem,
   *  UrlAccessDecisionMaker)
   */
  public String render(MenuItem item, UrlAccessDecisionMaker auth)
      throws MenuException {
    if (item == null) {
      throw new MenuException("Item object is null.");
    }

    if (!item.isSplit()) {
      throw new MenuException("You must set the 'split' attribute to 'true'.");
    }

    return MenuUtils.buildSpaces(item.getLevel()) + "_cmSplit";
  }

}
