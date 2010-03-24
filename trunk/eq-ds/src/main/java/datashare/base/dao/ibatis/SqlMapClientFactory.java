package datashare.base.dao.ibatis;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;
import org.springframework.util.ObjectUtils;

import com.ibatis.common.xml.NodeletException;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapConfigParser;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapParser;
import com.ibatis.sqlmap.engine.builder.xml.XmlParserState;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.transaction.TransactionConfig;
import com.ibatis.sqlmap.engine.transaction.TransactionManager;
import com.ibatis.sqlmap.engine.transaction.jdbc.JdbcTransactionConfig;
import com.systop.core.ApplicationException;

import datashare.DataType;
import datashare.admin.ds.model.DataSourceInfo;
import datashare.admin.ds.service.DataSourceManager;

/**
 * IBatis SqlMapClient Factory.因为项目的特殊性（数据源会动态改变）， 所以不能使用Spring提供的<code>SqlMapClientFactoryBean</code>.
 * 但是，实现过程参考了Spring的这个类。
 * 
 * @author Sam
 * 
 */
@SuppressWarnings("deprecation")
public class SqlMapClientFactory {
  private static final ThreadLocal<LobHandler> configTimeLobHandlerHolder = new ThreadLocal<LobHandler>();
  
  private static final ConcurrentMap<DataType, SqlMapClient> SQL_MAP_CLIENTS 
    = new ConcurrentHashMap<DataType, SqlMapClient>();
  /**
   * DataSourceManager用于取得数据源
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;
  
  /**
   * 测震Sql Map 配置文件的位置
   */
  
  private Resource[] czConfigLocations;
  /**
   * 前兆Sql Map 配置文件的位置
   */
  
  private Resource[] qzConfigLocations;
  
  /**
   * 测震Sql 配置文件的位置
   */
  
  private Resource[] czMappingLocations;
  /**
   * 前兆Sql 配置文件的位置
   */
  
  private Resource[] qzMappingLocations;
  
  /**
   * 用于标识数据源信息是否改变
   */
  private String dataSourceInfoString;


  /**
   * 根据给出的数据类型（测震、前兆），返回相应的<code>SqlMapClient</code>对象。
   * 
   * @param dataType 数据源类型
   * @return Instance of <code>SqlMapClient</code>.
   * @throws IllegalStateException 如果测震和前兆数据源未定义或定义错误，则抛出此异常.
   */
  public SqlMapClient getSqlMapClient(DataType dataType) {
    DataSourceInfo dsi = dataSourceManager.get();
    if(dsi == null || !dataSourceManager.isDefined()) {
      throw new IllegalStateException("测震和前兆数据源未定义.");
    }
    //如果数据源信息发生变化，则重建所有SqlMapClient对象。
    if(!StringUtils.equals(dsi.toString(), dataSourceInfoString)) {
      try {
        SqlMapClient czClient = buildSqlMapClient(czConfigLocations, czMappingLocations, null, DataType.SEISMIC);
        SqlMapClient qzClient = buildSqlMapClient(qzConfigLocations, qzMappingLocations, null, DataType.SIGN);
        SQL_MAP_CLIENTS.put(DataType.SEISMIC, czClient);
        SQL_MAP_CLIENTS.put(DataType.SIGN, qzClient);        
      } catch (IOException e) {
        e.printStackTrace();
        throw new ApplicationException("SqlMapClient创建出现IO错误:" + e.getMessage());
      }

    } 
    
    return SQL_MAP_CLIENTS.get(dataType);
  }

  
  /**
   * 创建合适的<code>LobHandler</code>的实例。因为Oracle的Lob字段的处理方式
   * 比较特殊，所以，需要根据数据源类型创建不同的<code>LobHandler</code>的实例
   * @param dataType 数据源类型（测震、前兆）
   * @return< code>LobHandler</code>的实例
   */
  private LobHandler getAppropriateLobHandler(DataType dataType) {
    if(dataSourceManager.isOracle(dataType)) {
      OracleLobHandler olh = new OracleLobHandler();
      C3P0NativeJdbcExtractor nativeJdbcExtractor = new C3P0NativeJdbcExtractor();
      olh.setNativeJdbcExtractor(nativeJdbcExtractor);
      return olh;
      
    } else {
      return new DefaultLobHandler();
    }
  }
  
