package com.systop.common.map;

import java.util.List;

import com.systop.common.cache.Cache;
import com.systop.common.map.model.Entry;
import com.systop.common.map.service.EntryManager;
import com.systop.common.map.service.MapManager;

/**
 * Entry的工具类用于方便的取得Map和Entry的数据
 * @author Systop_Guo
 */
public class EntryUtil {
  /**
   * 由于类别是不经常变化的数据，所以要放在Cache中以提高性能
   */
  private Cache cache;

	/**
	 * 对<code>Entry</code>的CRUD操作
	 */
	private EntryManager entryManager;

	public EntryManager getEntryManager() {
		return entryManager;
	}

	public void setEntryManager(EntryManager entryManager) {
		this.entryManager = entryManager;
	}
	
	/** 对<code>Map</code> 的CRUD操作*/
	private MapManager mapManager;


	public MapManager getMapManager() {
		return mapManager;
	}

	public void setMapManager(MapManager mapManager) {
		this.mapManager = mapManager;
	}
	
	/**
   * 根据类别标识返回某个类别的全部引用
   * @param mapSign 类别标识
   * @return 包含<code>Entry</code>对象的List，如果指定类别不存在，返回空List
   */
  public List<Entry> getEntriesBySign(String mapSign) {
    List<Entry> entrList = (List<Entry>) cache.get(mapSign);

    // 判断缓存数据是否为空，如果为空执行查询
    if (entrList == null || entrList.isEmpty()) {
    	entrList = entryManager.getEntriesBySign(mapSign); // 无此类别返回空List
      if (!entrList.isEmpty()) {
        cache.put(mapSign, entrList);
      }
    }
    return entrList;
  }
  
  /**
   * 根据类别标题返回某个类别的全部引用
   * @param mapTitle 类别标题
   * @return 包含<code>Entry</code>对象的List，如果指定类别不存在，返回空List
   */
  public List<Entry> getEntriesByTitle(String mapTitle) {
    List<Entry> entrList = (List<Entry>) cache.get(mapTitle);

    // 判断缓存数据是否为空，如果为空执行查询
    if (entrList == null || entrList.isEmpty()) {
    	entrList = entryManager.getEntriesByTitle(mapTitle); // 无此类别返回空List
      if (!entrList.isEmpty()) {
        cache.put(mapTitle, entrList);
      }
    }
    return entrList;
  }

  /**
   * 根据类别标识和引用值，得到相应的显示值viewText
   * @param mapSign 类别标识
   * @param refValue 引用值
   * @return 显示值
   */
  public String getViewTextBySign(String mapSign, String refValue) {
    String strValue = null; // 返回值
    List<Entry> entryList = this.getEntriesBySign(mapSign);

    for (Entry entry : entryList) {
      if (refValue.equals(entry.getRefValue())) {
        strValue = entry.getViewText(); // 符合条件赋值
      }
    } 
    return strValue;
  }
  
  /**
   * 根据类别标题和引用值，得到相应的显示值viewText
   * @param mapTitle 类别标题
   * @param refValue 引用值
   * @return 显示值
   */
  public String getViewTextByTitle(String mapTitle, String refValue) {
    String strValue = null; // 返回值
    List<Entry> entryList = this.getEntriesByTitle(mapTitle);

    for (Entry entry : entryList) {
      if (refValue.equals(entry.getRefValue())) {
        strValue = entry.getViewText(); // 符合条件赋值
      }
    }
    return strValue;
  }

  /**
   * 根据指定的类别标识[mapSign]和类型显示值[viewText]，返回该描述值对应的引用值[refValue]
   * @param mapSign 类别标识
   * @param viewText 显示值
   * @return　引用值
   */
  public String getRefValueBySign(String mapSign, String viewText) {
    String strValue = null; // 返回值
    List<Entry> entryList = this.getEntriesBySign(mapSign);

    for (Entry entry : entryList) {
      if (viewText.equals(entry.getViewText())) {
        strValue = entry.getRefValue(); // 符合条件赋值
      }
    }
    return strValue;
  }
  
  /**
   * 根据指定的类别标题[mapTitle]和类型显示值[viewText]，返回该描述值对应的引用值[refValue]
   * @param mapTitle 类别标题
   * @param viewText 显示值
   * @return　引用值
   */
  public String getRefValueByTitle(String mapTitle, String viewText) {
    String strValue = null; // 返回值
    List<Entry> entryList = this.getEntriesByTitle(mapTitle);

    for (Entry entry : entryList) {
      if (viewText.equals(entry.getViewText())) {
        strValue = entry.getRefValue(); // 符合条件赋值
      }
    }
    return strValue;
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
