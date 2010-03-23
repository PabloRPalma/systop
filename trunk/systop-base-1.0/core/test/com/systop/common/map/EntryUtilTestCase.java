package com.systop.common.map;

import java.util.List;

import com.systop.common.map.model.Entry;
import com.systop.common.map.model.Map;
import com.systop.common.test.BaseTestCase;

/**
 * EntryUtil测试类
 * @author GHL
 */
public class EntryUtilTestCase extends BaseTestCase {

	/**
   * @return the instance of the EntryUtil
   */
  private EntryUtil getEntryUtil() {
    return (EntryUtil) applicationContext.getBean("entryUtil");
  }
  
  /**
   * Test method for {@link EntryManager#save(Object)}
   */
  public void testSave() {
  	insertData();
  	assertTrue(getEntryUtil().getEntryManager().get().size() == 2 
				&& getEntryUtil().getMapManager().get().size() == 1);
  }

  /**
   *Test method for {@link EntryUtil#getEntriesBySign(String)}
   */
	public void testGetEntriesBySign() {
		List<Entry> entryList = getEntryUtil().getEntriesBySign("degree");
		assertTrue(entryList.size() == 2 
				&& entryList.get(0).getViewText().equals("博士"));
	}
	

	/**
   *Test method for {@link EntryUtil#getEntriesByTitle(String)}
   */
	public void testGetEntriesByTitle() {
		List<Entry> entryList = getEntryUtil().getEntriesByTitle("学历");
		assertTrue(entryList.size() == 2 
				&& entryList.get(0).getRefValue().equals("0"));
	}
	
	/**
   *Test method for {@link EntryUtil#getViewTextBySign(String, String)}
   */
	public void testGetViewTextBySign() {
		assertTrue(
				getEntryUtil().getViewTextBySign("degree", "0").equals("博士"));
	}
	
	/**
   *Test method for {@link EntryUtil#getViewTextByTitle(String, String)}
   */
	public void testGetViewTextByTitle() {
		assertTrue(
				getEntryUtil().getViewTextByTitle("学历", "1").equals("硕士"));
	}
	
	/**
   *Test method for {@link EntryUtil#getRefValueBySign(String, String)}
   */
	public void testGetRefValueBySign() {
		assertTrue(
				getEntryUtil().getRefValueBySign("degree", "博士").equals("0"));
	}
	
	/**
   *Test method for {@link EntryUtil#getRefValueByTitle(String, String)}
   */
	public void testGetRefValueByTitle() {
		assertTrue(
				getEntryUtil().getRefValueBySign("学历", "硕士").equals("1"));
	}
	
	/**
	 * 填充测试数据
	 */
	private void insertData() {
		Map map = new Map();
		map.setMapTitle("学历");
		map.setMapSign("degree");
		map.setMapDescn("学历条目");
		getEntryUtil().getMapManager().save(map);
		
		Entry entr = new Entry();
		entr.setRefValue("0");
		entr.setViewText("博士");
		entr.setMap(map);
		getEntryUtil().getEntryManager().save(entr);
		
		Entry entr1 = new Entry();
		entr1.setRefValue("1");
		entr1.setViewText("硕士");
		entr1.setMap(map);
		getEntryUtil().getEntryManager().save(entr1);
		setComplete(); // 提交数据
	}
}