  /**
   * Build a SqlMapClient instance based on the given standard configuration.
   * <p>The default implementation uses the standard iBATIS {@link SqlMapClientBuilder}
   * API to build a SqlMapClient instance based on an InputStream (if possible,
   * on iBATIS 2.3 and higher) or on a Reader (on iBATIS up to version 2.2).
   * @param configLocations the config files to load from
   * @param properties the SqlMapClient properties (if any)
   * @return the SqlMapClient instance (never <code>null</code>)
   * @throws IOException if loading the config file failed
   * @see com.ibatis.sqlmap.client.SqlMapClientBuilder#buildSqlMapClient
   */
  protected SqlMapClient buildSqlMapClient(Resource[] configLocations, Resource[] mappingLocations, Properties properties, DataType dataType) throws IOException{
    if (ObjectUtils.isEmpty(configLocations)) {
      throw new IllegalArgumentException("At least 1 'configLocation' entry is required");
    }
    LobHandler lobHandler = getAppropriateLobHandler(dataType);
    // Make given LobHandler available for SqlMapClient configuration.
    // Do early because because mapping resource might refer to custom types.
    configTimeLobHandlerHolder.set(lobHandler);
    try {
      SqlMapClient client = null;
      SqlMapConfigParser configParser = new SqlMapConfigParser();
      for (int i = 0; i < configLocations.length; i++) {
        InputStream is = configLocations[i].getInputStream();
        try {
          client = configParser.parse(is, properties);
        } catch (RuntimeException ex) {
          throw new NestedIOException("Failed to parse config resource: " + configLocations[i], ex
              .getCause());
        }
      }

      if (mappingLocations != null) {
        SqlMapParser mapParser = SqlMapParserFactory.createSqlMapParser(configParser);
        for (int i = 0; i < mappingLocations.length; i++) {
          try {
            mapParser.parse(mappingLocations[i].getInputStream());
          } catch (NodeletException ex) {
            throw new NestedIOException("Failed to parse mapping resource: " + mappingLocations[i],
                ex);
          }
        }
      }
      TransactionConfig transactionConfig = new JdbcTransactionConfig();
      DataSource dataSourceToUse = this.dataSourceManager.get(dataType);
      transactionConfig.setDataSource(dataSourceToUse);
      applyTransactionConfig(client, transactionConfig);

      return client;
    } finally {
      // Reset LobHandler holder.
      configTimeLobHandlerHolder.set(null);
    }
  }
  
  /**
   * Apply the given iBATIS TransactionConfig to the SqlMapClient.
   * <p>The default implementation casts to ExtendedSqlMapClient, retrieves the maximum
   * number of concurrent transactions from the SqlMapExecutorDelegate, and sets
   * an iBATIS TransactionManager with the given TransactionConfig.
   * @param sqlMapClient the SqlMapClient to apply the TransactionConfig to
   * @param transactionConfig the iBATIS TransactionConfig to apply
   * @see com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient
   * @see com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate#getMaxTransactions
   * @see com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate#setTxManager
   */
  protected void applyTransactionConfig(SqlMapClient sqlMapClient, TransactionConfig transactionConfig) {
    if (!(sqlMapClient instanceof ExtendedSqlMapClient)) {
      throw new IllegalArgumentException(
          "Cannot set TransactionConfig with DataSource for SqlMapClient if not of type " +
          "ExtendedSqlMapClient: " + sqlMapClient);
    }
    ExtendedSqlMapClient extendedClient = (ExtendedSqlMapClient) sqlMapClient;
    transactionConfig.setMaximumConcurrentTransactions(extendedClient.getDelegate().getMaxTransactions());
    extendedClient.getDelegate().setTxManager(new TransactionManager(transactionConfig));
  }
  /**
   * Inner class to avoid hard-coded iBATIS 2.3.2 dependency (XmlParserState class).
   */
  private static class SqlMapParserFactory {

    public static SqlMapParser createSqlMapParser(SqlMapConfigParser configParser) {
      // Ideally: XmlParserState state = configParser.getState();
      // Should raise an enhancement request with iBATIS...
      XmlParserState state = null;
      try {
        Field stateField = SqlMapConfigParser.class.getDeclaredField("state");
        stateField.setAccessible(true);
        state = (XmlParserState) stateField.get(configParser);
      }
      catch (Exception ex) {
        throw new IllegalStateException("iBATIS 2.3.2 'state' field not found in SqlMapConfigParser class - " +
            "please upgrade to IBATIS 2.3.2 or higher in order to use the new 'mappingLocations' feature. " + ex);
      }
      return new SqlMapParser(state);
    }
  }
  
  /**
   * Return the LobHandler for the currently configured iBATIS SqlMapClient,
   * to be used by TypeHandler implementations like ClobStringTypeHandler.
   * <p>This instance will be set before initialization of the corresponding
   * SqlMapClient, and reset immediately afterwards. It is thus only available
   * during configuration.

   * @see eq.core.base.sqlmap.support.ClobStringTypeHandler
   * @see eq.core.base.sqlmap.support.BlobByteArrayTypeHandler
   */
  public static LobHandler getConfigTimeLobHandler() {
    return (LobHandler) configTimeLobHandlerHolder.get();
  }

  
  /**
   * @param czConfigLocations the czConfigLocations to set
   */
  public void setCzConfigLocations(Resource[] czConfigLocations) {
    this.czConfigLocations = czConfigLocations;
  }

  /**
   * @param qzConfigLocations the qzCfonfigLocations to set
   */
  public void setQzConfigLocations(Resource[] qzConfigLocations) {
    this.qzConfigLocations = qzConfigLocations;
  }

  /**
   * @param czMappingLocations the czMappingLocations to set
   */
  public void setCzMappingLocations(Resource[] czMappingLocations) {
    this.czMappingLocations = czMappingLocations;
  }

  /**
   * @param qzMappingLocations the qzMappingLocations to set
   */
  public void setQzMappingLocations(Resource[] qzMappingLocations) {
    this.qzMappingLocations = qzMappingLocations;
  }
}
