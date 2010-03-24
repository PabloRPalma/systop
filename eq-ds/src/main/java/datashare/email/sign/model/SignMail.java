package datashare.email.sign.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;

import datashare.sign.data.model.Criteria;

@Entity
@Table(name = "sign_mails")
public class SignMail extends BaseModel {
  /**
   * 主键
   */
  private Integer id;
  /**
   * 订阅用户
   */
  private User subscriber;
  /**
   * 邮件地址，缺省是用户注册邮件地址
   */
  private String emailAddr;
  /**
   * 台站ID
   */
  private String stationId;
  /**
   * 测项分量
   */
  private String itemId;
  /**
   * 测点
   */
  private String pointId;
  /**
   * 数据类型（DYU、DYS）
   */
  private String dataType;
  /**
   * 采样率，只包含在采样率管理中，允许订阅的
   */
  private String sampleRate;
  /**
   * 状态，0(default)表示没审核，1表示审核通过
   */
  private String state;
  /**
   * 最后一次发送时间，初次发送时间同{@link #createDate}
   */
  private Date lastSendDate;
  /**
   * 订阅创建时间
   */
  private Date createDate;
  
  private String stationName;
  private String itemName;
  private String sampleRateName;
  private String dataTypeName;
  /**
   * @return the id
   */
  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", unique = true, nullable = false)
  public Integer getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(Integer id) {
    this.id = id;
  }
 
  /**
   * @return the subscriber
   */
  @ManyToOne(cascade = { }, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User getSubscriber() {
    return subscriber;
  }
  /**
   * @param subscriber the subscriber to set
   */
  public void setSubscriber(User subscriber) {
    this.subscriber = subscriber;
  }
  /**
   * @return the emailAddr
   */
  @Column(name = "email_addr")
  public String getEmailAddr() {
    return emailAddr;
  }
  /**
   * @param emailAddr the emailAddr to set
   */
  public void setEmailAddr(String emailAddr) {
    this.emailAddr = emailAddr;
  }
  /**
   * @return the stationId
   */
  @Column(name = "station_id")
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
   * @return the itemId
   */
  @Column(name = "item_id")
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
   * @return the pointId
   */
  @Column(name = "point_id")
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
   * @return the dataType
   */
  @Column(name = "data_type")
  public String getDataType() {
    return dataType;
  }
  /**
   * @param dataType the dataType to set
   */
  public void setDataType(String dataType) {
    this.dataType = dataType;
  }
  /**
   * @return the sampleRate
   */
  @Column(name = "sample_rate")
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
   * @return the state
   */
  @Column(name = "state", length = 1, columnDefinition = "char(1) default '0'")
  public String getState() {
    return state;
  }
  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }
  /**
   * @return the lastSendDate
   */
  @Column(name = "last_send_date")
  public Date getLastSendDate() {
    return lastSendDate;
  }
  /**
   * @param lastSendDate the lastSendDate to set
   */
  public void setLastSendDate(Date lastSendDate) {
    this.lastSendDate = lastSendDate;
  }
  /**
   * @return the createDate
   */
  @Column(name = "create_date")
  public Date getCreateDate() {
    return createDate;
  }
  /**
   * @param createDate the createDate to set
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  
  /**
   * 转换为前兆数据查询条件.<b>Schema属性没有设置</b>
   * 如果没有审核通过，则抛出{@link java.lang.IllegalStateException}
   */
  @Transient
  public Criteria getCriteria() {
    if("0".equals(state)) {
      throw new IllegalStateException("前兆数据邮件没有审核通过,id=" + id);
    }
    Criteria criteria = new Criteria();
    criteria.setStationId(stationId);
    criteria.setItemId(itemId);
    criteria.setMethodId(itemId.substring(0, 3));
    criteria.setTableCategory(dataType);
    criteria.setPointId(pointId);
    criteria.setSampleRate(sampleRate);
    criteria.setStartDate((lastSendDate == null) ? createDate : lastSendDate);
    criteria.setEndDate(new Date());
    return criteria;
  }
  
  @Transient
  public String getStationName() {
    return stationName;
  }
  
  public void setStationName(String stationName) {
    this.stationName = stationName;
  }
  
  @Transient
  public String getItemName() {
    return itemName;
  }
  
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }
  
  @Transient
  public String getSampleRateName() {
    return sampleRateName;
  }
  
  public void setSampleRateName(String sampleRateName) {
    this.sampleRateName = sampleRateName;
  }
  
  @Transient
  public String getDataTypeName() {
    return dataTypeName;
  }
  
  public void setDataTypeName(String dataTypeName) {
    this.dataTypeName = dataTypeName;
  }
}
