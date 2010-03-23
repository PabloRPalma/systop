package com.systop.common.webapp.menu.impl;

import com.systop.common.webapp.menu.ItemRender;
import com.systop.common.webapp.menu.MenuItem;

/**
 * 根据Item的类别，创建不同的ItemRender.
 * @author Sam
 * 
 */
public final class ItemRenderFactory {

  /**
   * @param child MenuItem的实例
   */
  public static ItemRender createRender(MenuItem child) {
    if (child.isSplit()) {
      return new SplitItemRenderJSCookMenuImpl();
    } else if (child.getChildren().hasNext()) {
      return new FolderItemRenderJSCookMenuImpl();
    } else if (!child.getChildren().hasNext() && child.getUrl().length() > 0) {
      return new LeafItemRenderJSCookMenuImpl();
    } else {
      return null;
    }
  }

  /**
   * Prevent from init
   */
  private ItemRenderFactory() {

  }
}
