package com.systop.fsmis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 举报投诉表
 * @author DU
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINS", schema = "FSMIS")
public class Complain extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 事发地点
	 */
	private String addr;
	/**
	 * 事件原因
	 */
	private String cause;
	/**
	 * 事发时间
	 */
	private Date reportTime;
	/**
	 * 系统保存时间
	 */
	private Date createTime;
	/**
	 * 事件投诉人
	 */
	private String reporter;
	/**
	 * 事件投诉人电话
	 */
	private String phoneNo;
	/**
	 * 事件描述
	 */
	private String descn;
	/**
	 * 事件信息标志，0(缺省)-未被业务系统采用;1-已被业务系统采用
	 */
	private String isConfirmed;
	/**
	 * 是否wap投诉，0（缺省）表示否，1表示是
	 */
	private String isWap;
	/**
	 * 当使用wap投诉的时候，系统发送的验证字符
	 */
	private String secureStr;
	/**
	 * 是否已经验证（0,否，1是）
	 */
	private String isValidated;
	/**
	 * 是否最新（0,否，1是）
	 */
	private String isNew;

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "ADDR")
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "CAUSE")
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	@Column(name = "REPORT_TIME")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "REPORTER")
	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	@Column(name = "PHONE_NO")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "DESCN")
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "IS_CONFIRMED")
	public String getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	@Column(name = "IS_WAP")
	public String getIsWap() {
		return isWap;
	}

	public void setIsWap(String isWap) {
		this.isWap = isWap;
	}

	@Column(name = "SECURE_STR")
	public String getSecureStr() {
		return secureStr;
	}

	public void setSecureStr(String secureStr) {
		this.secureStr = secureStr;
	}

	@Column(name = "IS_VALIDATED")
	public String getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(String isValidated) {
		this.isValidated = isValidated;
	}

	@Column(name = "IS_NEW")
	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
}
