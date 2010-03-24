package datashare.admin.samplerate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

@Entity
@Table(name = "sample_rates")
public class SampleRate extends BaseModel implements Serializable {
  /**
   * 主键,采样率代码
   * 
   * @see {@link eq.admin.ds.DsConstants#PK}
   */
  private String id;
  /**
   * 采样率名称
   */
  private String name;
  /**
   * 采样率对应的时间格式，例如，秒值是yyyy-MM-dd HH:mm:ss
   */
  private String dateFormat;
  /**
   * 在该采样率下，每天采集多少数据，例如，秒值是86400，日均值是1
   */
  private Integer dataAmount;

  /**
   * 使用stock显示的时间间隔，例如，日均值可以显示1个月、两个月，那么就是MM； 小时值则是DD;秒值是hh;
   */
  private String stockPeriod;
  /**
   * 使用stock显示的时间间隔的名称，例如，日均值可以显示1个月、两个月，那么就是“月”； 小时值则是“天”;秒值是“小时”;
   */
  private String stockPeriodName;

  /**
   * 使用stock显示的时间格式，与<code>dateFormat</code>不同，它的日期部分都是 大写，时分秒都是小写，例如，秒值：YYYY-MM-DD hh:mm:ss
   */
  private String stockDateFormat;

  /**
   * 可以订阅，1表示可以，0表示不可以
   */
  private String forMail = "0";
  
  /**
   *排序顺序 
   */
  private Integer sort;
  /**
   * 是否启用
   */
  private String enabled = "0";

  /**
   * @return the enabled
   */
  @Column(name = "enabled", length = 1, columnDefinition = "char(1) default '0'")
  public String getEnabled() {
    return enabled;
  }

  /**
   * @param enabled the enabled to set
   */
  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  /**
   * @return the pk
   */
  @Id
  @GeneratedValue(generator = "PK")
  @GenericGenerator(name = "PK", strategy = "assigned")
  @Column(name = "id")
  public String getId() {
    return id;
  }

  /**
   * @param pk the pk to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the dateFormat
   */
  @Column(name = "date_format")
  public String getDateFormat() {
    return dateFormat;
  }

  /**
   * @param dateFormat the dateFormat to set
   */
  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  /**
   * @return the dataAmount
   */
  @Column(name = "data_amount")
  public Integer getDataAmount() {
    return dataAmount;
  }

  /**
   * @param dataAmount the dataAmount to set
   */
  public void setDataAmount(Integer dataAmount) {
    this.dataAmount = dataAmount;
  }

  /**
   * @return the stockPeriod
   */
  @Column(name = "stock_period")
  public String getStockPeriod() {
    return stockPeriod;
  }

  /**
   * @param stockPeriod the stockPeriod to set
   */
  public void setStockPeriod(String stockPeriod) {
    this.stockPeriod = stockPeriod;
  }

  /**
   * @return the stockPeriodName
   */
  @Column(name = "stock_period_name")
  public String getStockPeriodName() {
    return stockPeriodName;
  }

  /**
   * @param stockPeriodName the stockPeriodName to set
   */
  public void setStockPeriodName(String stockPeriodName) {
    this.stockPeriodName = stockPeriodName;
  }

  /**
   * @return the forMail
   */
  @Column(name = "for_mail", length = 1, columnDefinition = "char(1) default '0'")
  public String getForMail() {
    return forMail;
  }

  /**
   * @param forMail the forMail to set
   */
  public void setForMail(String forMail) {
    this.forMail = forMail;
  }
  /**
   * @return the sort
   */
  @Column(name = "sort", columnDefinition="numeric(2,0)")
  public Integer getSort() {
    return sort;
  }

  /**
   * @param sort the sort to set
   */
  public void setSort(Integer sort) {
    this.sort = sort;
  }
  /**
   * @return the stockDateFormat
   */
  @Column(name = "stock_date_format")
  public String getStockDateFormat() {
    return stockDateFormat;
  }

  /**
   * @param stockDateFormat the stockDateFormat to set
   */
  public void setStockDateFormat(String stockDateFormat) {
    this.stockDateFormat = stockDateFormat;
  }

  /**
   * 如果这个采样率是可以被订阅的，返回<code>true</code>
   * 
   * @return
   */
  @Transient
  public boolean isSubscible() {
    return "1".equals(forMail);
  }

  /**
   * 得到该采样率的毫秒递增值，用于计算单个数据的采样时间
   */
  @Transient
  public Integer getMselInc() {
    return (86400 / dataAmount) * 1000;
  }


}
