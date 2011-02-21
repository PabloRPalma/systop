package quake.admin.sitecfg.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试网站配置管理
 * 
 * @author yj
 * 
 */
public class SiteCfgManagerTest extends BaseTransactionalTestCase {
  @Autowired
  private SiteCfgManager siteCfgManager;

  /**
   * 测试得到数据库中唯一网站配置model
   */
  public void testGetCmsConfig() {
    assertNotNull(siteCfgManager.getCmsConfig());
  }

  public void testIsDefined() {
    assertTrue(siteCfgManager.isDefined());
  }

}
