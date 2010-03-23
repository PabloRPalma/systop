package com.systop.common.map.service;

import java.util.List;

import com.systop.common.map.model.Entry;
import com.systop.common.map.model.Map;
import com.systop.common.test.BaseTestCase;

/**
 * EntryManager测试类
 * @author GHL
 */
public class EntryManagerTestCase extends BaseTestCase {
	/**
	 * @return the instance of the MapManager
	 */
	private MapManager getMapMgr() {
		return (MapManager) applicationContext
												.getBean("mapManager");
	}
	
	/**
	 * @return the instance of the EntryManager
	 */
	private EntryManager getEntryMgr() {
		return (EntryManager) applicationContext
												.getBean("entryManager");
	}
	
	/**
	 * 测试save方法，加入测试数据
	 * Test method for {@link  EntryManagerImpl#save(Entry)} 
	 */
	public void testInsertData() {
		this.insertData();
		assertTrue(getEntryMgr().get().size() == 2 
				&& getMapMgr().get().size() == 1);
	}
	
	
	/**
	 *Test method for {@link  EntryManagerImpl#getEntriesBySign()} 
	 */
	public void testGetEntriesBySign() {
		List<Entry> entrList = getEntryMgr().getEntriesBySign("degree");
		assertTrue(entrList.get(0).getRefValue().equals("0") 
				&& entrList.size() == 2);
	}
	
	/**
	 *Test method for {@link  EntryManagerImpl#getEntriesByTitle()} 
	 */
	public void testGetEntriesByTitle() {
		List<Entry> entrList = getEntryMgr().getEntriesByTitle("学历");
		assertTrue(entrList.get(1).getRefValue().equals("1")
				&& entrList.size() == 2);
	}
	
	/**
	 *Test method for {@link  EntryManagerImpl#getEntry(Map, String)} 
	 */
	public void testGetEntry() {
		Map map = new Map();
		map.setMapSign("degree");
		assertTrue(getEntryMgr().getEntry(map, "0")
				.getViewText().equals("博士"));
	}
	
	/**
	 *Test method for {@link EntryManagerImpl#
	 *getRefValueByViewText(String, String)}
	 */
	public void testGetRefValueByViewText() {
		assertTrue(getEntryMgr()
				.getRefValueByViewText("degree", "博士").equals("0"));
	}
	
	/**
	 * Test method for {@link EntryManagerImpl#GetViewText(String, String)}
	 *
	 */
	public void testGetViewText() {
		Map map = new Map();
		map.setMapSign("degree");
		assertTrue(getEntryMgr().getViewText(map, "1").equals("硕士"));
	}
	
	/**
	 * Test method for {@link EntryManagerImpl#isEntryInUse(Entry)}
	 */
	public void testIsEntryInUse() {
		Map map = new Map();
		map.setMapTitle("学历");
		map.setMapSign("degree");
		map.setMapDescn("学历条目");
		
		Entry entr = new Entry();
		entr.setRefValue("0");
		entr.setViewText("博士");
		entr.setMap(map);
		assertTrue(this.getEntryMgr().isEntryInUse(entr));
		
	}
	
	/**
	 * Test method for {@link EntryManagerImpl#etEntriesByMapId(ID)}
	 */
	public void testGetEntriesByMapId() {
		Map map = this.getMapMgr().getMapByMapTitle("学历");
		List list = this.getEntryMgr().getEntriesByMapId(map.getMapId());
		assertTrue(list.size() == 2);
	}
	
	
	
	/**
	 * 填充测试数据
	 */
	private void insertData() {
		Map map = new Map();
		map.setMapTitle("学历");
		map.setMapSign("degree");
		map.setMapDescn("学历条目");
		this.getMapMgr().save(map);
		
		Entry entr = new Entry();
		entr.setRefValue("0");
		entr.setViewText("博士");
		entr.setMap(map);
		this.getEntryMgr().save(entr);
		
		Entry entr1 = new Entry();
		entr1.setRefValue("1");
		entr1.setViewText("硕士");
		entr1.setMap(map);
		this.getEntryMgr().save(entr1);
		this.setComplete();
	}
}
