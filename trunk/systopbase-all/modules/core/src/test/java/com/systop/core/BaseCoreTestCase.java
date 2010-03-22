package com.systop.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;

/**
 * Dao层测试基类，为基础框架的测试提供独立的测试环境，与应用模块的测试环境分离。
 * 
 * @author samlee
 * 
 */
@ContextConfiguration(locations = { "classpath*:test-applicationContext.xml" })
public class BaseCoreTestCase extends AbstractTransactionalJUnit38SpringContextTests {
  /**
   * Test nothing...
   */
  public void test() {

  }
}
