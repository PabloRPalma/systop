package quake.seismic.station.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.seismic.station.model.Criteria;
import quake.seismic.station.model.Digitizer;
import quake.seismic.station.model.Loc;
import quake.seismic.station.util.JdomUtil;

/**
 * 导出台站数据xmlDAO
 * 
 * @author yj
 */
@Repository
@SuppressWarnings("unchecked")
public class ExportXmlDao extends AbstractStationDao {
  /**
   * 台站
   */
  private Map station = null;

  private String schema;

  private Element response;

  private List<Map> channelList;

  private Map loc_info;

  /**
   * 通道下的response
   */
  private List channelResponse;
  /**
   * 数采下的response
   */
  private List digitizerResponse;

  /**
   * 获得台站
   */
  public void getSta(String id) {
    Criteria c = new Criteria();
    c.setId(id);
    c.setSchema(schema);
    List<Map> stationList = queryStationById(c);
    if (stationList.size() == 1) {
      station = stationList.get(0);
    }
  }

  /**
   * 导出xml
   */
  public String queryForXml(String id, String staSchema) {
    schema = staSchema;
    getSta(id);
    getChannelList();
    setResponse();
    setChannel();
    return setDoc();
  }

  /**
   * 添加channel节点
   */
  private void setChannel() {
    for (int i = 0; i < channelList.size(); i++) {
      Map c = channelList.get(i);
      Element channel = new Element("channel");
      setChnAtt(c, channel);
      setFirstAndZero(c, channel);
      setSecond(c, channel);
      response.addContent(channel);
    }
  }

  /**
   * 第二三四级
   * 
   * @param c
   * @param channel
   */
  private void setSecond(Map c, Element channel) {
    String response = getDigitizerResponses();
    if (StringUtils.isNotBlank(response)) {
      digitizerResponse = JdomUtil.getResponseValue(response);
    }

    for (int i = 2; i < 5; i++) {
      Element stage = new Element("stage");
      stage.setAttribute("Stage_sequence", String.valueOf(i));

      setBlock054(i, stage);
      setBlock057(i, stage);
      setBlock058(i, digitizerResponse, stage);

      channel.addContent(stage);
    }
  }

