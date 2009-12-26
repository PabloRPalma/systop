package com.systop.fsmis.model;

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 专家表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXPERTS", schema = "FSMIS")
public class Expert extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 类别
	 */
	private ExpertCategory expertCategory;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 出生日期
	 */
	private Date birthDate;
	/**
	 * 毕业院校
	 */
	private String graduateSchool;
	/**
	 * 院校
	 */
	private String schoolRecord;
	/**
	 * 学历
	 */
	private String degree;
	/**
	 * 移动电话
	 */
	private String mobile;
	/**
	 * 家庭电话
	 */
	private String homePhone;
	/**
	 * 办公电话
	 */
	private String officePhone;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 照片
	 */
	private String photoPath;
	/**
	 * 单位
	 */
	private String units;
	/**
	 * 职务
	 */
	private String position;
	/**
	 * 职称
	 */
	private String title;
	/**
	 * 研究方向
	 */
	private String research;
	/**
	 * 专家简介
	 */
	private String summery;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 级别
	 */
	private String level;

	/**
	 * 评估的成员
	 */
	private Set<AsseMember> asseMemberse = new HashSet<AsseMember>(0);

	public Expert() {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXPERT_CATEGORY")
	public ExpertCategory getExpertCategory() {
		return this.expertCategory;
	}

	public void setExpertCategory(ExpertCategory expertCategory) {
		this.expertCategory = expertCategory;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTH_DATE", length = 11)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "GRADUATE_SCHOOL", length = 110)
	public String getGraduateSchool() {
		return this.graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	@Column(name = "SCHOOL_RECORD", length = 110)
	public String getSchoolRecord() {
		return this.schoolRecord;
	}

	public void setSchoolRecord(String schoolRecord) {
		this.schoolRecord = schoolRecord;
	}

	@Column(name = "DEGREE", length = 110)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "MOBILE", length = 110)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "HOME_PHONE", length = 110)
	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	@Column(name = "OFFICE_PHONE", length = 110)
	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name = "EMAIL", length = 510)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHOTO_PATH", length = 510)
	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Column(name = "UNITS", length = 510)
	public String getUnits() {
		return this.units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@Column(name = "POSITION", length = 110)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "TITLE", length = 110)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "RESEARCH", length = 510)
	public String getResearch() {
		return this.research;
	}

	public void setResearch(String research) {
		this.research = research;
	}

	@Column(name = "SUMMERY", length = 510)
	public String getSummery() {
		return this.summery;
	}

	public void setSummery(String summery) {
		this.summery = summery;
	}

	@Column(name = "REMARK", length = 510)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "LEVEL", length = 100)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "expert")
	public Set<AsseMember> getAsseMemberse() {
		return this.asseMemberse;
	}

	public void setAsseMemberse(Set<AsseMember> asseMemberse) {
		this.asseMemberse = asseMemberse;
	}
}
