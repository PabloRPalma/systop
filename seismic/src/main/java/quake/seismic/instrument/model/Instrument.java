package quake.seismic.instrument.model;

import java.util.Date;

import quake.base.model.PageSchemaAware;

/**
 * 仪器查询条件类
 * @author DU
 *
 */
public class Instrument extends PageSchemaAware {

  /**
   * 台网代码
   */
  private String netCode;
  /**
   * 用途
   */
  private String useType;
  /**
   * 仪器类型
   */
  private String instrType;
  /**
   * 仪器型号
   */
  private String instrModel;
  /**
   * 启用开始时间
   */
  private Date startDate;
  /**
   * 启用结束时间
   */
  private Date endDate;
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
  public String getUseType() {
    return useType;
  }
  public void setUseType(String useType) {
    this.useType = useType;
  }
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public String getInstrType() {
    return instrType;
  }
  public void setInstrType(String instrType) {
    this.instrType = instrType;
  }
  public String getInstrModel() {
    return instrModel;
  }
  public void setInstrModel(String instrModel) {
    this.instrModel = instrModel;
  }
  
}
