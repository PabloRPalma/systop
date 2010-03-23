package eq.datasource.factory;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import eq.datasource.DataSourceConstants;

@ContextConfiguration(locations = { "classpath*:applicationContext*.xml" })
public class JdbcTemplateFactoryTest extends AbstractJUnit38SpringContextTests {
  static Logger logger = LoggerFactory.getLogger(JdbcTemplateFactoryTest.class);
  private JdbcTemplateFactory factory;

  /**
   * @param factory the factory to set
   */
  @Autowired
  public void setFactory(JdbcTemplateFactory factory) {
    this.factory = factory;
  }
  
  public void test() {
    JdbcTemplate jt = factory.create(DataSourceConstants.DB_MYSQL);
    try {
      System.out.println(jt.getDataSource().getConnection().getMetaData().getURL());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
