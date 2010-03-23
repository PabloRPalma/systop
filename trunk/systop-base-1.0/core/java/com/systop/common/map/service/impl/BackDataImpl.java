package com.systop.common.map.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.systop.common.map.Constants;
import com.systop.common.map.model.Entry;
import com.systop.common.map.model.Map;
import com.systop.common.map.model.MapEntryMateData;
import com.systop.common.map.service.BackDataManager;
import com.systop.common.map.service.EntryManager;
import com.systop.common.map.service.MapManager;
import com.systop.common.util.DateUtil;
import com.systop.common.util.ReflectUtil;

/**
 * 对类别的备份和恢复
 * @author Systop_Guo
 */
public class BackDataImpl implements BackDataManager {

	/**
	 * Log for class.
	 */
	protected Log log = LogFactory.getLog(getClass());
	
	/** EntryManager */
	private EntryManager entrMagr;
	
	/** MapManager */
	private MapManager mapMagr;
	/**
	 * @see com.systop.common.map.service.EntryManager#backData()
	 */
	public boolean backData() {
		
		MapEntryMateData mateData = new MapEntryMateData();
		Date date = new Date();
		mateData.setBackDate(DateUtil.getDate(date) + " " 
				+ DateUtil.getTimeNow(date));
		mateData.setEntrList(entrMagr.get());
		mateData.setMapList(mapMagr.get());
		String folderName = System.getProperty("user.home")
							+ Constants.DATA_BACK_FOLDER;
		File folder = new File(folderName);
		
		if (!folder.exists()) { //目录不存在创建
			folder.mkdir();
		} else {
			if (!folder.isDirectory()) { //文件存在但不是目录删除，再创建
				if (folder.delete()) { //目录删除成功创建
					folder.mkdir();
				} else { //目录删除失败输出日志
					log.error(folderName + "不是目录，且不能被删除,数据无法备份");
					return false;
				}
			} 
		}
		
		String fileName = folderName + Constants.DATA_BACK_FILE;
		
		File dataFile = new File(fileName);
		
		if (dataFile.exists()) { //备份前判断，如果文件存在删除，备份新数据
			if (!dataFile.delete()) { //删除成功开始备份
				log.warn(fileName + "备份无法删除，导致数据无法备份，请检查文件属性");
				return false;
			} 
		} 
		return writeObject(dataFile, mateData);
	}

	/**
	 * @see com.systop.common.map.service.EntryManager#revertData()
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public boolean revertData() {
		
		MapEntryMateData bData = readObject();
		
		if (bData == null) { //如果对象为空则终止恢复数据
			return false;
		}
		for (Entry entr : entrMagr.get()) {
			entrMagr.remove(entr);
		}
		for (Map map : mapMagr.get()) {
			mapMagr.remove(map);
		}
		
	if (entrMagr.get().isEmpty() && this.mapMagr.get().isEmpty()) {
			for (Map map : bData.getMapList()) { //先保持Map对象
				Map newMap = new Map();
				ReflectUtil.copyProperties(newMap, map, 
						new String[]{"mapSign", "mapTitle", "mapDescn"});
				mapMagr.save(newMap);
				
				for (Entry entry : bData.getEntrList()) { //再保持Entry对象
					if (map.equals(entry.getMap())) { //为Entry设置Map
						Entry newEntr = new Entry();
						ReflectUtil.copyProperties(newEntr, entry,
							new String[]{"refValue", "viewText", "entryDescn"});
						newEntr.setMap(newMap);
						entrMagr.save(newEntr);
					} 
				}
			}
		} else {
			log.error("当恢复类别数据的时候，表中的原始数据不能被清空,导致无法恢复!");
			return false;
		}
		return true;
	}
	
	/**
	 * 读取备份文件中的数据存储对象
	 * @return MapEntryMateData
	 */
	private MapEntryMateData readObject() {
		String fileName = System.getProperty("user.home") 
			+ Constants.DATA_BACK_FOLDER 
			+ Constants.DATA_BACK_FILE;
		
		MapEntryMateData bData = null;
		try {
			ObjectInputStream dataIn = new ObjectInputStream(
					new FileInputStream(new File(fileName)));
			bData = (MapEntryMateData) dataIn.readObject();
		} catch (FileNotFoundException e) {
			log.warn(fileName + "文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log.warn("读取文件中的对象类型和数据备份对象类型不匹配");
			e.printStackTrace();
		}
		return bData;
	}
	
	/**
	 * 写入数据
	 * @param dataFile
	 * @param obj
	 * @return
	 */
	private boolean writeObject(File dataFile, Object obj) {
		boolean tempF = false;
		try {
			ObjectOutputStream objOut = new ObjectOutputStream(
							new FileOutputStream(dataFile));
			objOut.writeObject(obj);
			objOut.close();
			tempF = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempF;
	}

	public EntryManager getEntrMagr() {
		return entrMagr;
	}

	public void setEntrMagr(EntryManager entrMagr) {
		this.entrMagr = entrMagr;
	}

	public MapManager getMapMagr() {
		return mapMagr;
	}

	public void setMapMagr(MapManager mapMagr) {
		this.mapMagr = mapMagr;
	}

}
