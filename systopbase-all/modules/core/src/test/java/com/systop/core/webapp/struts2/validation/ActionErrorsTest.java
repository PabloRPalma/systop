package com.systop.core.webapp.struts2.validation;

import junit.framework.TestCase;

import com.systop.core.dao.testmodel.TestUser;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

/**
 * @author Sam
 *
 */
public class ActionErrorsTest extends TestCase {
  private ActionErrors er;
  /**
   * @see junit.framework.TestCase#setUp()
   */
  @SuppressWarnings("unchecked")
  protected void setUp() throws Exception {
    er = new ActionErrors();
    er.setObjectName(TestUser.class.getName());
    er.setStrutsXml("classpath:test-struts.xml");
    er.setAction(new DefaultCrudAction());
  }

  /**
   * Test method for {@link ActionErrors#reject(String, bject[], String)}.
   * Test method for {@link ActionErrors#rejectValue(String, String, Object[], String)}.
   */
  public void testReject() {
    er.reject("", null, "ABC");
    er.reject("testMsg", new String[]{"靠"}, "");
    er.reject("testMsg");
    er.reject("testMsg", "靠！@！！");
    er.rejectValue("name", "testMsg", new String[]{"靠"}, "");
    er.rejectDirectly("{0}", "耶");
    er.rejectFieldErrorDirectly("", "{0}", "耶");
    er.rejectDirectly("ccc", (Object[]) null);
    try {
      er.rejectDirectly(null, (Object[]) null);
    } catch (Exception e) {
      
    }
  }
}
