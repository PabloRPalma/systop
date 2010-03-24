package datashare.admin.ds.service;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.systop.core.ApplicationException;
import com.systop.core.Constants;
import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.util.ResourceBundleUtil;

import datashare.DataType;
import datashare.GlobalConstants;
import datashare.admin.ds.model.DataSourceInfo;
import datashare.base.service.Definable;

@SuppressWarnings("unchecked")
@Service
public class DataSourceManager implements Definable {
  private static Logger logger = LoggerFactory.getLogger(DataSourceManager.class);
  private BaseHibernateDao baseHibernateDao;
  /**
   * 数据源信息标识，如果此标识改变，则需要重建DataSource
   */
  private String dsString;

  private static Map<DataType, DataSource> dataSources = new HashMap<DataType, DataSource>();
  /**
   * 缺省前兆SCHEMA
   */
  public static final String DEFAULT_QZ_SCHEMA = "QZDATA";
  /**
   * 缺省测震SCHEMA
   */
  public static final String DEFAULT_CZ_SCHEMA = "CZDATA";
  /**
   * 数据源表主键（为了配合Hibernate）
   */
  public static final String PK = "datasource";
  /**
   * 数据库类型——测震MySQL数据库
   */
  public static final String DB_CZ_MYSQL = "cz_mysql";
  /**
   * 数据库类型——测震Oracle数据库
   */
  public static final String DB_CZ_ORACLE = "cz_oracle";
  /**
   * 数据库类型——前兆Oracle数据库
   */
  public static final String DB_QZ_ORACLE = "qz_oracle";

  /**
   * 连接池属性,初始连接数量
   */
  public static final int INITIAL_POOL_SIZE = 10;
  /**
   * 连接池属性,最大连接数量
   */
  public static final int MAX_POOL_SIZE = 30;
  /**
   * 连接池属性,测试有效连接的时间（sec）
   */
  public static final int TEST_PERIOD = 60;
  /**
   * 600秒未使用的连接将被丢弃
   */
  public static final int MAX_IDLE_TIME = 600;
  /**
   * 测震数据源，用“台站参数表”作为测试表
   */
  public static final String PREFERRED_TEST_QUERY_CZ = "Station_info";
  /**
   * 前兆数据源，用“ALL_TABLES”作为测试表，
   */
  public static final String PREFERRED_TEST_QUERY_QZ = "SELECT 0 FROM ALL_TABLES";

  private Map<String, String> dbTypes = new HashMap(10);
  /**
   * 驱动程序Map
   */
  private Map<String, String> drivers = new HashMap(10);
  /**
   * URL Map
   */
  private Map<String, String> urls = new HashMap(10);

  /**
   * 使用{@ link ReentrantReadWriteLock}提高并发性能
   */
  protected final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  protected final Lock read = readWriteLock.readLock();
  protected final Lock write = readWriteLock.writeLock();

  /**
   * 根据给出的类型，创建一个<code>DataSource</code>的实例。
   * 
   * @param dataType 数据源类型,取值为{@link GlobalConstants#DATA_CZ}或{@link GlobalConstants#DATA_QZ}
   * @return instance of <code>DataSource</code>
   * @see {@link GlobalConstants#DATA_CZ}
   * @see {@link GlobalConstants#DATA_QZ}
   */
  @Transactional(readOnly = true)
  public DataSource get(DataType dataType) {
    read.lock();
    try {
      if (!isDefined()) {
        throw new ApplicationException("测震或前兆数据源未定义！");
      }
      return dataSources.get(dataType);
    } finally {
      read.unlock();
    }
  }

  /**
   * 根据数据类型（测震，前兆），如果数据源是Oracle，则返回<code>true</code>
   * 
   * @param dataType 数据类型（测震，前兆）
   * @return <code>true</code> if the target database is Oracle.
   */
  public boolean isOracle(DataType dataType) {
    if (DataType.SIGN.equals(dataType)) {
      return true;
    }

    ComboPooledDataSource ds = (ComboPooledDataSource) this.get(dataType);

    String driver = ds.getDriverClass();
    logger.debug("Jdbc Driver ：{}", driver);
    if (StringUtils.isNotBlank(driver)) {
      return "oracle.jdbc.driver.OracleDriver".equals(driver);
    }

    return false;
  }

  /**
   * 返回数据库中存储的测震和前兆数据源信息
   */
  @Transactional(readOnly = true)
  public DataSourceInfo get() {
    return baseHibernateDao.get(DataSourceInfo.class, PK);
  }

