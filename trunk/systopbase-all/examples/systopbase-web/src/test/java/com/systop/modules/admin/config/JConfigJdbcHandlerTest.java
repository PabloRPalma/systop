package com.systop.modules.admin.config;

import org.jconfig.Configuration;
import org.jconfig.ConfigurationManagerException;
import org.jconfig.parser.ConfigurationParser;
import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * @author Sam Lee
 *
 */
public class JConfigJdbcHandlerTest extends BaseTransactionalTestCase {
  private JConfigJdbcHandler jConfigJdbcHandler;
  
  public JConfigJdbcHandler getJConfigJdbcHandler() {
    return jConfigJdbcHandler;
  }
  
  @Autowired
  public void setJConfigJdbcHandler(JConfigJdbcHandler configJdbcHandler) {
    jConfigJdbcHandler = configJdbcHandler;
  }
  
  /**
   * Test method for {@link JConfigJdbcHandler#load(String)}.
   */
  public void testLoadString() {
    try {
      Configuration cfg = getJConfigJdbcHandler().load("Application");
      cfg.setCategory("TEST");
      cfg.setProperty("X", "Y", "TEST");
      getJConfigJdbcHandler().store(cfg);
      
    } catch (ConfigurationManagerException e) {
      e.printStackTrace();
    }
  }

  /**
   * Test method for {@link JConfigJdbcHandler#load(String, ConfigurationParser)}.
   */
  public void testLoadStringConfigurationParser() {
    //fail("Not yet implemented");
  }

  /**
   * Test method for {@link JConfigJdbcHandler#store(Configuration)}.
   */
  public void testStore() {
    //fail("Not yet implemented");
  }

}
