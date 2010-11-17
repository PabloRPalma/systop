package quake.seismic.station.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import quake.GlobalConstants;
import quake.base.model.PageSchemaAware;

/**
 * 测震台站查询条件
 * 
 * @author DU
 * 
 */
public class Criteria extends PageSchemaAware {
  /**
   * 台站ID
   */
  private String id;
  /**
   * 台网名称
   */
  private String netCode;

  /**
   * 台站代码
   */
  private String staCode;

  /**
   * 起始日期查询条件
   */
  private String startDate;

  /**
   * 结束日期查询条件
   */
  private String endDate;

  /**
   * 起始年限的开始时间
   */
  private Date startTimeOfTheYear;

  /**
   * 起始年限的结束时间
   */
  private Date endTimeOfTheYear;

  /**
   * 地震计类型标示
   */
  private String sensorModel;

  /**
   * 数据采集类型标示
   */
  private String digitizerModel;

  /**
   * 台站类型
   */
  String staType;
  /**
   * 台基类型
   */
  String rockType;
  /**
   * 台站名称
   */
  String staName;
  
  /**
   * 通道id
   */
  String channelId;
  /**
   * 排序顺序
   */
  private String order;

  /**
   * @return the order
   */
  public String getOrder() {
    return (GlobalConstants.ORDER_DESC.equalsIgnoreCase(order)) ? GlobalConstants.ORDER_DESC
        : GlobalConstants.ORDER_ASC;
  }

  /**
   * @param order the order to set
   */
  public void setOrder(String order) {
    this.order = order;
  }

  /**
   * 构造测震台站数据表名称
   */
  public String getTableName() {
    return "STATION_INFO";
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, new String[] { "tableName", "page" });
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, new String[] { "tableName", "page" });
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

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
   * @return the startTimeOfTheYear
   */
  public Date getStartTimeOfTheYear() {
    return startTimeOfTheYear;
  }

  /**
   * @param startTimeOfTheYear the startTimeOfTheYear to set
   */
  public void setStartTimeOfTheYear(Date startTimeOfTheYear) {
    this.startTimeOfTheYear = startTimeOfTheYear;
  }

  /**
   * @return the endTimeOfTheYear
   */
  public Date getEndTimeOfTheYear() {
    return endTimeOfTheYear;
  }

  /**
   * @param endTimeOfTheYear the endTimeOfTheYear to set
   */
  public void setEndTimeOfTheYear(Date endTimeOfTheYear) {
    this.endTimeOfTheYear = endTimeOfTheYear;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStaCode() {
    return staCode;
  }

  public void setStaCode(String staCode) {
    this.staCode = staCode;
  }

  public String getStaType() {
    return staType;
  }

  public void setStaType(String staType) {
    this.staType = staType;
  }

  public String getRockType() {
    return rockType;
  }

  public void setRockType(String rockType) {
    this.rockType = rockType;
  }

  public String getStaName() {
    return staName;
  }

  public void setStaName(String staName) {
    this.staName = staName;
  }

  public String getSensorModel() {
    return sensorModel;
  }

  public void setSensorModel(String sensorModel) {
    this.sensorModel = sensorModel;
  }

  public String getDigitizerModel() {
    return digitizerModel;
  }

  public void setDigitizerModel(String digitizerModel) {
    this.digitizerModel = digitizerModel;
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }
}
