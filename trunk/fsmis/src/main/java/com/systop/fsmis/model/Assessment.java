package com.systop.fsmis.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;

/**
 * 评估表
 */
@Entity
@Table(name = "ASSESSMENTS", schema = "FSMIS")
@SuppressWarnings("serial")
public class Assessment extends BaseModel {

	private Integer id;
	private FsCase fsCase;
	/**
	 * 申请人
	 */
	private User proposer;
	private Date askDate;
	private String askCause;

	/**
	 * 风险评估状态 "0" → 待审核; "1" → 审核通过; "2" → 审核未通过;  "3" → 评估启动;  "4" → 评估完毕
	 */
	private String state;
	
	/** 评估等级 */
	private String level;
	private Date resultDate;
	private String result;

	private Set<AsseMember> asseMemberse = new HashSet<AsseMember>(0);
	
	private Set<AssessmentAttach> asseAtts = new HashSet<AssessmentAttach>(0);

	private Set<CheckResult> checkResults = new HashSet<CheckResult>(0);
	
	public Assessment() {
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
	@JoinColumn(name = "FS_CASE")
	public FsCase getFsCase() {
		return this.fsCase;
	}

	public void setFsCase(FsCase fsCase) {
		this.fsCase = fsCase;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSER")
	public User getProposer() {
		return this.proposer;
	}

	public void setProposer(User proposer) {
		this.proposer = proposer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ASK_DATE", length = 11)
	public Date getAskDate() {
		return this.askDate;
	}

	public void setAskDate(Date askDate) {
		this.askDate = askDate;
	}

	@Column(name = "ASK_CAUSE")
	@Type(type = "text")
	public String getAskCause() {
		return this.askCause;
	}

	public void setAskCause(String askCause) {
		this.askCause = askCause;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "LEVELS", length = 50)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RESULT_DATE", length = 11)
	public Date getResultDate() {
		return this.resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	@Column(name = "RESULT")
	@Type(type = "text")
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment")
	public Set<AsseMember> getAsseMemberse() {
		return this.asseMemberse;
	}

	public void setAsseMemberse(Set<AsseMember> asseMemberse) {
		this.asseMemberse = asseMemberse;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment")
	public Set<AssessmentAttach> getAsseAtts() {
		return this.asseAtts;
	}

	public void setAsseAtts(Set<AssessmentAttach> asseAtts) {
		this.asseAtts = asseAtts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment")
	public Set<CheckResult> getCheckResults() {
  	return checkResults;
  }

	public void setCheckResults(Set<CheckResult> checkResults) {
  	this.checkResults = checkResults;
  }

}
