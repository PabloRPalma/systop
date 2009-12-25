package com.systop.fsmis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.systop.common.modules.dept.model.Dept;

/**
 */
@Entity
@Table(name = "DEPT_SEND_SYPES", schema = "FSMIS")
public class DeptSendSype implements java.io.Serializable {

	private long id;
	private SendType sendTypes;
	private Dept dept;
	private String leaderDept;
	private String generalDept;

	public DeptSendSype() {
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
	@JoinColumn(name = "SEND_TYPE")
	public SendType getSendTypes() {
		return this.sendTypes;
	}

	public void setSendTypes(SendType sendTypes) {
		this.sendTypes = sendTypes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY")
	public Dept getDepts() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "LEADER_DEPT")
	public String getLeaderDept() {
		return this.leaderDept;
	}

	public void setLeaderDept(String leaderDept) {
		this.leaderDept = leaderDept;
	}

	@Column(name = "GENERAL_DEPT", length = 500)
	public String getGeneralDept() {
		return this.generalDept;
	}

	public void setGeneralDept(String generalDept) {
		this.generalDept = generalDept;
	}

}
