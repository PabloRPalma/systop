package com.systop.cms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 链接类别
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "link_catas")
public class LinkCatas extends BaseModel {

  /** 主键 */
  private Integer id;

  /** 类别名称 */
  private String name;

  /** 描述 */
  private String descn;

  /** 所有链接 */
  private Set<Links> links = new HashSet<Links>(0);

  /** 构造方法 */
  public LinkCatas() {
  }

  /** 构造方法 */
  public LinkCatas(Integer id) {
    this.id = id;
  }

  /** 构造方法 */
  public LinkCatas(Integer id, String name, String descn, Set<Links> links) {
    this.id = id;
    this.name = name;
    this.descn = descn;
    this.links = links;
  }

  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", unique = true, nullable = false)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "NAME", length = 255)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "DESCN", length = 500)
  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "linkCatas")
  public Set<Links> getLinks() {
    return this.links;
  }

  public void setLinks(Set<Links> links) {
    this.links = links;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof LinkCatas)) {
      return false;
    }
    LinkCatas castOther = (LinkCatas) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
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
