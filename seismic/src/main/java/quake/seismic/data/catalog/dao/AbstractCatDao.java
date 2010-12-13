package quake.seismic.data.catalog.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import quake.DataType;
import quake.base.dao.AbstractSeismicDao;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.catalog.model.MagCriteria;


/**
 * 地震目录浏览Dao抽象实现类
 * @author DU
 */
public abstract class AbstractCatDao<T> extends AbstractSeismicDao {
  /**
   * 地震目录数据查询在IBatis中的statementName,按矩形区域
   */
  protected static final String SQL_ID = "cz.queryCatData";
  /**
   * 查询数据行数的statementName,按矩形区域
   */
  protected static final String SQL_COUNT_ID = SQL_ID + "Count";

  /**
   * 地震目录数据查询在IBatis中的statementName,按圆形区域
   */
  protected static final String SQL_ROUND_ID = "cz.queryCatDataAsRound";
  /**
   * 查询数据行数的statementName,按圆形区域
   */
  protected static final String SQL_ROUND_COUNT_ID = SQL_ROUND_ID + "Count";
  /**
   * 地震目录关联震级查询
   */
  protected static final String SQL_CAT_MAG_ID = "cz.queryCatMagData";
  /**
   * 根据台网台站代码取得台站信息
   */
  protected static final String SQL_NET_STA_CODE_ID = "cz.queryStationAtBulletin";
  /**
   * 根据台网台站代码取得台站信息_台站信息表和位置信息表联合查询
   */
  protected static final String SQL_NET_STA_LOC_ID = "cz.queryStationLocAtBulletin";
  /**
   * 查询数据行数的statementName
   */
  protected static final String SQL_CAT_MAG_COUNT_ID = SQL_CAT_MAG_ID + "Count";

  /**
   * 根据地震目录与M_SOURCE查询震级
   */
  protected static final String SQL_MAG_ID = "cz.queryMagData";
  
  /**
   * 根据地震目录查询震级名称
   */
  
  protected static final String SQL_MAG_NAME = "cz.queryMagM";

  /**
   * 根据条件查询地震目录信息
   * @param criteria  封装查询条件的<code>Criteria</code>对象
   * @return
   */
  public abstract T query(Criteria criteria);

  @Override
  protected DataType getDataType() {
    return DataType.SEISMIC;
  }

  /**
   * 根据条件查询震级
   * @return
   */
  @SuppressWarnings("unchecked")
  public Double getMagVal(MagCriteria MagCriteria) {
    List<Double> list = getTemplate().queryForList(SQL_MAG_ID, MagCriteria);
    return list.isEmpty() ? null : list.get(0);
  }

  /**
   * 从震级表取得震级
   * @param rows
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Map> setMagVal(List<Map> rows, Criteria criteria) {
    MagCriteria mc = new MagCriteria();
    mc.setSchema(criteria.getSchema());
    mc.setTableName(criteria.getMagTname());

    for (Map row : rows) {
      String id = (String) row.get("ID");
      String magName = (String) row.get("M_SOURCE");
      //只有两个条件才能确定唯一震级
      if (StringUtils.isBlank(id) || StringUtils.isBlank(magName)) {
        continue;
      }
      mc.setCatId(id);
      mc.setMagName(magName);
      Double magVal = getMagVal(mc);
      if (magVal != null) {
        row.put("M", magVal);
      }
    }
    return rows;
  }
  
}
