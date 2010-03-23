package eq.datasource.factory;

import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import eq.datasource.DataSourceConstants;
import eq.datasource.DataSourceInfo;

@Service
@SuppressWarnings("unchecked")
public class DataSourceFactory {
  private static Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
  /**
   * 用于查询系统自带的数据库，从中得到其他数据源的信息
   */
  private JdbcTemplate jdbcTemplate;

  private static Map<String, DataSource> dataSources = new HashMap();
  
  /**
   * 使用{@ link ReentrantReadWriteLock}提高并发性能
   */
  protected final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  protected final Lock read = readWriteLock.readLock();
  protected final Lock write = readWriteLock.writeLock();

  /**
   * @param jdbcTemplate the jdbcTemplate to set
   */
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * 从<code>dataSources</code>中取得数据源，本方法是非线程安全的。
   * 
   * @param type 数据库类型
   * @see {@link DataSourceConstants}
   * @return {@link DataSource}
   */
  @Transactional(readOnly = true)
  public DataSource getDataSource(String type) {
    // 如果数据源已经建立，则直接返回数据源
    read.lock();
    try {
      if (dataSources.containsKey(type)) {
        logger.debug("使用现有数据源");
        return dataSources.get(type);
      }
    } finally {
      read.unlock();
    }

    return buildDataSource(type);
  }

  /**
   * 线程安全的重建数据源
   */
  private DataSource buildDataSource(String type) {
    write.lock();
    try{
      // 如果数据源已经建立，则直接返回数据源
      if (dataSources.containsKey(type)) {
        return dataSources.get(type);
      }
      // 从数据库中读取数据源信息 ，建立数据源。
      logger.debug("建立新的数据源");
      Map dsInfo = getDsInfo(type);
      if (CollectionUtils.isEmpty(dsInfo)) {
        throw new RuntimeException("数据源尚未设置,请首先设置数据源!");
      }
      final ComboPooledDataSource ds = new ComboPooledDataSource();
      try {
        // 从常量类中取得driver
        ds.setDriverClass(DataSourceConstants.DRIVERS.get(dsInfo.get("type")));
        ds.setJdbcUrl((String) dsInfo.get("url"));
        ds.setUser((String) dsInfo.get("username"));
        ds.setPassword((String) dsInfo.get("password"));
        // 从常量类中取得连接池其他信息
        ds.setInitialPoolSize(DataSourceConstants.INITIAL_POOL_SIZE);
        ds.setMaxPoolSize(DataSourceConstants.MAX_POOL_SIZE);
        ds.setIdleConnectionTestPeriod(DataSourceConstants.TEST_PERIOD);
        ds.setAutomaticTestTable(DataSourceConstants.TEST_TABLE);
      } catch (PropertyVetoException e) {
        e.printStackTrace();
      }

      dataSources.put(type, ds);
      return ds;
    } finally {
      write.unlock();
    }
  }
  /**
   * 创建新的数据源信息
   */
  @Transactional
  public void createDataSourceInfo(final DataSourceInfo dsi) {
    if(dsi == null || StringUtils.isBlank(dsi.getType())
        ||StringUtils.isBlank(dsi.getUrl())
        ||StringUtils.isBlank(dsi.getUsername())) {
      logger.error("必须指明数据库信息(DataSourceInfo = null)");
      throw new IllegalArgumentException("参数DataSourceInfo为null,或其某个属性为null.");
    }
    //如果数据类型已经存在，则调用更新方法
    if(!CollectionUtils.isEmpty(getDsInfo(dsi.getType()))) {
      updateDataSourceInfo(dsi);
    }
    logger.equals("新建数据源");
    jdbcTemplate.execute("INSERT INTO data_source (type, url, username, password) VALUES (?,?,?,?)", new PreparedStatementCallback() {
      public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
          DataAccessException {
        ps.setString(1, dsi.getType());
        ps.setString(2, dsi.getUrl());
        ps.setString(3, dsi.getUsername());
        ps.setString(4, dsi.getPassword());
        ps.executeUpdate();
        return null;
      }
      
    });
    removeDs(dsi.getType());
  }
  /**
   * 因为指定类型的数据源已经重建，所以需要将内存中的数据源清空。
   */
  private void removeDs(String type) {
    ComboPooledDataSource ds = null;
    read.lock();
    try {
      ds = (ComboPooledDataSource) dataSources.get(type);
      if(ds == null) {
        return;
      }
    } finally {
      read.unlock();
    }
    
    write.lock();
    try {
      ds.close();
      dataSources.remove(type);
    } finally {
      read.unlock();
      write.unlock();
    }
    
  }
  
  /**
   * 更新已有类型的数据源
   * @param dsi 数据源信息
   */
  @Transactional
  private void updateDataSourceInfo(final DataSourceInfo dsi) {
    jdbcTemplate.execute("UPDATE data_source SET url=?, username=?, password=? WHERE type=?", new PreparedStatementCallback() {
      public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
          DataAccessException {
        ps.setString(1, dsi.getUrl());
        ps.setString(2, dsi.getUsername());
        ps.setString(3, dsi.getPassword());
        ps.setString(4, dsi.getType());
        ps.executeUpdate();
        return null;
      }
      
    });
  }
  
  /**
   * 根据type，得到对应的数据源信息
   */
  public Map getDsInfo(String type) {
    List dsInfos = jdbcTemplate.query(
        "SELECT id,type,url,username,password FROM data_source WHERE type=?",
        new Object[] { type }, new ColumnMapRowMapper());
    if (!CollectionUtils.isEmpty(dsInfos)) {
      return (Map) dsInfos.get(0);
    } else {
      return Collections.EMPTY_MAP;
    }
  }
}
