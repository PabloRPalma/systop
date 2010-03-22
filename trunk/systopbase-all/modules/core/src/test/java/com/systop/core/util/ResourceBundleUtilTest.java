package com.systop.core.util;

import java.util.ResourceBundle;

import org.springframework.util.ClassUtils;

import junit.framework.TestCase;

public class ResourceBundleUtilTest extends TestCase {

  private ResourceBundle rb = null;

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    rb = ResourceBundle.getBundle(ClassUtils.getPackageName(getClass())
        + ".ResourceBundleUtilTest");
  }
  
  /**
   * Test method for {@link ResourceBundleUtil#getInt(ResourceBundle, String, int)}.
   */
  public void testGetInt() {
    assertEquals(25, ResourceBundleUtil.getInt(rb, "mail.smtp.port", 100));
    assertEquals(100, ResourceBundleUtil.getInt(rb, "aaaaajjjj", 100));
    assertEquals(100, ResourceBundleUtil.getInt(rb, "mail.smtp.host", 100));
    assertEquals(100, ResourceBundleUtil.getInt(null, "mail.smtp.host", 100));
    assertEquals(100, ResourceBundleUtil.getInt(null, null, 100));
  }
  
  /**
   * Test method for {@link ResourceBundleUtil#getString(ResourceBundle, String, String)}.
   */
  public void testGetString() {
    assertEquals("smtp.163.com", ResourceBundleUtil.getString(rb, "mail.smtp.host", "smtp.163.com"));
    assertEquals("smtp.126.com", ResourceBundleUtil.getString(rb, "aaaaajjjj", "smtp.126.com"));
    assertEquals("smtp.126.com", ResourceBundleUtil.getString(rb, "mail.smtp.por", "smtp.126.com"));
    assertEquals("smtp.126.com", ResourceBundleUtil.getString(null, "mail.smtp.por", "smtp.126.com"));
    assertEquals("smtp.126.com", ResourceBundleUtil.getString(null, null, "smtp.126.com"));
  }
  
  /**
   * Test method for {@link ResourceBundleUtil#getBoolean(ResourceBundle, String, String)}.
   */
  public void testGetBoolean() {
    assertEquals(true, ResourceBundleUtil.getBoolean(rb, "mail.smtp.auth", false));
    assertEquals(false, ResourceBundleUtil.getBoolean(rb, "aaaaajjjj", false));
    assertEquals(false, ResourceBundleUtil.getBoolean(rb, "mail.smtp.por", false));
    assertEquals(false, ResourceBundleUtil.getBoolean(null, "mail.smtp.por", false));
    assertEquals(false, ResourceBundleUtil.getBoolean(null, null, false));
  }
}
