package quake.seismic.station.dao;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * 导出台站数据DAO
 * 
 * @author yj
 */
@SuppressWarnings("unchecked")
@Repository
public class ExportStationDao extends AbstractStationDao {

  public StringBuffer queryForEqt(Criteria model) {
    StringBuffer sb = new StringBuffer();
    sb.append(StationConstants.ROW_START).append("    ").append(
        "<< IRIS SEED Reader, Release 5.0 >>").append(StationConstants.NEW_LINE);
    sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);
    List<Map> stationList = queryStationById(model);
    if (stationList.size() == 1) {
      Map m = stationList.get(0);
      model.setStaCode(m.get("STA_CODE").toString());
      model.setNetCode(m.get("NET_CODE").toString());
      List<Map> channelList = queryChannel(model);
      for (int i = 0; i < channelList.size(); i++) {
        Map channelMap = channelList.get(i);
        sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);
        sb.append(StationConstants.ROW_START).append("    ").append(
            "======== CHANNEL RESPONSE DATA ========").append(StationConstants.NEW_LINE);
        sb.append("B050F03").append("    ").append("Station:").append("    ").append(
            m.get("STA_CODE")).append(StationConstants.NEW_LINE);
        sb.append("B050F16").append("    ").append("Network:").append("    ").append(
            m.get("NET_CODE")).append(StationConstants.NEW_LINE);
        sb.append("B052F03").append("    ").append("Location:").append("   ").append(
            m.get("SITE_NAME")).append(StationConstants.NEW_LINE);
        sb.append("B050F04").append("    ").append("Channel:").append("    ").append(
            channelMap.get("CHN_CODE")).append(StationConstants.NEW_LINE);
        if (m.get("STA_STARTDATE") == null) {
          sb.append("B050F22").append("    ").append("Start date:").append("  ").append(
              "No Starting Time").append(StationConstants.NEW_LINE);
        } else {
          sb.append("B050F22").append("    ").append("Start date:").append("  ").append(
              m.get("STA_STARTDATE")).append(StationConstants.NEW_LINE);
        }
        if (m.get("STA_ENDDATE") == null) {
          sb.append("B050F23").append("    ").append("End date:").append("   ").append(
              "No Ending Time").append(StationConstants.NEW_LINE);
        } else {
          sb.append("B050F23").append("    ").append("End date:").append("   ").append(
              m.get("STA_ENDDATE")).append(StationConstants.NEW_LINE);
        }
        sb.append(StationConstants.ROW_START).append("    ").append(
            "========================================").append(StationConstants.NEW_LINE);
        if (channelMap.get("RESPONSE") != null) {
          sb.append(StationConstants.ROW_START).append("    ").append("+")
              .append("               ").append("+--------------------------------------------+")
              .append("               ").append("+").append(StationConstants.NEW_LINE);
          sb.append(StationConstants.ROW_START).append("    ").append("+")
              .append("               ").append("|   Response  (Poles & Zeros),   )").append(
                  m.get("STA_CODE")).append(" ch ").append(channelMap.get("CHN_CODE")).append(
                  "   |").append("               ").append("+").append(StationConstants.NEW_LINE);
          sb.append(StationConstants.ROW_START).append("    ").append("+")
              .append("               ").append("+--------------------------------------------+")
              .append("               ").append("+").append(StationConstants.NEW_LINE);
          sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);

          List l = getResponsetValue(channelMap.get("RESPONSE").toString());
          Map temp = new HashMap();
          temp.put("Stage_sequence", "1");
          temp.put("blockette", "blockette053");
          temp.put("att", "Type");

          sb.append("B053F03").append("    ").append("Transfer function type:").append("    ")
              .append(StationConstants.BLOCKETTE053_TYPE.get(getElementValue(l, temp))).append(
                  StationConstants.NEW_LINE);
          sb.append("B053F04").append("    ").append("Stage sequence number:").append("    ")
              .append("1").append(StationConstants.NEW_LINE);

          temp.put("att", "Signal_input_unit");

          sb.append("B053F05").append("    ").append("Response in units lookup:").append("    ")
              .append(getElementValue(l, temp)).append(StationConstants.NEW_LINE);

          temp.put("att", "Signal_output_unit");

          sb.append("B053F05").append("    ").append("Response out units lookup:").append("    ")
              .append(getElementValue(l, temp)).append(StationConstants.NEW_LINE);

          temp.put("att", "Normalization_factor");

          sb.append("B053F05").append("    ").append("A0 Normalization factor:").append("    ")
              .append(getElementValue(l, temp)).append(StationConstants.NEW_LINE);

          temp.put("att", "Normalization_frequency");

          sb.append("B053F05").append("    ").append("Normalization frequency:").append("    ")
              .append(getElementValue(l, temp)).append(StationConstants.NEW_LINE);

          temp.put("att", "zero");
          List zeroList = getChildCount(l, temp);
          sb.append("B053F05").append("    ").append("Normalization frequency:").append("    ")
              .append(zeroList.size()).append(StationConstants.NEW_LINE);

          temp.put("att", "pole");
          List poleList = getChildCount(l, temp);
          sb.append("B053F05").append("    ").append("Normalization frequency:").append("    ")
              .append(poleList.size()).append(StationConstants.NEW_LINE);

          if (zeroList.size() > 0) {
            sb.append(StationConstants.ROW_START).append("    ").append("Complex zeros:").append(
                StationConstants.NEW_LINE);
            sb.append(StationConstants.ROW_START).append(
                "    i    real      imag     real_error      imag_error").append(
                StationConstants.NEW_LINE);
            for (int j = 0; j < zeroList.size(); j++) {
              Map map = (HashMap) zeroList.get(j);
              sb.append("B053F10-13").append("    ").append(j).append("    ").append(
                  map.get("Real")).append("    ").append(map.get("Imaginary")).append("    ")
                  .append(map.get("Real_error")).append("    ").append(map.get("Imaginary_error"))
                  .append(StationConstants.NEW_LINE);
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
              sb.append("B053F15-18").append("    ").append(j).append("    ").append(
                  map.get("Real")).append("    ").append(map.get("Imaginary")).append("    ")
                  .append(map.get("Real_error")).append("    ").append(map.get("Imaginary_error"))
                  .append(StationConstants.NEW_LINE);
            }
          }
          sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);

