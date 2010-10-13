package quake.email.seismic.model;

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

import quake.seismic.data.catalog.model.Criteria;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;


@SuppressWarnings("serial")
@Entity
@Table(name = "seismic_mails")
public class SeismicMail extends BaseModel {
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
   * 地震目录表名
   */
  private String catalog;
  
  /**
   * 地震目录名称
   */
  private String catalogName;
  /**
   * 开始经度
   */
  private Double startEpiLon;
  /**
   * 结束经度
   */
  private Double endEpiLon;
  /**
   * 开始纬度
   */
  private Double startEpiLat;
  /**
   * 结束纬度
   */
  private Double endEpiLat;
  /**
   * 最大震级
   */
  private Double maxM;
  /**
   * 最小震级
   */
  private Double minM;
  /**
   * 最后发送时间
   */
  private Date lastSendDate;
  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 是否通过审核，0（default）表示没有通过审核，1表示通过审核
   */
  private String state;
  
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
   * @return the catalog
   */
  public String getCatalog() {
    return catalog;
  }
  /**
   * @param catalog the catalog to set
   */
  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }
  
  /**
   * @return the catalogName
   */
  @Column(name = "catalog_name")
  public String getCatalogName() {
    return catalogName;
  }
  /**
   * @param catalogName the catalogName to set
   */
  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }
  /**
   * @return the startEpiLon
   */
  @Column(name = "start_epi_lon", columnDefinition = "numeric(10,5)")
  public Double getStartEpiLon() {
    return startEpiLon;
  }
  /**
   * @param startEpiLon the startEpiLon to set
   */
  public void setStartEpiLon(Double startEpiLon) {
    this.startEpiLon = startEpiLon;
  }
  /**
   * @return the endEpiLon
   */
  @Column(name = "end_epi_lon", columnDefinition = "numeric(10,5)")
  public Double getEndEpiLon() {
    return endEpiLon;
  }
  /**
   * @param endEpiLon the endEpiLon to set
   */
  public void setEndEpiLon(Double endEpiLon) {
    this.endEpiLon = endEpiLon;
  }
  /**
   * @return the startEpiLat
   */
  @Column(name = "start_epi_lat", columnDefinition = "numeric(10,5)")
  public Double getStartEpiLat() {
    return startEpiLat;
  }
  /**
   * @param startEpiLat the startEpiLat to set
   */
  public void setStartEpiLat(Double startEpiLat) {
    this.startEpiLat = startEpiLat;
  }
  /**
   * @return the endEpiLat
   */
  @Column(name = "end_epi_lat", columnDefinition = "numeric(10,5)")
  public Double getEndEpiLat() {
    return endEpiLat;
  }
  /**
   * @param endEpiLat the endEpiLat to set
   */
  public void setEndEpiLat(Double endEpiLat) {
    this.endEpiLat = endEpiLat;
  }
  /**
   * @return the maxM
   */
  @Column(name = "max_m", columnDefinition = "numeric(4,2)")
  public Double getMaxM() {
    return maxM;
  }
  /**
   * @param maxM the maxM to set
   */
  public void setMaxM(Double maxM) {
    this.maxM = maxM;
  }
  /**
   * @return the minM
   */
  @Column(name = "min_m", columnDefinition = "numeric(4,2)")
  public Double getMinM() {
    return minM;
  }
  /**
   * @param minM the minM to set
   */
  public void setMinM(Double minM) {
    this.minM = minM;
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
   * @return the verified
   */
  @Column(name = "state", length = 1, columnDefinition = "char(1) default '0'")
  public String getState() {
    return state;
  }
  /**
   * @param verified the verified to set
   */
  public void setState(String state) {
    this.state = state;
  }
  
  /**
   * 从测震订阅邮件中中得到查询条件，其中排序条件为null，也就是按照asc排序。
   * 如果没有审核通过则抛出抛出{@link java.lang.IllegalStateException}
   */
  @Transient
  public Criteria getCriteria() {
    if("0".equals(state)) {
      throw new IllegalStateException("测震数据邮件没有审核通过,id=" + id);
    }
    Criteria criteria = new Criteria();
    criteria.setTableName(catalog);
    criteria.setStartLon(startEpiLon);
    criteria.setEndLon(endEpiLon);
    criteria.setStartLat(startEpiLat);
    criteria.setEndLat(endEpiLat);
    criteria.setStartM(minM);
    criteria.setEndM(maxM);
    criteria.setStartDate((lastSendDate == null) ? createDate : lastSendDate);
    criteria.setEndDate(new Date());
    return criteria;
  }
  
}
