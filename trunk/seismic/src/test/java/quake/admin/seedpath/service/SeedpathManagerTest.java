package quake.admin.seedpath.service;

import org.springframework.beans.factory.annotation.Autowired;

import quake.admin.seedpath.model.Seedpath;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试波形文件存储路径及seed程序配置文件管理类
 * 
 * @author yj
 * 
 */
public class SeedpathManagerTest extends BaseTransactionalTestCase {
  /**
   * <code>Seedpath</code>管理类
   */
  @Autowired
  private SeedpathManager seedpathManager;

  /**
   * 测试返回数据库中存储的波形文件存储路径信息
   */
  public void testGet() {
    assertNotNull(seedpathManager.get());
  }
  /**
   * 测试保存或者更新路径
   * 
   */
  public void testSave() {
    Seedpath seedpath=new Seedpath();
    seedpathManager.save(seedpath);
    assertNotNull(seedpathManager.get());
  }
/**
 * 测试修改seed目录下的dir配置文件
 */
  public void testInitConfigFile() {
    seedpathManager.initConfigFile("/home/yj/seed/", "root", "root");
  }
}
