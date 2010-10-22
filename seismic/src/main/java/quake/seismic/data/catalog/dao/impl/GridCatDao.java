package quake.seismic.data.catalog.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import quake.seismic.data.EQTimeFormat;
import quake.seismic.data.catalog.dao.AbstractCatDao;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.catalog.model.MagCriteria;

import com.systop.core.dao.support.Page;


/**
 * 地震目录查询dao
 * @author wbb
 */
@Repository
public class GridCatDao extends AbstractCatDao<Page> {
  
  @SuppressWarnings("unchecked")
  @Override
  public Page query(Criteria criteria) {
    String sql = "";
    String sqlCount = "";
    //单表查询，还是级连震级表查询
    if(StringUtils.isNotBlank(criteria.getMagTname())) {//关联震级表
      sql = SQL_CAT_MAG_ID;
      sqlCount = SQL_CAT_MAG_COUNT_ID;
    } else { //不关联震级表
      sql = SQL_ID;
      sqlCount = SQL_COUNT_ID;
    }
    int count = (Integer) (getTemplate().queryForObject(sqlCount, criteria));
    List<Map> rows = getTemplate().queryForList(sql, criteria, criteria.getPage().getStartIndex(),
        criteria.getPage().getPageSize());
    
    if(StringUtils.isNotBlank(criteria.getMagTname())) {//关联震级表
      setMagM(rows, criteria);
    }
    criteria.getPage().setData(EQTimeFormat.getEqTimeValue(rows, "O_TIME", "O_TIME_FRAC"));
    criteria.getPage().setRows(count);
    return criteria.getPage();
  }
  
  /**
   * 地震目录单表查询,获取GIS显示所需要数据，无分页查询
   * @param criteria
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Map> queryForGis(Criteria criteria) {
    String sql = SQL_CAT_MAG_ID;
    if(StringUtils.isBlank(criteria.getMagTname())){
      sql = SQL_ID;
    }
    List<Map> rows = getTemplate().queryForList(sql, criteria);
    return rows;
  }
  
  /**
   * 根据地震目录ID查询震级
   * @param magCriteria
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<Map> getMagM(MagCriteria magCriteria){
    List<Map> list = getTemplate().queryForList(SQL_MAG_NAME,magCriteria);
    return list.isEmpty() ? null : list;
  }
  
  /**
   * 查询所有地震目录震级
   * @param rows 地震目录查询结果
   * @param criteria
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<Map> setMagM(List<Map> rows,Criteria criteria){
    MagCriteria magCriteria = new MagCriteria();
    magCriteria.setSchema(criteria.getSchema());
    magCriteria.setTableName(criteria.getMagTname());
    
    for (Map catalog : rows) {
      String catalogId = (String)catalog.get("ID");
      String magcId = (String)catalog.get("MAGC_ID");
      if(StringUtils.isBlank(catalogId)){
        continue;
      }
      magCriteria.setCatId(catalogId);
      //根据关联震级表的ID查询，只按照catalogId查询MAG_C表里有重复数据
      magCriteria.setMagcId(magcId);
      List<Map> magList = getMagM(magCriteria);
      if (magList != null && magList.size() > 0){
        for (Map magtype : magList) {
          catalog.put(magtype.get("MAG_NAME"), magtype.get("MAG_VAL"));
        }
      }
    }
    return rows;
  }
}
