package com.systop.common.test;

import java.io.FileNotFoundException;

import org.springframework.test.
  AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.util.Log4jConfigurer;

/**
 * 测试基类. 通过继承AbstractTransactionalDataSourceSpringContextTests，
 * 使得测试执行之后，事务自动回滚。
 * <p>如果数据需要保存到数据库,在每一个测试中调用 <code>setComplete()</code>即可.
 * 如果将"defaultRollback"设置为"false"，也可以达到同样效果，这个属性的缺省值为"false"
 * </p>
 * 可以做到Open Session in Test ，解决Hibernate的lazy-load问题；而且接管原来的DAO里
 * 的事务控制定义，通过setDefaultRollback(boolean)方法控制最后回滚还是提交，
 * 如果默认为回滚，则测试产生数据变动不会影响数据库内数据。

 * @ see AbstractTransactionalDataSourceSpringContextTests
 * @author Sam
 */
public class BaseTestCase extends
    AbstractTransactionalDataSourceSpringContextTests {
  /**
   * 缺省测试次数。
   */
  protected static final int DEFAULT_TIMES = 10;
  /**
   * 测试中经常需要重复测试多个用例，（例如：测试的时候创建几个被测试对象）
   * times变量可以作为一个常量用作上述情况。
   */
  protected static int times = DEFAULT_TIMES;
  
  /**
   * Context配置文件的位置，子类直接修改这个属性，以加载特定的Context文件
   */
  protected String []locations = { "classpath*:context/*.xml",
    "classpath*:context/test/*.xml" };
  
  /**
   * 默认构造器，初始化log4j
   */
  public BaseTestCase() {
    try {
      Log4jConfigurer.initLogging("classpath:log4j.properties");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * applicaitonContext配置文件 默认为context/*.xml
   * 如果子函数需要限定载入的applicatonContext.xml,重载此函数
   * @see org.springframework.test.
   * AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
   */
  public final String[] getConfigLocations() {
    return locations;
  }

  /**
   * 子类可以覆盖这个方法以实现在事务创建之前执行某些操作。
   * @see {@link super{@link #onSetUpBeforeTransaction()}}
   */
  protected void onSetUpBeforeTransaction() throws Exception {
  }

}
