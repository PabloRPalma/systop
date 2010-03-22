package com.systop.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import junit.framework.TestCase;

public class StringUtilTest extends TestCase {
  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Test method for {@link StringUtil#unqualify(String)}.
   */
  public void testUnqualify() {
    assertEquals("StringUtilTest", StringUtil
        .unqualify("com.systop.common.core.util.StringUtilTest"));
  }

  /**
   * Test method for {@link StringUtil#qualifier(String)}.
   */
  public void testQualifier() {
    assertEquals("com.systop.core.util", StringUtil
        .qualifier("com.systop.core.util.StringUtilTest"));
    assertEquals("com.systop.core.util.StringUtilTest", StringUtil
        .qualify("com.systop.core.util", "StringUtilTest"));
    assertNotNull(StringUtil.qualify("com.systop.core.util",
        new String[] { "StringUtilTest", "StringUtil" }));
    assertNotNull(StringUtil.qualify(null, new String[] { "StringUtilTest",
        "StringUtil" }));
  }

  /**
   * Test method for {@link StringUtil#stripTags(String)}.
   */
  public void testStripTags() {
    String srcStr = "<node name=\"UserRecording\" x=\"538\" y=\"7\">";
    String str = "&lt;node name=\"UserRecording\" x=\"538\" y=\"7\"&gt;";
    assertEquals("", StringUtil.stripTags(""));
    assertEquals(str, StringUtil.stripTags(srcStr));
  }

  /**
   * Test method for {@link StringUtil#parseCommaSplitedString(String)}.
   */
  @SuppressWarnings("unchecked")
  public void testParseCommaSplitedString() {
    String str = "  2 ,j  ,a,c,dd  ";
    Set set = StringUtil.parseCommaSplitedString(str);
    Collection col = new ArrayList<String>();
    col.add("2");
    col.add("j");
    col.add("a");
    col.add("c");
    col.add("dd");

    assertTrue(set.containsAll(col));

  }

  /**
   * Test method for {@link StringUtil#zeroPadding(Integer, int)}.
   */
  public void testZeroPadding() {
    assertEquals("0555", StringUtil.zeroPadding(555, 4));
    assertEquals("0000555", StringUtil.zeroPadding(555, 7));
    assertEquals("0000", StringUtil.zeroPadding(null, 4));
  }

  /**
   * Test method for {@link StringUtil#getNumFromSerial(String)}.
   */
  public void testNumFromSerial() {
    assertEquals(Integer.valueOf(98), StringUtil.getNumFromSerial("000098"));
    assertEquals(Integer.valueOf(98), StringUtil.getNumFromSerial("98"));
    assertNull(StringUtil.getNumFromSerial(""));
    assertNull(StringUtil.getNumFromSerial("asdfsadf"));
  }

  /**
   * Test method for {@link StringUtil#parseSetter(String)}.
   */
  public void testParseSetter() {
    assertEquals("setAw", StringUtil.parseSetter("Aw"));
    assertEquals("setAw", StringUtil.parseSetter("aw"));
    assertEquals("setAW", StringUtil.parseSetter("aW"));
    assertEquals("setAw", StringUtil.parseSetter("aw"));
    assertEquals("setAw", StringUtil.parseSetter("_aw"));
    assertEquals("setAw", StringUtil.parseSetter("-aw"));
  }

  /**
   * Test method for {@link StringUtil#parseGetter(String)}.
   */
  public void testParseGetter() {
    assertEquals("getAw", StringUtil.parseGetter("Aw"));
    assertEquals("getAw", StringUtil.parseGetter("aw"));
    assertEquals("getAW", StringUtil.parseGetter("aW"));
    assertEquals("getAw", StringUtil.parseGetter("aw"));
    assertEquals("getAw", StringUtil.parseGetter("_aw"));
    assertEquals("getAw", StringUtil.parseGetter("-aw"));
  }

  /**
   * Test method for {@link StringUtil#isGetter(String)}.
   */
  public void testIsGetter() {
    assertTrue(StringUtil.isGetter("getAw"));
    assertTrue(StringUtil.isGetter("getAW"));
    assertFalse(StringUtil.isGetter("GetAw"));
  }

  /**
   * Test method for {@link StringUtil#isSetter(String)}.
   */
  public void testIsSetter() {
    assertTrue(StringUtil.isSetter("setAw"));
    assertTrue(StringUtil.isSetter("setAW"));
    assertFalse(StringUtil.isSetter("SetAw"));
  }

  /**
   * Test method for {@link StringUtil#accessor2Property(String)}.
   */
  public void testAccessor2Property() {
    assertEquals("aw", StringUtil.accessor2Property("setAw"));
    assertEquals("aW", StringUtil.accessor2Property("setAW"));
    assertEquals("aw", StringUtil.accessor2Property("getAw"));
    assertEquals("aW", StringUtil.accessor2Property("getAW"));
  }
  
  /**
   * Test method for {@link StringUtil#parseProperty(String)}.
   */
  public void testParseProperty() {
    assertEquals("userName", StringUtil.parseProperty("user_name"));
    assertEquals("username", StringUtil.parseProperty("username"));
    assertEquals("userName", StringUtil.parseProperty("user-name"));
    assertEquals("userNameShort", StringUtil.parseProperty("user-name_short"));
  }
}
