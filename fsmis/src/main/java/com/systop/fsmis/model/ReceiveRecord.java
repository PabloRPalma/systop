package com.systop.fsmis.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.model.BaseModel;

/**
 * 部门接收纪录
 * @author ZW
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "RECEIVE_RECORDS", schema = "FSMIS")
public class ReceiveRecord extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 接收部门
	 */
	private Dept dept;
	
	/**
	 * 对应通知
	 */
	private Notice notice;
	
	/**
	 * 是否最新
	 */
	private String isNew;
	
	/**
	 * 接收时间
	 */
	private Date receiveDate;

	/**
	 * 默认构造器
	 */
	public ReceiveRecord() {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPT")
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NOTICE")
	public Notice getNotice() {
		return this.notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	@Column(name = "IS_NEW", length = 1)
	public String getIsNew() {
		return this.isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVE_DATE", length = 11)
	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

}
