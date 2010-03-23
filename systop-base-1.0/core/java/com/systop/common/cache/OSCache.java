package com.systop.common.cache;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 用OsCache实现<code>Cache</code>接口。
 * @author Sam
 *
 */
public class OSCache implements Cache {
  /**
   * Log for the class
   */
  private static Log logger = LogFactory.getLog(OSCache.class);

  /**
   * The OSCache 2.0 cache administrator. By author:this class is copied from
   * Hibernate
   */
  private GeneralCacheAdministrator cache;
  
  /**
   * 刷新频率
   */
  private final int refreshPeriod;
  
  /**
   * cron表达式
   */
  private final String cron;
  
  /**
   * regionName
   */
  private final String regionName;
  
  /**
   * 构造方法，用于根据刷新频率，cron表达式，region生成<code>Cache</code>
   * 的实例。
   * 
   */
  public OSCache(int refreshPeriod, String cron, String region) {
    cache = new GeneralCacheAdministrator();
    this.refreshPeriod = refreshPeriod;
    this.cron = cron;
    this.regionName = region;
  }

  /**
   * Make the key for cache
   * @param key the gavin key object
   * @return the key as string
   */
  private String makeKey(Object key) {
    return String.valueOf(key) + '.' + regionName;
  }

  /**
   * set the cache capactity by number
   */
  public void setCacheCapacity(int cacheCapacity) {
    cache.setCacheCapacity(cacheCapacity);
  }

  /*
   * The implementation of Cache
   */

  /**
   * @see net.chinasam.common.cache.Cache#read(Object)
   */
  public Object read(Object key) {
    if (logger.isDebugEnabled()) {
      logger.debug("read a object by key:" + key.toString());
    }
    return get(key);
  }

  /**
   * @see net.chinasam.common.cache.Cache#get(Object)
   */
  public Object get(Object key) {
    try {
      if (logger.isDebugEnabled()) {
        logger.debug("get a object from cache:" + key.toString());
      }
      return cache.getFromCache(makeKey(key), refreshPeriod, cron);
    } catch (NeedsRefreshException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("OSCache:cancelUpdate");
      }
      cache.cancelUpdate(makeKey(key));
      return null;
    }
  }

  /**
   * @see net.chinasam.common.cache.Cache#put(Object, Object)
   */
  public void put(Object key, Object value) {
    if (logger.isDebugEnabled()) {
      logger.debug("OSCache:putInCache.");
    }
    cache.putInCache(makeKey(key), value);
  }

  /**
   * @see net.chinasam.common.cache.Cache#update(Object, Object)
   */
  public void update(Object key, Object value) {
    if (logger.isDebugEnabled()) {
      logger.debug("put in cache:" + key);
    }
    put(key, value);
  }

  /**
   * @see net.chinasam.common.cache.Cache#remove(Object)
   */
  public void remove(Object key) {
    if (logger.isDebugEnabled()) {
      logger.debug("flush entry" + key.toString());
    }
    cache.flushEntry(makeKey(key));
  }

  /**
   * @see net.chinasam.common.cache.Cache#clear()
   */
  public void clear() {
    if (logger.isDebugEnabled()) {
      logger.debug("OSCache:flushAll");
    }
    cache.flushAll();
  }

  /**
   * @see net.chinasam.common.cache.Cache#destroy()
   */
  public void destroy() {
    if (logger.isDebugEnabled()) {
      logger.debug("OSCache:destroy");
    }
    cache.destroy();
  }

  /**
   * @see net.chinasam.common.cache.Cache#lock(Object)
   */
  public void lock(Object key) {
    throw new UnsupportedOperationException("OSCache.lock");
  }

  /**
   * @see net.chinasam.common.cache.Cache#unlock(Object)
   */
  public void unlock(Object key) {
    throw new UnsupportedOperationException("OSCache.unlock");
  }

  /**
   * @see net.chinasam.common.cache.Cache#getRegionName()
   */
  public String getRegionName() {
    return regionName;
  }

  /**
   * @see net.chinasam.common.cache.Cache#toMap()
   */
  public Map toMap() {
    throw new UnsupportedOperationException("OSCache.toMap");
  }

}
