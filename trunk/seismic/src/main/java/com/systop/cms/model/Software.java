package com.systop.cms.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 软件下载Model
 * 
 * @author Lunch
 */
@SuppressWarnings({"unchecked","serial"})
@Entity
@Table(name = "software")
public class Software extends BaseModel {

  /** 主键 */
  private Integer id;

  /** 软件名称 */
  private String name;

  /** 软件版本 */
  private String softVersion;

  /** 软件大小 */
  private Long size;

  /** 下载次数 */
  private Integer downCount;

  /** 操作系统 */
  private String os;

  /** 授权方式 */
  private String authorization;

  /** 软件介绍 */
  private String introduction;

  /** 发布日期 */
  private Date pubDate;

  /** 本站下载地址 */
  private String downUrl;

  /** 备注 */
  private String descn;

  /** 软件类别 */
  private SoftCatas softCatalog;

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

  @Column(name = "NAME", length = 255)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "SOFT_VERSION", length = 50)
  public String getSoftVersion() {
    return softVersion;
  }

  public void setSoftVersion(String softVersion) {
    this.softVersion = softVersion;
  }

  @Column(name = "SIZE")
  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  @Column(name = "DOWN_COUNT")
  public Integer getDownCount() {
    return downCount;
  }

  public void setDownCount(Integer downCount) {
    this.downCount = downCount;
  }

  @Column(name = "OS", length = 255)
  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  @Column(name = "AUTHORIZATION", length = 255)
  public String getAuthorization() {
    return authorization;
  }

  public void setAuthorization(String authorization) {
    this.authorization = authorization;
  }

  @Column(name = "INTRODUCTION", length = 2000)
  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  @Column(name = "PUB_DATE")
  public Date getPubDate() {
    return pubDate;
  }

  public void setPubDate(Date pubDate) {
    this.pubDate = pubDate;
  }

  @Column(name = "DESCN")
  public String getDescn() {
    return descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  @Column(name = "DOWN_URL", length = 255)
  public String getDownUrl() {
    return downUrl;
  }

  public void setDownUrl(String downUrl) {
    this.downUrl = downUrl;
  }

  @ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
  @JoinColumn(name="SOFT_CATALOG")
  public SoftCatas getSoftCatalog() {
    return softCatalog;
  }

  public void setSoftCatalog(SoftCatas softCatalog) {
    this.softCatalog = softCatalog;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Software other = (Software) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