  /**
   * 保存或者更新数据源
   * 
   * @param dsInfo
   * @throws ApplicationException 如果数据源定义错误
   */
  @Transactional
  public void save(DataSourceInfo dsInfo) {
    Assert.notNull(dsInfo);
    if (StringUtils.isBlank(dsInfo.getId())) {
      dsInfo.setId(PK);
    }
    if (get() != null) {
      baseHibernateDao.merge(dsInfo);
    } else {
      baseHibernateDao.save(dsInfo);
    }

    rebuild(dsInfo); // 重建数据源（如果需要）
  }

  /**
   * 根据<code>DataSourceInfo</code>重建数据源
   * 
   * @param dsInfo
   */
  protected void rebuild(DataSourceInfo dsInfo) {
    if (!StringUtils.equals(dsInfo.toString(), dsString)) { // 如果数据源信息改变
      logger.info("正在重建数据源...");
      write.lock();
      try {
        dsString = dsInfo.toString();
        // 构建测震数据源
        dataSources.put(DataType.SEISMIC, closeAndRebuild(dsInfo, DataType.SEISMIC));
        // 构建前兆数据源
        dataSources.put(DataType.SIGN, closeAndRebuild(dsInfo, DataType.SIGN));
      } finally {
        write.unlock();
      }
      logger.info("数据源成功建立!");
    } else {
      logger.info("前兆和测震数据源都没有改变，不要随便乱点按钮。"); 
    }
  }

  /**
   * 在初始化完毕后，建立数据源（如果有<code>DataSourceInfo</code>信息）
   */
  @PostConstruct
  public void init() {
    urls.put(DB_CZ_MYSQL, "jdbc:mysql://"); // localhost/db
    urls.put(DB_CZ_ORACLE, "jdbc:oracle:thin:@"); // localhost:1521:orcl
    urls.put(DB_QZ_ORACLE, "jdbc:oracle:thin:@");

    drivers.put(DB_CZ_MYSQL, "com.mysql.jdbc.Driver");
    drivers.put(DB_CZ_ORACLE, "oracle.jdbc.driver.OracleDriver");
    drivers.put(DB_QZ_ORACLE, "oracle.jdbc.driver.OracleDriver");

    dbTypes.put(DB_CZ_ORACLE, "Oracle数据库");
    dbTypes.put(DB_CZ_MYSQL, "MySQL数据库");
    // dbTypes.put(DB_QZ_ORACLE, "前兆Oracle数据库");

    try {
      DataSourceInfo dsInfo = get();
      if (dsInfo != null) {
        rebuild(dsInfo);
      }
    } catch (Exception e) { // 可能是数据源尚未配置
      logger.warn(e.getMessage());
    }
  }

  /**
   * 关闭现有数据源（如果有）并且根据新的属性重建数据源
   * 
   * @param dsInfo 数据源信息
   */
  private DataSource closeAndRebuild(DataSourceInfo dsInfo, DataType dataType) {
    // 根据数据类型（CZ/QZ）和数据库类型（MySQL，ORACLE）得到Driver
    String driver = drivers.get((dataType.equals(DataType.SEISMIC)) ? dsInfo.getCzType()
        : DB_QZ_ORACLE);
    if(driver != null) {
      driver = driver.trim();
    }
    // 构建URL
    String url = buildUrl(dsInfo, dataType);
    String user = (dataType.equals(DataType.SEISMIC)) ? dsInfo.getCzUser() : dsInfo.getQzUser();
    String password = (dataType.equals(DataType.SEISMIC)) ? dsInfo.getCzPwd() : dsInfo.getQzPwd();
    logger.debug("Driver:{}\n Url:{}\n User:{}\n Password:{}", new String[] { driver, url, user,
        password });
    //检查是否能够连接数据库
    isAvailable(driver, url, user, password, dataType);
    
    // 如果数据源已经存在，则先释放
    if (dataSources.containsKey(dataType)) {
      ComboPooledDataSource ds = (ComboPooledDataSource) dataSources.get(dataType);
      ds.resetPoolManager(true);
      ds.close();
      ds = null;
      dataSources.remove(dataType);
    }

    final ComboPooledDataSource ds = new ComboPooledDataSource();
    try {
      // 创建ComboPooledDataSource
      ds.setDriverClass(driver.trim());
      ds.setJdbcUrl(url.trim());
      ds.setUser(user.trim());
      ds.setPassword(password.trim());
      // 从常量类中取得连接池其他信息
      ds.setInitialPoolSize(ResourceBundleUtil.getInt(Constants.RESOURCE_BUNDLE,
          "ds.pool.initialPoolSize", INITIAL_POOL_SIZE));
      ds.setMaxPoolSize(ResourceBundleUtil.getInt(Constants.RESOURCE_BUNDLE,
          "ds.pool.maxPoolSize", MAX_POOL_SIZE));
      // 设置数据库有效性检查属性
      ds.setIdleConnectionTestPeriod(300);
      if (isOracle(dsInfo, dataType)) {
        ds.setPreferredTestQuery("select 0 from ALL_TABLES");
      } else {
        ds.setPreferredTestQuery("show tables");
      }
      // ds.setMaxIdleTime(MAX_IDLE_TIME);

    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }
    
    return ds;
  }

