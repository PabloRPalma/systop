package com.systop.common.map;

/**
 * 类别模块用到的常量
 * @author Systop_Guo
 */
public final class Constants {
	
	/**
   * Prevent from initializing.
   */
  private Constants() {
  }
  
  /**
   * 类别标题在使用中
   */
  public static final int MAPTITLE_INUSE = -11;
  
  /**
   * 类别标识在使用中
   */
  public static final int MAPSIGN_INUSE = -12;
  
  /**
   * 添加条目没有选择类别
   */
  public static final int ADD_ENTRY_NO_MAP = 22;
  
  /**
   * 添加条目的时候已经在使用,即引用值和map_id组合不惟一
   */
  public static final int ENTRY_IS_INUSE = 23;
  
  /**
   * 添加条目完成
   */
  public static final int ADD_ENTRY_OK = 20;
  
  /**
   * 修改条目完成
   */
  public static final int UPDATE_ENTRY_OK = 21;
  
  /**数据备份文件夹名称*/
  public static final String DATA_BACK_FOLDER = "\\Systop_Data_Back";
  
  /**数据备份文件名称*/
  public static final String DATA_BACK_FILE = "\\Map_Entry_Data.bak";
}
