package quake.special.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "specials")
public class Special extends BaseModel {
  private Integer id;
  /**
   * 地震目录
   */
  private String qc_id;
  
  /**
   * 地震目录对应的表
   */
  private String tableName;
  
  /**
   * 发震时间
   */
  private String quakeTime;
  /**
   * 地点
   */
  private String location;
  /**
   * 经度
   */
  private String longitude;
  /**
   * 纬度
   */
  private String latitude;
  /**
   * 深度
   */
  private String depth;
  /**
   * 震级
   */
  private String magnitude;

  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 是否通过审核，0（default）表示没有通过审核，1表示通过审核
   */
  private String state;
  /**
   * 震源破裂过程
   */
  private String fracture;
  /**
   * 事件波型数据
   */
  private String event_wave;

  /**
   * 台站波形记录图
   */
  private String station_wave;

  /**
   * 烈度分布
   */
  private String intensity;
  /**
   * 震源机制解
   */
  private String mechanism;
  /**
   * 震中区M-T图
   */
  private String m_t;
  /**
   * 历史地震分布图
   */
  private String history_pic;
  /**
   * 影响地区
   */
  private String area;
  /**
   * 震中分布图
   */
  private String epifocus;
  
  /**
   * 专题标题
   */
  private String title;
  /**
   * 前台显示图片
   */
  private String front_pic;
  /**
   * 地震描述
   */
  private String desn;
  
  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", unique = true, nullable = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  @Column(name = "CREATE_TIME")
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  @Column(name = "state", length = 1)
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
  @Column(name = "fracture")
  public String getFracture() {
    return fracture;
  }

  public void setFracture(String fracture) {
    this.fracture = fracture;
  }
  @Column(name = "event_wave")
  public String getEvent_wave() {
    return event_wave;
  }

  public void setEvent_wave(String eventWave) {
    event_wave = eventWave;
  }
  @Column(name = "station_wave")
  public String getStation_wave() {
    return station_wave;
  }

  public void setStation_wave(String stationWave) {
    station_wave = stationWave;
  }
  @Column(name = "intensity")
  public String getIntensity() {
    return intensity;
  }

  public void setIntensity(String intensity) {
    this.intensity = intensity;
  }
  @Column(name = "mechanism")
  public String getMechanism() {
    return mechanism;
  }

  public void setMechanism(String mechanism) {
    this.mechanism = mechanism;
  }
  @Column(name = "m_t")
  public String getM_t() {
    return m_t;
  }

  public void setM_t(String mT) {
    m_t = mT;
  }
  @Column(name = "area")
  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }
  @Column(name = "epifocus")
  public String getEpifocus() {
    return epifocus;
  }

  public void setEpifocus(String epifocus) {
    this.epifocus = epifocus;
  }
  @Column(name = "front_pic")
  public String getFront_pic() {
    return front_pic;
  }

  public void setFront_pic(String frontPic) {
    front_pic = frontPic;
  }
  @Column(name = "desn")
  public String getDesn() {
    return desn;
  }

  public void setDesn(String desn) {
    this.desn = desn;
  }
  @Column(name = "history_pic")
  public String getHistory_pic() {
    return history_pic;
  }

  public void setHistory_pic(String historyPic) {
    history_pic = historyPic;
  }
  @Column(name = "qc_id")
  public String getQc_id() {
    return qc_id;
  }

  public void setQc_id(String qcId) {
    qc_id = qcId;
  }
  @Column(name = "TITLE")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  @Column(name = "TABLENAME")
  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getQuakeTime() {
    return quakeTime;
  }

  public void setQuakeTime(String quakeTime) {
    this.quakeTime = quakeTime;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getDepth() {
    return depth;
  }

  public void setDepth(String depth) {
    this.depth = depth;
  }

  public String getMagnitude() {
    return magnitude;
  }

  public void setMagnitude(String magnitude) {
    this.magnitude = magnitude;
  }
}
