package com.systop.common.security.acegi.cache;

import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.systop.common.cache.Cache;

/**
 * Implements of <code>UserCache</code>
 * @author Sam
 * 
 */
public class OsCacheBasedUserCache implements UserCache {
  /**
   * Log for the class
   */
  private static Log log = LogFactory.getLog(OsCacheBasedResourceCache.class);
  /**
   * Cache instance
   */
  private Cache cache;

  /**
   * @see UserCache#getUserFromCache(java.lang.String)
   */
  public UserDetails getUserFromCache(String username) {
    log.debug("get UserDetails from cache " + username);
    return (UserDetails) cache.get(username);
  }

  /**
   * @see UserCache#putUserInCache(UserDetails)
   */
  public void putUserInCache(UserDetails user) {
    if (user != null) {
      log.debug("put user in cache " + user.getUsername());
      cache.put(user.getUsername(), user);
    }
  }

  /**
   * @see UserCache#removeUserFromCache(java.lang.String)
   */
  public void removeUserFromCache(String username) {
    log.debug("remove user from cache " + username);
    cache.remove(username);
  }

  /**
   * @return the cache
   */
  public Cache getCache() {
    return cache;
  }

  /**
   * @param cache the cache to set
   */
  public void setCache(Cache cache) {
    this.cache = cache;
  }

}
