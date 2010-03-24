package datashare.seismic.data.seed.model;

import datashare.base.model.PageSchemaAware;

/**
 * 台站中文名查询条件
 * @author dhm
 *
 */
public class StaCriteria extends PageSchemaAware{

  /**
   * 数据库schema
   */
  private String schema;
  
  /**
   * 台网代码
   */
  private String netCode;
  
  /**
   * 台站代码
   */
  private String staCode;
  
  public String getNetCode() {
    return netCode;
  }
  
  public void setNetCode(String netCode) {
    this.netCode = netCode;
  }
  
  public String getStaCode() {
    return staCode;
  }
  
  public void setStaCode(String staCode) {
    this.staCode = staCode;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }
}