  private boolean isOracle(DataSourceInfo dsInfo, DataType dataType) {
    if (DataType.SIGN.equals(dataType)) {
      return true;
    } else if (DB_CZ_MYSQL.equals(dsInfo.getCzType())) {
      return false;
    }
    return true;
  }

  /**
   * 检查数据源是否可用,如果不可用，则抛出ApplicationException
   * @deprecated use {@link #isAvailable(String, String, String, String, DataType)}
   */
  
  @SuppressWarnings("unused")
  private void isAvailable(DataSource ds, DataType dataType) throws ApplicationException {
    try {
      String dbName = ds.getConnection().getMetaData().getDatabaseProductName();
      logger.debug(dbName);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ApplicationException(((DataType.SEISMIC.equals(dataType)) ? "测震" : "前兆")
          + "数据源信息错误，请检查各项属性.");
    }
  }

  private void isAvailable(String driver, String url, String user, String password, DataType dataType) {
    String msg = ((DataType.SEISMIC.equals(dataType)) ? "测震" : "前兆") + "数据源信息错误，请检查各项属性.";
    
    Connection conn = null;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);      
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new ApplicationException(msg + "(无法加载驱动程序)");
    } catch (LinkageError e) {
      e.printStackTrace();
      throw new ApplicationException(msg + "(无法加载驱动程序,连接错误.)");
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ApplicationException(msg + "(" + e.getMessage() + ")");
    } finally {
      if(conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 根据不同的数据库类型构建URL,区分MySQL和Oracle
   */
  private String buildUrl(DataSourceInfo dsInfo, DataType dataType) {
    // MySQL和Oracle的URL分隔符是不同的...
    String splitter = ":";
    if (DataType.SEISMIC.equals(dataType) && StringUtils.equals(dsInfo.getCzType(), DB_CZ_MYSQL)) {
      splitter = "/";
    }
    // 根据数据类型（CZ/QZ）和数据库类型（MySQL，ORACLE）得到URL前缀
    String prefix = urls.get((dataType.equals(DataType.SEISMIC)) ? dsInfo.getCzType()
        : DB_QZ_ORACLE);
    // 根据数据类型（CZ/QZ）到User\Port\Instance
    String host = (dataType.equals(DataType.SEISMIC)) ? dsInfo.getCzHost() : dsInfo.getQzHost();
    String port = (dataType.equals(DataType.SEISMIC)) ? dsInfo.getCzPort() : dsInfo.getQzPort();
    String ins = (dataType.equals(DataType.SEISMIC)) ? dsInfo.getCzInstance() : dsInfo
        .getQzInstance();
    // 合成URL
    String url = new StringBuffer(50).append(prefix).append(host).append(":").append(port).append(
        splitter).append(ins).toString();
    return url;
  }

  /**
   * 如果测震和前兆数据源都已经建立，则返回<code>true</code>
   */
  @Override
  public boolean isDefined() {
    return dataSources.size() == DataType.values().length;
  }

  public Map getCzTypes() {
    return this.dbTypes;
  }

  /**
   * 返回前兆SCHEMA
   */
  public String getQzSchema() {
    if (isDefined()) {
      String schema = get().getQzSchema();
      return (StringUtils.isBlank(schema)) ? DEFAULT_QZ_SCHEMA : schema;
    }

    return null;
  }

  /**
   * 返回测震SCHEMA
   */
  public String getCzSchema() {
    if (isDefined()) {
      String schema = get().getCzSchema();
      return (StringUtils.isBlank(schema)) ? DEFAULT_CZ_SCHEMA : schema;
    }

    return null;
  }

  /**
   * @param baseHibernateDao the baseHibernateDao to set
   */
  @Autowired(required = true)
  public void setBaseHibernateDao(BaseHibernateDao baseHibernateDao) {
    this.baseHibernateDao = baseHibernateDao;
  }
}
