package quake.seismic.station;

import java.util.HashMap;
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
   * 响应类型
   */
  /**
   * 台网代码名称Map
   */
  public static final Map<String, String> BLOCKETTE053_TYPE = new HashMap<String, String>();
  static {
    BLOCKETTE053_TYPE.put("A", "A [Laplace Transform (Rad/sec)]");
    BLOCKETTE053_TYPE.put("B", "B");
    BLOCKETTE053_TYPE.put("C", "C");
    BLOCKETTE053_TYPE.put("D", "D");
  }
}
