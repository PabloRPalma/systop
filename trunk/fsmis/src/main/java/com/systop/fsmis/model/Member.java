package com.systop.fsmis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.model.BaseModel;

/**
 * 委员会成员表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "MEMBERS", schema = "FSMIS")
public class Member extends BaseModel {

	/** 
	 * 主键 
	 */
	private Integer id;
	/** 
	 * 姓名 
	 */
	private String name;
	/** 
	 * 部门 
	 */
	private String dept;
	/** 
	 * 职位 
	 */
	private String jobs;
	/** 
	 * 固话 
	 */
	private String phone;
	/** 
	 * 手机 
	 */
	private String mobile;
	/** 
	 * 备注
	 */
	private String descn;
	
	/**
	 * 所属区县
	 */
	private Dept county;

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DEPT")
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "DESCN", length = 4000)
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}
	
	@Column(name = "JOBS")
	public String getJobs() {
		return jobs;
	}

	public void setJobs(String jobs) {
		this.jobs = jobs;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY")
	public Dept getCounty() {
  	return county;
  }

	public void setCounty(Dept county) {
  	this.county = county;
  }
}
