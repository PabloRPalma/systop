package com.systop.common.map.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.systop.common.map.model.Entry;
import com.systop.common.map.model.Map;
import com.systop.common.map.service.BackDataManager;
import com.systop.common.map.service.EntryManager;
import com.systop.common.map.service.MapManager;
import com.systop.common.webapp.struts2.action.BaseDwrAjaxAction;

/**
 * 
 * @author Systop_Guo
 */
public class MapDwrAction extends BaseDwrAjaxAction {

	/** Manager of Map */
	private MapManager mapManager;

	/** Manager of Entry */
	private EntryManager entryManager;
	
	/** BackData */
	private BackDataManager backDataMgr;

	/**
	 * 返回所有的Map
	 * 
	 * @return
	 */
	public List<Map> getAllMaps() {
		List<Map> maps = mapManager.getAllMaps();

		List result = new ArrayList();
		// 转化为符合dojo要求的Map对象.
		for (Map sysMap : maps) {
			java.util.Map hsahMap = new HashMap();
			hsahMap.put("title", sysMap.getMapTitle());
			hsahMap.put("objectId", sysMap.getMapId());
			result.add(hsahMap);
		}
		return result;
	}

	/**
	 * @param mapId
	 * @return
	 */
	public Map getMap(Integer mapId) {
		return mapManager.get(mapId);
	}

	/**
	 * 将Map成功保存后返回其主键值
	 * 
	 * @param map
	 * @return
	 */
	public Integer saveMap(Map map) {
		return this.mapManager.saveMapDojoTree(map);
	}

	/**
	 * 更新类别
	 * 
	 * @param mapFrom
	 */
	public Integer updateMap(Map mapFrom) {
		return mapManager.updateMap(mapFrom);
	}

	/**
	 * 删除类别
	 */
	public void removeMap(Integer mapId) {
		Map map = mapManager.get(mapId);
		if (map != null) {
			mapManager.remove(map);
		}
	}
	
	/**
	 * 得到主键对应类别的所以细目
	 * 
	 * @param mapId
	 * @return
	 */
	public List<Entry> getEntryByMapId(Integer mapId) {
		return getEntryManager().getEntriesByMapId(mapId);
	}
	
	/**
	 * 得到需要的Entry[条目]
	 * @param entryId
	 * @return
	 */
	public Entry getEntry(Integer entryId) {
		return this.entryManager.get(entryId);
	}

	/**
	 * 添加Entry
	 * @param entry
	 */
	public Integer saveEntry(Entry entry, Integer mapId) {
		return this.entryManager.saveEntry(entry, mapId);
	}
	
	/**
	 * 更新Entry
	 * @param entry
	 */
	public Integer updateEntry(Entry entry) {
		return this.entryManager.updateEntry(entry);
	}
	
	/**
	 * 删除选中的条目
	 * @param enteryId
	 */
	public void removeSelectEntry(Integer[] entryIds) {
		entryManager.removeSelectEntry(entryIds);
	}

	/**备份数据*/
	public boolean backup() {
		return getBackDataMgr().backData();
	}
	
	/**恢复数据*/
	public boolean revert() {
		return getBackDataMgr().revertData();
	}

	public EntryManager getEntryManager() {
		return entryManager;
	}

	public void setEntryManager(EntryManager entryManager) {
		this.entryManager = entryManager;
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	public void setMapManager(MapManager mapManager) {
		this.mapManager = mapManager;
	}

	public BackDataManager getBackDataMgr() {
		return backDataMgr;
	}

	public void setBackDataMgr(BackDataManager backDataMgr) {
		this.backDataMgr = backDataMgr;
	}
}
