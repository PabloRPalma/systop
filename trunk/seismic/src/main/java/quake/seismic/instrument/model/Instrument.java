package quake.seismic.instrument.model;

import quake.base.model.PageSchemaAware;

/**
 * 仪器查询条件类
 * @author DU
 *
 */
public class Instrument extends PageSchemaAware {

  private String netCode;
  /**
   * 排序字段
   */
  private String sortProperty;
  /**
   * 排序方向
   */
  private String sortDir;
  
  public String getNetCode() {
    return netCode;
  }
  public void setNetCode(String netCode) {
    this.netCode = netCode;
  }
  public String getSortProperty() {
    return sortProperty;
  }
  public void setSortProperty(String sortProperty) {
    this.sortProperty = sortProperty;
  }
  public String getSortDir() {
    return sortDir;
  }
  public void setSortDir(String sortDir) {
    this.sortDir = sortDir;
  }
  
}
