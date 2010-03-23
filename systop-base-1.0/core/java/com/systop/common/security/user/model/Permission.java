package com.systop.common.security.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * @hibernate.class table="PERMISSIONS" schema="PUBLIC" lazy="true"
 */
@Entity
@Table(name = "PERMISSIONS", schema = "PUBLIC")
public class Permission implements Serializable {
  /** default serial version id, required for serializable classes */
  private static final long serialVersionUID = 1L;

  /** 权限是否被选中 */
  private transient boolean selected;

  /** identifier field */
  private Integer id;

  /** nullable persistent field */
  private String descn;

  /** nullable persistent field */
  private String name;

  /** nullable persistent field */
  private String operation;

  /** nullable persistent field */
  private String status;

  /** nullable persistent field */
  private Integer version;

  /** persistent field */
  private Set resources = new HashSet(0);

  /** persistent field */
  private Set roles = new HashSet(0);

  /** default constructor */
  public Permission() {
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
   * @hibernate.property column="OPERATION" length="255"
   */
	@Column(name = "OPERATION")
  public String getOperation() {
    return this.operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  /**
   * @hibernate.property column="STATUS" length="2"
   */
	@Column(name = "STATUS")
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
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
   * @hibernate.set table="PERMIS_RESC"
   * @hibernate.key column="PERMISSION_ID" not-null="true"
   * @hibernate.many-to-many 
   * class="com.systop.common.security.user.model.Resource"
   *                         column="RESC_ID" not-null="true"
   */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
  		targetEntity = com.systop.common.security.user.model.Resource.class)
  @JoinTable(
  		name = "permis_resc",
  		joinColumns = {@JoinColumn (name = "permission_id") },
  		inverseJoinColumns = {@JoinColumn (name = "resc_id") }
  )
  public Set getResources() {
    return this.resources;
  }

 
  public void setResources(Set resources) {
    this.resources = resources;
  }

  /**
   * @hibernate.set table="ROLE_PERMIS"
   * @hibernate.key column="PERMISSION_ID" not-null="true"
   * @hibernate.many-to-many class="com.systop.common.security.user.model.Role"
   *                         column="ROLE_ID" not-null="true"
   */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
  				mappedBy = "permissions",
  				targetEntity = com.systop.common.security.user.model.Role.class)
  public Set getRoles() {
    return this.roles;
  }

  public void setRoles(Set roles) {
    this.roles = roles;
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
    if (!(other instanceof Permission)) {
      return false;
    }
    Permission castOther = (Permission) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId())
        .append(this.getName(), castOther.getName()).isEquals();
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder().append(getId())
      .append(getName()).toHashCode();
  }

  @Transient
  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
  /**
   * 将Permission对象转化为<code>GrantedAuthority</code>
   */
  public GrantedAuthority toGrantedAuthority() {
    return new GrantedAuthorityImpl(this.name);
  }

}
