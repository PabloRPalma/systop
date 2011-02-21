package quake.admin.sitecfg.service;

import quake.admin.sitecfg.SiteConstants;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试初始化网站基本信息，存入静态变量
 * 
 * @author yj
 * 
 */
public class SiteCfgInitTest extends BaseTransactionalTestCase {
  /**
   * 测试初始化网站基本信息，存入静态变量
   */
  public void testInitSiteCfgManager() {
    SiteCfgInit siteCfgInit = new SiteCfgInit();
    siteCfgInit.initSiteCfgManager();
    assertNotNull(SiteConstants.siteCfg);
  }

}
