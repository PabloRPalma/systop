package com.systop.core.util;

import junit.framework.TestCase;

/**
 * @author Sam Lee
 *
 */
public class PropertyUtilTest extends TestCase {

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Test method for {@link PropertyUtil#parseSetter(String)}.
   */
  public void testParseSetter() {
    assertEquals("setA", PropertyUtil.parseSetter("setA"));
    assertEquals("setA", PropertyUtil.parseSetter("A"));
    assertEquals("setA", PropertyUtil.parseSetter("a"));
    assertEquals("setLoginId", PropertyUtil.parseSetter("loginId"));
    assertEquals("setLoginId", PropertyUtil.parseSetter("login_id"));
    assertEquals("setLoginId", PropertyUtil.parseSetter("LOGIN_ID"));
    assertEquals("", PropertyUtil.parseSetter(null));
  }

  /**
   * Test method for {@link PropertyUtil#parseGetter(String)}.
   */
  public void testParseGetter() {
    assertEquals("getA", PropertyUtil.parseGetter("getA"));
    assertEquals("getA", PropertyUtil.parseGetter("A"));
    assertEquals("getA", PropertyUtil.parseGetter("a"));
    assertEquals("getLoginId", PropertyUtil.parseGetter("loginId"));
    assertEquals("getLoginId", PropertyUtil.parseGetter("login_id"));
    assertEquals("getLoginId", PropertyUtil.parseGetter("LOGIN_ID"));
  }

  /**
   * Test method for {@link PropertyUtil#isSetter(String)}.
   */
  public void testIsSetter() {
    assertTrue(PropertyUtil.isSetter("setA"));
    assertFalse(PropertyUtil.isSetter("setb"));
    assertFalse(PropertyUtil.isSetter("c"));
    assertFalse(PropertyUtil.isSetter(null));
  }

  /**
   * Test method for {@link PropertyUtil#isGetter(String)}.
   */
  public void testIsGetter() {
    assertTrue(PropertyUtil.isGetter("getA"));
    assertFalse(PropertyUtil.isGetter("getb"));
    assertFalse(PropertyUtil.isGetter("c"));
    assertFalse(PropertyUtil.isGetter(null));
  }

  /**
   * Test method for {@link PropertyUtil#accessor2Property(String)}.
   */
  public void testAccessor2Property() {
    assertEquals("loginId", PropertyUtil.accessor2Property("getLoginId"));
    assertEquals("loginId", PropertyUtil.accessor2Property("setLoginId"));
    assertEquals("loginId", PropertyUtil.accessor2Property("getLogin_Id"));
    assertEquals("loginId", PropertyUtil.accessor2Property("loginId"));
  }

  /**
   * Test method for {@link PropertyUtil#parseProperty(String)}.
   */
  public void testParseProperty() {
    assertEquals("username", PropertyUtil.parseProperty("username"));
    assertEquals("userName", PropertyUtil.parseProperty("user_name"));
    assertEquals("userName", PropertyUtil.parseProperty("USER_NAME"));
    assertEquals("userName", PropertyUtil.parseProperty("user_Name"));
    assertEquals("uSERNAME", PropertyUtil.parseProperty("USERNAME"));
  }

}
