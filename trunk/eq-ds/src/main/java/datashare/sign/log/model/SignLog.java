package datashare.sign.log.model;

import java.sql.Date;

/**
 * 用于描述前兆数据对应的日志信息
 * @author Lunch
 */
public class SignLog {

  /** 台站编号 */
  private String stationId;
  
  /** 台站名称 */
  private String stationName;
  
  /** 测点编号 */
  private String pointId;
  
  /** 测点名称 */
  private String pointName;
  
  /** 测项分量编号 */
  private String itemId;
  
  /** 测项分量名称 */
  private String itemName;
  
  /** 开始日期 */
  private Date startDate;
  
  /** 结束日期 */
  private Date endDate;
  
  /** 日志类型码 */
  private String evtId;
  
  /** 日志描述 */
  private String evtDesc;
  
  /** 日志填写人员 */
  private String logPerson;
  
  /** 是否进行预处理 */
  private Integer isProcessed;
  
  /** 是否为自动处理 */
  private Integer isAutoProcessed;
  
  /** 处理软件名称 */
  private String processSoftWare;
  
  /** 置为null数据个数 */
  private Integer ProNullNum;
  
  /** 处理人员 */
  private String evtPeson;
  
  /** 预处理依据 */
  private String evtProcessing;
  
  /** 预处理时间  */
  private Date proDate;
  
  /**  预处理描述 */
  private String proDesc;
  
  /** 备注 */
  private String evtNote;

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
   * @return the stationName
   */
  public String getStationName() {
    return stationName;
  }

  /**
   * @param stationName the stationName to set
   */
  public void setStationName(String stationName) {
    this.stationName = stationName;
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
   * @return the pointName
   */
  public String getPointName() {
    return pointName;
  }

  /**
   * @param pointName the pointName to set
   */
  public void setPointName(String pointName) {
    this.pointName = pointName;
  }

  /**
   * @return the itemId
   */
  public String getItemId() {
    return itemId;
  }

  /**
   * @param itemId the itemId to set
   */
  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  /**
   * @return the itemName
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * @param itemName the itemName to set
   */
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  /**
   * @return the evtId
   */
  public String getEvtId() {
    return evtId;
  }

  /**
   * @param evtId the evtId to set
   */
  public void setEvtId(String evtId) {
    this.evtId = evtId;
  }

  /**
   * @return the evtDesc
   */
  public String getEvtDesc() {
    return evtDesc;
  }

  /**
   * @param evtDesc the evtDesc to set
   */
  public void setEvtDesc(String evtDesc) {
    this.evtDesc = evtDesc;
  }

  /**
   * @return the logPerson
   */
  public String getLogPerson() {
    return logPerson;
  }

  /**
   * @param logPerson the logPerson to set
   */
  public void setLogPerson(String logPerson) {
    this.logPerson = logPerson;
  }

  /**
   * @return the isProcessed
   */
  public Integer getIsProcessed() {
    return isProcessed;
  }

  /**
   * @param isProcessed the isProcessed to set
   */
  public void setIsProcessed(Integer isProcessed) {
    this.isProcessed = isProcessed;
  }

  /**
   * @return the isAutoProcessed
   */
  public Integer getIsAutoProcessed() {
    return isAutoProcessed;
  }

  /**
   * @param isAutoProcessed the isAutoProcessed to set
   */
  public void setIsAutoProcessed(Integer isAutoProcessed) {
    this.isAutoProcessed = isAutoProcessed;
  }

  /**
   * @return the processSoftWare
   */
  public String getProcessSoftWare() {
    return processSoftWare;
  }

  /**
   * @param processSoftWare the processSoftWare to set
   */
  public void setProcessSoftWare(String processSoftWare) {
    this.processSoftWare = processSoftWare;
  }

  /**
   * @return the proNullNum
   */
  public Integer getProNullNum() {
    return ProNullNum;
  }

  /**
   * @param proNullNum the proNullNum to set
   */
  public void setProNullNum(Integer proNullNum) {
    ProNullNum = proNullNum;
  }

  /**
   * @return the evtPeson
   */
  public String getEvtPeson() {
    return evtPeson;
  }

  /**
   * @param evtPeson the evtPeson to set
   */
  public void setEvtPeson(String evtPeson) {
    this.evtPeson = evtPeson;
  }

  /**
   * @return the evtProcessing
   */
  public String getEvtProcessing() {
    return evtProcessing;
  }

  /**
   * @param evtProcessing the evtProcessing to set
   */
  public void setEvtProcessing(String evtProcessing) {
    this.evtProcessing = evtProcessing;
  }

  /**
   * @return the proDate
   */
  public Date getProDate() {
    return proDate;
  }

  /**
   * @param proDate the proDate to set
   */
  public void setProDate(Date proDate) {
    this.proDate = proDate;
  }

  /**
   * @return the proDesc
   */
  public String getProDesc() {
    return proDesc;
  }

  /**
   * @param proDesc the proDesc to set
   */
  public void setProDesc(String proDesc) {
    this.proDesc = proDesc;
  }

  /**
   * @return the evtNote
   */
  public String getEvtNote() {
    return evtNote;
  }

  /**
   * @param evtNote the evtNote to set
   */
  public void setEvtNote(String evtNote) {
    this.evtNote = evtNote;
  }
  
  

}
