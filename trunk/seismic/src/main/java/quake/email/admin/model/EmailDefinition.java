package quake.email.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "email_def")
public class EmailDefinition {
  /**
   * 主键
   */
  private String id;
  /**
   * 测震数据发送频率，cron表达式
   */
  private String freqSeismic;
  /**
   * 前兆数据发送频率，cron表达式
   */
  private String freqSign;
  /**
   * 最小震级
   */
  private Integer minM;
  /**
   * 最多测项分量数量
   */
  private Integer maxItems;
  /**
   * 版本号
   */
  private Integer version;
  /**
   * @return the id
   */
  @Id
  @GeneratedValue(generator = "dsPK")
  @GenericGenerator(name = "dsPK", strategy = "assigned")
  @Column(name = "id")
  public String getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * @return the freqSeismic
   */
  @Column(name = "freq_seismic")
  public String getFreqSeismic() {
    return freqSeismic;
  }
  /**
   * @param freqSeismic the freqSeismic to set
   */
  public void setFreqSeismic(String freqSeismic) {
    this.freqSeismic = freqSeismic;
  }
  /**
   * @return the freqSign
   */
  @Column(name = "freq_sign")
  public String getFreqSign() {
    return freqSign;
  }
  /**
   * @param freqSign the freqSign to set
   */
  public void setFreqSign(String freqSign) {
    this.freqSign = freqSign;
  }
  /**
   * @return the minM
   */
  @Column(name = "min_m")
  public Integer getMinM() {
    return minM;
  }
  /**
   * @param minM the minM to set
   */
  public void setMinM(Integer minM) {
    this.minM = minM;
  }
  /**
   * @return the maxItems
   */
  @Column(name = "max_items")
  public Integer getMaxItems() {
    return maxItems;
  }
  /**
   * @param maxItems the maxItems to set
   */
  public void setMaxItems(Integer maxItems) {
    this.maxItems = maxItems;
  }
  /**
   * @return the version
   */
  @Version
  @Column(name = "version")
  public Integer getVersion() {
    return version;
  }
  /**
   * @param version the version to set
   */
  public void setVersion(Integer version) {
    this.version = version;
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return new StringBuffer(freqSeismic)
    .append("\n").append(freqSign).toString();
  }
}
