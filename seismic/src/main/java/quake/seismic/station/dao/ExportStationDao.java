package quake.seismic.station.dao;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.xml.sax.InputSource;

import quake.seismic.station.StationConstants;
import quake.seismic.station.model.Criteria;
import quake.seismic.station.model.Digitizer;
import quake.seismic.station.model.Loc;

/**
 * 导出台站数据DAO
 * 
 * @author yj
 */
@SuppressWarnings("unchecked")
@Repository
public class ExportStationDao extends AbstractStationDao {
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
   * 
   * @param id 通道id
   * @return
   */
  public StringBuffer queryForResp(String id, String staSchema) {
    schema = staSchema;
    StringBuffer sb = new StringBuffer();
    sb.append(setChannelMeg());
    if (channel.get("RESPONSE") != null) {
      channelResponse = getResponseValue(channel.get("RESPONSE").toString());
      sb.append(first());
      sb.append(second());
      sb.append(third());
      sb.append(four());
      sb.append(setSensitivity());
    }
    return sb;
  }

  /**
   * 第四级
   * 
   * @return
   */
  private StringBuffer four() {
    StringBuffer sb = new StringBuffer();
    sb.append(setCoefficients("4"));
    sb.append(setDecimation("4"));
    sb.append(setGain("4", digitizerResponse));
    return sb;
  }

  /**
   * 0级别显示
   * 
   * @return
   */
  private StringBuffer setSensitivity() {
    StringBuffer sb = new StringBuffer();
    sb.append(setTitle("Channel Sensitivity"));
    sb.append("B058F03").append("    ").append("Stage sequence number:").append("    ").append("0")
        .append(StationConstants.NEW_LINE);
    Map temp = new HashMap();
    temp.put("Stage_sequence", "0");
    temp.put("blockette", "blockette058");
    Map att = getElementValue(channelResponse, temp);

    sb.append("B058F04").append("    ").append("Sensitivity:").append("    ").append(
        att.get("Sensitivity")).append(StationConstants.NEW_LINE);
    sb.append("B058F05").append("    ").append("Frequency of sensitivity:").append("    ").append(
        att.get("Freq")).append(StationConstants.NEW_LINE);
    sb.append("B058F06").append("    ").append("Numbet of calibtations:").append("    ")
        .append("0").append(StationConstants.NEW_LINE);
    return sb;
  }

  /**
   * 每个模块显示头
   * 
   * @param name
   * @return
   */
  private StringBuffer setTitle(String name) {
    StringBuffer sb = new StringBuffer();
    sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);

