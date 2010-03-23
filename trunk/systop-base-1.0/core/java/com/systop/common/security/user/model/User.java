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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.Constants;
import com.systop.common.security.dept.model.Dept;

/**
 * @hibernate.class table="USERS" schema="PUBLIC" lazy="true"
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable, UserDetails {
  /** default serial version id, required for serializable classes */
  private static final long serialVersionUID = 1L;
  
  /**
   * 重复输入的密码
   */
  private transient String confirmPwd;
  
  /** identifier field */
  private Integer id;

  /** nullable persistent field */
  private String descn;

  /** nullable persistent field */
  private String email;

  /** nullable persistent field */
  private String loginId;

  /** nullable persistent field */
  private String name;

  /** nullable persistent field */
  private String password;

  /** nullable persistent field */
  private String region;

  /** nullable persistent field */
  private String status;

  /** nullable persistent field */
  private Integer version;

  /** persistent field */
  private Set createdContents = new HashSet(0);

  /** persistent field */
  private Set updatedContents = new HashSet(0);


  /** persistent field */
  private Set roles = new HashSet(0);
  
  /** nullable persistent field */
  private Dept dept;

  /** default constructor */
  public User() {
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
   * @return the confirmPwd
   */
  @Transient
  public String getConfirmPwd() {
    return confirmPwd;
  }

  /**
   * @param confirmPwd the confirmPwd to set
   */
  public void setConfirmPwd(String confirmPwd) {
    this.confirmPwd = confirmPwd;
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
   * @hibernate.property column="EMAIL" length="255"
   */
  @Column(name = "EMAIL")
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @hibernate.property column="LOGIN_ID" length="255"
   */
  @Column(name = "LOGIN_ID")
  public String getLoginId() {
    return this.loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
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
   * @hibernate.property column="PASSWORD" length="255"
   */
  @Column(name = "PASSWORD")
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @hibernate.property column="REGION" length="255"
   */
  @Column(name = "REGION")
  public String getRegion() {
    return this.region;
  }

  public void setRegion(String region) {
    this.region = region;
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
   * @hibernate.set inverse="true"
   * @hibernate.key column="AUTHOR"
   * @hibernate.one-to-many class="com.systop.cms.model.Content"
   */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
  		mappedBy = "author",
  		targetEntity = com.systop.cms.model.Content.class)
  public Set getCreatedContents() {
    return this.createdContents;
  }

  public void setCreatedContents(Set createdContents) {
    this.createdContents = createdContents;
  }

  /**
   * @hibernate.set inverse="true"
   * @hibernate.key column="UPDATER"
   * @hibernate.one-to-many class="com.systop.cms.model.Content"
   */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,	
  		mappedBy = "updater",
  		targetEntity = com.systop.cms.model.Content.class)
  public Set getUpdatedContents() {
    return this.updatedContents;
  }

  public void setUpdatedContents(Set updatedContents) {
    this.updatedContents = updatedContents;
  }

  /**
   * @hibernate.set inverse="true" table="USER_ROLE"
   * @hibernate.key column="USER_ID" not-null="true"
   * @hibernate.many-to-many class="com.systop.common.security.user.model.Role"
   *                         column="ROLE_ID" not-null="true"
   */
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
  		targetEntity = com.systop.common.security.user.model.Role.class)
  @JoinTable(
  		name = "user_role",
  		joinColumns = {@JoinColumn (name = "user_id") },
  		inverseJoinColumns = {@JoinColumn (name = "role_id") }
  )
  public Set getRoles() {
    return this.roles;
  }

  public void setRoles(Set roles) {
    this.roles = roles;
  }
  
  /** 
   *            @hibernate.many-to-one
   *            @hibernate.column name="DEPT_ID"           
   */
  @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPT_ID")
  public Dept getDept() {
      return this.dept;
  }

  public void setDept(Dept dept) {
      this.dept = dept;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).append("loginId",
        getLoginId()).append("name", getName()).toString();
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object other) {
    if ((this == other)) {
      return true;
    }
    if (!(other instanceof User)) {
      return false;
    }
    User castOther = (User) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId())
        .append(this.getLoginId(), castOther.getLoginId()).isEquals();
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder().append(getId())
      .append(getLoginId()).toHashCode();
  }

  // Method of UserDetails
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
   * @param authorities the authorities to set
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

}
