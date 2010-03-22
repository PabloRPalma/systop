package com.systop.core.util;

import junit.framework.TestCase;

import com.systop.core.dao.testmodel.TestUser;
import com.systop.core.service.SampleManager;

public class GenericsUtilTest extends TestCase {
  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Test method for {@link GenericsUtil#getGenericClass(Class, int)}.
   */
  public void testGetGenericClass() {
    SampleManager test = new SampleManager();
    assertEquals("TestUser", GenericsUtil.getGenericClass(test.getClass())
        .getSimpleName());
    
    assertNull(GenericsUtil.getGenericClass(
        test.getClass(), 10));
    assertNull(GenericsUtil.getGenericClass(new TestUser().getClass()));
  }
}
