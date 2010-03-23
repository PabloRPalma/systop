package com.systop.common.map.service;

/**
 *  对类别的备份和恢复
 * @author Systop_Guo
 */
public interface BackDataManager {
	
	/**
	 * 备份数据
	 * @return
	 */
	boolean backData();
	
	/**
	 * 恢复数据
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	boolean revertData();
}
