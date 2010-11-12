package quake.admin.catalog.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import quake.DataType;
import quake.GlobalConstants;
import quake.admin.ds.service.DataSourceManager;


/**
 * 验证表名称是否存在Dao
 * @author wbb
 */

@Repository
public class ExistTableNameDao {
  private static Logger logger = LoggerFactory.getLogger(ExistTableNameDao.class);
  /**
   * 用于得到DataSource
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  /**
   * @param dataType   数据源类型,取值为{@link GlobalConstants#DATA_CZ}或{@link GlobalConstants#DATA_QZ}
   * @param tableName  判断的表名
   * @return 存在=true
   */
  public boolean exists(DataType dataType, String tableName) {
    DataSource dsi = dataSourceManager.get(dataType);
    if (dsi == null || !dataSourceManager.isDefined()) {
      throw new IllegalStateException("测震和前兆数据源未定义.");
    }
    JdbcTemplate jt = new JdbcTemplate();
    jt.setDataSource(dsi);
    String schema = dataSourceManager.getSeismicSchema();   
    StringBuffer sql = new StringBuffer("select * from ")
      .append(schema)
      .append(".")
      .append(tableName).append(" where 1=0");
    try {
      logger.debug(sql.toString());
      jt.queryForList(sql.toString());
    } catch (DataAccessException e) {
      return false;
    }
    return true;
  }
}
