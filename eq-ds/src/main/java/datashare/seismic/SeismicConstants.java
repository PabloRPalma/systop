package datashare.seismic;

import java.util.HashMap;
import java.util.Map;

/**
 * 测震数据常量
 */
public class SeismicConstants {

  /**
   * 测震台站信息查询访问地址
   */
  public static String STATION_INFO_URL = "/datashare/seismic/station/stationSettings.do";
  
  /**
   * 测震台网代码
   */
  public static String NETWORK_INFO_CB = "CB";
  public static String NETWORK_INFO_BU = "BU";
  public static String NETWORK_INFO_BJ = "BJ";
  public static String NETWORK_INFO_TJ = "TJ";
  public static String NETWORK_INFO_HE = "HE";
  public static String NETWORK_INFO_SX = "SX";
  public static String NETWORK_INFO_NM = "NM";
  public static String NETWORK_INFO_LN = "LN";
  public static String NETWORK_INFO_XJ = "XJ";
  public static String NETWORK_INFO_HL = "HL";
  public static String NETWORK_INFO_SH = "SH";
  public static String NETWORK_INFO_JS = "JS";
  public static String NETWORK_INFO_ZJ = "ZJ";
  public static String NETWORK_INFO_AH = "AH";
  public static String NETWORK_INFO_FJ = "FJ";
  public static String NETWORK_INFO_JX = "JX";
  public static String NETWORK_INFO_SD = "SD";
  public static String NETWORK_INFO_HA = "HA";
  public static String NETWORK_INFO_NX = "NX";
  public static String NETWORK_INFO_SN = "SN";
  public static String NETWORK_INFO_GD = "GD";
  public static String NETWORK_INFO_GX = "GX";
  public static String NETWORK_INFO_HI = "HI";
  public static String NETWORK_INFO_SC = "SC";
  public static String NETWORK_INFO_GS = "GS";
  public static String NETWORK_INFO_YN = "YN";
  public static String NETWORK_INFO_HN = "HN";
  public static String NETWORK_INFO_CQ = "CQ";
  public static String NETWORK_INFO_HB = "HB";
  public static String NETWORK_INFO_XZ = "XZ";
  public static String NETWORK_INFO_QH = "QH";
  public static String NETWORK_INFO_GZ = "GZ";
  public static String NETWORK_INFO_JL = "JL";
  
  /**
   * 台网代码名称Map
   */
  public static final Map<String, String> NETWORK_INFO = new HashMap<String, String>();
  static {
    NETWORK_INFO.put(NETWORK_INFO_CB, "国家(CB)");
    NETWORK_INFO.put(NETWORK_INFO_BU, "地球所(BU)");
    NETWORK_INFO.put(NETWORK_INFO_BJ, "北京(BJ)");
    NETWORK_INFO.put(NETWORK_INFO_TJ, "天津(TJ)");
    NETWORK_INFO.put(NETWORK_INFO_HE, "河北(HE)");
    NETWORK_INFO.put(NETWORK_INFO_SX, "山西(SX)");
    NETWORK_INFO.put(NETWORK_INFO_NM, "内蒙(NM)");
    NETWORK_INFO.put(NETWORK_INFO_LN, "辽宁(LN)");
    NETWORK_INFO.put(NETWORK_INFO_XJ, "新疆(XJ)");
    NETWORK_INFO.put(NETWORK_INFO_HL, "黑龙江(HL)");
    NETWORK_INFO.put(NETWORK_INFO_SH, "上海(SH)");
    NETWORK_INFO.put(NETWORK_INFO_JS, "江苏(JS)");
    NETWORK_INFO.put(NETWORK_INFO_ZJ, "浙江(ZJ)");
    NETWORK_INFO.put(NETWORK_INFO_AH, "安徽(AH)");
    NETWORK_INFO.put(NETWORK_INFO_FJ, "福建(FJ)");
    NETWORK_INFO.put(NETWORK_INFO_JX, "江西(JX)");
    NETWORK_INFO.put(NETWORK_INFO_SD, "山东(SD)");
    NETWORK_INFO.put(NETWORK_INFO_HA, "河南(HA)");
    NETWORK_INFO.put(NETWORK_INFO_NX, "宁夏(NX)");
    NETWORK_INFO.put(NETWORK_INFO_SN, "陕西(SN)");
    NETWORK_INFO.put(NETWORK_INFO_GD, "广东(GD)");
    NETWORK_INFO.put(NETWORK_INFO_GX, "广西(GX)");
    NETWORK_INFO.put(NETWORK_INFO_HI, "海南(HI)");
    NETWORK_INFO.put(NETWORK_INFO_SC, "四川(SC)");
    NETWORK_INFO.put(NETWORK_INFO_GS, "甘肃(GS)");
    NETWORK_INFO.put(NETWORK_INFO_YN, "云南(YN)");
    NETWORK_INFO.put(NETWORK_INFO_HN, "湖南(HN)");
    NETWORK_INFO.put(NETWORK_INFO_CQ, "重庆(CQ)");
    NETWORK_INFO.put(NETWORK_INFO_HB, "湖北(HB)");
    NETWORK_INFO.put(NETWORK_INFO_XZ, "西藏(XZ)");
    NETWORK_INFO.put(NETWORK_INFO_QH, "青海(QH)");
    NETWORK_INFO.put(NETWORK_INFO_GZ, "贵州(GZ)");
    NETWORK_INFO.put(NETWORK_INFO_JL, "吉林(JL)");
  }
  
  /**
   * 防止实例化
   */
  private SeismicConstants() {    
  }
}
