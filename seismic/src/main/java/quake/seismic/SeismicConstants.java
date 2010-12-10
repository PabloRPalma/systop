package quake.seismic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测震数据常量
 */
public class SeismicConstants {

  /**
   * 默认的震级表
   */
  public static String DEFAULT_MAG_TABLE = "Mag_w";
  /**
   * 默认的震相表
   */
  public static String DEFAULT_PHASE_TABLE = "Phase_w";
  /**
   * 按圆形区域查询
   */
  public static String ROUND_QUERY_YES = "1";
  /**
   * 按矩形区域查询
   */
  public static String ROUND_QUERY_NO = "0";
  /**
   * 地震目录查询时（不分页），限制最大查询条数_用于震中分布图显示
   */
  public static int MAX_SIZE = 500;
  /**
   * 地震目录基本格式下载时最大地震目录条数
   */
  public static int CATALOG_BASIC_MAX_SIZE = 500;
  /**
   * 地震目录完全格式下载时最大地震目录条数
   */
  public static int CATALOG_FULL_MAX_SIZE = 100;
  /**
   * 震相观测报告下载时最大地震目录条数
   */
  public static int BULLETIN_FULL_MAX_SIZE = 50;
  /**
   * 测震台站信息查询访问地址
   */
  public static String STATION_INFO_URL = "/quake/seismic/station/stationSettings.do";
  /**
   * 地震目录导出的数据格式卷类型
   */
  public static String Catalog_basic = "Catalog_basic";
  public static String Catalog_full = "Catalog_full";
  public static String Bulletin_basic = "Bulletin_basic";
  public static String Bulletin_full = "Bulletin_full";
  /**
   * 地震类型代码
   */
  public static String eq = "天然地震";
  public static String ep = "爆破";
  public static String sp = "疑爆";
  public static String ss = "塌陷";
  public static String se = "可疑事件";
  public static String ve = "火山构造地震";
  public static String le = "长周期事件";
  public static String vh = "火山混合事件";
  public static String vp = "火山爆炸";
  public static String vt = "火山颤动";
  public static String ot = "其它";
  /**
   * 震相类型
   */
  public static String PHASE_TYPE_AMP = "amp";
  public static String PHASE_TYPE_TIME = "time";
  /**
   * 震相类型Map
   */
  public static final Map<String, String> PHASE_TYPE_MAP = new LinkedHashMap<String, String>();
  static {
    PHASE_TYPE_MAP.put(PHASE_TYPE_TIME, "到时类");
    PHASE_TYPE_MAP.put(PHASE_TYPE_AMP, "振幅类");
  }
  /**
   * 地震类型代码Map_用于地震目录下载时用
   */
  public static final Map<String, String> EQ_TYPE_MAP = new HashMap<String, String>();
  static {
    EQ_TYPE_MAP.put(eq, "eq");
    EQ_TYPE_MAP.put(ep, "ep");
    EQ_TYPE_MAP.put(sp, "sp");
    EQ_TYPE_MAP.put(ss, "ss");
    EQ_TYPE_MAP.put(se, "se");
    EQ_TYPE_MAP.put(ve, "ve");
    EQ_TYPE_MAP.put(le, "le");
    EQ_TYPE_MAP.put(vh, "vh");
    EQ_TYPE_MAP.put(vp, "vp");
    EQ_TYPE_MAP.put(vt, "vt");
    EQ_TYPE_MAP.put(ot, "ot");
  }
  /**
   * 地震类型代码Map_用于地震目录查询时用
   */
  public static final Map<String, String> QUERY_EQ_TYPE = new LinkedHashMap<String, String>();
  static {
    QUERY_EQ_TYPE.put(eq, eq);
    QUERY_EQ_TYPE.put(ep, ep);
    QUERY_EQ_TYPE.put(sp, sp);
    QUERY_EQ_TYPE.put(ss, ss);
    QUERY_EQ_TYPE.put(se, se);
    QUERY_EQ_TYPE.put(ve, ve);
    QUERY_EQ_TYPE.put(le, le);
    QUERY_EQ_TYPE.put(vh, vh);
    QUERY_EQ_TYPE.put(vp, vp);
    QUERY_EQ_TYPE.put(vt, vt);
    QUERY_EQ_TYPE.put(ot, ot);
  }
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
