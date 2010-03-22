package com.systop.modules.admin.security.rbac.model;

import java.text.DecimalFormat;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.systop.core.Constants;
import com.systop.core.model.BaseModel;
import com.systop.modules.hr.employee.model.Employee;

/**
 * 用户表 The persistent class for the users database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "users", uniqueConstraints = { })
public class User extends BaseModel implements UserDetails {

  /**
   * 毫秒转化成小时的除数常量
   */
  private static final double DIVISOR = 3600000.00;
  /**
   * 主键
   */
  private Integer id;
  /**
   * 用户描述
   */
  private String descn;
  /**
   * 电子邮件
   */
  private String email;
  /**
   * 最后登录IP
   */
  private String lastLoginIp;
  /**
   * 最后登录时间
   */
  private Date lastLoginTime;
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
   * 确认密码 
   */
  private String confirmPwd;

  /**
   * 状态（1缺省）
   */
  private String status = "1";
  /**
   * 类型
   */
  private String userType;
  /**
   * 版本
   */
  private Integer version;
  /**
   * 所具有的角色
   */
  private Set<Role> roles = new HashSet<Role>(0);
  /**
   * 用户所对应的员工，这里用OneToMany代替OneToOne
   */
  private Set<Employee> employees = new HashSet<Employee>();
  /**
   * 累计登录时间
   */
  private Integer onlineDuration;
  /**
   * 用户在线累计时间，毫秒变成小时
   */
  @SuppressWarnings("unused")
  private String durationToHour;
  
  /**
   * 缺省构造器
   */
  public User() {
  }

  public User(Integer id) {
    this.id = id;
  }

  public User(Integer id, String loginId) {
    this.id = id;
    this.loginId = loginId;
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

  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
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

  @Column(name = "last_login_time")
  public Date getLastLoginTime() {
    return this.lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
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

  @Column(name = "user_type")
  public String getUserType() {
    return this.userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  @Version
  @Column(name = "version")
  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @ManyToMany(targetEntity = Role.class, cascade = { }, fetch = FetchType.LAZY)
  @JoinTable(name = "user_role", joinColumns = {
      @JoinColumn(name = "user_id") }, inverseJoinColumns = {
      @JoinColumn(name = "role_id") })
  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  
  @OneToMany(cascade = {CascadeType.ALL }, fetch = FetchType.LAZY, 
      mappedBy = "user")
  public Set<Employee> getEmployees() {
    return employees;
  }
  
  public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof User)) {
      return false;
    }
    User castOther = (User) other;
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
  
  // Methods from UserDetails
  /**
   * 用户所具有的权限，可用通过角色-权限对应关系得到
   */
  private transient GrantedAuthority[] authorities;

  /**
   * @see {@link UserDetails#getAuthorities()}
   */
  @Transient
  public GrantedAuthority[] getAuthorities() {
    return this.authorities;
  }

  /**
   * @param authorities
   *          the authorities to set
   */
  public void setAuthorities(GrantedAuthority[] authorities) {
    this.authorities = authorities;
  }

  /**
   * @see {@link UserDetails#getUsername()}
   */
  @Transient
  public String getUsername() {
    return this.loginId;
  }

  /**
   * @see {@link UserDetails#isAccountNonExpired()}
   */
  @Transient
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * @see {@link UserDetails#isAccountNonLocked()}
   */
  @Transient
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * {@link UserDetails#isCredentialsNonExpired()}
   */
  @Transient
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * {@link UserDetails#isEnabled()}
   */
  @Transient
  public boolean isEnabled() {
    return StringUtils.equalsIgnoreCase(status, Constants.STATUS_AVAILABLE);
  }

  @Transient
  public String getConfirmPwd() {
	  return confirmPwd;
  }

  public void setConfirmPwd(String confirmPwd) {
	  this.confirmPwd = confirmPwd;
  }
  /**
   * 是否有角色？
   */
  @Transient
  public boolean getHasRoles() {
    return roles != null && roles.size() > 0;
  }
  /**
   * @return String 格式化以后的字符串
   */
  @Transient
  public String getDurationToHour() {
	DecimalFormat numberFormat = new DecimalFormat("0.00");
	if (getOnlineDuration() == null) {
		return "0.00";
	}
	return numberFormat.format(getOnlineDuration() / DIVISOR);
  }

  @Column(name = "online_duration")
  public Integer getOnlineDuration() {
	  return onlineDuration;
  }

  public void setOnlineDuration(Integer onlineDuration) {
	  this.onlineDuration = onlineDuration;
  }
}