package com.systop.fsmis.model;

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;

/**
 * 评估表 The persistent class for the ASSESSMENT database table.
 * @author DU
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ASSESSMENT", schema = "FSMIS")
public class Assessment extends BaseModel {

	/**
   * 主键
   */
	private Integer id;
	/**
   * 基本案件
   */
	private GenericCase genericCase;
	/**
   * 综合案件
   */
	private CompositiveCase compositiveCase;
	/**
   * 审核人
   */
	private User usersByAuditor;
	/**
   * 申请人
   */
	private User usersByProposer;
	/**
   * 申请日期
   */
	private Date askDate;
	/**
   * 申请原因
   */
	private Clob askCause;
	/**
   * 审核日期
   */
	private Date auditDate;
	/**
   * 是否通过
   */
	private Character isConsent;
	/**
   * 结果日期
   */
	private Date resultDate;
	/**
   * 评估结果
   */
	private String result;
	/**
   * 是否完成
   */
	private Character isComplete;
	/**
   * 专家组长ID
   */
	private Long leaderId;
	/**
   * 专家
   */
	private Set<Expert> experts = new HashSet<Expert>(0);
	/**
   * 附件
   */
	private Set<AssessmentAttach> assessmentAttachs = new HashSet<AssessmentAttach>(0);

	/**
   * 缺省构造器
   */
	public Assessment() {
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
	@JoinColumn(name = "GENERIC_CASE")
	public GenericCase getGenericCase() {
		return this.genericCase;
	}

	public void setGenericCase(GenericCase genericCase) {
		this.genericCase = genericCase;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPOSITIVE_CASE")
	public CompositiveCase getCompositiveCase() {
		return this.compositiveCase;
	}

	public void setCompositiveCase(CompositiveCase compositiveCase) {
		this.compositiveCase = compositiveCase;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUDITOR")
	public User getUsersByAuditor() {
		return this.usersByAuditor;
	}

	public void setUsersByAuditor(User usersByAuditor) {
		this.usersByAuditor = usersByAuditor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSER")
	public User getUsersByProposer() {
		return this.usersByProposer;
	}

	public void setUsersByProposer(User usersByProposer) {
		this.usersByProposer = usersByProposer;
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
	public Clob getAskCause() {
		return this.askCause;
	}

	public void setAskCause(Clob askCause) {
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
	public Character getIsConsent() {
		return this.isConsent;
	}

	public void setIsConsent(Character isConsent) {
		this.isConsent = isConsent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RESULT_DATE", length = 11)
	public Date getResultDate() {
		return this.resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	@Column(name = "RESULT", length = 4000)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "IS_COMPLETE", length = 1)
	public Character getIsComplete() {
		return this.isComplete;
	}

	public void setIsComplete(Character isComplete) {
		this.isComplete = isComplete;
	}

	@Column(name = "LEADER_ID", precision = 10, scale = 0)
	public Long getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ASSESSMENT_EXPERT", joinColumns = { @JoinColumn(name = "ASSESSMENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "EXPERT_ID", nullable = false, updatable = false) })
	public Set<Expert> getExperts() {
		return this.experts;
	}

	public void setExperts(Set<Expert> experts) {
		this.experts = experts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment")
	public Set<AssessmentAttach> getAssessmentAttachs() {
		return this.assessmentAttachs;
	}

	public void setAssessmentAttachs(Set<AssessmentAttach> assessmentAttachs) {
		this.assessmentAttachs = assessmentAttachs;
	}
}