    sb.append(StationConstants.ROW_START).append("    ").append("+").append("               ")
        .append("+--------------------------------------------+").append("               ").append(
            "+").append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append("    ").append("+").append("               ")
        .append("|   ").append(name).append(",   ").append(channel.get("STA_CODE")).append(" ch ")
        .append(channel.get("CHN_CODE")).append("  |").append("               ").append("+")
        .append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append("    ").append("+").append("               ")
        .append("+--------------------------------------------+").append("               ").append(
            "+").append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);
    return sb;
  }

  /**
   * 第三级
   * 
   * @return
   */
  private StringBuffer third() {
    StringBuffer sb = new StringBuffer();
    sb.append(setCoefficients("3"));
    sb.append(setDecimation("3"));
    sb.append(setGain("3", digitizerResponse));
    return sb;
  }

  /**
   * 第一级
   * 
   * @param m
   * @return
   */
  private StringBuffer first() {
    StringBuffer sb = new StringBuffer();
    sb.append(setPoleZero());
    // Decimation 地震计
    // sb.append(setDecimation());
    sb.append(setGain("1", channelResponse));
    return sb;
  }

  /**
   * 第二级
   * 
   * @param m
   * @return
   */
  private StringBuffer second() {
    String response = getDigitizerResponse();
    if (StringUtils.isNotBlank(response)) {
      digitizerResponse = getResponseValue(response);
    }
    StringBuffer sb = new StringBuffer();
    sb.append(setCoefficients("2"));
    sb.append(setDecimation("2"));
    sb.append(setGain("2", digitizerResponse));
    return sb;
  }

  /**
   * 数采decimation显示
   * 
   * @return
   */
  private StringBuffer setDecimation(String level) {
    StringBuffer sb = new StringBuffer();
    sb.append("Decimation");
    /**
     * 获得结点属性
     */
    Map temp = new HashMap();
    temp.put("Stage_sequence", level);
    temp.put("blockette", "blockette057");
    Map att = getElementValue(digitizerResponse, temp);

    sb.append("B057F03").append("    ").append("Stage sequence number::").append("    ").append(
        level).append(StationConstants.NEW_LINE);
    sb.append("B057F04").append("    ").append("Input sanple rate:").append("    ").append(
        att.get("input_sanple_rate")).append(StationConstants.NEW_LINE);
    sb.append("B057F05").append("    ").append("Decimation factor:").append("    ").append(
        att.get("Decimation_factor")).append(StationConstants.NEW_LINE);
    sb.append("B057F06").append("    ").append("Decimation offset:").append("    ").append(
        att.get("Decimation_offset")).append(StationConstants.NEW_LINE);
    sb.append("B057F07").append("    ").append("Estimated delay (seconds):").append("    ").append(
        att.get("Estimated_delay")).append(StationConstants.NEW_LINE);
    sb.append("B057F08").append("    ").append("Correction applied (seconds):").append("    ")
        .append(att.get("Correction_applied")).append(StationConstants.NEW_LINE);

    return sb;
  }

  /**
   * 获得DigitizerResponse
   */
  private String getDigitizerResponse() {
    Loc loc = new Loc();
    loc.setSchema(schema);
    if (channel != null) {
      loc.setNetCode(channel.get("NET_CODE").toString());
      loc.setStaCode(channel.get("STA_CODE").toString());
    }
    List<Map> locList = queryLoc(loc);
    Map loc_info = null;
    if (locList.size() == 1) {
      loc_info = locList.get(0);
    }
    Digitizer d = new Digitizer();
    d.setSchema(schema);
    if (loc_info != null) {
      d.setInstr_model(channel.get("DIGITIZER_MODEL").toString());
      d.setResp_id(channel.get("DIGITIZER_RESPID").toString());
    }
    List<Map> digitizerList = queryDigitizer(d);
    String response = null;
    if (digitizerList.size() == 1) {
      Map m = digitizerList.get(0);
      if (m.get("RESPONSE") != null) {
        response = m.get("RESPONSE").toString();
      }
    }
    return response;
  }

  /**
   * 第二级别 Coefficients
   * 
   * @param m
   * @param l
   * @return
   */
  private StringBuffer setCoefficients(String level) {
    StringBuffer sb = new StringBuffer();
    sb.append("Response  (Cofficients)");
    /**
     * 获得结点属性
     */
    Map temp = new HashMap();
    temp.put("Stage_sequence", level);
    temp.put("blockette", "blockette054");
    Map att = getElementValue(digitizerResponse, temp);

    sb.append("B053F03").append("    ").append("Transfer function type:").append("    ").append(
        StationConstants.BLOCKETTE053_TYPE.get(att.get("Type"))).append(StationConstants.NEW_LINE);
    sb.append("B053F04").append("    ").append("Stage sequence number:").append("    ").append(
        level).append(StationConstants.NEW_LINE);

    sb.append("B053F05").append("    ").append("Response in units lookup:").append("    ").append(
        att.get("Signal_input_unit")).append(StationConstants.NEW_LINE);

    sb.append("B053F06").append("    ").append("Response out units lookup:").append("    ").append(
        att.get("Signal_output_unit")).append(StationConstants.NEW_LINE);

    temp.put("att", "numerator");
    List numeratorList = getChildCount(digitizerResponse, temp);
    sb.append("B053F07").append("    ").append("Number of numerators :").append("    ").append(
        numeratorList.size()).append(StationConstants.NEW_LINE);

    temp.put("att", "denominator");
    List denominatorList = getChildCount(digitizerResponse, temp);
    sb.append("B053F10").append("    ").append("Number of denominators :").append("    ").append(
        denominatorList.size()).append(StationConstants.NEW_LINE);

    if (numeratorList.size() > 0) {
      sb.append(StationConstants.ROW_START).append("    ").append("Numerator cofficients:").append(
          StationConstants.NEW_LINE);
      sb.append(StationConstants.ROW_START).append("    i,    cofficient,      error").append(
          StationConstants.NEW_LINE);
      for (int j = 0; j < numeratorList.size(); j++) {
        Map map = (HashMap) numeratorList.get(j);
        sb.append("B054F08-09").append("    ").append(j).append("    ").append(map.get("coeff"))
            .append("    ").append(map.get("error")).append(StationConstants.NEW_LINE);
      }
    }
    if (denominatorList.size() > 0) {
      sb.append(StationConstants.ROW_START).append("    ").append("Denominator cofficients:")
          .append(StationConstants.NEW_LINE);
      sb.append(StationConstants.ROW_START).append("    i,    cofficient,      error").append(
          StationConstants.NEW_LINE);

      for (int j = 0; j < denominatorList.size(); j++) {
        Map map = (HashMap) denominatorList.get(j);
        sb.append("B054F11-12").append("    ").append(j).append("    ").append(map.get("coeff"))
            .append("    ").append(map.get("error")).append(StationConstants.NEW_LINE);
      }
    }
    return sb;
  }

  /**
   * 第一级中的Gain
   * 
   * @param m
   * @return
   */
  private StringBuffer setGain(String level, List l) {
    StringBuffer sb = new StringBuffer();
    sb.append("Channel  Gain");

    sb.append("B058F03").append("    ").append("Stage sequence number:").append("    ").append(
        level).append(StationConstants.NEW_LINE);
    Map temp = new HashMap();
    temp.put("Stage_sequence", level);
    temp.put("blockette", "blockette058");
    Map att = getElementValue(l, temp);

    sb.append("B058F04").append("    ").append("Gain:").append("    ").append(
        att.get("Sensitivity")).append(StationConstants.NEW_LINE);
    sb.append("B058F05").append("    ").append("Frequency of gain:").append("    ").append(
        att.get("Freq")).append(StationConstants.NEW_LINE);
    sb.append("B058F06").append("    ").append("Numbet of calibtations:").append("    ")
        .append("0").append(StationConstants.NEW_LINE);
    return sb;
  }

  /**
   * 第一级中的Poles and Zeros
   * 
   * @param m
   * @return
   */
  private StringBuffer setPoleZero() {
    StringBuffer sb = new StringBuffer();
    sb.append(setTitle("Response  (Poles & Zeros)"));
    /**
     * 获得结点属性
     */
    Map temp = new HashMap();
    temp.put("Stage_sequence", "1");
    temp.put("blockette", "blockette053");
    Map att = getElementValue(channelResponse, temp);

    sb.append("B053F03").append("    ").append("Transfer function type:").append("    ").append(
        StationConstants.BLOCKETTE053_TYPE.get(att.get("Type"))).append(StationConstants.NEW_LINE);
    sb.append("B053F04").append("    ").append("Stage sequence number:").append("    ").append("1")
        .append(StationConstants.NEW_LINE);

    sb.append("B053F05").append("    ").append("Response in units lookup:").append("    ").append(
        att.get("Signal_input_unit")).append(StationConstants.NEW_LINE);

    sb.append("B053F06").append("    ").append("Response out units lookup:").append("    ").append(
        att.get("Signal_output_unit")).append(StationConstants.NEW_LINE);

    sb.append("B053F07").append("    ").append("A0 Normalization factor:").append("    ").append(
        att.get("Normalization_factor")).append(StationConstants.NEW_LINE);

    sb.append("B053F08").append("    ").append("Normalization frequency:").append("    ").append(
        att.get("Normalization_frequency")).append(StationConstants.NEW_LINE);

    temp.put("att", "zero");
    List zeroList = getChildCount(channelResponse, temp);
    sb.append("B053F09").append("    ").append("Number of zeros :").append("    ").append(
        zeroList.size()).append(StationConstants.NEW_LINE);

    temp.put("att", "pole");
    List poleList = getChildCount(channelResponse, temp);
    sb.append("B053F14").append("    ").append("Number of peles :").append("    ").append(
        poleList.size()).append(StationConstants.NEW_LINE);

    if (zeroList.size() > 0) {
      sb.append(StationConstants.ROW_START).append("    ").append("Complex zeros:").append(
          StationConstants.NEW_LINE);
      sb.append(StationConstants.ROW_START).append(
          "    i    real      imag     real_error      imag_error").append(
          StationConstants.NEW_LINE);
      for (int j = 0; j < zeroList.size(); j++) {
        Map map = (HashMap) zeroList.get(j);
        sb.append("B053F10-13").append("    ").append(j).append("    ").append(map.get("Real"))
            .append("    ").append(map.get("Imaginary")).append("    ").append(
                map.get("Real_error")).append("    ").append(map.get("Imaginary_error")).append(
                StationConstants.NEW_LINE);
      }
    }
    if (poleList.size() > 0) {
      sb.append(StationConstants.ROW_START).append("    ").append("Complex poles:").append(
          StationConstants.NEW_LINE);
      sb.append(StationConstants.ROW_START).append(
          "    i    real      imag     real_error      imag_error").append(
          StationConstants.NEW_LINE);

      for (int j = 0; j < poleList.size(); j++) {
        Map map = (HashMap) poleList.get(j);
        sb.append("B053F15-18").append("    ").append(j).append("    ").append(map.get("Real"))
            .append("    ").append(map.get("Imaginary")).append("    ").append(
                map.get("Real_error")).append("    ").append(map.get("Imaginary_error")).append(
                StationConstants.NEW_LINE);
      }
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
      sb.append("B050F22").append("    ").append("Start date:").append("  ").append(
          "No Starting Time").append(StationConstants.NEW_LINE);
    } else {
      sb.append("B050F22").append("    ").append("Start date:").append("  ").append(
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
   * 获得某一级别下的子结点的属性值
   * 
   * @param response
   * @param name
   * @return
   */
  public Map getElementValue(List l, Map m) {
    Map map = null;
    for (int i = 0; i < l.size(); i++) {
      Map temp = (HashMap) l.get(i);
      if (temp.get("Stage_sequence").equals(m.get("Stage_sequence"))) {
        List childrenList = (ArrayList) temp.get("childrenList");
        for (int j = 0; j < childrenList.size(); j++) {
          Map child = (HashMap) childrenList.get(j);
          if (child.get("etName").equals(m.get("blockette"))) {
            map = child;
          }
        }
      }
    }
    return map;
  }

  /**
   * 获得某一节点下包含指定的子节点的集合
   */
  public List getChildCount(List l, Map m) {
    List nodeList = new ArrayList();
    for (int i = 0; i < l.size(); i++) {
      Map temp = (HashMap) l.get(i);
      if (temp.get("Stage_sequence").equals(m.get("Stage_sequence"))) {
        List childrenList = (ArrayList) temp.get("childrenList");
        for (int j = 0; j < childrenList.size(); j++) {
          Map child = (HashMap) childrenList.get(j);
          if (child.get("etName").equals(m.get("blockette"))) {
            List list = (ArrayList) child.get("childrenList");
            for (int p = 0; p < list.size(); p++) {
              Map mTemp = (HashMap) list.get(p);
              if (mTemp.get("etName").equals(m.get("att"))) {
                nodeList.add(mTemp);
              }
            }
          }
        }
      }
    }
    return nodeList;
  }

  /**
   * 获得response内容
   * 
   * @param response
   * @return
   */
  public List getResponseValue(String response) {
    List l = new ArrayList();
    // 创建一个新的字符串
    StringReader read = new StringReader(response);
    // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
    InputSource source = new InputSource(read);
    // 创建一个新的SAXBuilder
    SAXBuilder sAXBuilder = new SAXBuilder();
    // 通过输入源构造一个Document
    try {
      Document doc = sAXBuilder.build(source);
      // 取的根元素
      Element root = doc.getRootElement();
      // 得到根元素所有子元素的集合
      List nodeList = root.getChildren();
      Element et = null;
      for (int i = 0; i < nodeList.size(); i++) {
        et = (Element) nodeList.get(i);// 循环依次得到子元素
        Map m = new HashMap();
        m = getChildValue(et, m);
        l.add(m);
      }
    } catch (JDOMException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return l;
  }

  /**
   * 递归查询该接点下的所有内容
   * 
   * @param et
   * @param m
   * @return
   */
  public Map getChildValue(Element et, Map m) {
    List children = new ArrayList();
    List attList = et.getAttributes();
    for (int i = 0; i < attList.size(); i++) {
      Attribute att = (Attribute) attList.get(i);
      m.put(att.getName(), att.getValue());
    }
    List childs = et.getChildren();
    for (int i = 0; i < childs.size(); i++) {
      Map childMap = new HashMap();
      children.add(this.getChildValue((Element) childs.get(i), childMap));
    }
    m.put("etName", et.getName());
    m.put("childrenList", children);
    return m;
  }

  /**
   * 根据查询条件执行测震台站数据查询。
   * 
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  public List<Map> queryStationById(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_STATION_ID, criteria);
  }

  /**
   * 根据查询条件执行通道数据查询。
   * 
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  public List<Map> queryChannel(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_CHANNEL, criteria);
  }

  /**
   * 根据查询条件执行通道数据查询。
   * 
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  private List<Map> queryChannelById(Criteria c) {
    Assert.notNull(c, "Criteria is null.");
    return getTemplate().queryForList(SQL_CHANNEL_ID, c);
  }

  /**
   * 根据查询条件执行数采查询。
   * 
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  private List<Map> queryDigitizer(Digitizer d) {
    return getTemplate().queryForList(SQL_DIGITIZER, d);
  }

  /**
   * 根据查询条件执行位置信息查询。
   * 
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象。
   */
  private List<Map> queryLoc(Loc l) {
    return getTemplate().queryForList(SQL_LOC, l);
  }
}
