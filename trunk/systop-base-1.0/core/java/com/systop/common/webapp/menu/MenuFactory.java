package com.systop.common.webapp.menu;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;

import com.systop.common.webapp.menu.impl.MenuConfigImpl;
import com.systop.common.webapp.menu.impl.MenuRenderJSCookMenuImpl;
/**
 * Menu模块的工厂类。使用ThreadLocal保存各个menuConfig配置文件的菜单对象以及MenuRender
 * 的配置。
 * @author Administrator
 *
 */
public final class MenuFactory {
  /**
   * Log of the class
   */
  private static Log log = LogFactory.getLog(MenuFactory.class);
  
  /**
   * 用于保存菜单配置信息
   */
  private static ThreadLocal<Map> cfgHolder = new ThreadLocal();
  
  /**
   * 用于保存菜单渲染器
   */
  private static ThreadLocal<MenuRender> renderHolder = new ThreadLocal();
  
  /**
   * prevent from initializing.
   */
  private MenuFactory() {
  }
  
  /**
   * 根据指定的配置文件，返回菜单配置对象。
   */
  public final static MenuConfig getMenuConfig(String menuConfig) {
    MenuConfig cfg = null;
    URL url = null;
    try {
      url = ResourceUtils.getURL(menuConfig);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
   
    Map<URL, MenuConfig> menuConfigs = null;
    
    if (cfgHolder.get() != null) {
      menuConfigs = cfgHolder.get();
      if (log.isDebugEnabled()) {
        log.debug("get menu config map from ThreadLocal.");
      }
    } else {
      menuConfigs = new HashMap();
    }
    
    if (menuConfigs.containsKey(url)) {
      cfg = menuConfigs.get(url);
      if (log.isDebugEnabled()) {
        log.debug("get menu config from ThreadLocal.");
      }
    } else {
      cfg = new MenuConfigImpl(url);
      cfg.config();
      //将配置文件的url和读取配置文件形成的结果put
      //这样可以使用多个配置文件了
      menuConfigs.put(url, cfg);
      cfgHolder.set(menuConfigs);
      if (log.isDebugEnabled()) {
        log.debug("create new menu config");
      }
    }
    
    return cfg;
  }
  
  /**
   * 创建菜单渲染器
   */
  public final static MenuRender createMenuRender() {
    MenuRender mr = renderHolder.get();
    if (mr == null) {
      mr = new MenuRenderJSCookMenuImpl();
      renderHolder.set(mr);
    }
    
    return mr; 
  }

}
