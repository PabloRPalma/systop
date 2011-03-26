package quake.seismic.station;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 台站常量
 * 
 * @author yj
 * 
 */
public final class StationConstants {
  /**
   * 导出文件，每行开始标记
   */
  public static final String ROW_START = "#";
  /**
   * 换行
   */
  public static final String NEW_LINE = "\n\r";
  
  /**
   * 台站类型_固定
   */
  public static String STA_TYPE_PERMANENT = "permanent";
  /**
   * 台站类型_流动
   */
  public static String STA_TYPE_TEMPORARY = "temporary";
  /**
   * 台基类型
   */
  public static String ROCK_TYPE_ = "temporary";
  /**
   * 台基类型
   */
  public static String ROCK_TYPE_JIYAN = "基岩";
  public static String ROCK_TYPE_MOORSTONE = "花岗岩";
  public static String ROCK_TYPE_GNEISS = "片麻岩";
  public static String ROCK_TYPE_JINGXIA = "井下";
  /**
   * 响应类型
   */
  
  public static final Map<String, String> BLOCKETTE053_TYPE = new HashMap<String, String>();
  static {
    BLOCKETTE053_TYPE.put("A", "A [Laplace Transform (Rad/sec)]");
    BLOCKETTE053_TYPE.put("B", "B");
    BLOCKETTE053_TYPE.put("C", "C");
    BLOCKETTE053_TYPE.put("D", "D");
  }
 
  public static final Map<String, String> BLOCKETTE053_IN_UNITS = new HashMap<String, String>();
  static {
    BLOCKETTE053_IN_UNITS.put("M/S", "M/S - Velocity in Meters Per Second");
    BLOCKETTE053_IN_UNITS.put("V", "V - Volts");
    BLOCKETTE053_IN_UNITS.put("COUNTS", "COUNTS - COUNTS");
  }
  public static final Map<String, String> BLOCKETTE053_OUT_UNITS = new HashMap<String, String>();
  static {
    BLOCKETTE053_OUT_UNITS.put("M/S", "M/S - Velocity in Meters Per Second");
    BLOCKETTE053_OUT_UNITS.put("V", "V - Volts");
    BLOCKETTE053_OUT_UNITS.put("COUNTS", "COUNTS - COUNTS");
  }
  /**
   * 台站类型
   */
  public static final Map<String, String> STA_TYPE = new LinkedHashMap<String,String>();
  static {
    STA_TYPE.put(STA_TYPE_PERMANENT, "固定");
    STA_TYPE.put(STA_TYPE_TEMPORARY, "流动");
  }
  /**
   * 台基类型
   */
  public static final Map<String, String> ROCK_TYPE = new LinkedHashMap<String,String>();
  static {
    ROCK_TYPE.put(ROCK_TYPE_JIYAN, "基岩");
    ROCK_TYPE.put(ROCK_TYPE_MOORSTONE, "花岗岩");
    ROCK_TYPE.put(ROCK_TYPE_GNEISS, "片麻岩");
    ROCK_TYPE.put(ROCK_TYPE_JINGXIA, "井下");
  }
}
