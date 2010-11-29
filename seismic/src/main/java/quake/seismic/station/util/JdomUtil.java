package quake.seismic.station.util;

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
import org.xml.sax.InputSource;

public final class JdomUtil {
  /**
   * cannot be instantiated
   * 
   */
  private JdomUtil() {
  }

  /**
   * 获得某一级别下的子结点的属性值
   */
  @SuppressWarnings("unchecked")
  public static Map getElementValue(List l, Map m) {
    Map map = null;
    for (int i = 0; i < l.size(); i++) {
      Map temp = (HashMap) l.get(i);
      if (temp.get("etName").equals("stage")) {
        if (temp.get("Stage_sequence").equals(m.get("Stage_sequence"))) {
          List childrenList = (ArrayList) temp.get("childrenList");
          for (int j = 0; j < childrenList.size(); j++) {
            Map child = (HashMap) childrenList.get(j);
            if (child.get("etName").equals(m.get("blockette"))) {
              map = child;
            }
          }
        }
      } else {
        map = getElementValue((ArrayList) temp.get("childrenList"), m);
      }
    }
    return map;
  }

  /**
   * 获得某一节点下包含指定的子节点的集合
   */
  @SuppressWarnings("unchecked")
  public static List getChildCount(List l, Map m) {
    List nodeList = new ArrayList();
    for (int i = 0; i < l.size(); i++) {
      Map temp = (HashMap) l.get(i);
      if (temp.get("etName").equals("stage")) {
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
      } else {
        nodeList = getChildCount((ArrayList) temp.get("childrenList"), m);
      }
    }
    return nodeList;
  }

  /**
   * 获得response内容
   */
  @SuppressWarnings("unchecked")
  public static List getResponseValue(String response) {
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
   */
  @SuppressWarnings("unchecked")
  public static Map getChildValue(Element et, Map m) {
    List children = new ArrayList();
    List attList = et.getAttributes();
    for (int i = 0; i < attList.size(); i++) {
      Attribute att = (Attribute) attList.get(i);
      m.put(att.getName(), att.getValue());
    }
    List childs = et.getChildren();
    for (int i = 0; i < childs.size(); i++) {
      Map childMap = new HashMap();
      children.add(getChildValue((Element) childs.get(i), childMap));
    }
    m.put("etName", et.getName());
    m.put("childrenList", children);
    return m;
  }
}
