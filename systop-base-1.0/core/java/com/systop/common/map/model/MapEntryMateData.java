package com.systop.common.map.model;

import java.io.Serializable;
import java.util.List;


/**
 * 用于备份和还原Map和Entriese表。 序列化对象，将保存到文件
 * @author Systop_Guo
 */
public class MapEntryMateData implements Serializable {
	/**
	 * 备份日期
	 */
	private String backDate;
	
	/**
	 */
	private List<String> entrySql;

	/***/
	private List<String> mapSql;
	
	/**
	 * 条目纪录
	 */
	private List<Entry> entrList;
	
	/**
	 * 类别纪录
	 */
	private List<Map> mapList;

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public List<Entry> getEntrList() {
		return entrList;
	}

	public void setEntrList(List<Entry> entrList) {
		this.entrList = entrList;
	}

	public List<Map> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}

	public List<String> getEntrySql() {
		return entrySql;
	}

	public void setEntrySql(List<String> entrySql) {
		this.entrySql = entrySql;
	}

	public List<String> getMapSql() {
		return mapSql;
	}

	public void setMapSql(List<String> mapSql) {
		this.mapSql = mapSql;
	}
}