  /**
   * 设置2，3，4级别中的Block057
   * 
   * @param i
   * @param stage
   */
  private void setBlock057(int i, Element stage) {
    Element blockette057 = new Element("blockette057");
    Map temp = new HashMap();
    temp.put("Stage_sequence", String.valueOf(i));
    temp.put("blockette", "blockette057");
    Map att = JdomUtil.getElementValue(digitizerResponse, temp);
    blockette057.setAttribute("Input_sample_rate", getValue(att, "Input_sample_rate"));
    blockette057.setAttribute("Decimation_factor", getValue(att, "Decimation_factor"));
    blockette057.setAttribute("Decimation_offset", getValue(att, "Decimation_offset"));
    blockette057.setAttribute("Estimated_delay", getValue(att, "Estimated_delay"));
    blockette057.setAttribute("Correction_applied", getValue(att, "Correction_applied"));

    stage.addContent(blockette057);
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

  /**
   * 设置2，3，4级别中的Block054
   * 
   * @param i
   * @param stage
   */
  private void setBlock054(int i, Element stage) {
    Element blockette054 = new Element("blockette054");
    /**
     * 获得结点属性
     */
    Map temp = new HashMap();
    temp.put("Stage_sequence", String.valueOf(i));
    temp.put("blockette", "blockette054");
    Map att = JdomUtil.getElementValue(digitizerResponse, temp);
    blockette054.setAttribute("Type", getValue(att, "Type"));
    blockette054.setAttribute("Signal_input_unit", getValue(att, "Signal_input_unit"));
    blockette054.setAttribute("Signal_output_unit", getValue(att, "Signal_output_unit"));

    temp.put("att", "numerator");
    List numeratorList = JdomUtil.getChildCount(digitizerResponse, temp);
    temp.put("att", "denominator");
    List denominatorList = JdomUtil.getChildCount(digitizerResponse, temp);
    blockette054.setAttribute("numerators_num", String.valueOf(numeratorList.size()));
    blockette054.setAttribute("denominators_num", String.valueOf(denominatorList.size()));

    if (numeratorList.size() > 0) {
      for (int x = 0; x < numeratorList.size(); x++) {
        Map map = (HashMap) numeratorList.get(x);
        Element numerator = new Element("numerator");
        numerator.setAttribute("coeff", getValue(map, "coeff"));
        numerator.setAttribute("error", getValue(map, "error"));
        blockette054.addContent(numerator);
      }
    }
    if (denominatorList.size() > 0) {
      for (int x = 0; x < denominatorList.size(); x++) {
        Map map = (HashMap) denominatorList.get(x);
        Element denominator = new Element("denominator");
        denominator.setAttribute("coeff", getValue(map, "coeff"));
        denominator.setAttribute("error", getValue(map, "error"));
        blockette054.addContent(denominator);
      }
    }
    stage.addContent(blockette054);
  }

  /**
   * 获得DigitizerResponse
   */
  private String getDigitizerResponses() {
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
   * 设置第一级别,0级别
   */
  private void setFirstAndZero(Map c, Element ce) {
    if (c != null && c.get("RESPONSE") != null) {
      channelResponse = JdomUtil.getResponseValue(c.get("RESPONSE").toString());
      for (int i = 0; i < 2; i++) {
        Element stage = new Element("stage");
        stage.setAttribute("Stage_sequence", String.valueOf(i));

        if (i == 0) {
          setBlock058(0, channelResponse, stage);
        }
        if (i == 1) {
          setBlock053(stage);
          setBlock058(1, channelResponse, stage);
        }
        ce.addContent(stage);
      }
    }
  }

  /**
   * 设置第一级别中Block053
   * 
   * @param stage
   */
  private void setBlock053(Element stage) {
    Map t = new HashMap();
    Element blockette053 = new Element("blockette053");
    t.put("Stage_sequence", "1");
    t.put("blockette", "blockette053");
    Map att = JdomUtil.getElementValue(channelResponse, t);

    setBlock053Att(att, blockette053);

    t.put("att", "zero");
    List zeroList = JdomUtil.getChildCount(channelResponse, t);
    if (zeroList.size() > 0) {
      t.put("list", zeroList);
      t.put("name", "zero");
      setZeroOrPole(t, blockette053);
    }
    t.put("att", "pole");
    List poleList = JdomUtil.getChildCount(channelResponse, t);
    if (poleList.size() > 0) {
      t.put("list", poleList);
      t.put("name", "pole");
      setZeroOrPole(t, blockette053);
    }
    stage.addContent(blockette053);
  }

  /**
   * zero pole 节点使用
   * 
   * @param t
   * @param blockette053
   */
  private void setZeroOrPole(Map t, Element blockette053) {
    for (int i = 0; i < ((ArrayList) t.get("list")).size(); i++) {
      Map map = (HashMap) ((ArrayList) t.get("list")).get(i);
      Element e = new Element(t.get("name").toString());

      e.setAttribute("Imaginary", getValue(map, "Imaginary"));
      e.setAttribute("Imaginary_error", getValue(map, "Imaginary_error"));
      e.setAttribute("Real", getValue(map, "Real"));
      e.setAttribute("Real_error", getValue(map, "Real_error"));

      blockette053.addContent(e);
    }
  }

  /**
   * 设置第一级别blockette053属性
   * 
   * @param att
   * @param blockette053
   */
  private void setBlock053Att(Map att, Element blockette053) {
    blockette053.setAttribute("Type", getValue(att, "Type"));
    blockette053.setAttribute("Signal_input_unit", getValue(att, "Signal_input_unit"));
    blockette053.setAttribute("Signal_output_unit", getValue(att, "Signal_output_unit"));
    blockette053.setAttribute("Normalization_factor", getValue(att, "Normalization_factor"));
    blockette053.setAttribute("Normalization_frequency", getValue(att, "Normalization_frequency"));
  }

  /**
   * 第0，1，2，3，4级别通用
   * 
   * @param level
   * @param l
   * @param ce
   */
  private void setBlock058(int level, List l, Element ce) {
    Map t = new HashMap();
    Element blockette058 = new Element("blockette058");
    t.put("Stage_sequence", String.valueOf(level));
    t.put("blockette", "blockette058");
    Map att = JdomUtil.getElementValue(l, t);

    blockette058.setAttribute("Sensitivity", getValue(att, "Sensitivity"));
    blockette058.setAttribute("Freq", getValue(att, "Freq"));

    ce.addContent(blockette058);
  }

  /**
   * channel节点属性
   */
  private void setChnAtt(Map c, Element ce) {
    Loc loc = new Loc();
    loc.setSchema(schema);
    if (c != null) {
      loc.setNetCode(c.get("NET_CODE").toString());
      loc.setStaCode(c.get("STA_CODE").toString());
      loc.setLoc_id(c.get("LOC_ID").toString());
    }
    List<Map> locList = queryLoc(loc);
    if (locList.size() == 1) {
      loc_info = locList.get(0);
    }
    ce.setAttribute("Chn_code", c.get("CHN_CODE").toString());
    ce.setAttribute("Loc_id", c.get("LOC_ID").toString());
    ce.setAttribute("Sensor_model", loc_info.get("SENSOR_MODEL").toString());
    ce.setAttribute("Sensor_RespID", loc_info.get("SENSOR_RESPID").toString());
    ce.setAttribute("Digitizer_model", loc_info.get("DIGITIZER_MODEL").toString());
    ce.setAttribute("Digitizer_RespID", loc_info.get("DIGITIZER_RESPID").toString());
  }

  /**
   * document中添加内容，设置xml格式
   */
  private String setDoc() {
    Document d = new Document(response);
    Format format = Format.getCompactFormat();
    format.setIndent("     ");// 设置缩进
    XMLOutputter out = new XMLOutputter(format);
    // 获得XML字符串形式
    String xmlStr = out.outputString(d);
    return xmlStr;
  }

  /**
   * xml 设置根节点
   */
  private void setResponse() {
    response = new Element("response");
    response.setAttribute("Net_code", station.get("NET_CODE").toString());
    response.setAttribute("Sta_code", station.get("STA_CODE").toString());
    response.setAttribute("Chn_num", String.valueOf(channelList.size()));

  }

  /**
   * 获得该台站下的所有通道
   */
  private void getChannelList() {
    Criteria model = new Criteria();
    model.setSchema(schema);
    if (station != null) {
      model.setNetCode(station.get("NET_CODE").toString());
      model.setStaCode(station.get("STA_CODE").toString());
    }
    channelList = queryChannel(model);
  }

  /**
   * 根据查询条件执行测震台站数据查询。
   */
  public List<Map> queryStationById(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_STATION_ID, criteria);
  }

  /**
   * 根据查询条件执行通道数据查询。
   */
  public List<Map> queryChannel(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    return getTemplate().queryForList(SQL_CHANNEL, criteria);
  }

  /**
   * 根据查询条件执行位置信息查询。
   */
  private List<Map> queryLoc(Loc l) {
    return getTemplate().queryForList(SQL_LOC, l);
  }

  /**
   * 根据查询条件执行数采查询。
   */
  private List<Map> queryDigitizer(Digitizer d) {
    return getTemplate().queryForList(SQL_DIGITIZER, d);
  }
}
