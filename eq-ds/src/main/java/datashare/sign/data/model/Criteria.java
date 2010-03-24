package datashare.sign.data.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.systop.core.util.DateUtil;

import datashare.GlobalConstants;
import datashare.base.model.PageSchemaAware;

/**
 * 前兆数据查询条件
 * @author Sam
 *
 */
public class Criteria extends PageSchemaAware {
  /**
   * 前兆数据表前缀（机构代码）
   */
  public static final String QZ_TABLE_PREFIX = "QZ";
  /**
   * 测项代码
   */
  private String methodId;
  /**
   * 表类型标识
   */
  private String tableCategory;
  
  /**
   * 采样率代码
   */
  private String sampleRate;
  
  /**
   * 起始时间,如果为null，则表示是当天
   */
  private Date startDate;
  /**
   * 截止时间,如果为null，则表示是当天
   */
  private Date endDate;
  
  /**
   * 台站代码
   */
  private String stationId;
  
  /**
   * 测点代码
   */
  private String pointId;
  
  /**
   * 测项分量代码
   */
  private String itemId;
  
  /**
   * startDate排序顺序
   */
  private String order;
  
  /**
   * @return the methodId
   */
  public String getMethodId() {
    if(StringUtils.isBlank(methodId) && StringUtils.isNotBlank(itemId) &&
        itemId.length() >= 3) {
      methodId = itemId.substring(0, 3);
    }
    return methodId;
  }

  /**
   * @param methodId the methodId to set
   */
  public void setMethodId(String methodId) {
    this.methodId = methodId;
  }

  /**
   * @return the tableCategory
   */
  public String getTableCategory() {
    return tableCategory;
  }

  /**
   * @param tableCategory the tableCategory to set
   */
  public void setTableCategory(String tableCategory) {
    this.tableCategory = tableCategory;
  }

  /**
   * @return the sampleRate
   */
  public String getSampleRate() {
    return sampleRate;
  }

  /**
   * @param sampleRate the sampleRate to set
   */
  public void setSampleRate(String sampleRate) {
    this.sampleRate = sampleRate;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return (startDate == null) ? new Date() : startDate;
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
    return (endDate == null) ? new Date() : endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(Date endDate) {
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
   * 构造数据表名称：机构代码_测项代码_表类型标识_采样率代码
   */
  public String getTableName() {
    return new StringBuilder(50)
    .append(QZ_TABLE_PREFIX)
    .append(GlobalConstants.SPLITTER)
    .append(getMethodId()) //一定要用getMethodId而不要用methodId，因为methodId可能为空
    .append(GlobalConstants.SPLITTER)
    .append(tableCategory)
    .append(GlobalConstants.SPLITTER)
    .append(sampleRate)
    .toString();
  }
  
  /**
   * 获得日志查询的表名
   * @return
   */
  public String getLogTableName(){
    return new StringBuilder(50)
    .append(QZ_TABLE_PREFIX)
    .append(GlobalConstants.SPLITTER)
    .append(getMethodId()) //一定要用getMethodId而不要用methodId，因为methodId可能为空
    .append(GlobalConstants.SPLITTER)
    .append(tableCategory)
    .toString();
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
   * 构造由查询条件组成的query string，页面上可以用这个方法组成一个url：
   * <pre>
   * csv.do${model}
   * </pre>
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return new StringBuffer(200)
    .append("?model.methodId=")
    .append(getMethodId())
    .append("&model.itemId=")
    .append(getItemId())
    .append("&model.pointId=")
    .append(getPointId())
    .append("&model.sampleRate=")
    .append(getSampleRate())
    .append("&model.tableCategory=")
    .append(getTableCategory())
    .append("&model.stationId=")
    .append(getStationId())
    .append("&model.startDate=")
    .append(DateUtil.getDateTime("yyyy-MM-dd", getStartDate()))
    .append("&model.endDate=")
    .append(DateUtil.getDateTime("yyyy-MM-dd", getEndDate()))
    .toString();
  }

  /**
   * @return the order
   */
  public String getOrder() {
    return (GlobalConstants.ORDER_DESC.equalsIgnoreCase(order)) ? 
        GlobalConstants.ORDER_DESC : GlobalConstants.ORDER_ASC;
  }

  /**
   * @param order the order to set
   */
  public void setOrder(String order) {
    this.order = order;
  }
}
