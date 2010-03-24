package datashare.seismic.data.catalog.model;

import java.util.Date;

import datashare.base.model.PageSchemaAware;

/**
 * 地震目录查询条件
 * @author wbb
 */
public class Criteria extends PageSchemaAware {
  /**
   * 目录表名
   */
  private String tableName;
  /**
   * 与地震目录相关的震级表名
   */
  private String magTname;

  /**
   * 与地震目录相关的震相表名
   */
  private String phaseTname;
  /**
   * 发震开始时间
   */
  private Date startDate;
  /**
   * 发震结束时间
   */
  private Date endDate;
  /**
   * 开始伟度
   */
  private Double startLat;
  /**
   * 结束伟度
   */
  private Double endLat;
  /**
   * 开始经度
   */
  private Double startLon;
  /**
   * 结束经度
   */
  private Double endLon;
  /**
   * 开始震级
   */
  private Double startM;
  /**
   * 结束震级
   */
  private Double endM;
  
  /**
   * 排序字段
   */
  private String sortProperty;
  /**
   * 排序方向
   */
  private String sortDir;
  
  /**
   * 导出类型 WKF EQT
   */
  private String expType;
  
  /**
   * 表与对应的中文名称
   */
  private String clcName;
  
  /**
   * 地震目录震级列显示类型
   */
  private String disType;
  
  /**
   * 表与对应描述
   */
  private String clDescn;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getMagTname() {
    return magTname;
  }

  public void setMagTname(String magTname) {
    this.magTname = magTname;
  }

  public String getPhaseTname() {
    return phaseTname;
  }

  public void setPhaseTname(String phaseTname) {
    this.phaseTname = phaseTname;
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

  public Double getStartLat() {
    return startLat;
  }

  public void setStartLat(Double startLat) {
    this.startLat = startLat;
  }

  public Double getEndLat() {
    return endLat;
  }

  public void setEndLat(Double endLat) {
    this.endLat = endLat;
  }

  public Double getStartLon() {
    return startLon;
  }

  public void setStartLon(Double startLon) {
    this.startLon = startLon;
  }

  public Double getEndLon() {
    return endLon;
  }

  public void setEndLon(Double endLon) {
    this.endLon = endLon;
  }

  public Double getStartM() {
    return startM;
  }

  public void setStartM(Double startM) {
    this.startM = startM;
  }

  public Double getEndM() {
    return endM;
  }

  public void setEndM(Double endM) {
    this.endM = endM;
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

  public String getExpType() {
    return expType;
  }

  public void setExpType(String expType) {
    this.expType = expType;
  }

  public String getClcName() {
    return clcName;
  }

  public void setClcName(String clcName) {
    this.clcName = clcName;
  }

  public String getClDescn() {
    return clDescn;
  }

  public void setClDescn(String clDescn) {
    this.clDescn = clDescn;
  }

  /**
   * @return the disType
   */
  public String getDisType() {
    return disType;
  }

  /**
   * @param disType the disType to set
   */
  public void setDisType(String disType) {
    this.disType = disType;
  }
}
