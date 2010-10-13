package quake.admin.czcatalog;

import java.util.HashMap;
import java.util.Map;

/**
 * 地震目录常量类
 * @author DU
 *
 */
public class CzConstants {

  /**
   * 地震目录震级列显示类型_ML
   */
  private static String DISTYPE_ML = "ML";
  /**
   * 地震目录震级列显示类型_Ms
   */
  private static String DISTYPE_MS = "Ms";
  /**
   * 是否显示有事件波形的地震目录_不显示
   */
  public static String SEEDDIS_NO = "0";
  /**
   * 是否显示有事件波形的地震目录_显示
   */
  public static String SEEDDIS_YES = "1";
  /**
   * 地震目录震级列显示类型MAP
   */
  public static final Map<String, String> DISTYPE_MAP = new HashMap<String, String>();
  static {
    DISTYPE_MAP.put(DISTYPE_ML, "ML");
    DISTYPE_MAP.put(DISTYPE_MS, "Ms");
  }
  /**
   * 是否显示有事件波形的地震目录MAP
   */
  public static final Map<String, String> SEEDDIS_MAP = new HashMap<String, String>();
  static {
    SEEDDIS_MAP.put(SEEDDIS_YES, "显示");
    SEEDDIS_MAP.put(SEEDDIS_NO, "不显示");
  }
  
  private CzConstants() {
  }
}
