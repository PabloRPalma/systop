package quake.seismic.data.phase.model;

import java.util.HashMap;
import java.util.Map;

import quake.base.model.PageSchemaAware;


/**
 * 震相查询条件
 * @author wbb
 */
public class PhaseCriteria extends PageSchemaAware {
  /**
   * 震相表名
   */
  private String tableName;
  
  /**
   * 地震目录ID
   */
  private String catId;
  
  private Map<String, String> catalog = new HashMap<String, String>();

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getCatId() {
    return catId;
  }

  public void setCatId(String catId) {
    this.catId = catId;
  }

  public Map<String, String> getCatalog() {
    return catalog;
  }

  public void setCatalog(Map<String, String> catalog) {
    this.catalog = catalog;
  }
}
