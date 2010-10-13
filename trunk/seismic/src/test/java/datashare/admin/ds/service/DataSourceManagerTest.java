package datashare.admin.ds.service;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import quake.DataType;
import quake.admin.ds.model.DataSourceInfo;
import quake.admin.ds.service.DataSourceManager;

import datashare.base.test.BaseTestCase;
/**
 * DataSourceManager测试类
 * @author Sam
 *
 */
public class DataSourceManagerTest extends BaseTestCase {
  @Autowired
  private DataSourceManager manager;
  
  public void testGet() throws SQLException {
    manager.save(createDataSourceInfo());
    DataSource ds = manager.get(DataType.SEISMIC);
    assertNotNull(ds);
    System.out.println(ds.getConnection().getMetaData().getURL());
  }

  public void testSave() {
    manager.save(createDataSourceInfo());
  }
  
  private DataSourceInfo createDataSourceInfo() {
    DataSourceInfo dsi = new DataSourceInfo();
    dsi.setCzType(DataSourceManager.DB_CZ_MYSQL);
    dsi.setCzHost("localhost");
    dsi.setCzInstance("eq");
    dsi.setCzPort("3306");
    dsi.setCzUser("root");
    dsi.setCzPwd("love125");
    
    dsi.setQzHost("192.168.1.140");
    dsi.setQzInstance("orcl");
    dsi.setQzPort("1521");
    dsi.setQzUser("ersmis");
    dsi.setQzPwd("ersmis");
    return dsi;
  }
  
  public void testIsOracle() {
    assertTrue(manager.isOracle(DataType.SIGN));
    assertFalse(manager.isOracle(DataType.SEISMIC));
  }

  public void testIsDefined() {
    
  }

}
