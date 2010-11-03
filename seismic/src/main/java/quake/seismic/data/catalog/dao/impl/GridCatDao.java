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
   */
  @SuppressWarnings("unchecked")
  @Override
  public Page query(Criteria criteria) {    
    int count = (Integer) (getTemplate().queryForObject(SQL_COUNT_ID, criteria));
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria);
    
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
   * @param criteria
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Map> queryForGis(Criteria criteria) {
    //GIS显示地震目录条数设定值为常量自定义的最大值
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria, 0, SeismicConstants.MAX_SIZE);
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
}
