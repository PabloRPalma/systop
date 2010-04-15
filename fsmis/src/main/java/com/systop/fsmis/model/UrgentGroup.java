package com.systop.fsmis.model;

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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;

/**
 * 应急事件指挥组
 * 
 * @author yj
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "URGENT_GROUPS", schema = "FSMIS")
public class UrgentGroup extends BaseModel {

	/** 主键 */
	private Integer id;

	/** 组名 */
	private String name;

	/** 描述 */
	private String descr;

	/** 显示内容 */
	private String displays;

	/** 是否公用数据 Y/N */
	private String isPublic;

	/** 派遣类别 */
	private UrgentType urgentType;

	/** 对应的结果对象名称 */
	private String category;

	/** 所属区县 */
	private Dept county;

	/** 是否原始数据 0:否，1:是 */
	private String isOriginal;

	/**
	 * 此组的用户
	 */
	private Set<User> users = new HashSet<User>(0);
	
	/** 固话 */
	private String phone;

	/** 手机号 */
	private String mobel;
	
	/** 负责人 */
	private String principal;

	public UrgentGroup() {
	}

	/**
	 * @param name
	 *          组名称
	 * @param category
	 *          对应指挥组类别
	 * @param isPublic
	 *          是否公共
	 * @param isOriginal
	 *          是否原始数据
	 */
	public UrgentGroup(String name, String category, String isPublic,
			String isOriginal) {
		this.name = name;
		this.category = category;
		this.isPublic = isPublic;
		this.isOriginal = isOriginal;

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

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IS_PUBLIC")
	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "UCTYPE_ID")
	public UrgentType getUrgentType() {
		return urgentType;
	}

	public void setUrgentType(UrgentType urgentType) {
		this.urgentType = urgentType;
	}

	@Column(name = "DISPLAYS", length = 500)
	public String getDisplays() {
		return displays;
	}

	public void setDisplays(String displays) {
		this.displays = displays;
	}

	@Column(name = "DESCR", length = 500)
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY")
	public Dept getCounty() {
		return county;
	}

	public void setCounty(Dept county) {
		this.county = county;
	}

	@Column(name = "CATEGORY", length = 500)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "IS_ORIGINAL", length = 255)
	public String getIsOriginal() {
		return isOriginal;
	}

	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}

	@ManyToMany(targetEntity = User.class, cascade = {}, fetch = FetchType.LAZY)
	@JoinTable(name = "user_urgentGroup", joinColumns = { @JoinColumn(name = "urgentGroup_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "MOBEL")
	public String getMobel() {
		return mobel;
	}

	public void setMobel(String mobel) {
		this.mobel = mobel;
	}
	@Column(name = "PRINCIPAL")
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
}
