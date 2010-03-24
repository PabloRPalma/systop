package datashare.datainit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基本数据初始化
 * 
 * @author Lunch
 */
@Service
public class DataShareInit {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired(required = true)
  private JdbcTemplate jdbcTemplate;

  /**
   * 待执行的Sql文件资源
   */
  private Resource[] sqlResources;

  /**
   * 本类被实例化后默认执行的方法
   */
  @PostConstruct
  @Transactional
  public void init() {
    if (this.sqlResources == null) {
      logger.debug("数据初始化未指定sql文件。");
      return;
    }
    // 文件排序，防止外键异常
    orderSqlResources();
    String tableName = null;
    for (Resource r : sqlResources) {
      tableName = getTableName(r.getFilename());
      if (enableInit(tableName)) {
        executeSqlResource(r);
      }
    }
  }

  /**
   * 将执行的sql文件排序，如果有method_subject.sql则将其放置在数组最后一个。防止因外键不存在抛异常
   */
  private void orderSqlResources() {
    String tName = null;
    for (int i = 0; i < sqlResources.length; i++) {
      tName = getTableName(sqlResources[i].getFilename());
      if ("method_subject".equals(tName)) {
        if (i != sqlResources.length - 1) {
          Resource r = sqlResources[sqlResources.length - 1];
          sqlResources[sqlResources.length - 1] = sqlResources[i];
          sqlResources[i] = r;
          break;
        }
      }
    }
  }

  /**
   * 根据sql文件名解析初始化数据的表名称
   * 
   * @param sqlFileName
   * @return
   */
  private String getTableName(String sqlFileName) {
    if (sqlFileName.indexOf(".") > 0) {
      sqlFileName = sqlFileName.substring(0, sqlFileName.indexOf("."));
    }
    return sqlFileName;
  }

  /**
   * 判断指定的表名是否可以初始化
   * 
   * @param tableName
   * @return
   */
  private boolean enableInit(String tableName) {
    boolean flg = true;
    try {
      int count = jdbcTemplate.queryForInt("select count(0) from " + tableName);
      if (count > 0) {// 表中已经存在数据，禁止初始化操作
        logger.debug(tableName + "中已经存在数据，未执行初始化操作。");
        flg = false;
      }
    } catch (Exception e) {// 查询异常，可能是tableName对应的表不存在。
      logger.error(e.getMessage());
      logger.error("数据库中不存在此表:" + tableName);
      flg = false;
    }
    return flg;
  }

  /**
   * 执行指定的sql文件
   * 
   * @param sqlFile
   * @throws DataAccessException
   * @throws IOException
   */
  private void executeSqlResource(Resource r) {
    File sqlFile = null;
    FileInputStream in = null;
    BufferedReader reader = null;
    try {
      sqlFile = r.getFile();
      in = new FileInputStream(sqlFile);
      reader = new BufferedReader((new InputStreamReader(in, "UTF-8")));
      String line = null;
      while ((line = reader.readLine()) != null) {
        jdbcTemplate.execute(line);
      }
      logger.debug(r.getFilename() + "中的数据导入完毕。");
    } catch (IOException e) {
      logger.error(e.getMessage());
      logger.error("文件：" + r.getFilename() + "不存在，请检查配置文件是否正确。");
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * @return the sqlResources
   */
  public Resource[] getSqlResources() {
    return sqlResources;
  }

  /**
   * @param sqlResources the sqlResources to set
   */
  public void setSqlResources(Resource[] sqlResources) {
    this.sqlResources = sqlResources;
  }

}
