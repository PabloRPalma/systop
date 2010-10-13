package datashare.admin.czcatalog.dao;

import org.springframework.beans.factory.annotation.Autowired;

import quake.DataType;
import quake.admin.czcatalog.dao.ExistTableNameDao;

import datashare.base.test.BaseTestCase;

/**
 * 验证表名是否存在测试
 * @author wbb
 */
public class ExistTableNameDaoTest extends BaseTestCase {
  @Autowired(required = true)
  private ExistTableNameDao validateTableNameDao;
  
  public void testExistTableName() {
    boolean bln = validateTableNameDao.exists(DataType.SEISMIC, "CATALOG_C");
    logger.info("表名 'CATALOG_C' 在测震目录是否存在：" + bln);
  }
}
