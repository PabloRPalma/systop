package com.systop.common.map.service;

import java.util.List;

import com.systop.common.map.Constants;
import com.systop.common.map.model.Map;
import com.systop.common.test.BaseTestCase;
import com.systop.common.util.ReflectUtil;

/**
 * MapManager测试类
 * @author GHL
 */

public class MapManagerTestCase extends BaseTestCase {
	
	/**
	 * @return the instance of the MapManager
	 */
	private MapManager getMapMgr() {
		return (MapManager) applicationContext
												.getBean("mapManager");
	}
	

	/**
	 * 测试save方法，加入测试数据
	 * Test method for {@link  MapManagerImpl#saveMapDojoTree(Map)} 
	 */
	/**
	 * 填充测试数据
	 */
	public void testSaveMapDojoTree() {
		boolean boolA = false;
		boolean boolB = false;
		boolean boolC = false;
		Integer temp = null;
	
		Map mapA = new Map();
		mapA.setMapTitle("学历");
		mapA.setMapSign("degree");
		mapA.setMapDescn("学历条目");
		temp = getMapMgr().saveMapDojoTree(mapA);
		if (temp.intValue() != Constants.MAPTITLE_INUSE 
				&& temp.intValue() != Constants.MAPSIGN_INUSE) {
			boolA = true;
		}
		
		Map mapB = new Map();
		mapB.setMapTitle("级别");
		mapB.setMapSign("jibie");
		mapB.setMapDescn("级别条目");
		temp = getMapMgr().saveMapDojoTree(mapB);
		if (temp.intValue() != Constants.MAPTITLE_INUSE 
				&& temp.intValue() != Constants.MAPSIGN_INUSE) {
			boolB = true;
		}
		
		Map mapC = new Map();
		mapC.setMapTitle("级别");
		mapC.setMapSign("degree");
		mapC.setMapDescn("级别条目");
		temp = getMapMgr().saveMapDojoTree(mapC);
		if (temp.intValue() != Constants.MAPTITLE_INUSE 
				&& temp.intValue() != Constants.MAPSIGN_INUSE) {
			boolC = true;
		}
		this.setComplete(); // 提交数据
		assertTrue(boolA && boolB && !boolC);
	}
	/**
	 *Test method for {@link  MapManagerImpl#getAllMapTitles()} 
	 */
	public void testGetAllMapTitles() {
		List<String> titleList = getMapMgr().getAllMapTitles();
		assertTrue(titleList.get(0).equals("学历") 
				&& titleList.get(1).equals("级别"));
	}
	
	/**
	 *Test method for {@link  MapManagerImpl#isMapSignInUes()}
	 */
	public void testIsMapSignInUes() {
		Map map = new Map();
		map.setMapTitle("学历");
		map.setMapSign("degree");
		//判断添加的时候
		boolean isInUseAdd = getMapMgr().isMapSignInUes(map);
		//数据库中查询得到
		map = getMapMgr().getMapByMapSign("degree");
		//判断修改的时候
		boolean isInUseUpdate = getMapMgr().isMapSignInUes(map);
		assertTrue(isInUseAdd && !isInUseUpdate);
	}
	
	/**
	 * Test method for {@link  MapManagerImpl#isMapTitleInUes()} 
	 */
	public void testIsMapTitleInUes() {
		Map map = new Map();
		map.setMapTitle("学历");
		map.setMapSign("degree");
		//判断添加的时候
		boolean isInUseAdd = getMapMgr().isMapTitleInUes(map);
		//数据库中查询得到
		map = getMapMgr().getMapByMapSign("degree");
		//判断修改的时候
		boolean isInUseUpdate = getMapMgr().isMapTitleInUes(map);
		
		assertTrue(isInUseAdd && !isInUseUpdate);
	}
	
	/**
	 * Test method for {@link  MapManagerImpl#UpdateMap()} 
	 */
	public void testUpdateMap() {
		boolean boolA = false;
		boolean boolB = false;
		boolean boolC = false;
		Integer temp = null;
		Map mapA = this.getMapMgr().getMapByMapTitle("学历");
		Map mapTestA = new Map();
		ReflectUtil.copyProperties(mapTestA, mapA);
		mapTestA.setMapSign("jibie");
		temp = getMapMgr().updateMap(mapTestA);
		if (temp.intValue() != Constants.MAPTITLE_INUSE 
				&& temp.intValue() != Constants.MAPSIGN_INUSE) {
			boolA = true;
		}
		
		Map mapB = this.getMapMgr().getMapByMapTitle("学历");
		Map matTestB = new Map();
		ReflectUtil.copyProperties(matTestB, mapB);
		matTestB.setMapTitle("级别");
		temp = getMapMgr().updateMap(matTestB);
		if (temp.intValue() != Constants.MAPTITLE_INUSE 
				&& temp.intValue() != Constants.MAPSIGN_INUSE) {
			boolB = true;
		}
		
		Map mapC = this.getMapMgr().getMapByMapTitle("学历");
		Map matTestC = new Map();
		ReflectUtil.copyProperties(matTestC, mapC);
		temp = getMapMgr().updateMap(matTestC);
		if (temp.intValue() != Constants.MAPTITLE_INUSE 
				&& temp.intValue() != Constants.MAPSIGN_INUSE) {
			boolC = true;
		}
		assertTrue(!boolA && !boolB && boolC);
	}
	
	
}
