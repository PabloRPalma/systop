package quake.seismic.data.catalog.model;

import java.util.Date;

import quake.base.model.PageSchemaAware;


/**
 * 地震目录查询条件
 * @author DU
 */
public class Criteria extends PageSchemaAware {
  
  private String seedId;
  /**
   * 台网代码
   */
  private String netCode;
  /**
   * 地震类型
   */
  private String eqType;
  /**
   * 地震序列标识
   */
  private String sequenName;
  /**
   * 查询类型 0：矩形；1：圆形
   */
  private String isRoundQuery;
  /**
   * 离中心经纬度的距离
   */
  private String range;
  /**
   * 定位台数
   */
  private String locStn;
  /**
   * 台站名称（台网代码/台站代码）用于震相表查询时的字段
   */
  private String staNetCode;
  /**
   * 震相类型_用于震相表查询时的字段
   */
  private String phaseType;
  /**
   * 震相名称_用于震相表查询时的字段
   */
  private String phaseName;
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
   * 震中地名
   */
  private String location;
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
   * 地震目录ID
   */
  private String qcId;
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

  public String getQcId() {
    return qcId;
  }

  public void setQcId(String qcId) {
    this.qcId = qcId;
  }
  public String getNetCode() {
    return netCode;
  }

  public void setNetCode(String netCode) {
    this.netCode = netCode;
  }
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getEqType() {
    return eqType;
  }

  public void setEqType(String eqType) {
    this.eqType = eqType;
  }

  public String getSequenName() {
    return sequenName;
  }

  public void setSequenName(String sequenName) {
    this.sequenName = sequenName;
  }

  public String getSeedId() {
    return seedId;
  }

  public void setSeedId(String seedId) {
    this.seedId = seedId;
  }


  public String getIsRoundQuery() {
    return isRoundQuery;
  }

  public void setIsRoundQuery(String isRoundQuery) {
    this.isRoundQuery = isRoundQuery;
  }

  public String getRange() {
    return range;
  }

  public void setRange(String range) {
    this.range = range;
  }

  public String getLocStn() {
    return locStn;
  }

  public void setLocStn(String locStn) {
    this.locStn = locStn;
  }

  public String getStaNetCode() {
    return staNetCode;
  }

  public void setStaNetCode(String staNetCode) {
    this.staNetCode = staNetCode;
  }

  public String getPhaseType() {
    return phaseType;
  }

  public void setPhaseType(String phaseType) {
    this.phaseType = phaseType;
  }

  public String getPhaseName() {
    return phaseName;
  }

  public void setPhaseName(String phaseName) {
    this.phaseName = phaseName;
  }
}
