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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 用户表 The persistent class for the users database table.
 * 
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "test_users", uniqueConstraints = { })
public class TestUser extends BaseModel implements Serializable {
  /**
   * 主键
   */
  private Integer id;

  /**
   * 电子邮件
   */
  private String email;

  /**
   * 最后登录IP
   */
  private String lastLoginIp;

  /**
   * 登录ID
   */
  private String loginId;

  /**
   * 登录次数
   */
  private Integer loginTimes;

  /**
   * 密码
   */
  private String password;

  /**
   * 状态（1缺省）
   */
  private String status = "1";

  /**
   * 版本
   */
  private Integer version;

  /**
   * 对应的职员
   */
  private TestEmployee employee;

  /**
   * 所具有的角色
   */
  private Set<TestRole> roles = new HashSet<TestRole>(0);

  /**
   * 缺省构造器
   */
  public TestUser() {
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

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "last_login_ip")
  public String getLastLoginIp() {
    return this.lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  @Column(name = "login_id")
  public String getLoginId() {
    return this.loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  @Column(name = "login_times")
  public Integer getLoginTimes() {
    return this.loginTimes;
  }

  public void setLoginTimes(Integer loginTimes) {
    this.loginTimes = loginTimes;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Version
  @Column(name = "version")
  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @OneToOne(cascade = { CascadeType.ALL }, 
      fetch = FetchType.LAZY, mappedBy = "user")
  public TestEmployee getEmployee() {
    return this.employee;
  }

  public void setEmployee(TestEmployee employee) {
    this.employee = employee;
  }

  @ManyToMany(targetEntity = TestRole.class, cascade = { }, 
      fetch = FetchType.LAZY)
  @JoinTable(name = "test_user_role", 
      joinColumns = { @JoinColumn(name = "user_id") }, 
      inverseJoinColumns = { @JoinColumn(name = "role_id") })
  public Set<TestRole> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<TestRole> roles) {
    this.roles = roles;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof TestUser)) {
      return false;
    }
    TestUser castOther = (TestUser) other;
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