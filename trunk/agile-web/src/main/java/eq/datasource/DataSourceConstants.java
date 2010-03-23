package eq.datasource;

import java.util.HashMap;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * DataSource 常量类
 * @author Sam
 *
 */
@SuppressWarnings("unchecked")
public final class DataSourceConstants {
  public static final String DB_MYSQL = "mysql";
  public static final String DB_ORACLE = "oralce";
  public static final String DB_HSQLDB = "hsqldb";
  
  
  public static final Map<String, String> DB_TYPES = new HashMap(10);
  static {
    DB_TYPES.put(DB_ORACLE, "Oracle");
    DB_TYPES.put(DB_MYSQL, "MySQL");
    DB_TYPES.put(DB_HSQLDB, "hsqldb");
  }
  
  /**
   * 驱动程序Map
   */
  public static final Map<String, String> DRIVERS = Collections.synchronizedMap(new HashMap(10));
  static {
    DRIVERS.put(DB_MYSQL, "com.mysql.jdbc.Driver");
    DRIVERS.put(DB_ORACLE, "oracle.jdbc.driver.JdbcDriver");
    DRIVERS.put(DB_HSQLDB, "org.hsqldb.jdbcDriver");
  }
  
  /**
   * URL Map
   */
  public static final Map URLS = Collections.synchronizedMap(new HashMap(10));
  static {
    URLS.put(DB_MYSQL, "jdbc:mysql://localhost/db");
    URLS.put(DB_ORACLE, "jdbc:oracle:thin:@localhost:1521:orcl");
    URLS.put(DB_HSQLDB, "jdbc:hsqldb:hsql://localhost");
  }
  
  public static final int INITIAL_POOL_SIZE = 20;
  public static final int MAX_POOL_SIZE = 50;
  public static final int TEST_PERIOD = 60;
  public static final String TEST_TABLE = "c3p0_test_table";
  
  private DataSourceConstants() {
  }
}
