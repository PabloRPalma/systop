package eq.datasource.factory;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * JdbcTemplate的工厂类
 * @author SAM
 *
 */
@Component
public class JdbcTemplateFactory {
  private DataSourceFactory dataSourceFactory;
  
  public JdbcTemplateFactory() {
    
  }
  
  public JdbcTemplateFactory(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }
  
  /**
   * 根据不同的databaseType类型，创建一个JdbcTemplate的实例.
   * @param databaseType (@link DataSourceConstants}
   * @return {@link JdbcTemplate}
   */
  public JdbcTemplate create(String databaseType) {
    DataSource ds = dataSourceFactory.getDataSource(databaseType);
    return new JdbcTemplate(ds);
  }

  /**
   * @param dataSourceFactory the dataSourceFactory to set
   */
  @Autowired
  public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }
}
