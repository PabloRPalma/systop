package quake.base.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import quake.DataType;
import quake.base.dao.ibatis.SqlMapClientTemplateFactory;


/**
 * 数据共享DAO基类，提供了日志支持和IBatis的支持。子类需要覆盖{@link #getDataType()} 方法，以确定连接的是测震数据源还是前兆数据源。
 * 
 * @author Sam
 * 
 */
public abstract class AbstractSeismicDao {
  /**
   * 子类可以直接使用的Logger
   */
  protected Logger logger = LoggerFactory.getLogger(getClass());
  /**
   * SqlMapClientTemplate工厂类，用于创建SqlMapClientTemplate
   */
  @Autowired(required = true)
  private SqlMapClientTemplateFactory sqlMapClientTemplateFactory;


  /**
   * 获取 {@link SqlMapClientTemplate} 对象，如果没有正确定义前兆或测震数据源， 则跑出IllegalStateException.
   */
  public SqlMapClientTemplate getTemplate() {
    try {
      return sqlMapClientTemplateFactory.getTemplate(getDataType());
    } catch (IllegalStateException e) {
      logger.error("数据源没有定义，数据服务无法使用。");
      return null;
    }
  }

  /**
   * 子类实现这个方法以确定连接的是测震数据源还是前兆数据源，通常实现如下：
   * 
   * <pre>
   * protected DataType getDataType() {
   *   return DataType.SEISMIC; //or DataType.SIGN
   * }
   * </pre>
   */
  protected abstract DataType getDataType();
}
