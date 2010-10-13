package com.systop.common.modules.security.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 注册说明信息表 The persistent class for the reg_memo database table.
 * 
 * @author DU
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "reg_memo", uniqueConstraints = { })
public class RegMemo extends BaseModel implements Serializable {

  /**
   * 主键
   */
  private String id;
  
  /**
   * 标题
   */
  private String title;
  
  /**
   * 说明描述
   */
  private String content;

  /**
   * 缺省构造器
   */
  public RegMemo() {
  }
  
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

  @Column(name = "title")
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }
  
  @Column(name = "content")
  @Lob
  public String getContent() {
    return content;
  }

  /**
   * @param content the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }
  
  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof RegMemo)) {
      return false;
    }
    RegMemo regMemo = (RegMemo) other;
    return new EqualsBuilder().append(this.getId(), regMemo.getId()).isEquals();
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
