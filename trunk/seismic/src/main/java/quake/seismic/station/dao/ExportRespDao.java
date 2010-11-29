package quake.seismic.station.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.seismic.station.StationConstants;
import quake.seismic.station.model.Criteria;
import quake.seismic.station.model.Digitizer;
import quake.seismic.station.model.Loc;
import quake.seismic.station.util.JdomUtil;

/**
 * 导出台站数据respDAO
 * 
 * @author yj
 */
@SuppressWarnings("unchecked")
@Repository
public class ExportRespDao extends AbstractStationDao {
  /**
   * 通道
   */
  private Map channel = null;
  /**
   * 通道下的response
   */
  private List channelResponse;
  /**
   * 数采下的response
   */
  private List digitizerResponse;

  private String schema;
  /**
   * 定义每行的长度
   */
  private int length = 40;

  /**
   * 获得通道
   */
  public void getChannel(String id) {
    Criteria c = new Criteria();
    c.setChannelId(id);
    c.setSchema(schema);
    List<Map> ChannelList = queryChannelById(c);
    if (ChannelList.size() == 1) {
      channel = ChannelList.get(0);
    }
  }

  /**
   * 导出resp
   */
  public StringBuffer queryForResp(String id, String staSchema) {
    schema = staSchema;
    getChannel(id);
    StringBuffer sb = new StringBuffer();
    sb.append(setChannelMeg());
    if (channel != null && channel.get("RESPONSE") != null) {
      channelResponse = JdomUtil.getResponseValue(channel.get("RESPONSE").toString());
      sb.append(first());
      sb.append(second());
      sb.append(setSensitivity());
    }
    return sb;
  }

  /**
   * 0级别显示
   */
  private StringBuffer setSensitivity() {
    StringBuffer sb = new StringBuffer();
    Map temp = new HashMap();
    temp.put("Stage_sequence", "0");
    temp.put("blockette", "blockette058");
    Map att = JdomUtil.getElementValue(channelResponse, temp);

    sb.append(setTitle("Channel Sensitivity"));
    sb.append("B058F03").append("    ").append("Stage sequence number:");
    sb.append(setSpace(length, "Stage sequence number:"));
    sb.append("0").append(StationConstants.NEW_LINE);
    sb.append("B058F04").append("    ").append("Sensitivity:");
    sb.append(setSpace(length, "Sensitivity:"));
    sb.append(getValue(att, "Sensitivity")).append(StationConstants.NEW_LINE);
    sb.append("B058F05").append("    ").append("Frequency of sensitivity:");
    sb.append(setSpace(length, "Frequency of sensitivity:"));
    sb.append(getValue(att, "Freq")).append(StationConstants.NEW_LINE);
    sb.append("B058F06").append("    ").append("Numbet of calibtations:");
    sb.append(setSpace(length, "Numbet of calibtations:"));
    sb.append("0").append(StationConstants.NEW_LINE);

    return sb;
  }

  /**
   * 每个模块显示头
   */
  private StringBuffer setTitle(String name) {
    int length = 25;
    StringBuffer sb = new StringBuffer();
    sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);

