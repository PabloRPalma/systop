package quake.seismic.instrument;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测震仪器常量
 */
public class InstrumentConstants {

  /**
   * 仪器用途_用于固定台站
   */
  public static String USE_TYPE_PERMANENT = "permanent";
  /**
   * 仪器用途_用于流动观测
   */
  public static String USE_TYPE_TEMPORARY = "temporary";
  /**
   * 仪器用途_备用设备
   */
  public static String USE_TYPE_BACKUP = "backup"; 
  /**
   * 仪器用途_报废设备
   */
  public static String USE_TYPE_SCRAP = "scrap";
  /**
   * 仪器类型_传感器
   */
  public static String INSTR_TYPE_SENSOR = "Sensor";
  /**
   * 仪器类型_数据采集器
   */
  public static String INSTR_TYPE_DIGITIZER = "Digitizer";
  /**
   * 仪器类型_一体化仪器
   */
  public static String INSTR_TYPE_COMBINED = "Combined";
  /**
   * 台网代码名称Map
   */
  public static final Map<String, String> USE_TYPE = new LinkedHashMap<String, String>();
  static {
    USE_TYPE.put(USE_TYPE_PERMANENT, "固定台站");
    USE_TYPE.put(USE_TYPE_TEMPORARY, "流动观测");
    USE_TYPE.put(USE_TYPE_BACKUP, "备用设备");
    USE_TYPE.put(USE_TYPE_SCRAP, "报废设备");
  }
  /**
   * 仪器类型Map
   */
  public static final Map<String, String> INSTR_TYPE = new LinkedHashMap<String,String>();
  static {
    INSTR_TYPE.put(INSTR_TYPE_SENSOR, "传感器");
    INSTR_TYPE.put(INSTR_TYPE_DIGITIZER, "数据采集器");
    INSTR_TYPE.put(INSTR_TYPE_COMBINED, "一体化仪器");
  }
  /**
   * 防止实例化
   */
  private InstrumentConstants(){
  }
}
