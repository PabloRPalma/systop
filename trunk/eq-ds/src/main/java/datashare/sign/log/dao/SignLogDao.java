package datashare.sign.log.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import datashare.DataType;
import datashare.base.dao.AbstractDataShareDao;
import datashare.sign.data.model.Criteria;

/**
 * 日志信息查询Dao
 * @author Lunch
 *
 */
@Repository
public class SignLogDao extends AbstractDataShareDao {

  @Override
  protected DataType getDataType() {
    return DataType.SIGN;
  }

  /**
   * 表示sql语句的变量
   */
  protected static final String SQL_QUERY_LOG = "qz.querySignLog";

  @SuppressWarnings("unchecked")
  public List queryLog(Criteria criteria) {
    List logs = getTemplate().queryForList(SQL_QUERY_LOG, criteria);
    return logs;
  }
}
