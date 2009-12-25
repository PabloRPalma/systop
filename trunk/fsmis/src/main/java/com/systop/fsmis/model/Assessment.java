package com.systop.fsmis.model;

import java.sql.Clob;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private FsCase fsCases;
	/**
	 * 申请人
	 */
	private User proposer;
	
	/**
	 * 审核人
	 */
	private User auditor;
	private Date askDate;
	private String askCause;
	private Date auditDate;
	/**
	 * 是否通过
	 */
	private String isConsent;
	private String opinion;
	private Date resultDate;
	private Clob result;
	private String isComplete;
	private Set<ExpertGroup> expertGroupses = new HashSet<ExpertGroup>(0);
	private Set<AssessmentAttach> assessmentAttachses = new HashSet<AssessmentAttach>(
			0);

	public Assessment() {
	}


	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FS_CASE")
	public FsCase getFsCase() {
		return this.fsCases;
	}

	public void setFsCase(FsCase fsCases) {
		this.fsCases = fsCases;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUDITOR")
	public User getAuditor() {
		return this.auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
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

	@Column(name = "ASK_CAUSE", length = 1000)
	public String getAskCause() {
		return this.askCause;
	}

	public void setAskCause(String askCause) {
		this.askCause = askCause;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDIT_DATE", length = 11)
	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Column(name = "IS_CONSENT", length = 1)
	public String getIsConsent() {
		return this.isConsent;
	}

	public void setIsConsent(String isConsent) {
		this.isConsent = isConsent;
	}

	@Column(name = "OPINION", length = 500)
	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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
	public Clob getResult() {
		return this.result;
	}

	public void setResult(Clob result) {
		this.result = result;
	}

	@Column(name = "IS_COMPLETE", length = 1)
	public String getIsComplete() {
		return this.isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assessments")
	public Set<ExpertGroup> getExpertGroupses() {
		return this.expertGroupses;
	}

	public void setExpertGroupses(Set<ExpertGroup> expertGroupses) {
		this.expertGroupses = expertGroupses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assessments")
	public Set<AssessmentAttach> getAssessmentAttachses() {
		return this.assessmentAttachses;
	}

	public void setAssessmentAttachses(
			Set<AssessmentAttach> assessmentAttachses) {
		this.assessmentAttachses = assessmentAttachses;
	}

}
