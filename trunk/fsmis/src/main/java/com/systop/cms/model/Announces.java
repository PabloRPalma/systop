package com.systop.cms.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.security.user.model.Role;
import com.systop.core.model.BaseModel;

/**
 * 网站公告表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "announces")
public class Announces extends BaseModel {

  /** 主键 */
  private Integer id;

  /** 公告标题 */
  private String title;

  /** 公告内容 */
  private String content;

  /** 公告发布人 */
  private String author;

  /** 发布时间 */
  private Date creatDate;

  /** 是否最新 */
  private String isNew;

  /** 显示类型 */
  private String showType;

  /** 过期时间 */
  private BigDecimal outTime;

  /** 构造方法 */
  public Announces() {
  }

  /** 构造方法 */
  public Announces(Integer id) {
    this.id = id;
  }

  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", nullable = false)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "TITLE")
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

//oracle Clob 支持　@Type(type = "org.springframework.orm.hibernate3
//.support.ClobStringType")
  @Column(name = "CONTENT")
  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Column(name = "AUTHOR", length = 50)
  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Column(name = "CREAT_DATE")
  public Date getCreatDate() {
    return this.creatDate;
  }

  public void setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
  }

  @Column(name = "IS_NEW", length = 1)
  public String getIsNew() {
    return this.isNew;
  }

  public void setIsNew(String isNew) {
    this.isNew = isNew;
  }

  @Column(name = "SHOW_TYPE", length = 1)
  public String getShowType() {
    return this.showType;
  }

  public void setShowType(String showType) {
    this.showType = showType;
  }

  @Column(name = "OUT_TIME", precision = 22, scale = 0)
  public BigDecimal getOutTime() {
    return this.outTime;
  }

  public void setOutTime(BigDecimal outTime) {
    this.outTime = outTime;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Role)) {
      return false;
    }
    Announces castOther = (Announces) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId())
        .isEquals();
  }

  /**
   * @see Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }
}
