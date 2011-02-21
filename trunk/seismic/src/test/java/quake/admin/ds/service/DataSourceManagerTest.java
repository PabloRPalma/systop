package quake.admin.ds.service;

import org.springframework.beans.factory.annotation.Autowired;

import quake.DataType;
import quake.admin.ds.model.DataSourceInfo;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试数据源管理
 * 
 * @author yj
 * 
 */
public class DataSourceManagerTest extends BaseTransactionalTestCase {
  @Autowired
  private DataSourceManager dataSourceManager;
  private DataType dataType;

  /**
   * 测试根据给出的类型，创建一个<code>DataSource</code>的实例。
   */
  public void testGetDataType() {
    assertNull(dataSourceManager.get(DataType.SEISMIC));
  }

  /**
   * 测试根据数据类型（测震，前兆），如果数据源是Oracle，则返回<code>true</code>
   */
  public void testIsOracle() {
    assertTrue(dataSourceManager.isOracle(DataType.SEISMIC));
  }

  /**
   * 测试返回数据库中存储的测震和前兆数据源信息
   */
  public void testGet() {
    assertNull(dataSourceManager.get());
  }

  /**
   * 测试保存或者更新数据源
   */
  public void testSave() {
    DataSourceInfo dsInfo = new DataSourceInfo();
    dataSourceManager.save(dsInfo);
    assertNotNull(dataSourceManager.get());
  }

  /**
   * 测试返回测震SCHEMA
   */
  public void testGetSeismicSchema() {
    System.out.println(dataSourceManager.getSeismicSchema());
  }

  /**
   * 测试返回前兆SCHEMA
   */
  public void testGetStationSchema() {
    System.out.println(dataSourceManager.getStationSchema());
  }

}
