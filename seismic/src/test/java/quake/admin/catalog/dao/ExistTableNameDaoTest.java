package quake.admin.catalog.dao;

import org.springframework.beans.factory.annotation.Autowired;

import quake.DataType;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试验证表名称是否存在Dao
 * 
 * @author yj
 * 
 */
public class ExistTableNameDaoTest extends BaseTransactionalTestCase {
  @Autowired(required = true)
  private ExistTableNameDao existTableNameDao;
  /**
   * 测试是否存在
   */
  public void testExists() {
    assertTrue(existTableNameDao.exists(DataType.SEISMIC, "catalog_w"));
  }

}