    sb.append(StationConstants.ROW_START).append("    ").append("+").append("               ")
        .append("+--------------------------------------------+").append("               ").append(
            "+").append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append("    ").append("+").append("               ")
        .append("|   ").append(name).append(",   ").append(channel.get("STA_CODE")).append(" ch ")
        .append(channel.get("CHN_CODE"));
    sb.append(setSpace(length, name));
    sb.append("  |").append("               ").append("+").append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append("    ").append("+").append("               ")
        .append("+--------------------------------------------+").append("               ").append(
            "+").append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);
    return sb;
  }

  /**
   * 第一级
   */
  private StringBuffer first() {
    StringBuffer sb = new StringBuffer();
    sb.append(setPoleZero());
    // Decimation 地震计
    // sb.append(setDecimation());
    sb.append(setGain(1, channelResponse));
    return sb;
  }

  /**
   * 第二三四级
   */
  private StringBuffer second() {
    String response = getDigitizerResponses();
    if (StringUtils.isNotBlank(response)) {
      digitizerResponse = JdomUtil.getResponseValue(response);
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 2; i < 5; i++) {
      sb.append(setCoefficients(i));
      sb.append(setDecimation(i));
      sb.append(setGain(i, digitizerResponse));
    }
    return sb;
  }

  /**
   * 数采decimation显示
   */
  private StringBuffer setDecimation(int level) {
    StringBuffer sb = new StringBuffer();
    /**
     * 获得结点属性
     */
    Map temp = new HashMap();
    temp.put("Stage_sequence", String.valueOf(level));
    temp.put("blockette", "blockette057");
    Map att = JdomUtil.getElementValue(digitizerResponse, temp);

    sb.append(setTitle("Decimation"));
    sb.append("B057F03").append("    ").append("Stage sequence number:");
    sb.append(setSpace(length, "Stage sequence number:"));
    sb.append(level).append(StationConstants.NEW_LINE);
    sb.append("B057F04").append("    ").append("Input sample rate:");
    sb.append(setSpace(length, "Input sample rate:"));
    sb.append(getValue(att, "Input_sample_rate")).append(StationConstants.NEW_LINE);
    sb.append("B057F05").append("    ").append("Decimation factor:");
    sb.append(setSpace(length, "Decimation factor:"));
    sb.append(getValue(att, "Decimation_factor")).append(StationConstants.NEW_LINE);
    sb.append("B057F06").append("    ").append("Decimation offset:");
    sb.append(setSpace(length, "Decimation offset:"));
    sb.append(getValue(att, "Decimation_offset")).append(StationConstants.NEW_LINE);
    sb.append("B057F07").append("    ").append("Estimated delay (seconds):");
    sb.append(setSpace(length, "Estimated delay (seconds):"));
    sb.append(getValue(att, "Estimated_delay")).append(StationConstants.NEW_LINE);
    sb.append("B057F08").append("    ").append("Correction applied (seconds):");
    sb.append(setSpace(length, "Correction applied (seconds):"));
    sb.append(getValue(att, "Correction_applied")).append(StationConstants.NEW_LINE);

    return sb;
  }

  /**
   * 获得DigitizerResponse
   */
  private String getDigitizerResponses() {
    Loc loc = new Loc();
    loc.setSchema(schema);
    if (channel != null) {
      loc.setNetCode(channel.get("NET_CODE").toString());
      loc.setStaCode(channel.get("STA_CODE").toString());
      loc.setLoc_id(channel.get("LOC_ID").toString());
    }
    List<Map> locList = queryLoc(loc);
    Map loc_info = null;
    if (locList.size() == 1) {
      loc_info = locList.get(0);
    }
    Digitizer d = new Digitizer();
    d.setSchema(schema);
    if (loc_info != null) {
      d.setInstr_model(loc_info.get("DIGITIZER_MODEL").toString());
      d.setResp_id(loc_info.get("DIGITIZER_RESPID").toString());
    }
    List<Map> digitizerList = queryDigitizer(d);
    String response = null;
    if (digitizerList.size() == 1) {
      Map m = digitizerList.get(0);
      if (m != null && m.get("RESPONSE") != null) {
        response = m.get("RESPONSE").toString();
      }
    }
    return response;
  }

  /**
   * 第二三四级别 Coefficients
   */
  private StringBuffer setCoefficients(int level) {
    StringBuffer sb = new StringBuffer();
    /**
     * 获得结点属性
     */
    Map temp = new HashMap();
    temp.put("Stage_sequence", String.valueOf(level));
    temp.put("blockette", "blockette054");
    Map att = JdomUtil.getElementValue(digitizerResponse, temp);

    sb.append(setTitle("Response  (Cofficients)"));
    att.put("level", level);
    att.put("blockette", "B054");
    sb.append(setAtt(att));
    temp.put("att", "numerator");
    List numeratorList = JdomUtil.getChildCount(digitizerResponse, temp);
    sb.append("B054F07").append("    ").append("Number of numerators :");
    sb.append(setSpace(length, "Number of numerators :"));
    sb.append(numeratorList.size()).append(StationConstants.NEW_LINE);
    temp.put("att", "denominator");
    List denominatorList = JdomUtil.getChildCount(digitizerResponse, temp);
    sb.append("B054F10").append("    ").append("Number of denominators :");
    sb.append(setSpace(length, "Number of denominators :"));
    sb.append(denominatorList.size()).append(StationConstants.NEW_LINE);
    if (numeratorList.size() > 0) {
      temp.put("att", "B054F08-09");
      temp.put("list", numeratorList);
      temp.put("title", "Numerator cofficients:");
      sb.append(setNumeratorOrDenominator(temp));
    }
    if (denominatorList.size() > 0) {
      temp.put("att", "B054F11-12");
      temp.put("list", denominatorList);
      temp.put("title", "Denominator cofficients:");
      sb.append(setNumeratorOrDenominator(temp));
    }

    return sb;
  }

  /**
   * 第2，3，4 级别中第一部分Numerator或者Denominator部分显示
   */
  public StringBuffer setNumeratorOrDenominator(Map m) {
    StringBuffer sb = new StringBuffer();
    int space = 10;
    sb.append(StationConstants.ROW_START).append("    ").append("Numerator cofficients:").append(
        StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append("             i,       cofficient,        error")
        .append(StationConstants.NEW_LINE);
    for (int j = 0; j < ((ArrayList) m.get("list")).size(); j++) {
      Map map = (HashMap) ((ArrayList) m.get("list")).get(j);
      sb.append("B054F08-09").append("    ").append(j);
      if (map.get("coeff").toString().startsWith("-")) {
        sb.append(setSpace(space - 1, String.valueOf(j)));
      } else {
        sb.append(setSpace(space, String.valueOf(j)));
      }
      sb.append(map.get("coeff"));
      if (map.get("coeff").toString().startsWith("-")) {
        sb.append(setSpace(length, map.get("coeff").toString()));
      } else {
        sb.append(setSpace(length - 1, map.get("coeff").toString()));
      }
      sb.append(map.get("error")).append(StationConstants.NEW_LINE);
    }
    return sb;
  }

  /**
   * 各级别中的Gain
   */
  private StringBuffer setGain(int level, List l) {
    StringBuffer sb = new StringBuffer();
    Map temp = new HashMap();
    temp.put("Stage_sequence", String.valueOf(level));
    temp.put("blockette", "blockette058");
    Map att = JdomUtil.getElementValue(l, temp);

    sb.append(setTitle("Channel  Gain"));
    sb.append("B058F03").append("    ").append("Stage sequence number:");
    sb.append(setSpace(length, "Stage sequence number:"));
    sb.append(level).append(StationConstants.NEW_LINE);
    sb.append("B058F04").append("    ").append("Gain:");
    sb.append(setSpace(length, "Gain:"));
    sb.append(getValue(att, "Sensitivity")).append(StationConstants.NEW_LINE);
    sb.append("B058F05").append("    ").append("Frequency of gain:");
    sb.append(setSpace(length, "Frequency of gain:"));
    sb.append(getValue(att, "Freq")).append(StationConstants.NEW_LINE);
    sb.append("B058F06").append("    ").append("Numbet of calibtations:");
    sb.append(setSpace(length, "Numbet of calibtations:"));
    sb.append("0").append(StationConstants.NEW_LINE);

    return sb;
  }

  /**
   * 第一级中的Poles and Zeros
   */
  private StringBuffer setPoleZero() {
    StringBuffer sb = new StringBuffer();
    /**
     * 获得结点属性
     */
    Map temp = new HashMap();
    temp.put("Stage_sequence", "1");
    temp.put("blockette", "blockette053");
    Map att = JdomUtil.getElementValue(channelResponse, temp);

    sb.append(setTitle("Response  (Poles & Zeros)"));
    att.put("level", "1");
    att.put("blockette", "B053");
    sb.append(setAtt(att));
    sb.append("B053F07").append("    ").append("A0 Normalization factor:");
    sb.append(setSpace(length, "A0 Normalization factor:"));
    sb.append(getValue(att, "Normalization_factor")).append(StationConstants.NEW_LINE);
    sb.append("B053F08").append("    ").append("Normalization frequency:");
    sb.append(setSpace(length, "Normalization frequency:"));
    sb.append(getValue(att, "Normalization_frequency")).append(StationConstants.NEW_LINE);
    temp.put("att", "zero");
    List zeroList = JdomUtil.getChildCount(channelResponse, temp);
    sb.append("B053F09").append("    ").append("Number of zeros :");
    sb.append(setSpace(length, "Number of zeros :"));
    sb.append(zeroList.size()).append(StationConstants.NEW_LINE);
    temp.put("att", "pole");
    List poleList = JdomUtil.getChildCount(channelResponse, temp);
    sb.append("B053F14").append("    ").append("Number of peles :");
    sb.append(setSpace(length, "Number of peles :"));
    sb.append(poleList.size()).append(StationConstants.NEW_LINE);
    if (zeroList.size() > 0) {
      temp.put("att", "B053F10-13");
      temp.put("list", zeroList);
      temp.put("complex", "Complex zeros:");
      sb.append(setComplex(temp));
    }
    if (poleList.size() > 0) {
      temp.put("att", "B053F15-18");
      temp.put("list", poleList);
      temp.put("complex", "Complex poles:");
      sb.append(setComplex(temp));
    }
    return sb;
  }

  /**
   * 第一级别下的poles/zeros
   */
  public StringBuffer setComplex(Map m) {
    int space = 10;
    StringBuffer sb = new StringBuffer();
    sb.append(StationConstants.ROW_START).append("    ").append(m.get("complex")).append(
        StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append(
        "           i       real       imag       real_error       imag_error").append(
        StationConstants.NEW_LINE);
    for (int j = 0; j < ((ArrayList) m.get("list")).size(); j++) {
      Map map = (HashMap) ((ArrayList) m.get("list")).get(j);
      sb.append(m.get("att")).append("    ").append(j);
      if (map.get("Real").toString().startsWith("-")) {
        sb.append(setSpace(space - 1, String.valueOf(j)));
      } else {
        sb.append(setSpace(space, String.valueOf(j)));
      }
      sb.append(map.get("Real"));
      if (map.get("Imaginary").toString().startsWith("-")) {
        sb.append(setSpace(space - 1, map.get("Real").toString()));
      } else {
        sb.append(setSpace(space, map.get("Real").toString()));
      }
      sb.append(map.get("Imaginary"));
      if (map.get("Imaginary").toString().startsWith("-")) {
        sb.append(setSpace(space + 1, map.get("Imaginary").toString()));
      } else {
        sb.append(setSpace(space, map.get("Imaginary").toString()));
      }
      sb.append(map.get("Real_error"));
      sb.append(setSpace(space, map.get("Real_error").toString()));
      sb.append(map.get("Imaginary_error")).append(StationConstants.NEW_LINE);
    }
    return sb;
  }

  /**
   * 填充空格
   */
  public StringBuffer setSpace(int length, String s) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length - s.length(); i++) {
      sb.append(" ");
    }
    return sb;
  }

  /**
   * 通用每一级别第一部分前四个属性
   */
  private StringBuffer setAtt(Map att) {
    StringBuffer sb = new StringBuffer();

    sb.append(att.get("blockette")).append("F03").append("    ").append("Transfer function type:");
    sb.append(setSpace(length, "Transfer function type:"));
    if (getValue(att, "Type").equals("")) {
      sb.append("").append(StationConstants.NEW_LINE);
    } else {
      sb.append(StationConstants.BLOCKETTE053_TYPE.get(att.get("Type"))).append(
          StationConstants.NEW_LINE);
    }
    sb.append(att.get("blockette")).append("F04").append("    ").append("Stage sequence number:");
    sb.append(setSpace(length, "Stage sequence number:"));
    sb.append(att.get("level")).append(StationConstants.NEW_LINE);

    sb.append(att.get("blockette")).append("F05").append("    ")
        .append("Response in units lookup:");
    sb.append(setSpace(length, "Response in units lookup:"));
    if (getValue(att, "Signal_input_unit").endsWith("")) {
      sb.append("").append(StationConstants.NEW_LINE);
    } else {
      sb.append(StationConstants.BLOCKETTE053_IN_UNITS.get(att.get("Signal_input_unit"))).append(
          StationConstants.NEW_LINE);
    }

    sb.append(att.get("blockette")).append("F06").append("    ").append(
        "Response out units lookup:");
    sb.append(setSpace(length, "Response out units lookup:"));
    if (getValue(att, "Signal_output_unit").endsWith("")) {
      sb.append("").append(StationConstants.NEW_LINE);
    } else {
      sb.append(StationConstants.BLOCKETTE053_OUT_UNITS.get(att.get("Signal_output_unit"))).append(
          StationConstants.NEW_LINE);
    }
    return sb;
  }

  /**
   * RESP开始信息以及通道信息显示
   */
  public StringBuffer setChannelMeg() {
    StringBuffer sb = new StringBuffer();
    sb.append(StationConstants.ROW_START).append("    ").append(
        "<< IRIS SEED Reader, Release 5.0 >>").append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append("    ").append(
        "======== CHANNEL RESPONSE DATA ========").append(StationConstants.NEW_LINE);
    sb.append("B050F03").append("    ").append("Station:").append("    ").append(
        channel.get("STA_CODE")).append(StationConstants.NEW_LINE);
    sb.append("B050F16").append("    ").append("Network:").append("    ").append(
        channel.get("NET_CODE")).append(StationConstants.NEW_LINE);
    sb.append("B052F03").append("    ").append("Location:").append("   ").append(
        channel.get("LOC_ID")).append(StationConstants.NEW_LINE);
    sb.append("B050F04").append("    ").append("Channel:").append("    ").append(
        channel.get("CHN_CODE")).append(StationConstants.NEW_LINE);
    if (channel.get("ONDATE") == null) {
      sb.append("B050F22").append("    ").append("Start date:").append(" ").append(
          "No Starting Time").append(StationConstants.NEW_LINE);
    } else {
      sb.append("B050F22").append("    ").append("Start date:").append(" ").append(
          channel.get("ONDATE")).append(StationConstants.NEW_LINE);
    }
    if (channel.get("OFFDATE") == null) {
      sb.append("B050F23").append("    ").append("End date:").append("   ")
          .append("No Ending Time").append(StationConstants.NEW_LINE);
    } else {
      sb.append("B050F23").append("    ").append("End date:").append("   ").append(
          channel.get("OFFDATE")).append(StationConstants.NEW_LINE);
    }
    return sb;
  }

  /**
   * 根据查询条件执行通道数据查询。
   */
  public List<Map> queryChannelById(Criteria c) {
    Assert.notNull(c, "Criteria is null.");
    return getTemplate().queryForList(SQL_CHANNEL_ID, c);
  }

  /**
   * 根据查询条件执行数采查询。
   */
  private List<Map> queryDigitizer(Digitizer d) {
    return getTemplate().queryForList(SQL_DIGITIZER, d);
  }

  /**
   * 根据查询条件执行位置信息查询。
   */
  private List<Map> queryLoc(Loc l) {
    return getTemplate().queryForList(SQL_LOC, l);
  }

  /**
   * 节点属性不存在的时候返回""
   * 
   * @param m
   * @param key
   * @return
   */
  private String getValue(Map m, String key) {
    String value = null;
    if (m != null && m.get(key) != null) {
      value = m.get(key).toString();
    } else {
      value = "";
    }
    return value;
  }
}
