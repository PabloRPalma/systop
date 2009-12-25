package com.systop.fsmis.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 */
@Entity
@Table(name = "SEND_TYPES",schema="FSMIS")
public class SendType implements java.io.Serializable {

	private long id;
	private String name;
	private String descn;
	private BigDecimal sortId;
	private Set<DeptSendSype> deptSendSypeses = new HashSet<DeptSendSype>(0);
	private Set<FsCase> fsCases = new HashSet<FsCase>(0);

	public SendType() {
	}

	public SendType(long id) {
		this.id = id;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCN")
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "SORT_ID", precision = 22, scale = 0)
	public BigDecimal getSortId() {
		return this.sortId;
	}

	public void setSortId(BigDecimal sortId) {
		this.sortId = sortId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sendTypes")
	public Set<DeptSendSype> getDeptSendSypeses() {
		return this.deptSendSypeses;
	}

	public void setDeptSendSypeses(Set<DeptSendSype> deptSendSypeses) {
		this.deptSendSypeses = deptSendSypeses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sendTypes")
	public Set<FsCase> getFsCases() {
		return this.fsCases;
	}

	public void setFsCases(Set<FsCase> cases) {
		this.fsCases = cases;
	}

}
