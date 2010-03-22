package com.systop.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;

import junit.framework.TestCase;

public class RequestUtilTest extends TestCase {
  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Test method for {@link RequestUtil#isJsonRequest(HttpServletRequest)}.
   */
  public void testIsJsonRequest() {
    MockHttpServletRequest req = null;
    req = getMockHttpServletRequest();
    
    assertFalse(RequestUtil.isJsonRequest(req));
    
    req.addHeader("Accept", "text/x-json;charset=UTF-8");
    assertTrue(RequestUtil.isJsonRequest(req));
    
    req = getMockHttpServletRequest();
    req.addHeader("Accept", "charset=UTF-8");
    assertFalse(RequestUtil.isJsonRequest(req));
    
    req = getMockHttpServletRequest();
    req.addHeader("Accept", "application/x-json;text/x-json;charset=UTF-8");
    assertTrue(RequestUtil.isJsonRequest(req));
 
    req = getMockHttpServletRequest();
    req.addParameter("Accept", "x-json");
    assertTrue(RequestUtil.isJsonRequest(req));
  }
  
  private MockHttpServletRequest getMockHttpServletRequest() {
    return (MockHttpServletRequest)WebMockUtil.getHttpServletRequest();
  }
}
