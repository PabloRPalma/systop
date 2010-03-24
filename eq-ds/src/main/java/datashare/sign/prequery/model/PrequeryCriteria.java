package datashare.sign.prequery.model;

import datashare.sign.data.model.Criteria;

/**
 * 此类是Prequery专用的查询条件对象 
 * @author Lunch
 */
public class PrequeryCriteria extends Criteria {
  
  public PrequeryCriteria(){
    super();
  }
  
  /**
   * 模糊查询，用于匹配TABLENAME %tableCategory_sampleRate%
   */
  private String likeTableName;

  /** 具体查询的表名称，将来替换getTableName */
  private String queryTableName;

  /**
   * @return the likeTableName
   */
  public String getLikeTableName() {
    return likeTableName;
  }

  /**
   * @param likeTableName the likeTableName to set
   */
  public void setLikeTableName(String likeTableName) {
    this.likeTableName = likeTableName;
  }

  /**
   * @return the queryTableName
   */
  public String getQueryTableName() {
    return queryTableName;
  }

  /**
   * @param queryTableName the queryTableName to set
   */
  public void setQueryTableName(String queryTableName) {
    this.queryTableName = queryTableName;
  }
}
