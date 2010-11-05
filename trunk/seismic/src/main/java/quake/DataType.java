package quake;

/**
 * 用于区分测震数据和前兆数据的枚举类
 * @author SAM
 *
 */
public enum DataType {
  /**
   * 测震数据
   */
  SEISMIC, 
  /**
   * 基本信息数据(台网,台站等数据字典)
   */
  SIGN; 
}
