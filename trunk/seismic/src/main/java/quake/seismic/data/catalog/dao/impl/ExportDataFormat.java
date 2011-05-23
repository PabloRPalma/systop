package quake.seismic.data.catalog.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.ecside.util.ExtremeUtils;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public final class ExportDataFormat {

  public static Logger logger = (Logger) LoggerFactory.getLogger(ExportDataFormat.class);
  
  public static final String[] SPECIAL_STRINGS = {"-99999.0", "-99,999.0", "-99999", "0.0", "0.00"};
  
  private ExportDataFormat(){
  }
  
  /**
   * VLM文件导出时，转换经纬度的值
   * @param val
   * @param len
   * @param lonOrlat
   */
  protected static String convertEpiOfEQT(Double val, int len, String lonOrlat) {
    int copVal = -99;
    String strVal = null;
    if (val == null) {
      strVal = "";
    }
    if ("LON".equals(lonOrlat)) {
      copVal = -999;
      if (val <= copVal) {
        strVal = "";
      } else {
        strVal = ExtremeUtils.formatNumber("##0.000", val);
      } 
    }
    if ("LAT".equals(lonOrlat)) {
      if (val <= copVal) {
        strVal = "";
      } else {
        strVal = ExtremeUtils.formatNumber("#0.000", val);
      } 
    }
    
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换深度值,将空值和-99***等值转换为000
   * @param val
   * @param len
   */
  protected static String convertDepth(Double val, int len) {
    String strVal = null;
    if (val == null || val <= -99) {
      strVal = ExtremeUtils.formatNumber("###", "000");
    } else {
      strVal = ExtremeUtils.formatNumber("###", val);
    }
    
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换震级名
   * @param val
   * @param len
   */
  protected static String convertMagName(String val, int len) {
    if ("null".equalsIgnoreCase(val)) {
      return " ";
    }
    if (StringUtils.isBlank(val)) {
      return " ";
    }
    return StringUtils.rightPad(val, len, ' ');
  }
  
  /**
   * 转换震级值
   * @param val
   * @param len
   */
  protected static String convertMagVal(Double val, int len) {
    String strVal = null;
    if (val == null || val <= -99) {
      strVal = "";
    } else {
      strVal = ExtremeUtils.formatNumber("0.0", val);
    }
    
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换RMS
   * @param val
   * @param len
   */
  protected static String convertRms(Double val, int len) {
    String strVal = null;
    if (val == null || val <= -99) {
      strVal = "";
    } else {
      strVal = ExtremeUtils.formatNumber("0.000", val);
    }
    
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换Qloc
   * @param val
   * @param len
   */
  protected static String convertQloc(String val) {
    if (StringUtils.isNotEmpty(val) && !"nul".equalsIgnoreCase(val)) {
      return val;
    }
    return " ";
  }
  
  /**
   * 转换Sun_stn,Loc_stn
   * @param val
   * @param len
   */
  protected static String convertStn(Integer val, int len) {
    String strVal = null;
    if (val == null) {
      strVal = ExtremeUtils.formatNumber("###", "000");
    } else {
      strVal = ExtremeUtils.formatNumber("###", val);
    }
    
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换2位值
   */
  protected static String convertTwoStr(String val) {
    if (StringUtils.isNotEmpty(val) && !val.equalsIgnoreCase("null")) {
      return val;
    }
    return "  ";
  }
  
  /**
   * 转换字符串，不够位数左侧补空格_String类型
   * @param strVal
   * @param len
   */
  protected static String convertValue(String val, int len) {
    String strVal = " ";
    if(StringUtils.isNotEmpty(val) && !val.equalsIgnoreCase("null")) {
      strVal = val;
    }
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换字符串，不够位数左侧补空格_Integer类型
   * @param strVal
   * @param len
   */
  protected static String convertValue(Integer intVal, int len) {
    String strVal = " ";
    if (intVal != null) {
      for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
        if(StringUtils.equals(SPECIAL_STRINGS[i], String.valueOf(intVal).trim())) {
          return StringUtils.leftPad(" ", len, ' ');
        } else {
          strVal = String.valueOf(intVal);
        }
      }
    }
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * Double类型数据，保留一位小数
   * @param val
   * @param len
   */
  protected static String convertDoubleVal(Double val, int len, String flag) {
    String strVal = " ";
    if (val == null) {
      strVal = " ";
    } else {
      if ("five".equals(flag)) {
        for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
          if(StringUtils.equals(SPECIAL_STRINGS[i], String.valueOf(val).trim())) {
            return StringUtils.leftPad(" ", len, ' ');
          } else {
            strVal = ExtremeUtils.formatNumber("##0.0", val);
          }
        }
      }
      if("six".equals(flag)) {
        for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
          if(StringUtils.equals(SPECIAL_STRINGS[i], String.valueOf(val).trim())) {
            return StringUtils.leftPad(" ", len, ' ');
          } else {
            strVal = ExtremeUtils.formatNumber("###0.0", val);
          }
        }
      }
    }
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * Double类型数据，保留两位小数
   * @param val
   * @param len
   */
  protected static String convertDoubleTwoVal(Double val, int len, String flag) {
    String strVal = " ";
    if (val == null) {
      strVal = " ";
    } else {
      if ("six".equals(flag)) {
        for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
          if(StringUtils.equals(SPECIAL_STRINGS[i], String.valueOf(val).trim())) {
            return StringUtils.leftPad(" ", len, ' ');
          } else {
            strVal = ExtremeUtils.formatNumber("##0.00", val);
          }
        }
      }
      if("seven".equals(flag)) {
        for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
          if(StringUtils.equals(SPECIAL_STRINGS[i], String.valueOf(val).trim())) {
            return StringUtils.leftPad(" ", len, ' ');
          } else {
            strVal = ExtremeUtils.formatNumber("###0.00", val);
          }
        }
      }
    }
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换震相AMP
   * @param val
   * @param len
   */
  protected static String convertAMP(Double val, int len) {
    String strVal = " ";
    if (val == null) {
      strVal = " ";
    } else {
      for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
        if(StringUtils.equals(SPECIAL_STRINGS[i], String.valueOf(val).trim())) {
          return StringUtils.leftPad(" ", len, ' ');
        } else {
          strVal = ExtremeUtils.formatNumber("######0.0", val);
        }
      }
    }
    return StringUtils.leftPad(strVal, len, ' ');
  }
}
