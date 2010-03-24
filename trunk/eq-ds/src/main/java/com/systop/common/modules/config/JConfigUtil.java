package com.systop.common.modules.config;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.jconfig.Category;
import org.jconfig.Configuration;
import org.jconfig.ConfigurationManager;
import org.jconfig.ConfigurationManagerException;
import org.jconfig.handler.ConfigurationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;
import org.springframework.util.ReflectionUtils;

import com.systop.core.Constants;
import com.systop.core.util.ReflectUtil;
import com.systop.core.util.ResourceBundleUtil;
import com.systop.core.util.StringUtil;
/**
 * Utilities for jconfig.
 * @author Sam Lee
 *
 */
@SuppressWarnings("unchecked")
public final class JConfigUtil {
  private static Logger logger = LoggerFactory.getLogger(JConfigUtil.class);
  
  /**
   * ConfigurationName of jconfig
   */
  public static final String DEFAULT_CONFIGURATION_NAME = ResourceBundleUtil
  .getString(Constants.RESOURCE_BUNDLE, "global.jconfig.configurationName", "application");
  /**
   * Instance of <code>ConfigurationManager</code>d
   */
  public static final ConfigurationManager CFG_MGR = ConfigurationManager.getInstance();
  
  /**
   * Load indicated configuration.
   */
  public static Configuration load(ConfigurationHandler handler, String configurationName) {
    try {
      CFG_MGR.load(handler, configurationName);
    } catch (ConfigurationManagerException e) {
      logger.error("Error occurs when loading configuration.{}", e.getMessage());
      e.printStackTrace();
    }
    return ConfigurationManager.getConfiguration(configurationName);
  }
  /**
   * Load default configuration, the name is defined in confg/application.properites
   */
  public static Configuration load(ConfigurationHandler handler) {
    try {
      CFG_MGR.load(handler, DEFAULT_CONFIGURATION_NAME);
    } catch (ConfigurationManagerException e) {
      logger.error("Error occured when loading default configuration.{}", e.getMessage());
      e.printStackTrace();
    }
    
    return ConfigurationManager.getConfiguration(DEFAULT_CONFIGURATION_NAME);
  }
  
  /**
   * Save the configuration using indicated handler.
   */
  public static void save(ConfigurationHandler handler, Configuration config) {
    try {
      CFG_MGR.save(handler, config);
    } catch (ConfigurationManagerException e) {
      logger.error("Error occured when saving configuration.{}", e.getMessage());
      e.printStackTrace();
    }
  }
  /**
   * Save the configuration using the associated handler.
   */
  public static void save(String configurationName) {
    try {
      CFG_MGR.save(configurationName);
    } catch (ConfigurationManagerException e) {
      logger.error("Error occured when saving configuration.{}", e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Extract the properties of the indicated category, and encapsulate them to a Java Bean
   * @param <T> 范型转换的定义
   * @param clazz 指定的类型
   * @param category category name.
   * @return Instance of <code>clazz</code>
   * @see {@link StringUtil#parseSetter(String)}
   */

   public static <T> T toObject(Class<T> clazz, String category, Configuration cfg) {
    Object object = ReflectUtil.newInstance(clazz);
    //得到给定Category下的所有Properties
    String[] names = cfg.getPropertyNames(category);
    if (names == null || names.length == 0) {
      return (T) object;
    }
    //设置属性
    for (int i = 0; i < names.length; i++) {
      String value = cfg.getProperty(names[i], StringUtils.EMPTY, category);
      //从Property name得到setter name
      String setter = StringUtil.parseSetter(names[i]); 
      //得到声明的Method对象
      Method method = ReflectUtil.findDeclaredMethod(clazz, setter);
      if (method.getParameterTypes().length == 1) {
        Class paramType = method.getParameterTypes()[0];
        //处理property 类型为数字的情况   
        if ((paramType.getSuperclass() != null && paramType.getSuperclass().equals(Number.class))) {
          ReflectionUtils.invokeMethod(method, object, new Object[] {NumberUtils
              .parseNumber(value, paramType) });
        } else if (paramType.isInstance(StringUtils.EMPTY)) { //处理String的情况
          ReflectionUtils.invokeMethod(method, object, new Object[] { value });
        } else {
          ReflectionUtils.invokeMethod(method, object, new Object[] { null });
        }
      }
    }
    return (T) object;
  }
  
   /**
    * Extract the properties form a Java Bean ,and put them into a indicated category.
    */
  public static void fromObject(Object object, String category, Configuration cfg) {
    if(object == null) {
      return;
    }
    
    Category cat = cfg.getCategory(category);
    
    Class clazz = object.getClass();
    Method[] methods = clazz.getDeclaredMethods();
    for(Method method : methods) {
      String name = method.getName();
      if(StringUtil.isGetter(name)) {
        name = StringUtil.accessor2Property(name);
        Object value = ReflectionUtils.invokeMethod(method, object, null);
        cat.setProperty(name, (value == null) ? null : value.toString());
      }
    }
  }
  
  private JConfigUtil() {
  }
}
