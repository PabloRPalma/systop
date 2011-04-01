package quake.seismic.data.catalog.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.ecside.util.ExtremeUtils;

public final class ExportDataFormat {

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
    if (StringUtils.isNotEmpty(val)) {
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
    if (StringUtils.isNotEmpty(val)) {
      return val;
    }
    return "  ";
  }
}
