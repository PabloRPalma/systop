package quake.admin.googlemap.service;

import org.springframework.beans.factory.annotation.Autowired;

import quake.admin.googlemap.model.GoogleMap;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试地图密钥
 * 
 * @author yj
 * 
 */
public class GoogleMapManagerTest extends BaseTransactionalTestCase {
  /**
   * googleMap密钥manager
   */
  @Autowired
  private GoogleMapManager googleMapManager;

  /**
   * 测试获取指定密钥
   */
  public void testGet() {
    assertNotNull(googleMapManager.get());
  }

  /**
   * 测试保存密钥信息
   */
  public void testSave() {
    GoogleMap googleMap = new GoogleMap();
    googleMapManager.save(googleMap);
    assertNotNull(googleMapManager.get());
  }

}
