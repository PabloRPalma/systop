package com.systop.core.dao.testmodel;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * The persistent class for the roles database table.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "test_roles", uniqueConstraints = { })
public class TestRole extends BaseModel implements Serializable {
  /**
   * id
   */
  private Integer id;
  /**
   * 角色描述
   */
  private String descn;
  /**
   * 角色名称
   */
  private String name;
  /**
   * 版本
   */
  private Integer version;
  
  /**
   * 父角色
   */
  private TestRole parentRole;
  /**
   * 子角色
   */
  private Set<TestRole> childRoles = new HashSet<TestRole>(0);
  /**
   * 具有此角色的用户
   */
  private Set<TestUser> users = new HashSet<TestUser>(0);

  /**
   * 缺省构造器
   */
  public TestRole() {
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

  @Column(name = "version")
  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  

  

  @ManyToOne(cascade = { }, fetch = FetchType.LAZY)
  @JoinColumn(name = "parent")
  public TestRole getParentRole() {
    return this.parentRole;
  }

  public void setParentRole(TestRole parentRole) {
    this.parentRole = parentRole;
  }

  @OneToMany(cascade = { CascadeType.ALL },
      fetch = FetchType.LAZY, mappedBy = "parentRole")
  public Set<TestRole> getChildRoles() {
    return this.childRoles;
  }

  public void setChildRoles(Set<TestRole> childRoles) {
    this.childRoles = childRoles;
  }

  @ManyToMany(cascade = { }, fetch = FetchType.LAZY, mappedBy = "roles",
      targetEntity = TestUser.class)
  public Set<TestUser> getUsers() {
    return this.users;
  }

  public void setUsers(Set<TestUser> users) {
    this.users = users;
  }
  
  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof TestRole)) {
      return false;
    }
    TestRole castOther = (TestRole) other;
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