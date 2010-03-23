package com.systop.common.map.service.impl;

import java.util.List;

import com.systop.common.map.Constants;
import com.systop.common.map.model.Map;
import com.systop.common.map.service.MapManager;
import com.systop.common.service.BaseManager;

/**
 * @see {@link CatalogManager}
 * @author GHL
 */
public class MapManagerImpl extends BaseManager<Map> 
							implements MapManager {
	
	/**
	 * @see com.systop.common.map.service.MapManager#getAllMapTitles()
	 */
	public List<String> getAllMapTitles() {
		String strHql = "select m.mapTitle from Map m";
		return query(strHql);
	}

	/**
	 * @see com.systop.common.map.service.MapManager#
	 * getMapByMapSign(java.lang.String)
	 */
	public Map getMapByMapSign(String mapSign) {
		String hql = "from Map m where m.mapSign = ?";
		List<Map> mapList = this.query(hql, mapSign);
		return mapList.isEmpty() ? null : mapList.get(0);
	}

	/**
	 * @see com.systop.common.map.service.MapManager#
	 * getMapByMapTitle(java.lang.String)
	 */
	public Map getMapByMapTitle(String mapTitle) {
		String hql = "from Map m where m.mapTitle = ?";
		List<Map> mapList = this.query(hql, mapTitle);
		return mapList.isEmpty() ? null : mapList.get(0);
	}

	/**
	 * @see com.systop.common.map.service.MapManager#
	 * isMapSignInUes(java.lang.String)
	 */
	public boolean isMapSignInUes(Map map) {
		return exists(map, "mapSign");
	}

	/**
	 * @see com.systop.common.map.service.MapManager#
	 * isMapTitleInUes(java.lang.String)
	 */
	public boolean isMapTitleInUes(Map map) {
		return exists(map, "mapTitle");
	}

	/**
	 * @see com.systop.common.map.service.MapManager#
	 * getAllMaps()
	 */
	public List<Map> getAllMaps() {
		return this.get();
	}

	/**
	 * @throws MapTitleInUseException 
	 * @throws MapSignInUseException 
	 * @see com.systop.common.map.service.MapManager#
	 * dojoTreeSaveMap(Map map)
	 */
	public Integer saveMapDojoTree(Map map) {
		
		if (this.isMapTitleInUes(map)) {
			return Constants.MAPTITLE_INUSE;
		} else if (this.isMapSignInUes(map)) {
			return Constants.MAPSIGN_INUSE;
		} else {
			this.save(map);
			return map.getMapId();
		}
	}

	/**
	 * @see com.systop.common.map.service.MapManager#
	 * dojoTreeSaveMap(Map map)
	 */
	public Integer updateMap(Map mapFrom) {
		Map sysMap = get(mapFrom.getMapId());
		if (sysMap == null) {
			throw new NullPointerException(
  							"您要修改的对象不存在");
		}
		
		if (this.isMapTitleInUes(mapFrom)) {
			return Constants.MAPTITLE_INUSE;
		} else if (this.isMapSignInUes(mapFrom)) {
			return Constants.MAPSIGN_INUSE;
		} else {
			sysMap.setMapTitle(mapFrom.getMapTitle());
			sysMap.setMapSign(mapFrom.getMapSign());
			sysMap.setMapDescn(mapFrom.getMapDescn());
			save(sysMap);
			return sysMap.getMapId();
		}
	}
}
