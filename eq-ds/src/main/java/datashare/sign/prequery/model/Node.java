package datashare.sign.prequery.model;

import java.io.Serializable;

/**
 * 用于描述数据库查询结果中的一条记录
 * 
 * @author Lunch
 */
public class Node implements Serializable {

  /** 对应查询的表名 */
  private String tableName;

  /** 台站编码 */
  private String stationId;

  /** 台站名称 */
  private String stationName;

  /** 测点编码 */
  private String pointId;

  /** 测点名称 */
  private String pointName;

  /** 测项分量编码 */
  private String itemId;

  /** 测项分量名称 */
  private String itemName;

  /** 数据开始时间 */
  private String startDate;

  /** 数据截至时间 */
  private String endDate;

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
  public String getStartDate() {
    return startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
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

}
