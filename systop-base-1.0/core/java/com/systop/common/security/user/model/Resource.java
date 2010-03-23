package com.systop.common.security.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.acegisecurity.GrantedAuthority;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.security.acegi.resourcedetails.ResourceDetails;

/**
 * @hibernate.class table="RESOURCES" schema="PUBLIC" lazy="true"
 */
@Entity
@Table(name = "RESOURCES", schema = "PUBLIC")
public class Resource implements ResourceDetails, Serializable {
  /** default serial version id, required for serializable classes */
  private static final long serialVersionUID = 1L;

  /** 资源是否被选中 */
  private transient boolean selected;

  /** identifier field */
  private Integer id;

  /** nullable persistent field */
  private String descn;

  /** nullable persistent field */
  private String name;

  /** nullable persistent field */
  private String resString;

  /** nullable persistent field */
  private String resType;

  /** nullable persistent field */
  private Integer version;

  /** persistent field */
  private Set permissions;

  /** default constructor */
  public Resource() {
  }

  /** minimal constructor */
  public Resource(Set permissions) {
    this.permissions = permissions;
  }

  /**
   * @hibernate.id generator-class="hilo" type="java.lang.Integer" column="ID"
   */
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

  /**
   * @hibernate.property column="DESCN" length="255"
   */
	@Column(name = "DESCN")
  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  /**
   * @hibernate.property column="NAME" length="255"
   */
	@Column(name = "NAME")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @hibernate.property column="RES_STRING" length="255"
   */
  @Column(name = "RES_STRING")
  public String getResString() {
    return this.resString;
  }

  public void setResString(String resString) {
    this.resString = resString;
  }

  /**
   * @hibernate.property column="RES_TYPE" length="25"
   */
  @Column(name = "RES_TYPE")
  public String getResType() {
    return this.resType;
  }

  public void setResType(String resType) {
    this.resType = resType;
  }

  /**
   * @hibernate.version column="VERSION"
   */
	@Column(name = "VERSION")
  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * @hibernate.set inverse="true" table="PERMIS_RESC" cascade="save-update"
   * @hibernate.key column="RESC_ID" not-null="true"
   * @hibernate.many-to-many 
   * class="com.systop.common.security.user.model.Permission"
   *                         column="PERMISSION_ID" not-null="true"
   */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
  		mappedBy = "resources",
  		targetEntity = com.systop.common.security.user.model.Permission.class)
  public Set getPermissions() {
    return this.permissions;
  }

  public void setPermissions(Set permissions) {
    this.permissions = permissions;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).append("name",
        getName()).toString();
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object other) {
    if ((this == other)) {
      return true;
    }
    if (!(other instanceof Resource)) {
      return false;
    }
    Resource castOther = (Resource) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId()).append(
        getResString(), castOther.getResString()).append(getResType(),
        castOther.getResType()).isEquals();
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder().append(getId()).append(getResString()).append(
        getResType()).toHashCode();
  }

  @Transient
  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
  /**
   * 可以访问该资源的<code>GrantedAuthority</code>
   */
  private transient GrantedAuthority[] authorities;
  /**
   * @param authorities the authorities to set
   */
  public void setAuthorities(GrantedAuthority[] authorities) {
    this.authorities = authorities;
  }

  /**
   * 
   * @see ResourceDetails#getAuthorities()
   */
  @Transient
  public GrantedAuthority[] getAuthorities() {
    if (authorities != null) {
      return authorities;
    }
    // 将Permission转化为GrantedAuthority
    Set<Permission> perms = getPermissions();
    if (perms != null) {
      List auths = new ArrayList();

      for (Permission perm : perms) {
        auths.add(perm.toGrantedAuthority());
      }
      return (GrantedAuthority[]) auths.toArray(new GrantedAuthority[0]);
    }

    return new GrantedAuthority[] {};
  }

}
