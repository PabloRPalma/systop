package com.systop.common.cache;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.util.PropertiesHelper;
import org.springframework.beans.factory.FactoryBean;

import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.Config;
import com.systop.common.util.StringUtil;

/**
 * 生产OSCache实例的工厂类，OsCahe的创建过程比较复杂，所以使用了工厂模式。
 *  这里，应用的是Spring的FactoryBean
 * @author Sam
 * 
 */

public class OsCacheFactoryBean implements FactoryBean {
  /**
   * Log for the class
   */
  private static Log log = LogFactory.getLog(OsCacheFactoryBean.class);

  /**
   * The <tt>OSCache</tt> refresh period property suffix.
   * 在oscache.properties文件中用regionname.refresh.period=xxx的形式
   * 定义某个region的刷新频率
   */
  public static final String OSCACHE_REFRESH_PERIOD = "refresh.period";

  /**
   * The <tt>OSCache</tt> CRON expression property suffix.
   */
  public static final String OSCACHE_CRON = "cron";

  /**
   * The <tt>OSCache</tt> cache capacity property suffix.
   */
  public static final String OSCACHE_CAPACITY = "capacity";
  
  /**
   * OsCache配置属性，一般来说，要把OsCache.properties文件放在classpath下
   */
  private static final Properties OSCACHE_PROPERTIES = new Config()
      .getProperties();

  /**
   * Region name of the cache entry.
   */
  private String regionName;

  /**
   * 返回OSCache的实例
   * @see {@link FactoryBean#getObject()}
   */
  public Object getObject() throws Exception {

    int refreshPeriod = PropertiesHelper.getInt(StringUtil.qualify(regionName,
        OSCACHE_REFRESH_PERIOD), OSCACHE_PROPERTIES,
        CacheEntry.INDEFINITE_EXPIRY);
    String cron = OSCACHE_PROPERTIES.getProperty(StringUtil.qualify(regionName,
        OSCACHE_CRON));
    log.debug(regionName + " refresh period " + refreshPeriod);
    // construct the cache
    final OSCache cache = new OSCache(refreshPeriod, cron, regionName);
   
    Integer capacity = PropertiesHelper.getInteger(StringUtil.qualify(
        regionName, OSCACHE_CAPACITY), OSCACHE_PROPERTIES);
    if (capacity != null) {
      cache.setCacheCapacity(capacity.intValue());
    }

    if (log.isDebugEnabled()) {
      log.debug("A oscache instance has been created.");
    }
    return cache;
  }

  /**
   * @see {@link FactoryBean#getObjectType()}
   */
  public Class getObjectType() {
    return OSCache.class;
  }

  /**
   * @see {@link FactoryBean#isSingleton()}
   */
  public boolean isSingleton() {
    return false;
  }

  /**
   * @return the regionName
   */
  public String getRegionName() {
    return regionName;
  }

  /**
   * @param regionName the regionName to set
   */
  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

}
