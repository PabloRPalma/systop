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

/**
 * 
 */
@Entity
@Table(name = "DEPT_SEND_SYPES", schema = "FSMIS")
public class DeptSendType implements java.io.Serializable {

	private Integer id;
	private SendType sendType;
	private Dept county;
	private String leaderDept;
	private String generalDept;

	public DeptSendType() {
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
	@JoinColumn(name = "SEND_TYPE")
	public SendType getSendType() {
		return this.sendType;
	}

	public void setSendType(SendType sendType) {
		this.sendType = sendType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY")
	public Dept getCounty() {
		return this.county;
	}

	public void setCounty(Dept county) {
		this.county = county;
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
