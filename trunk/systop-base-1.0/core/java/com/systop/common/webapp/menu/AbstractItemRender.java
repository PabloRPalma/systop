package com.systop.common.webapp.menu;

/**
 * 提供<code>basePath</code>属性的缺省实现
 * 
 * @author Sam
 * 
 */
public abstract class AbstractItemRender implements ItemRender {
  /**
   * 菜单的URL基础
   */
  protected String basePath;

  /**
   * @see net.chinasam.common.webapp.menu.ItemRender#setBasePath
   *      (java.lang.String)
   */
  public final void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public final String getBasePath() {
    return basePath;
  }

  /**
   * 重新建立RUL，也就是根据菜单配置，将URL改写为绝对路径
   */
  protected final String rebuildUrl(String path) {
    if (path != null && path.trim().length() > 0 
        && !path.trim().equals("null")) {
      if (!path.startsWith("http://") && !path.startsWith("https://")
          && !path.startsWith("ftp://") && !path.startsWith("/")) {
        path = basePath + path;
      }
    }

    return path;
  }
  
  /**
   * 建立菜单ICON的绝对路径
   */
  protected final String buildIcon(String iconUrl) {
    if (iconUrl != null && iconUrl.trim().length() > 0
        && !iconUrl.trim().equals("null") && !iconUrl.startsWith("<img")) {
      StringBuffer buf = new StringBuffer("<img").append(" src=").append(
          basePath).append("menu/").append(iconUrl).append(" border=0>");
      return buf.toString();
    }

    return iconUrl;
  }

}
