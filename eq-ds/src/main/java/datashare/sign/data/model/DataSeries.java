package datashare.sign.data.model;

import java.util.Date;

import com.systop.core.util.DateUtil;

public class DataSeries {
  private Date time;
  private String strTime;
  private String value;
  
  public DataSeries() {
  }
  
  public DataSeries(Date time, String value) {
    this.time = time;
    this.value = value;
  }
  
  public DataSeries(Date time, String strTime, String value) {
    this.time = time;
    this.strTime = strTime;
    this.value = value;
  }
  
  /**
   * @return the time
   */
  public Date getTime() {
    return time;
  }
  
  /**
   * @param time the time to set
   */
  public void setTime(Date time) {
    this.time = time;
  }
  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }
  /**
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }
  
  @Override
  public String toString() {
    return new StringBuffer(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", time))
    .append(":").append(value).toString();
  }

  /**
   * @return the strTime
   */
  public String getStrTime() {
    return strTime;
  }

  /**
   * @param strTime the strTime to set
   */
  public void setStrTime(String strTime) {
    this.strTime = strTime;
  }
}
