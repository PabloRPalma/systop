package com.systop.modules.admin.config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jconfig.Category;
import org.jconfig.Configuration;
import org.jconfig.ConfigurationManagerException;
import org.jconfig.DefaultConfiguration;
import org.jconfig.error.ErrorReporter;
import org.jconfig.handler.ConfigurationHandler;
import org.jconfig.parser.ConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

/**
 * Implementation of ConfigurationHandler, using spring style.
 * @author Sam Lee
 */
@SuppressWarnings("unchecked")
@Service
public class JConfigJdbcHandler implements ConfigurationHandler {
  private static Logger logger = LoggerFactory.getLogger(JConfigJdbcHandler.class);
  
  private JdbcTemplate jdbcTemplate;
  


  /**
   * @param jdbcTemplate the jdbcTemplate to set
   */
  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate; 
  }
  
  /**
   * @see {@link ConfigurationHandler#load(String)}
   */
  public Configuration load(String configurationName) throws ConfigurationManagerException {
    if (StringUtils.isBlank(configurationName)) {
      ErrorReporter.getErrorHandler().reportError("Configuration name cannot be <null> or EMPTY");
      throw new ConfigurationManagerException("Configuration name cannot be <null> or EMPTY");
    }
    return loadConfiguration(configurationName);
  }
  
  /**
   * Unimplemented.
   */
  public Configuration load(String configurationName, ConfigurationParser parser)
      throws ConfigurationManagerException {
    throw new ConfigurationManagerException(
        "Using a specific parser with this handler is not supported");
  }
  
  /**
   * @see {@link ConfigurationHandler#store(Configuration)}
   */
  public void store(Configuration configuration) throws ConfigurationManagerException {
    try {
      long confoid = storeConfiguration(configuration.getConfigName());
      Map oldCategories = readOldCategories(confoid);
      String[] categoryNameArray = configuration.getCategoryNames();
      for (int i = 0; i < categoryNameArray.length; i++) {
        String categoryName = categoryNameArray[i];
        long catid = storeCategory(confoid, categoryName);
        oldCategories.remove(new Long(catid));
        storeProperties(catid, configuration.getCategory(categoryName));
      }
      // Delete old Categories rested in oldCategories
      Set setCat = oldCategories.keySet();
      for (Iterator itr = setCat.iterator(); itr.hasNext();) {
        Long catid = (Long) itr.next();
        deleteCategory(catid.longValue(), oldCategories.get(catid).toString());
      }

      String[] variableNameArray = getStringArray(configuration.getVariables());
      Map oldVariables = readOldVariables(confoid);
      for (int i = 0; i < variableNameArray.length; i++) {
        String variableName = variableNameArray[i];
        long varoid = storeVariable(confoid, variableName, configuration.getVariable(variableName));
        oldVariables.remove(new Long(varoid));
      }
      Set setVar = oldVariables.keySet();
      for (Iterator itr = setVar.iterator(); itr.hasNext();) {
        Long varoid = (Long) itr.next();
        deleteVariable(varoid.longValue(), oldVariables.get(varoid).toString());
      }
    } catch (Exception e) {
      logger.error("An error has occured when store configuration. {}", e.getMessage());
      e.printStackTrace();
    }
  }

  private Configuration loadConfiguration(String configurationName) {
    final Configuration config = new DefaultConfiguration(configurationName);
    jdbcTemplate.query("SELECT OID FROM T_CONFIGURATION WHERE NAME = ?",
        new Object[] { configurationName }, new RowCallbackHandler() {
          public void processRow(ResultSet rs) throws SQLException {
            long oid = rs.getLong("OID");
            loadCategories(config, oid);
            loadVariables(config, oid);
          }
        });
    logger.debug("Load config {}", configurationName);
    return config;
  }

  private void loadCategories(final Configuration config, long oid) {
    jdbcTemplate.query("SELECT OID, NAME FROM T_CATEGORY WHERE CONFIGURATION_OID = ?",
        new Object[] { oid }, new RowCallbackHandler() {
          public void processRow(ResultSet rs) throws SQLException {
            long catoid = rs.getLong("OID");
            String categoryName = rs.getString("NAME");
            Category cat = config.getCategory(categoryName);
            loadProperties(cat, catoid);
          }
        });
  }

  private void loadVariables(final Configuration config, long oid) {
    jdbcTemplate.query("SELECT OID, NAME, VALUE FROM T_VARIABLE WHERE CONFIGURATION_OID = ?",
        new Object[] { oid }, new RowCallbackHandler() {
          public void processRow(ResultSet rs) throws SQLException {
            // long catoid = rs.getLong("OID");
            String variableName = rs.getString("NAME");
            String variableValue = rs.getString("VALUE");
            config.setVariable(variableName, variableValue);
          }
        });
  }

  private void loadProperties(final Category cat, long catoid) {
    jdbcTemplate.query("SELECT OID, NAME, VALUE FROM T_PROPERTY WHERE CATEGORY_OID = ? ",
        new Object[] { catoid }, new RowCallbackHandler() {
          public void processRow(ResultSet rs) throws SQLException {
            // long propoid = rs.getLong("OID");
            String propertyName = rs.getString("NAME");
            String propertyValue = rs.getString("VALUE");
            cat.setProperty(propertyName, propertyValue);
          }
        });
  }

  private Map readOldCategories(long oid) {
    final Map oldCategories = new HashMap(100);
    jdbcTemplate.query("SELECT OID, NAME FROM T_CATEGORY WHERE CONFIGURATION_OID = ?",
        new Object[] { oid }, new RowCallbackHandler() {
          public void processRow(ResultSet rs) throws SQLException {
            Long catoid = new Long(rs.getLong("OID"));
            String categoryName = rs.getString("NAME");
            oldCategories.put(catoid, categoryName);
          }
        });

    return oldCategories;

  }

  private Map readOldVariables(long oid) {
    final Map oldVariables = new HashMap(100);
    jdbcTemplate.query("SELECT OID, NAME, VALUE FROM T_VARIABLE WHERE CONFIGURATION_OID = ?",
        new Object[] { oid }, new RowCallbackHandler() {
          public void processRow(ResultSet rs) throws SQLException {
            long catoid = rs.getLong("OID");
            String variableName = rs.getString("NAME");
            oldVariables.put(new Long(catoid), variableName);
          }
        });
    return oldVariables;
  }

  private Map readOldProperties(long catoid) {
    final Map oldProperties = new HashMap(100);
    jdbcTemplate.query("SELECT OID, NAME, VALUE FROM T_VARIABLE WHERE CONFIGURATION_OID = ?",
        new Object[] { catoid }, new RowCallbackHandler() {
          public void processRow(ResultSet rs) throws SQLException {
            long propoid = rs.getLong("OID");
            String propertyName = rs.getString("NAME");
            oldProperties.put(new Long(propoid), propertyName);
          }
        });

    return oldProperties;
  }

  private long storeConfiguration(String configurationName) throws ConfigurationManagerException {
    long oid = Long.MIN_VALUE;
    oid = (Long) jdbcTemplate.query("SELECT OID FROM T_CONFIGURATION WHERE NAME = ?",
        new Object[] { configurationName }, new ResultSetExtractor() {
          public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            long soid = Long.MIN_VALUE;
            while (rs.next()) {
              soid = rs.getLong("OID");
            }
            return soid;
          }

        });

    if (oid == Long.MIN_VALUE) {
      createNewConfiguration(configurationName);
      return storeConfiguration(configurationName);
    }
    logger.debug("Store configuration {}", configurationName);
    return oid;
  }

  private void createNewConfiguration(final String configurationName) {
    jdbcTemplate.execute("INSERT INTO T_CONFIGURATION (NAME) VALUES (?)",
        new PreparedStatementCallback() {

          public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
              DataAccessException {
            ps.setString(1, configurationName);
            ps.executeUpdate();
            return null;
          }

        });
    logger.debug("Create new cofiguration {}.", configurationName);
  }

  private long storeCategory(long confoid, String categoryName) {
    long catoid = Long.MIN_VALUE;

    catoid = (Long) jdbcTemplate.query(
        "SELECT OID FROM T_CATEGORY WHERE CONFIGURATION_OID = ? AND NAME = ?", new Object[] {
            confoid, categoryName }, new ResultSetExtractor() {

          public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            long soid = Long.MIN_VALUE;
            while (rs.next()) {
              soid = rs.getLong("OID");
            }
            return soid;
          }

        });

    if (catoid == Long.MIN_VALUE) {
      createNewCategory(confoid, categoryName);
      return storeCategory(confoid, categoryName);
    }
    logger.debug("stroe category {}-{}.", confoid, categoryName);
    return catoid;
  }

  private void createNewCategory(final long confoid, final String categoryName) {
    jdbcTemplate.execute("INSERT INTO T_CATEGORY (NAME, CONFIGURATION_OID) VALUES (?, ?)",
        new PreparedStatementCallback() {

          public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
              DataAccessException {
            ps.setString(1, categoryName);
            ps.setLong(2, confoid);
            ps.executeUpdate();
            return null;
          }

        });
    logger.debug("create category {}-{}.", confoid, categoryName);
  }

  private void storeProperties(long catid, Category category) throws ConfigurationManagerException {
    Enumeration enm = category.getProperties().keys();
    Map oldProperties = readOldProperties(catid);
    while (enm.hasMoreElements()) {
      String propertyName = (String) enm.nextElement();
      long propoid = storeProperty(catid, propertyName, category.getProperty(propertyName));
      oldProperties.remove(new Long(propoid));
    }
    Set set = oldProperties.keySet();
    for (Iterator itr = set.iterator(); itr.hasNext();) {
      Long propoid = (Long) itr.next();
      deleteProperty(propoid.longValue(), oldProperties.get(propoid).toString());
    }
    logger.debug("stroe Properties {}-{}.", catid, category.getCategoryName());
  }

  /**
   * @param propertyName
   * @param string
   */
  private long storeProperty(long catoid, String propertyName, final String propertyValue)
      throws ConfigurationManagerException {
    long propoid = Long.MIN_VALUE;
    propoid = (Long) jdbcTemplate.query(
        "SELECT OID FROM T_PROPERTY WHERE CATEGORY_OID = ? AND NAME= ?", new Object[] { catoid,
            propertyName }, new ResultSetExtractor() {

          public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            long soid = Long.MIN_VALUE;
            while (rs.next()) {
              soid = rs.getLong("OID");
              updateProperty(soid, propertyValue);
            }
            return soid;
          }

        });

    if (propoid == Long.MIN_VALUE) {
      createNewProperty(catoid, propertyName, propertyValue);
      return storeProperty(catoid, propertyName, propertyValue);
    }
    logger.debug("Store property {}-{}-{}", new Object[]{catoid, propertyName, propertyValue});
    return propoid;
  }
  
  private void updateProperty(final long propoid, final String propertyValue) {
    jdbcTemplate.execute("UPDATE T_PROPERTY SET VALUE=? WHERE OID=?", new PreparedStatementCallback() {
      public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
          DataAccessException {
        ps.setString(1, propertyValue);
        ps.setLong(2, propoid);
        ps.executeUpdate();
        return null;
      }
      
    });
  }
  private void createNewProperty(final long catoid, final String propertyName,
      final String propertyValue) throws ConfigurationManagerException {
    jdbcTemplate.execute("INSERT INTO T_PROPERTY (NAME, VALUE, CATEGORY_OID) VALUES (?, ?, ?)",
        new PreparedStatementCallback() {

          public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
              DataAccessException {
            ps.setString(1, propertyName);
            ps.setString(2, propertyValue);
            ps.setLong(3, catoid);
            ps.executeUpdate();
            return null;
          }

        });
    logger.debug("create new property {}", propertyName);
  }
  
  private long storeVariable(final long confoid, final String variableName,
      final String variableValue) throws ConfigurationManagerException {
    long varoid = Long.MIN_VALUE;
    varoid = (Long) jdbcTemplate.query(
        "SELECT OID FROM T_VARIABLE WHERE CONFIGURATION_OID = ? AND NAME= ?", new Object[] {
            confoid, variableName }, new ResultSetExtractor() {

          public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            long soid = Long.MIN_VALUE;
            while (rs.next()) {
              soid = rs.getLong("OID");
            }
            updateVariable(soid, variableValue);
            return soid;
          }

        });

    if (varoid == Long.MIN_VALUE) {
      createNewVariable(confoid, variableName, variableValue);
      return storeVariable(confoid, variableName, variableValue);
    }

    return varoid;
  }
  
  private void createNewVariable(final long confoid, final String variableName,
      final String variableValue) {
    jdbcTemplate.execute(
        "INSERT INTO T_VARIABLE (NAME, VALUE, CONFIGURATION_OID) VALUES (?, ?, ?)",
        new PreparedStatementCallback() {

          public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
              DataAccessException {
            ps.setString(1, variableName);
            ps.setString(2, variableValue);
            ps.setLong(3, confoid);
            ps.executeUpdate();
            return null;
          }

        });

  }
  
  private void updateVariable(final long varoid, final String variableValue) {
    jdbcTemplate.execute("UPDATE T_VARIABLE SET VALUE=? WHERE OID=?",
        new PreparedStatementCallback() {

          public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
              DataAccessException {
            ps.setString(1, variableValue);
            ps.setLong(2, varoid);
            ps.executeUpdate();
            return null;
          }

        });
  }
  
  private void deleteProperty(final long propoid, final String propertyName) {
    jdbcTemplate.execute("DELETE FROM T_PROPERTY WHERE OID = ? AND NAME = ?",
        new PreparedStatementCallback() {

          public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
              DataAccessException {
            ps.setLong(1, propoid);
            ps.setString(2, propertyName);
            ps.executeUpdate();
            return null;
          }

        });
    logger.debug("Delete property {}", propertyName);
  }
  
  private void deleteCategory(final long confoid, final String categoryName) {
    jdbcTemplate.execute("DELETE FROM T_CATEGORY WHERE CONFIGURATION_OID = ? AND NAME = ?",
        new PreparedStatementCallback() {

          public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
              DataAccessException {
            ps.setLong(1, confoid);
            ps.setString(2, categoryName);
            ps.executeUpdate();
            return null;
          }

        });
    logger.debug("Delete category {}", categoryName);
  }
  
  private void deleteVariable(final long varoid, final String variableName) {
    jdbcTemplate.execute("DELETE FROM T_VARIABLE WHERE OID = ?", new PreparedStatementCallback() {

      public Object doInPreparedStatement(PreparedStatement ps) throws SQLException,
          DataAccessException {
        ps.setLong(1, varoid);
        ps.executeUpdate();
        return null;
      }

    });
  }
  
  private String[] getStringArray(Map categories) {
    Set allCategories = categories.keySet();
    return (String[]) allCategories.toArray(new String[0]);
  }
}
