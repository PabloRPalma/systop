package com.systop.common.modules.security.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * The persistent class for the permissions database table.
 * 
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "permissions", uniqueConstraints = { })
public class Permission extends BaseModel implements Serializable {
  /**
   * id
   */
  private Integer id;
  /**
   * 权限描述
   */
  private String descn;
  /**
   * 权限名称
   */
  private String name;
  /**
   * 权限操作
   */
  private String operation;
  /**
   * 状态
   */
  private String status;
  /**
   * 版本
   */
  private Integer version;
  /**
   * 权限所含的资源
   */
  private Set<Resource> resources = new HashSet<Resource>(0);
  /**
   * 包含此权限的角色
   */
  private Set<Role> roles = new HashSet<Role>(0);
  
  /**
   * 是否系统Permission
   */
  private String isSys;

  /**
   * 缺省构造器
   */
  public Permission() {
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

  @Column(name = "descn")
  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  @Column(name = "name")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "operation")
  public String getOperation() {
    return this.operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  @Column(name = "status")
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
  
  /**
   * @return the isSys
   */
  @Column(name = "is_sys", columnDefinition = "char(1) default '0'")
  public String getIsSys() {
    return isSys;
  }

  /**
   * @param isSys the isSys to set
   */
  public void setIsSys(String isSys) {
    this.isSys = isSys;
  }

  @Column(name = "version")
  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @ManyToMany(targetEntity = Resource.class, cascade = { },
      fetch = FetchType.LAZY)
  @JoinTable(name = "permis_resc", joinColumns = { 
      @JoinColumn(name = "permission_id") }, inverseJoinColumns = { 
      @JoinColumn(name = "resc_id") })
  public Set<Resource> getResources() {
    return this.resources;
  }

  public void setResources(Set<Resource> resources) {
    this.resources = resources;
  }

  @ManyToMany(cascade = { }, fetch = FetchType.LAZY,
      mappedBy = "permissions")
  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  
  /**
   * 将Permission对象转化为<code>GrantedAuthority</code>
   */
  public GrantedAuthority toGrantedAuthority() {
    return new GrantedAuthorityImpl(this.name);
  }
  
  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Permission)) {
      return false;
    }
    Permission castOther = (Permission) other;
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