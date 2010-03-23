package com.systop.common.map.service;

import java.util.List;

import com.systop.common.map.model.Entry;
import com.systop.common.map.model.Map;
import com.systop.common.service.Manager;

/**
 * 对Entry的管理
 * @author GHL
 *
 */
public interface EntryManager extends Manager<Entry> {
	
	/**
	 * 根据类别标识和引用值，得到相应的String类型的用于在客户端显示的内容
	 * 
	 * @param mapSign  类别标识
	 * @param refValue  引用值
	 * @return 显示内容
	 */
	String getViewText(String mapSign, String refValue);
	
	/**
	 * 根据类别和引用值，得到相应的String类型的用于在客户端显示的内容
	 * @param map 类别
	 * @param refValue　引用值
	 * @return
	 */
	String getViewText(Map map, String refValue);
	
	/**
	 * 根据类别标识和引用值查找惟一的Entry
	 * @param mapSign　类别标识　
	 * @param refValue　引用值
	 * @return
	 */
	Entry getEntry(String mapSign, String refValue);
	

	/**
	 * 根据类别和引用值查找惟一的Entry
	 * @param map 类别　
	 * @param refValue　引用值
	 * @return
	 */
	Entry getEntry(Map map, String refValue);
	
	/**
	 * 根据指定的[类别标识]和[显示值]，返回该[显示值]对应的[引用值]
	 * 
	 * @param mapSign  类别标识
	 * @param viewText 显示值
	 * @return 引用值
	 */
	String getRefValueByViewText(String mapSign, String viewText);
	

	/**
	 * 返回类别标识对应类别的全部细目
	 * 
	 * @param mapSign  类别标识
	 * @return 包含<code>Entry</code>对象的List，如果指定类别不存在，返回空List
	 */
	List<Entry> getEntriesBySign(String mapSign);
	
	/**
	 * 返回类别标题对应类别的全部细目
	 * 
	 * @param mapTitle 类别标题
	 * @return 包含<code>Entry</code>对象的List，如果指定类别不存在，返回空List
	 */
	List<Entry> getEntriesByTitle(String mapTitle);
	
	/**
	 * 返回类别[map]主键对应的全部细目
	 * @param mapId
	 * @return
	 */
	List<Entry> getEntriesByMapId(Integer mapId);

	/**
	 * @param mapSign　类别标识
	 * @param refValue
	 * @return
	 */
	boolean isEntryInUse(Entry entry);
	
	/**
	 * 保存条目
	 * @param entry
	 */
	Integer saveEntry(Entry entry, Integer mapId);
	
	/**
	 * 更新条目[Entry]
	 * @param entry
	 * @return
	 */
	Integer updateEntry(Entry entry);
	
	/**
	 * 删除所选中的条目
	 * @param enteyIds
	 */
	void removeSelectEntry(Integer[] entryIds);
	
}
