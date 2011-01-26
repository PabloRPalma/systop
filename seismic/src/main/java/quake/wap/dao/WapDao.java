package quake.wap.dao;

import org.springframework.stereotype.Repository;

import quake.seismic.data.catalog.model.Criteria;

import com.systop.core.dao.support.Page;
/**
 *  手机dao
 * @author yj
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class WapDao extends AbstractWapDao<Page> {
  /**
   * 获得地震目录列表条数
   * @param criteria
   * @return
   */
  public int getCatalogCount(Criteria criteria){
    String SQL_COUNT = SQL_CAT_COUNT;
    int count = (Integer) (getTemplate().queryForObject(SQL_COUNT, criteria));
    return count;
  }
}
