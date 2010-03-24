package datashare.sign.station.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import datashare.base.model.PageSchemaAware;

public class BlobCriteria extends PageSchemaAware {

  /**
   * 数据库表
   */
  private String tableName;
  
  /**
   * 台站ID
   */
  private String stationId;
  /**
   * 台站测点ID
   */
  private String pointId;
  
  /**
   * 数据列
   */
  private String columnName;
  
  /**
   * 数据列类型：图片或WORD文档
   */
  private String columnType;
  
  /**
   * @return the columnName
   */
  public String getColumnName() {
    return columnName;
  }
  /**
   * @param columnName the columnName to set
   */
  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }
  /**
   * @return the stationId
   */
  public String getStationId() {
    return stationId;
  }
  /**
   * @param stationId the stationId to set
   */
  public void setStationId(String stationId) {
    this.stationId = stationId;
  }
  /**
   * @return the pointId
   */
  public String getPointId() {
    return pointId;
  }
  /**
   * @param pointId the pointId to set
   */
  public void setPointId(String pointId) {
    this.pointId = pointId;
  }
  /**
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }
  /**
   * @param tableName the tableName to set
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, new String[]{"tableName", "page"});
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, new String[]{"tableName", "page"});
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
  /**
   * @return the columnType
   */
  public String getColumnType() {
    return columnType;
  }
  /**
   * @param columnType the columnType to set
   */
  public void setColumnType(String columnType) {
    this.columnType = columnType;
  }
}
