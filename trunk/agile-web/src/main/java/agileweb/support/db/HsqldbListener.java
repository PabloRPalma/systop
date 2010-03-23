package agileweb.support.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

/**
 * 随Web应用程序启动hsqldb，随web应用关闭而关闭hsqldb
 * @author Sam
 * 
 */
public class HsqldbListener implements ServletContextListener {
  /**
   * Logger of the class
   */
  private static Logger log = LoggerFactory.getLogger(HsqldbListener.class);

  /**
   * 缺省的hsqldb数据库位置
   */
  private final static String DEFAULT_DB_PATH = "{user.home}/database";

  /**
   * 缺省的classpath下的测试数据库
   */
  private final static String DEFAULT_DB_SOURCE_SCRIPT = 
    "/hsqldb/db.script";

  /**
   * 缺省的classpath下的测试数据库属性文件
   */
  private final static String DEFAULT_DB_SOURCE_PROPERTIES = 
    "/hsqldb/db.properties";

  /**
   * 启动数据库的时间ms
   */
  private final static int STARTUP_WAITING = 800;

  /**
   * 数据库名称
   */
  private String dbName;

  /**
   * 数据库服务端口号
   */
  private int port = -1;

  /**
   * Context销毁
   */
  public final void contextDestroyed(ServletContextEvent event) {
    Connection conn = null;
    String url = "jdbc:hsqldb:hsql://localhost:" + port + "/" + dbName;
    try {
      log.debug("hsqldb shutdown...");

      Class.forName("org.hsqldb.jdbcDriver");
      conn = DriverManager.getConnection(url, "sa", "");
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("SHUTDOWN;");
    } catch (Exception e) {
      log.debug("hsqldb shut down failed." + e.getMessage());
    }
  }

  /**
   * Context创建
   */
  public final void contextInitialized(ServletContextEvent event) {
    dbName = event.getServletContext().getInitParameter("hsql.dbName");
    try {
      port = Integer.parseInt(event.getServletContext().getInitParameter(
          "hsql.port"));
    } catch (Exception e) {
      log.error("you must provide a valid hsqldb port.");
    }

    String path = getDbPath(event);

    if (!path.endsWith("/")) {
      path = path + "/";
    }
    if (log.isDebugEnabled()) {
      log.debug("hsql path: " + path);
      log.debug("hsql db: " + dbName);
      log.debug("hsql prot: " + port);
    }
    // 恢复数据库
    restoreDatabase(path, dbName);
    // Start hsqldb
    startServer(path, dbName, port);
  }

  /**
   * 恢复数据库，如果当前运行目录下没有数据库，则复制classpath下的测试数据库到运行目录下
   * @param path 运行目录
   * @param databaseName 数据库名称
   */
  private void restoreDatabase(String path, String databaseName) {
    File scriptFile = new File(path + databaseName + ".script");
    File propertiesFile = new File(path + databaseName + ".properties");
    if (!scriptFile.exists()) {
      // 建立运行时数据库目录
      File dbDir = new File(path);
      if (!dbDir.exists()) {
        log.info("Create Path:" + path);
        if (!dbDir.mkdirs()) {
          log.error("Can not create DB Dir for Hsql:" + dbDir);
          return;
        }
      }
      // 复制测试数据库
      try {
        FileCopyUtils.copy(this.getClass().getResourceAsStream(
            DEFAULT_DB_SOURCE_SCRIPT), new FileOutputStream(scriptFile));
        FileCopyUtils
            .copy(this.getClass().getResourceAsStream(
                DEFAULT_DB_SOURCE_PROPERTIES), new FileOutputStream(
                propertiesFile));
      } catch (IOException e) {
        log.error("Copy Default script file error");
        return;
      }
    }
  }

  /**
   * Start hsqldb
   * @param dbPath db path
   * @param databaseName db name
   * @param serverPort db server port
   */
  private void startServer(String dbPath, String databaseName, int serverPort) {
    Server server = new Server();
    server.setDatabaseName(0, databaseName);
    server.setDatabasePath(0, dbPath + databaseName);
    if (serverPort != -1) {
      server.setPort(serverPort);
    }

    server.setSilent(true);
    server.start();
    log.info("hsqldb started...");
    // 等待Server启动
    try {
      Thread.sleep(STARTUP_WAITING);
    } catch (InterruptedException e) {
      // do nothing
    }
  }

  /**
   * 读取context-param参数，获取数据库文件的位置
   */
  private String getDbPath(ServletContextEvent event) {
    String path = event.getServletContext().getInitParameter("hsql.dbPath");

    if (StringUtils.isEmpty(path)) {
      path = DEFAULT_DB_PATH;
    }
    // 如果数据库位于java启动目录下
    if (path.startsWith("{user.home}")) {
      path = path.replaceFirst("\\{user.home\\}", System.getProperty(
          "user.home").replace('\\', '/'));
    }
    // 如果数据库位于web应用程序root目录下
    if (path.startsWith("{webapp.root}")) {
      path = path.replaceFirst("\\{webapp.root\\}", event.getServletContext()
          .getRealPath("/").replace('\\', '/'));
    }
    return path;
  }

}
