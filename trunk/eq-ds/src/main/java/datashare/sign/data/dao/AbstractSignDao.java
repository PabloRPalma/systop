package datashare.sign.data.dao;

import org.springframework.beans.factory.annotation.Autowired;

import datashare.DataType;
import datashare.admin.samplerate.service.SampleRateManager;
import datashare.base.dao.AbstractDataShareDao;
import datashare.sign.data.model.Criteria;
import datashare.sign.data.support.CalcHelper;
/**
 * 前兆DAO接口。前兆数据查询中，需要有表格显示、曲线图（amcharts,需要scv格式）、
 * 导出文本等不同的数据需要，子类应根据不同的数据结果调用IBatis进行查询并对查询结果
 * 处理。
 * <br>
 * 当表格查询的时候（尤其秒值和分钟值），需要尽量缩小查询范围，只查询所需数据段当天
 * （最多再加上第二天）的数据。并且，当数据查询出来之后，还要根据实际页面上的分页要求，
 * 对数据进行分页查询
 * <br>
 * 当查询曲线图、文本的时候，不能对数据进行分页查询，此时数据量会非常大，必须调用IBatis
 * 提供的{@link com.ibatis.sqlmap.client.SqlMapExecutor#queryWithRowHandler(String, Object, com.ibatis.sqlmap.client.event.RowHandler)}
 * 方法以避免产生大量的数据对象导致OOM发生。
 * 
 * @author Sam
 * 
 * @param <T> 查询结果的数据类型.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractSignDao<T> extends AbstractDataShareDao {
  @Autowired(required = true)
  protected SampleRateManager srManager;
  /**
   * 前兆数据查询在IBatis中的statementName
   */
  protected static final String SQL_ID = "qz.queryData";
  /**
   * 查询数据行数的statementName
   */
  protected static final String SQL_COUNT_ID = SQL_ID + "Count";
  
  @Override
  protected DataType getDataType() {
    return DataType.SIGN;
  }
  
  protected CalcHelper getCalcHelper() {
    return new CalcHelper(srManager);
  }
  
  /**
   * 根据查询条件执行前兆数据查询。
   * @param criteria 封装查询条件的<code>Criteria</code>对象
   * @see {@link Criteria}
   * @return 查询结果对象，如果是表格查询，则应返回{@link eq.core.qz.model.QueryResult}
   * 对象，并且将总数据量和pageSize传给该对象。如果是SCV格式或者其他文本格式，
   * 应返回{@link java.io.StringWriter}或者{@link StringBuffer}对象。
   */
  public abstract T query(Criteria criteria);

}
