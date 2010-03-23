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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * @hibernate.class table="ROLES" schema="PUBLIC" lazy="true"
 */
@Entity
@Table(name = "ROLES", schema = "PUBLIC")
public class Role implements Serializable {
  /** default serial version id, required for serializable classes */
  private static final long serialVersionUID = 1L;
  /**
   * 角色是否被选中
   */
  private transient boolean selected;

  /** identifier field */
  private Integer id;

  /** nullable persistent field */
  private String descn;

  /** nullable persistent field */
  private String name;

  /** nullable persistent field */
  private Integer version;

  /** persistent field */
  private Set permissions = new HashSet(0);

  /** persistent field */
  private Set users = new HashSet(0);

  /** default constructor */
  public Role() {
  }

  /** minimal constructor */
  public Role(Set permissions, Set users) {
    this.permissions = permissions;
    this.users = users;
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
   * @hibernate.set inverse="true" table="ROLE_PERMIS"
   * @hibernate.key column="ROLE_ID" not-null="true"
   * @hibernate.many-to-many 
   * class="com.systop.common.security.user.model.Permission"
   * column="PERMISSION_ID" not-null="true"
   */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
  		targetEntity = com.systop.common.security.user.model.Permission.class)
  @JoinTable(
  		name = "role_permis",
  		joinColumns = {@JoinColumn (name = "role_id") },
  		inverseJoinColumns = {@JoinColumn (name = "permission_id") }
  )
  public Set getPermissions() {
    return this.permissions;
  }

  public void setPermissions(Set permissions) {
    this.permissions = permissions;
  }

  /**
   * @hibernate.set table="USER_ROLE"
   * @hibernate.key column="ROLE_ID" not-null="true"
   * @hibernate.many-to-many 
   * class="com.systop.common.security.user.model.User"
   * column="USER_ID" not-null="true"
   */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      mappedBy = "roles",
      targetEntity = com.systop.common.security.user.model.User.class)
  public Set getUsers() {
    return this.users;
  }

  public void setUsers(Set users) {
    this.users = users;
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
    if (!(other instanceof Role)) {
      return false;
    }
    Role castOther = (Role) other;
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

  /**
   * @return the selected
   */
  @Transient
  public boolean isSelected() {
    return selected;
  }

  /**
   * @param selected the selected to set
   */
  public void setSelected(boolean selected) {
    this.selected = selected;
  }

}
