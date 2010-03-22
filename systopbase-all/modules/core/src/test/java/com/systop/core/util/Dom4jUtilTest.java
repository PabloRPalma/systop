package com.systop.core.util;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jUtilTest extends TestCase {
  /***/
  private Element intElement = null;

  /***/
  private Element strElement = null;

  /***/
  private Element bolElement = null;

  /**
   * @see junit.framework.TestCase#setUp()
   */
 
  protected void setUp() throws Exception {
    super.setUp();
    String testFilePath = Dom4jUtilTest.class.getResource("")
        + "Dom4jUtilTest.xml";
    SAXReader saxReader = Dom4jUtil.createSAXReader(testFilePath);

    Document doc = saxReader.read(testFilePath);
    
    intElement = doc.getRootElement().element("int");
    strElement = doc.getRootElement().element("string");
    bolElement = doc.getRootElement().element("boolean");
  }

  /**
   * Test method for {@link Dom4jUtil#getInt(Element, String, int)}.
   */
  public void testGetInt() {
    assertEquals(18, Dom4jUtil.getInt(intElement, "attr", 0));
    assertEquals(0, Dom4jUtil.getInt(intElement, "notattr", 0));
    assertEquals(0, Dom4jUtil.getInt(strElement, "attr", 0));
    assertEquals(0, Dom4jUtil.getInt(strElement, "notattr", 0));

    assertEquals(188, Dom4jUtil.getInt(intElement, 0));
    assertEquals(0, Dom4jUtil.getInt(strElement, 0));
  }

  /**
   * Test method for {@link Dom4jUtil#getString(Element, String, String)}.
   */
  public void testGetString() {
    assertEquals("zhangsan", Dom4jUtil.getString(strElement, "attr", "lisi"));
    assertEquals("lisi", Dom4jUtil.getString(strElement, "notattr", "lisi"));
    assertEquals("18", Dom4jUtil.getString(intElement, "attr", "lisi"));
    assertEquals("lisi", Dom4jUtil.getString(intElement, "notattr", "lisi"));

    assertEquals("张三", Dom4jUtil.getString(strElement, "李四"));
    assertEquals("188", Dom4jUtil.getString(intElement, "李四"));
  }

  /**
   * Test method for {@link Dom4jUtil#getBoolean(Element, String, boolean)}.
   */
  public void testGetBoolean() {
    assertEquals(false, Dom4jUtil.getBoolean(bolElement, "attr", true));
    assertEquals(true, Dom4jUtil.getBoolean(bolElement, "notattr", true));
    assertEquals(true, Dom4jUtil.getBoolean(intElement, "attr", true));
    assertEquals(true, Dom4jUtil.getBoolean(intElement, "notattr", true));

    assertEquals(true, Dom4jUtil.getBoolean(bolElement, false));
    assertEquals(false, Dom4jUtil.getBoolean(intElement, false));
  }  
}
