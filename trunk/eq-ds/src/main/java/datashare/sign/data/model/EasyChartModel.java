package datashare.sign.data.model;


/**
 * 
 * EasyCharts显示所需数据
 * 
 */
public class EasyChartModel {
  /**
   * 显示的数据，格式为:时间|数值,时间|数值,时间|数值...
   */
  private String data;
  /**
   * 最大时间
   */
  private String minDate;
  /**
   * 最小时间
   */
  private String maxDate;
  /**
   * 最大数据
   */
  private String maxRange;
  /**
   * 最小数据
   */
  private String minRange;
  /**
   * 时间格式
   */
  private String dateFormat;
  
  /**
   * 台站ID
   */
  private String stationId;
  /**
   * 测相分量
   */
  private String itemId;
  
  /**
   * 测点
   */
  private String pointId;
  
  /**
   * Line title
   */
  private String title;
  
  /**
   * easycharts组件所需的数据,用于计算时间间隔
   */
  private String timeScale;

  public EasyChartModel(String data, String maxDate, String minDate, String maxRange,
      String minRange, String dateFormat, String timeScale) {
    this.data = data;
    this.minDate = minDate;
    this.maxDate = maxDate;
    this.maxRange = maxRange;
    this.minRange = minRange;
    this.dateFormat = dateFormat;
    this.timeScale = timeScale;
  }

  public String getData() {
    return data;
  }

  public String getMinDate() {
    return minDate;
  }

  public String getMaxDate() {
    return maxDate;
  }

  public String getMaxRange() {
    return maxRange;
  }

  public String getMinRange() {
    return minRange;
  }

  public String getDateFormat() {
    return dateFormat;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTimeScale() {
    return timeScale;
  }

  public String getStationId() {
    return stationId;
  }

  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getPointId() {
    return pointId;
  }

  public void setPointId(String pointId) {
    this.pointId = pointId;
  }
}
