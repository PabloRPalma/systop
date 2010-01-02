package com.systop.fsmis.model;

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
 */
@Entity
@Table(name = "CASE_TYPES", schema = "FSMIS")
@SuppressWarnings("serial")
public class CaseType extends BaseModel {

	/** 主键 */
	private Integer id;
	/** 上级类别ID */
	private CaseType caseType;
	/** 名称 */
	private String name;
	/** 描述 */
	private String descn;
	/** 类别对应的一般事件 */
	private Set<FsCase> fsCases = new HashSet<FsCase>(0);
	/** 类别记录 */
	private Set<CaseType> caseTypes = new HashSet<CaseType>(0);

	/** 默认构造 */
	public CaseType() {
	}
	
	public CaseType(String name){
		this.name = name;
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
	public Set<FsCase> getFsCases() {
		return this.fsCases;
	}

	public void setFsCases(Set<FsCase> fsCases) {
		this.fsCases = fsCases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caseType")
	public Set<CaseType> getCaseTypes() {
		return this.caseTypes;
	}

	public void setCaseTypes(Set<CaseType> caseTypes) {
		this.caseTypes = caseTypes;
	}

}
