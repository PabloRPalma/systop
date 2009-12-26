package com.systop.fsmis.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SEND_TYPES", schema = "FSMIS")
public class SendType implements java.io.Serializable {

	private Integer id;
	
	/** 类别名称 */
	private String name;
	
	/** 类别描述 */
	private String descn;
	
	/** 排列序号 */
	private Integer sortId;

	private Set<DeptSendType> deptSendSypeses = new HashSet<DeptSendType>(0);

	/** 对应案件 */
	private Set<FsCase> fsCases = new HashSet<FsCase>(0);

	public SendType() {
	}

	public SendType(Integer id) {
		this.id = id;
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

	@Column(name = "SORT_ID", precision = 22)
	public Integer getSortId() {
		return this.sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sendType")
	public Set<DeptSendType> getDeptSendSypeses() {
		return this.deptSendSypeses;
	}

	public void setDeptSendSypeses(Set<DeptSendType> deptSendSypeses) {
		this.deptSendSypeses = deptSendSypeses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sendType")
	public Set<FsCase> getFsCases() {
		return this.fsCases;
	}

	public void setFsCases(Set<FsCase> cases) {
		this.fsCases = cases;
	}

}
