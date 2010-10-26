package quake.special.model;

import java.util.Date;

import javax.persistence.Transient;

import quake.base.model.PageSchemaAware;

public class Criteria extends PageSchemaAware {
  /**
   * 用于标识对象的状态是否改变.
   */
  private transient Boolean changed = Boolean.FALSE;

  @Transient
  public Boolean getChanged() {
    return changed;
  }

  public void setChanged(Boolean changed) {
    this.changed = changed;
  }

  private String id;
  /**
   * 发震开始时间
   */
  private Date startDate;
  /**
   * 发震结束时间
   */
  private Date endDate;
  /**
   * 开始震级
   */
  private Double startM;
  /**
   * 结束震级
   */
  private Double endM;
  /**
   * 地点
   */
  private String location_cname;

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Double getStartM() {
    return startM;
  }

  public void setStartM(Double startM) {
    this.startM = startM;
  }

  public Double getEndM() {
    return endM;
  }

  public void setEndM(Double endM) {
    this.endM = endM;
  }

  public String getLocation_cname() {
    return location_cname;
  }

  public void setLocation_cname(String locationCname) {
    location_cname = locationCname;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