          sb.append(StationConstants.ROW_START).append("    ").append("+")
              .append("               ").append("+--------------------------------------------+")
              .append("               ").append("+").append(StationConstants.NEW_LINE);
          sb.append(StationConstants.ROW_START).append("    ").append("+")
              .append("               ").append("|   Channel  Gain,   ").append(m.get("STA_CODE"))
              .append(" ch ").append(channelMap.get("CHN_CODE")).append("   |").append(
                  "               ").append("+").append(StationConstants.NEW_LINE);
          sb.append(StationConstants.ROW_START).append("    ").append("+")
              .append("               ").append("+--------------------------------------------+")
              .append("               ").append("+").append(StationConstants.NEW_LINE);
          sb.append(StationConstants.ROW_START).append(StationConstants.NEW_LINE);

          sb.append("B058F03").append("    ").append("Stage sequence number:").append("    ")
              .append("1").append(StationConstants.NEW_LINE);
          sb.append("B058F04").append("    ").append("Gain:").append("    ").append(
              StationConstants.BLOCKETTE053_TYPE.get(getElementValue(l, temp))).append(
              StationConstants.NEW_LINE);

        }

      }
    }

    return sb;
  }

  /**
   * 获得用户需要的某个值
   * 
   * @param response
   * @param name
   * @return
   */
  public String getElementValue(List l, Map m) {
    String name = null;
    for (int i = 0; i < l.size(); i++) {
      Map temp = (HashMap) l.get(i);
      if (temp.get("Stage_sequence").equals(m.get("Stage_sequence"))) {
        List childrenList = (ArrayList) temp.get("childrenList");
        for (int j = 0; j < childrenList.size(); j++) {
          Map child = (HashMap) childrenList.get(j);
          if (child.get("etName").equals(m.get("blockette"))) {
            if (child.get(m.get("att")) != null) {
              name = child.get(m.get("att")).toString();
            }
          }
        }
      }
    }
    return name;
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
  public List getResponsetValue(String response) {
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
}
