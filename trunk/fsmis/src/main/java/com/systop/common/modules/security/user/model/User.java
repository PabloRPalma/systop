package com.systop.common.modules.security.user.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.Constants;
import com.systop.core.model.BaseModel;
import com.systop.fsmis.model.Assessment;
import com.systop.fsmis.model.UrgentGroup;

/**
 * 用户表 The persistent class for the users database table.
 * 
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "users", uniqueConstraints = {})
public class User extends BaseModel implements UserDetails, Serializable {
	/**
	 * Log
	 */
	private static Log log = LogFactory.getLog(User.class);
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 姓名或昵称
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 民族
	 */
	private String folk;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 电话
	 */
	private String hTel;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String zip;
	/**
	 * msn
	 */
	private String msn;
	/**
	 * qq
	 */
	private String qq;
	/**
	 * 用户描述
	 */
	private String descn;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 数据订阅时所用的邮箱
	 */
	private String dataEmail;
	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 注册时间
	 */
	private Date registTime;
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
	 * 状态
	 */
	private String status;

	/**
	 * 类型
	 */
	private String userType;

	/**
	 * 学历
	 */
	private String degree;

	/**
	 * 职务
	 */
	private String post;

	/**
	 * 工作单位
	 */
	private String unit;

	/**
	 * 单位性质
	 */
	private String unitKind;

	/**
	 * 地震行业
	 * */
	private String industry;

	/**
	 * 省份
	 */
	private String province;

	/**
	 * 用户级别
	 */
	private String level;

	/**
	 * 是否系统用户
	 */
	private String isSys;
	/**
	 * 照片路径
	 */
	private String photo;

	/**
	 * 版本
	 */
	private Integer version;

	/**
	 * 所具有的角色
	 */
	private Set<Role> roles = new HashSet<Role>(0);

	/**
	 * 对应的Dept
	 */
	private Dept dept;

	/**
	 * 对应应急组
	 */
	private UrgentGroup urgentGroup;
	
	/**
	 * 评估申请人
	 */
	private Set<Assessment> asseForProposer = new HashSet<Assessment>(0);

	/**
	 * 缺省构造器
	 */
	public User() {
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the folk
	 */
	public String getFolk() {
		return folk;
	}

	/**
	 * @param folk
	 *            the folk to set
	 */
	public void setFolk(String folk) {
		this.folk = folk;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the hTel
	 */
	@Column(name = "h_tel")
	public String getHTel() {
		return hTel;
	}

	/**
	 * @param tel
	 *            the hTel to set
	 */
	public void setHTel(String tel) {
		hTel = tel;
	}

	/**
	 * @return the mobil
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobil
	 *            the mobil to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
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

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "unit_kind")
	public String getUnitKind() {
		return unitKind;
	}

	public void setUnitKind(String unitKind) {
		this.unitKind = unitKind;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToMany(targetEntity = Role.class, cascade = {}, fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
	 *            the authorities to set
	 */
	public void setAuthorities(GrantedAuthority[] authorities) {
		log.info("Set GrantedAuthorities :" + Arrays.toString(authorities));
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the msn
	 */
	@Column(name = "msn")
	public String getMsn() {
		return msn;
	}

	/**
	 * @param msn
	 *            the msn to set
	 */
	public void setMsn(String msn) {
		this.msn = msn;
	}

	/**
	 * @return the qq
	 */
	@Column(name = "qq")
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "URGENT_GROUP")
	public UrgentGroup getUrgentGroup() {
		return this.urgentGroup;
	}

	public void setUrgentGroup(UrgentGroup urgentGroup) {
		this.urgentGroup = urgentGroup;
	}

	/**
	 * @return the registTime
	 */
	@Column(name = "regist_time")
	public Date getRegistTime() {
		return registTime;
	}

	/**
	 * @param registTime
	 *            the registTime to set
	 */
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	/**
	 * @return the isSys
	 */
	@Column(name = "is_sys", columnDefinition = "char(1) default '0'")
	public String getIsSys() {
		return isSys;
	}

	/**
	 * @param isSys
	 *            the isSys to set
	 */
	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	/**
	 * @return the dataEmail
	 */
	@Column(name = "data_email")
	public String getDataEmail() {
		return dataEmail;
	}

	/**
	 * @param dataEmail
	 *            the dataEmail to set
	 */
	public void setDataEmail(String dataEmail) {
		this.dataEmail = dataEmail;
	}

	@Column(name = "industry")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proposer")
	public Set<Assessment> getAsseForProposer() {
		return this.asseForProposer;
	}

	public void setAsseForProposer(Set<Assessment> asseForProposer) {
		this.asseForProposer = asseForProposer;
	}
}