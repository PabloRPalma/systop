package com.systop.fsmis.model;

// Generated 2009-12-16 9:41:03 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CaseType generated by hbm2java
 */
@Entity
@Table(name = "CASE_TYPE")
public class CaseType implements java.io.Serializable {

	private long id;
	private CaseType caseType;
	private String name;
	private String descn;
	private Set<GenericCase> genericCases = new HashSet<GenericCase>(0);
	private Set<CaseType> caseTypes = new HashSet<CaseType>(0);

	public CaseType() {
	}

	public CaseType(long id) {
		this.id = id;
	}

	public CaseType(long id, CaseType caseType, String name, String descn,
			Set<GenericCase> genericCases, Set<CaseType> caseTypes) {
		this.id = id;
		this.caseType = caseType;
		this.name = name;
		this.descn = descn;
		this.genericCases = genericCases;
		this.caseTypes = caseTypes;
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
	@JoinColumn(name = "PARENT")
	public CaseType getCaseType() {
		return this.caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	@Column(name = "NAME", length = 510)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCN", length = 1000)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caseType")
	public Set<GenericCase> getGenericCases() {
		return this.genericCases;
	}

	public void setGenericCases(Set<GenericCase> genericCases) {
		this.genericCases = genericCases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caseType")
	public Set<CaseType> getCaseTypes() {
		return this.caseTypes;
	}

	public void setCaseTypes(Set<CaseType> caseTypes) {
		this.caseTypes = caseTypes;
	}

}
