package com.systop.fsmis.model;

// Generated 2009-12-16 9:41:03 by Hibernate Tools 3.2.4.GA

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

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 事件类别
 * @author shaozhiyuan
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CASE_TYPE")
public class CaseType extends BaseModel {

	/** 主键 */
	private Integer id;
	/** 类别ID */
	private CaseType caseType;
	/** 名称 */
	private String name;
	/** 描述 */
	private String descn;
	/** 类别对应的一般事件 */
	private Set<GenericCase> genericCases = new HashSet<GenericCase>(0);
	private Set<CaseType> caseTypes = new HashSet<CaseType>(0);



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
