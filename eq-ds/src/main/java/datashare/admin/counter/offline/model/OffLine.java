package datashare.admin.counter.offline.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.systop.core.model.BaseModel;

/**
 * 离线服务model
 * @author dhm
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="offline")
public class OffLine extends BaseModel implements Serializable {

  private Integer id;
  
  /**
   * 离线服务数据类型
   */
  private String subject;
  
  /**
   * 数据下载量
   */
  private Double dataFlow;
  
  /**
   * 被服务人名称
   */
  private String userName;
  
  /**
   * 服务时间
   */
  private String time;
  
  /**
   * 行业内外
   */
  private String industry;
  

  @Column(name="SUBJECT",nullable=false,length=255)
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Column(name="TIME",nullable=false,length=20)
  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name="DATA_FLOW",nullable=false)
  public Double getDataFlow() {
    return dataFlow;
  }

  public void setDataFlow(Double dataFlow) {
    this.dataFlow = dataFlow;
  }

  @Column(name="USER_NAME",nullable=false,length=20)
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }
}
