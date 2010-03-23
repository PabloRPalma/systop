package com.systop.common.security.acegi.cache;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.list.TreeList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.systop.common.cache.Cache;
import com.systop.common.security.acegi.resourcedetails.ResourceDetails;

/**
 * 使用OsCache实现<code>ResourceCache</code>
 * @author Sam
 * 
 */
public class OsCacheBasedResourceCache implements ResourceCache,
    InitializingBean {
  /**
   * Log for the class
   */
  private static Log log = LogFactory.getLog(OsCacheBasedResourceCache.class);

  /**
   * 所有缓存中的URL资源string
   */
  private List urlStrings;

  /**
   * 所有缓存中的function资源string
   */
  private List  funcStrings;

  /**
   * 缓存中其他的资源string
   */
  private List otherStrings;

  /**
   * Cache 接口
   */
  private Cache cache;

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

  /**
   * @see ResourceCache#getResourceFormCache(java.lang.String)
   */
  public ResourceDetails getResourceFormCache(String resString) {
    log.debug("get Resource from cache " + resString);
    return (ResourceDetails) cache.get(resString);
  }

  /**
   * @see ResourceCache#getResStringsFormCache(java.lang.String)
   */
  public List getResStringsFormCache(String resType) {
    if (resType == null) {
      return Collections.EMPTY_LIST;
    }
    if (StringUtils.equals(resType, ResourceDetails.RES_TYPE_FUNCTION)) {
      return (List) cache.get(KEY_OF_FUNCSTRINGS);
    } else if (StringUtils.equals(resType, ResourceDetails.RES_TYPE_URL)) {
      return (List) cache.get(KEY_OF_URLSTRINGS);
    } else {
      return (List) cache.get(KEY_OF_OTHERSTRINGS);
    }

  }

  /**
   * @see ResourceCache#putResourceInCache(ResourceDetails)
   */
  public void putResourceInCache(ResourceDetails resourceDetails) {
    if (resourceDetails != null) {
      log.debug("put Resource in cache " + resourceDetails.getResString());
    }

    cache.put(resourceDetails.getResString(), resourceDetails);
    addResString(resourceDetails);
  }

  /**
   * 向urlStrings或funcStrings或otherStrings加入一个元素
   * 
   */
  private void addResString(ResourceDetails rd) {
    if (rd == null) {
      return;
    }
    if (StringUtils.equals(ResourceDetails.RES_TYPE_FUNCTION,
        rd.getResType())) {
      funcStrings.add(rd.getResString());
    } else if (StringUtils
        .equals(ResourceDetails.RES_TYPE_URL, rd.getResType())) {
      urlStrings.add(rd.getResString());
    } else {
      otherStrings.add(rd.getResString());
    }

  }

  /**
   * 从urlStrings或funcStrings或otherStrings删除一个元素
   * 
   */
  private void removeResString(ResourceDetails rd) {
    if (rd == null) {
      return;
    }
    if (StringUtils.equals(ResourceDetails.RES_TYPE_FUNCTION, 
        rd.getResType())) {
      funcStrings.remove(rd.getResString());
    } else if (StringUtils
        .equals(ResourceDetails.RES_TYPE_URL, rd.getResType())) {
      urlStrings.remove(rd.getResString());
    } else {
      otherStrings.remove(rd.getResString());
    }
  }

  /**
   * @see ResourceCache#removeResourceFromCache(java.lang.String)
   */
  public void removeResourceFromCache(String resString) {
    log.debug("remove Resource from cache " + resString);
    ResourceDetails rd = (ResourceDetails) cache.get(resString);
    removeResString(rd);
    cache.remove(resString);
  }

  /**
   * 存放所有urls string的缓存"key"
   */
  private static final String KEY_OF_URLSTRINGS = "key_urls";

  /**
   * 存放所有function string的缓存"key"
   */
  private static final String KEY_OF_FUNCSTRINGS = "key_funcs";

  /**
   * 存放其他资源 string的缓存"key"
   */
  private static final String KEY_OF_OTHERSTRINGS = "key_others";

  /**
   * @see InitializingBean#afterPropertiesSet()
   */
  public void afterPropertiesSet() throws Exception {
    initCache();
  }

  /**
   * 初始化缓存
   * 
   */
  private void initCache() {
    if (cache != null) {
      urlStrings = (List) cache.get(KEY_OF_URLSTRINGS);
      if (urlStrings == null) {
        urlStrings = Collections.synchronizedList(new TreeList());
        cache.put(KEY_OF_URLSTRINGS, urlStrings);
        log.debug("init URL Strings");        
      }
      
      funcStrings = (List) cache.get(KEY_OF_FUNCSTRINGS);
      if (funcStrings == null) {
        funcStrings = Collections.synchronizedList(new TreeList());
        cache.put(KEY_OF_FUNCSTRINGS, funcStrings);
        log.debug("init Function Strings");
      }

      otherStrings = (List) cache.get(KEY_OF_OTHERSTRINGS);
      if (otherStrings == null) {
        otherStrings = Collections.synchronizedList(new TreeList());
        cache.put(KEY_OF_OTHERSTRINGS, otherStrings);
        log.debug("init others Strings");
      }
    }
  }

}
