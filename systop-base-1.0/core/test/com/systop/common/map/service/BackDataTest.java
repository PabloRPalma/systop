package com.systop.common.map.service;

import com.systop.common.test.BaseTestCase;

/**
 * 测试备份和恢复
 * @author Systop_Guo
 */
public class BackDataTest extends BaseTestCase {
	
	/**
	 * @return the instance of the MapManager
	 */
	private BackDataManager getBactDataMgr() {
		return (BackDataManager) applicationContext
												.getBean("backDataManager");
	}
	
	/**
	 * Test method for {@link EntryManagerImpl#backData()}
	 */
	public void testBackData() {
		assertTrue(this.getBactDataMgr().backData());
	}
	
	/**
	 * 
	 */
	public void testRevertData() {
		assertTrue(this.getBactDataMgr().revertData());
	}
}
