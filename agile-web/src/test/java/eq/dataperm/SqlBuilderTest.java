package eq.dataperm;
import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import eq.dataperm.SqlBuilder;

@ContextConfiguration(locations = { "classpath*:applicationContext*.xml" })
public class SqlBuilderTest extends AbstractJUnit38SpringContextTests {
  private SqlBuilder sqlBuilder;

  /**
   * @param sqlBuilder the sqlBuilder to set
   */
  @Autowired
  public void setSqlBuilder(SqlBuilder sqlBuilder) {
    this.sqlBuilder = sqlBuilder;
  }
  
  public void test() {
    String sql = sqlBuilder.getSql("query_user", "ROLE_SAM");
    Assert.assertNotNull(sql);
    Assert.assertEquals(sql.trim(), "select * from test_1");
    sql = sqlBuilder.getSql("query_user", "ROLE_LEE");
    Assert.assertNotNull(sql);
    Assert.assertEquals(sql.trim(), "select * from test_2");
    try{
      sqlBuilder.getSql("nsss", "ROLE_SAM");
      Assert.fail();
    } catch (Exception e) {
    }
  }
}
