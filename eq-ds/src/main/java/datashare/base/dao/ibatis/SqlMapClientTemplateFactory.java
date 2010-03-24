package datashare.base.dao.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;

import datashare.DataType;
/**
 * Spring的<code>SqlMapClientTemplate</code>类的工厂类.
 * @author SAM
 *
 */
@Component
public class SqlMapClientTemplateFactory {
  @Autowired(required = true)
  private SqlMapClientFactory sqlMapClientFactory;
  
  /**
   * 根据<code>DataType</code>，返回<code>SqlMapClientTemplate</code>的实例
   * @param dataType 给出数据类型（测震、前兆）
   * @return Instance of <code>SqlMapClientTemplate</code>
   * @see {@link SqlMapClientFactory}
   */
  public SqlMapClientTemplate getTemplate(DataType dataType) {
    SqlMapClient client = sqlMapClientFactory.getSqlMapClient(dataType);
    return new SqlMapClientTemplate(client);
  }
}
