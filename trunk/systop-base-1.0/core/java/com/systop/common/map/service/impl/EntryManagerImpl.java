package com.systop.common.map.service.impl;

import java.util.List;

import com.systop.common.map.Constants;
import com.systop.common.map.model.Entry;
import com.systop.common.map.model.Map;
import com.systop.common.map.service.EntryManager;
import com.systop.common.service.BaseManager;

 /**
 * @see {@link CatalogManager}
 * @author Systop_Guo
 */
public class EntryManagerImpl extends BaseManager<Entry> 
						 implements EntryManager {

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * getEntriesBySign(java.lang.String)
	 */
	public List<Entry> getEntriesBySign(String mapSign) {
		String hql = "from Entry e where e.map.mapSign=?";
		return this.query(hql, mapSign);
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * getEntriesByTitle(java.lang.String)
	 */
	public List<Entry> getEntriesByTitle(String mapTitle) {
		String hql = "from Entry e where e.map.mapTitle=?";
		return query(hql, mapTitle);
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * getEntry(java.lang.String, java.lang.String)
	 */
	public Entry getEntry(String mapSign, String refValue) {
		String strHql = "from Entry e where e.map.mapSign=?" 
			+ " and e.refValue = ?";
		List<Entry> entriesList = query(strHql, 
								new Object[]{mapSign, refValue});
		
		return entriesList.isEmpty() ? null : entriesList.get(0);
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * getEntry(com.systop.common.map.model.Map, java.lang.String)
	 */
	public Entry getEntry(Map map, String refValue) {
		return getEntry(map.getMapSign(), refValue);
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * getRefValueByViewText(java.lang.String, java.lang.String)
	 */
	public String getRefValueByViewText(String mapSign, String viewText) {
		String hql = "from Entry e where e.map.mapSign=? and e.viewText=?";
		List<Entry> entriesList = query(hql, new Object[]{mapSign, viewText});
		return entriesList.isEmpty() ? null : entriesList.get(0).getRefValue();
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * getViewText(java.lang.String, java.lang.String)
	 */
	public String getViewText(String mapSign, String refValue) {
		String hql = "from Entry e where e.map.mapSign=? and e.refValue=?";
		List<Entry> entriesList = this.query(hql
				, new  Object[]{mapSign, refValue});
		return entriesList.isEmpty() ? null : entriesList.get(0).getViewText();
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * getViewText(com.systop.common.map.model.Map, java.lang.String)
	 */
	public String getViewText(Map map, String refValue) {
		return getViewText(map.getMapSign(), refValue);
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * isEntryInUse(com.systop.common.map.model.Entry)
	 */
	public boolean isEntryInUse(Entry entry) {
		String hql;
		if (entry.getMap() == null) {
			return false;
		} else {
			hql = "from Entry e where e.map.mapSign=? and e.refValue=?";
			List tempList = this.query(hql, new Object[]{
					entry.getMap().getMapSign(), entry.getRefValue()});
			return tempList.isEmpty() ? false : true;
		}
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * 	getEntriesByMapId(java.lang.Integer)
	 */
	public List<Entry> getEntriesByMapId(Integer mapId) {
		String hql = "from Entry e where e.map.mapId=? order by e.refValue";
		List<Entry> entrList = this.query(hql, mapId);
		return entrList;
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * saveEntry(com.systop.common.map.model.Entry)
	 */
	public Integer saveEntry(Entry entry, Integer mapId) {
		List<Map> mapList = query("from Map m where m.mapId=?", mapId);
		Map sysMap = null;
		if (!mapList.isEmpty() && mapList != null) {
			sysMap = mapList.get(0);
		} else {
			return Constants.ADD_ENTRY_NO_MAP;
		}

		entry.setMap(sysMap);
		if (this.isEntryInUse(entry)) { //如果entry在使用直接反值
			return Constants.ENTRY_IS_INUSE;
		}
		
		this.save(entry);
		return Constants.ADD_ENTRY_OK;
	}
	
	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * updateEntry(com.systop.common.map.model.Entry)
	 */
	public Integer updateEntry(Entry formEntry) {
		Entry sysEntry = this.get(formEntry.getEntryId());
		if (sysEntry == null) {
			throw new NullPointerException("您要修改的对象不存在");
		}
		String hql = "from Entry e where e.map.mapId=? " 
								+ "and e.refValue=? and e.entryId!=?";
		List entList = query(hql, sysEntry.getMap().getMapId(), 
				formEntry.getRefValue(), formEntry.getEntryId());
		
		//判断Entry是否可用,不能使用isEntryInUse判断:)
		if (entList.isEmpty()) {
			sysEntry.setViewText(formEntry.getViewText());
			sysEntry.setRefValue(formEntry.getRefValue());
			sysEntry.setEntryDescn(formEntry.getEntryDescn());
			this.save(sysEntry);
			return Constants.UPDATE_ENTRY_OK;
		} else {
			return Constants.ENTRY_IS_INUSE;
		}
	}
	
	/**
	 * @see com.systop.common.map.service.EntryManager#
	 * removeSelectEntry(java.lang.Integer[])
	 */
	public void removeSelectEntry(Integer[] entryIds) {
		for (int i = 0; i < entryIds.length; i++) {
			Entry entry = get(entryIds[i]);
			if (entry != null) {
				remove(entry);
			}
		}
	}
}
