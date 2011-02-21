package quake.admin.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;

import quake.admin.catalog.model.QuakeCatalog;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试管理地震目录service
 * 
 * @author yj
 * 
 */
public class QuakeCatalogManagerTest extends BaseTransactionalTestCase {
  @Autowired(required = true)
  private QuakeCatalogManager quakeCatalogManager;
  private QuakeCatalog model = new QuakeCatalog();

  /**
   * 为测试做准备操作
   */
  @Override
  protected void setUp() throws Exception {
    model.setClcName("test");
    model.setCltName("catalog_x");
    model.setMagTname("mag_w");
    model.setPhaseTname("phase_w");
  }

  /**
   * 测试保存地震目录
   */
  public void testSaveQuakeCatalog() {
    quakeCatalogManager.save(model);
    assertNotNull(quakeCatalogManager.queryByCltName("catalog_x"));
  }

  /**
   * 测试根据地震目录表名查询地震目录信息
   */
  public void testQueryByCltName() {
    assertNotNull(quakeCatalogManager.queryByCltName("catalog_w"));
  }

  /**
   * 测试得到所有地震目录，用于数据服务的显示
   * 
   */
  public void testGetCat() {
    assertTrue(quakeCatalogManager.getCat().size() > 0);
  }

  /**
   * 测试得到有震相表关联的地震目录列表，用于数据服务显示
   */
  public void testGetPhaseCat() {
    assertTrue(quakeCatalogManager.getPhaseCat().size() > 0);
  }

  /**
   * 测试得到有事件波形的地震目录，用于数据服务的显示
   * 
   */
  public void testGetSeedCat() {
    assertTrue(quakeCatalogManager.getSeedCat().size() > 0);
  }

  /**
   * 测试删除
   */
  public void testRemoveQuakeCatalog() {
    quakeCatalogManager.save(model);
    quakeCatalogManager.remove(model);
    assertNull(quakeCatalogManager.queryByCltName("catalog_x"));
  }

  public void testIsDefined() {
    assertTrue(quakeCatalogManager.isDefined());
  }
}
