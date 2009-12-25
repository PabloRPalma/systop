package com.systop.fsmis.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.systop.common.modules.dept.model.Dept;

@Entity
@Table(name = "RECEIVE_RECORDS")
public class ReceiveRecord implements java.io.Serializable {

	private long id;
	private Dept dept;
	private Notice notice;
	private Character isNew;
	private Date receiveDate;

	public ReceiveRecord() {
	}

	public ReceiveRecord(long id) {
		this.id = id;
	}

	public ReceiveRecord(long id, Dept dept, Notice notice, Character isNew,
			Date receiveDate) {
		this.id = id;
		this.dept = dept;
		this.notice = notice;
		this.isNew = isNew;
		this.receiveDate = receiveDate;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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
	public Character getIsNew() {
		return this.isNew;
	}

	public void setIsNew(Character isNew) {
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
