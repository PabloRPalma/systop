package com.systop.common.cache;

import java.util.Map;

/**
 * Implementors define a caching algorithm. All implementors
 * <b>must</b> be threadsafe.
 * ���4��Hibernate
 */
public interface Cache {
  /**
   * Get an item from the cache
   * @param key
   * @return the cached object or <tt>null</tt>
   * 
   */
  public Object read(Object key);
  /**
   * Get an item from the cache, nontransactionally
   * @param key
   * @return the cached object or <tt>null</tt>
   * 
   */
  public Object get(Object key);
  /**
   * Add an item to the cache, nontransactionally, with
   * failfast semantics
   * @param key
   * @param value
   * 
   */
  public void put(Object key, Object value);
  /**
   * Add an item to the cache
   * @param key
   * @param value
   * 
   */
  public void update(Object key, Object value);
  /**
   * Remove an item from the cache
   */
  public void remove(Object key);
  /**
   * Clear the cache
   */
  public void clear();
  /**
   * Clean up
   */
  public void destroy();
  /**
   * If this is a clustered cache, lock the item
   */
  public void lock(Object key);
  /**
   * If this is a clustered cache, unlock the item
   */
  public void unlock(Object key);

  /**
   * Get the name of the cache region
   */
  public String getRegionName();

  
  /**
   * optional operation
   */
  public Map toMap();
  
  
}
