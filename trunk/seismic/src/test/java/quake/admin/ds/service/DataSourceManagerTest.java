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
  private DataSourceInfo dsInfo = new DataSourceInfo();

  /**
   * 准备工作
   */
  @Override
  protected void setUp() throws Exception {
    dsInfo.setId("test");
    dsInfo.setCzHost("127.0.0.1");
    dsInfo.setCzInstance("seismic");
    dsInfo.setCzPort("3306");
    dsInfo.setCzPwd("root");
    dsInfo.setCzSchema("seismic");
    dsInfo.setCzType("cz_mysql");
    dsInfo.setCzUser("root");
    dsInfo.setQzHost("127.0.0.1");
    dsInfo.setQzInstance("seispara");
    dsInfo.setQzPort("3306");
    dsInfo.setQzPwd("root");
    dsInfo.setQzSchema("seispara");
    dsInfo.setQzUser("root");
  }

  /**
   * 测试根据给出的类型，创建一个<code>DataSource</code>的实例。
   */
  public void testGetDataType() {
    System.out.println(dataSourceManager.get(DataType.SEISMIC));
  }

  /**
   * 测试根据数据类型（测震，前兆），如果数据源是Oracle，则返回<code>true</code>
   */
  public void testIsOracle() {
    System.out.println(dataSourceManager.isOracle(DataType.SEISMIC));
  }

  /**
   * 测试返回数据库中存储的测震和前兆数据源信息
   */
  public void testGet() {
    System.out.println(dataSourceManager.get());
  }

  /**
   * 测试保存或者更新数据源
   */
  public void testSave() {
    dataSourceManager.save(dsInfo);
    System.out.println(dataSourceManager.get());
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

  /**
   * 测试根据<code>DataSourceInfo</code>重建数据源
   */
  public void testRebuild() {
    dataSourceManager.save(dsInfo);
    dataSourceManager.rebuild(dsInfo);
    System.out.println(dataSourceManager.get(DataType.SEISMIC));
  }

  /**
   * 测试在初始化完毕后，建立数据源（如果有<code>DataSourceInfo</code>信息）
   */
  public void testInit() {
    dataSourceManager.init();
    System.out.println(dataSourceManager.get(DataType.SEISMIC));
  }

  /**
   * 测试如果测震和前兆数据源都已经建立，则返回<code>true</code>
   */
  public void testIsDefined() {
    assertTrue(dataSourceManager.isDefined());
  }

  public void testGetCzTypes() {
    assertTrue(dataSourceManager.getCzTypes().size() > 0);
  }
}
