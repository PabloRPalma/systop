package quake.seismic.data.catalog.model;

public class MagCriteria {
  /**
   * 数据库名
   */
  private String schema;
  /**
   * 表名
   */
  private String tableName;
  /**
   * 地震目录ID
   */
  private String catId;
  /**
   * M_source
   */
  private String magName;

  /**
   * 关联震级表（Mag_C）的ID
   */
  private String magcId;
  
  /**
   * @return the magcId
   */
  public String getMagcId() {
    return magcId;
  }

  /**
   * @param magcId the magcId to set
   */
  public void setMagcId(String magcId) {
    this.magcId = magcId;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

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

  public String getMagName() {
    return magName;
  }

  public void setMagName(String magName) {
    this.magName = magName;
  }
}
