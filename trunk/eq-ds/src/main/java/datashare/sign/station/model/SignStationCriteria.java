package datashare.sign.station.model;

import datashare.base.model.PageSchemaAware;

/**
 * 前兆台站查询model
 * @author DU
 */
public class SignStationCriteria extends PageSchemaAware {
  /**
   * 学科ID
   */
  private String subjectId;
  /**
   * 学科对应的两位测项代码字符串
   */
  private String subjectItemIds;
  /**
   * 台站ID
   */
  private String stationId;
  /**
   * 台站对应的以逗号分隔的字符串
   */
  private String stationItemIds;
  /**
   * 台站测点ID
   */
  private String pointId;
  /**
   * 仪器代码 
   */
  private String instrCode;
  /** 
   * 测项代码
   */
  private String itemId;
  /**
   * 地磁方位标
   */
  private Integer aziFlagSize;
  /**
   * 地磁墩差
   */
  private Integer diffSize;
  /**
   * 分量
   */
  private Integer itemsSize;
  /**
   * 仪器运行
   */
  private Integer instrSize;
  /**
   * 仪器参数
   */
  private Integer instrParamSize;
  /**
   * 测点洞体
   */
  private Integer staCaveSize;
  /**
   * 井泉信息
   */
  private Integer staWellSize;
  /**
   * 台站相关洞体信息
   */
  private Integer statCaveSize;
  /**
   * 台站相关井信息
   */
  private Integer statWellSize;
  /**
   * 台站相关泉信息
   */
  private Integer statSpringSize;
  /**
   * 台站相关断层信息
   */
  private Integer faultsSize;
  /**
   * @return the instrCode
   */
  public String getInstrCode() {
    return instrCode;
  }
  /**
   * @param instrCode the instrCode to set
   */
  public void setInstrCode(String instrCode) {
    this.instrCode = instrCode;
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
   * @return the aziFlagSize
   */
  public Integer getAziFlagSize() {
    return aziFlagSize;
  }
  /**
   * @param aziFlagSize the aziFlagSize to set
   */
  public void setAziFlagSize(Integer aziFlagSize) {
    this.aziFlagSize = aziFlagSize;
  }
  /**
   * @return the diffSize
   */
  public Integer getDiffSize() {
    return diffSize;
  }
  /**
   * @param diffSize the diffSize to set
   */
  public void setDiffSize(Integer diffSize) {
    this.diffSize = diffSize;
  }
  /**
   * @return the itemsSize
   */
  public Integer getItemsSize() {
    return itemsSize;
  }
  /**
   * @param itemsSize the itemsSize to set
   */
  public void setItemsSize(Integer itemsSize) {
    this.itemsSize = itemsSize;
  }
  /**
   * @return the instrParamSize
   */
  public Integer getInstrParamSize() {
    return instrParamSize;
  }
  /**
   * @param instrParamSize the instrParamSize to set
   */
  public void setInstrParamSize(Integer instrParamSize) {
    this.instrParamSize = instrParamSize;
  }
  /**
   * @return the staCaveSize
   */
  public Integer getStaCaveSize() {
    return staCaveSize;
  }
  /**
   * @param staCaveSize the staCaveSize to set
   */
  public void setStaCaveSize(Integer staCaveSize) {
    this.staCaveSize = staCaveSize;
  }
  /**
   * @return the instrSize
   */
  public Integer getInstrSize() {
    return instrSize;
  }
  /**
   * @param instrSize the instrSize to set
   */
  public void setInstrSize(Integer instrSize) {
    this.instrSize = instrSize;
  }
  /**
   * @return the staWellSize
   */
  public Integer getStaWellSize() {
    return staWellSize;
  }
  /**
   * @param staWellSize the staWellSize to set
   */
  public void setStaWellSize(Integer staWellSize) {
    this.staWellSize = staWellSize;
  }
  /**
   * @return the subjectId
   */
  public String getSubjectId() {
    return subjectId;
  }
  /**
   * @param subjectId the subjectId to set
   */
  public void setSubjectId(String subjectId) {
    this.subjectId = subjectId;
  }
  /**
   * @return the subjectItemIds
   */
  public String getSubjectItemIds() {
    return subjectItemIds;
  }
  /**
   * @param subjectItemIds the subjectItemIds to set
   */
  public void setSubjectItemIds(String subjectItemIds) {
    this.subjectItemIds = subjectItemIds;
  }
  /**
   * @return the stationItemIds
   */
  public String getStationItemIds() {
    return stationItemIds;
  }
  /**
   * @param stationItemIds the stationItemIds to set
   */
  public void setStationItemIds(String stationItemIds) {
    this.stationItemIds = stationItemIds;
  }
  public Integer getStatCaveSize() {
    return statCaveSize;
  }
  public void setStatCaveSize(Integer statCaveSize) {
    this.statCaveSize = statCaveSize;
  }
  public Integer getStatWellSize() {
    return statWellSize;
  }
  public void setStatWellSize(Integer statWellSize) {
    this.statWellSize = statWellSize;
  }
  public Integer getStatSpringSize() {
    return statSpringSize;
  }
  public void setStatSpringSize(Integer statSpringSize) {
    this.statSpringSize = statSpringSize;
  }
  public Integer getFaultsSize() {
    return faultsSize;
  }
  public void setFaultsSize(Integer faultsSize) {
    this.faultsSize = faultsSize;
  }
}
