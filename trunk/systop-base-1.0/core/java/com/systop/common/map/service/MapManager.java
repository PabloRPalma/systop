package com.systop.common.map.service;

import java.util.List;

import com.systop.common.map.model.Map;
import com.systop.common.service.Manager;

/**
 * 对Map的管理
 * @author GHL
 */
public interface MapManager extends Manager<Map> {
	
	/**
	 * 判断类别标题在Catalogue表中是否已经存在。Catalogue不允许重复标题
	 * @param map 要修改的类别
	 * @return
	 */
	boolean isMapTitleInUes(Map map);

	/**
	 * 判断类别标识在Map表中是否已经存在。Map不允许重复标识
	 * @param map 要修改的类别
	 * @return
	 */
	boolean isMapSignInUes(Map map);

	/**
	 * 查询得到已有类别名称。
	 * 
	 * @return 已有类别名称
	 */
	List<String> getAllMapTitles();
	
	/**
	 * 得到所有的类别
	 * @return　所有类别
	 */
	List<Map> getAllMaps();
	/**
	 * @param mapTitle
	 * @return
	 */
	Map getMapByMapTitle(String mapTitle);
	
	/**
	 * @param mapSign
	 * @return
	 */
	Map getMapByMapSign(String mapSign);
	
	/**
	 * 当使用DojoTree保存Map的时候调用此方法，需要返回主键
	 * @param map
	 * @return
	 * @throws MapTitleInUseException 
	 * @throws MapSignInUseException 
	 */
	Integer saveMapDojoTree(Map map);
	
	/**
	 * 更新类别
	 * @param mapFrom
	 * @throws MapSignInUseException 
	 * @throws MapTitleInUseException 
	 */
	Integer updateMap(Map mapFrom);
}
