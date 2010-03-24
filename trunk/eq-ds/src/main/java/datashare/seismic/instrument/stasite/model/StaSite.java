package datashare.seismic.instrument.stasite.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import datashare.base.model.PageSchemaAware;

/**
 * 场地响应查询条件类
 * @author DU
 *
 */
public class StaSite extends PageSchemaAware {

  /**
   * 台网名称
   */
  private String netCode;
  
  /**
   * 台站代码查询条件
   */
  private String staCode;

  /**
   * 排序字段
   */
  private String sortProperty;
  /**
   * 排序方向
   */
  private String sortDir;
  
  /**
   * @return the netCode
   */
  public String getNetCode() {
    return netCode;
  }
  /**
   * @param netCode the netCode to set
   */
  public void setNetCode(String netCode) {
    this.netCode = netCode;
  }
  /**
   * @return the staCode
   */
  public String getStaCode() {
    return staCode;
  }
  /**
   * @param staCode the staCode to set
   */
  public void setStaCode(String staCode) {
    this.staCode = staCode;
  }
  /**
   * @return the sortProperty
   */
  public String getSortProperty() {
    return sortProperty;
  }
  /**
   * @param sortProperty the sortProperty to set
   */
  public void setSortProperty(String sortProperty) {
    this.sortProperty = sortProperty;
  }
  /**
   * @return the sortDir
   */
  public String getSortDir() {
    return sortDir;
  }
  /**
   * @param sortDir the sortDir to set
   */
  public void setSortDir(String sortDir) {
    this.sortDir = sortDir;
  }
  
  /**
   * 构造测震仪器数据表名称
   */
  public String getTableName() {
    return "STATION_SITE";
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
  
}
