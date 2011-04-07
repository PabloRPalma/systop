package quake.seismic.data.catalog.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import quake.seismic.SeismicConstants;
import quake.seismic.data.EQTimeFormat;
import quake.seismic.data.catalog.dao.AbstractCatDao;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.catalog.model.MagCriteria;

import com.systop.core.dao.support.Page;


/**
 * 地震目录查询DAO
 * @author DU
 */
@Repository
public class GridCatDao extends AbstractCatDao<Page> {
  
  /**
   * 查询地震目录
   * 两种查询方式，按矩形区域查询和按圆形区域查询
   * 根据地震目录配置情况判断是否关联震级表
   * @param criteria 目录查询参数
   */
  @SuppressWarnings("unchecked")
  @Override
  public Page query(Criteria criteria) {  
    String SQL = SQL_ID;
    String SQL_COUNT = SQL_COUNT_ID;
    if(SeismicConstants.ROUND_QUERY_YES.equals(criteria.getIsRoundQuery())){
      logger.debug("地震目录查询，按圆形区域....");
      SQL = SQL_ROUND_ID;
      SQL_COUNT = SQL_ROUND_COUNT_ID;
    }
    criteria.setSortProperty("O_TIME");
    criteria.setSortDir("desc");
    int count = (Integer) (getTemplate().queryForObject(SQL_COUNT, criteria));
    List<Map> rows = getTemplate().queryForList(SQL, criteria);
    
    if(StringUtils.isNotBlank(criteria.getMagTname())) {//关联震级表
      criteria.getPage().setData(EQTimeFormat.getEqTimeValue(setMagM(rows, criteria), "O_TIME", "O_TIME_FRAC"));
    } else {
      criteria.getPage().setData(EQTimeFormat.getEqTimeValue(rows, "O_TIME", "O_TIME_FRAC"));
    }
    
    criteria.getPage().setRows(count);
    return criteria.getPage();
  }
  
  /**
   * 地震目录单表查询,获取GIS显示所需要数据，无分页查询
   * 两种查询方式，按矩形区域查询和按圆形区域查询
   * @param criteria 目录查询参数
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Map> queryForGis(Criteria criteria) {
    String SQL = SQL_ID;
    if(SeismicConstants.ROUND_QUERY_YES.equals(criteria.getIsRoundQuery())){
      logger.debug("地震目录查询，按圆形区域....");
      SQL = SQL_ROUND_ID;
    }
    criteria.setSortProperty("O_TIME");
    criteria.setSortDir("desc");
    //GIS显示地震目录条数设定值为常量自定义的最大值
    List<Map> rows = getTemplate().queryForList(SQL, criteria, 0, SeismicConstants.MAX_SIZE);
    return rows;
  }
  
  /**
   * 根据地震目录ID查询震级
   * @param magCriteria 震级查询参数
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<Map> getMagM(MagCriteria magCriteria){
    List<Map> list = getTemplate().queryForList(SQL_MAG_NAME,magCriteria);
    return list.isEmpty() ? null : list;
  }
  
  /**
   * 查询所有地震目录震级
   * 将地震目录相关震级，包括ML,Ms,Mb,MB,Ms7,Mw以数据列的形式组织到查询结果中
   * @param rows 地震目录查询结果
   * @param criteria 震级查询参数
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<Map> setMagM(List<Map> rows,Criteria criteria){
    MagCriteria magCriteria = new MagCriteria();
    magCriteria.setSchema(criteria.getSchema());
    magCriteria.setTableName(criteria.getMagTname());
    
    for (Map catalog : rows) {
      String catalogId = (String)catalog.get("ID");
      //String magcId = (String)catalog.get("MAGC_ID");
      if(StringUtils.isBlank(catalogId)){
        continue;
      }
      magCriteria.setCatId(catalogId);
      //根据关联震级表的ID查询，只按照catalogId查询MAG_C表里有重复数据
      //magCriteria.setMagcId(magcId);
      List<Map> magList = getMagM(magCriteria);
      if (magList != null && magList.size() > 0){
        for (Map magtype : magList) {
          catalog.put(magtype.get("MAG_NAME"), magtype.get("MAG_VAL"));
        }
      }
    }
    return rows;
  }
  
  /**
   * 查询台网信息
   * @param criteria
   */
  @SuppressWarnings("unchecked")
  public List<Map> queryNetwordInfo(Criteria criteria) {
    return getTemplate().queryForList("cz.queryNetworkInfo", criteria);
  }
}
