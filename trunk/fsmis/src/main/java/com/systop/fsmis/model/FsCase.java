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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.model.BaseModel;

/**
 * 食品投诉案件
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FS_CASES", schema = "FSMIS")
public class FsCase extends BaseModel {

	private Integer id;
	private Supervisor supervisor;
	private SendType sendType;

	/**
	 * 对应上报市级后的案件
	 */
	private FsCase selfSJ;
	private Dept deptsByReportDept;
	private Dept deptsByCounty;
	private CaseType caseType;
	private Corp corp;
	private String title;
	/**
	 * 案发时间
	 */
	private Date caseTime;
	private String address;
	private String code;
	private String descn;
	private String coordinate;
	private String informer;
	private String informerPhone;
	/**
	 * 结案时间
	 */
	private Date closedTime;
	private String status;

	/**
	 * 是否查看（可能综合要使用）
	 */
	private String isRead;

	/**
	 * 是否综合案件
	 */
	private String isMultiple;

	/**
	 * 综合开始时间
	 */
	private Date beginDate;

	/**
	 * 综合结束时间
	 */
	private Date endDate;

	/**
	 * 是否上报市级
	 */
	private String isSubmitSj;
	private Date submitSjTime;
	
	
	private Set<Task> taskses = new HashSet<Task>(0);

	private Set<JointTask> jointTaskses = new HashSet<JointTask>(0);

	private Set<Assessment> assessmentses = new HashSet<Assessment>(0);

	private Set<FsCase> submitCases = new HashSet<FsCase>(0);

	/**
	 * 若为一般案件，代表所属的综合案件集合
	 */
	private Set<FsCase> compositiveCases = new HashSet<FsCase>(0);
	
	/**
	 * 若为综合案件，代表包含一般案件的集合
	 */
	private Set<FsCase> genericCases = new HashSet<FsCase>(0);

	private Set<SmsSend> smsSendses = new HashSet<SmsSend>(0);

	private Set<SmsReceive> smsReceiveses = new HashSet<SmsReceive>(0);

	public FsCase() {
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
	@JoinColumn(name = "SUPERVISOR")
	public Supervisor getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
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
	@JoinColumn(name = "SELF_SJ")
	public FsCase getSelfSJ() {
		return this.selfSJ;
	}

	public void setSelfSJ(FsCase selfSJ) {
		this.selfSJ = selfSJ;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORT_DEPT")
	public Dept getDeptsByReportDept() {
		return this.deptsByReportDept;
	}

	public void setDeptsByReportDept(Dept deptsByReportDept) {
		this.deptsByReportDept = deptsByReportDept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY")
	public Dept getDeptsByCounty() {
		return this.deptsByCounty;
	}

	public void setDeptsByCounty(Dept deptsByCounty) {
		this.deptsByCounty = deptsByCounty;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CASE_TYPE")
	public CaseType getCaseType() {
		return this.caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENTERPRISE")
	public Corp getCorp() {
		return this.corp;
	}

	public void setCorp(Corp corp) {
		this.corp = corp;
	}

	@Column(name = "TITLE", length = 510)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CASE_TIME", length = 11)
	public Date getCaseTime() {
		return this.caseTime;
	}

	public void setCaseTime(Date caseTime) {
		this.caseTime = caseTime;
	}

	@Column(name = "ADDRESS", length = 510)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CODE", length = 510)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "DESCN", length = 510)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "COORDINATE", length = 510)
	public String getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	@Column(name = "INFORMER", length = 510)
	public String getInformer() {
		return this.informer;
	}

	public void setInformer(String informer) {
		this.informer = informer;
	}

	@Column(name = "INFORMER_PHONE", length = 510)
	public String getInformerPhone() {
		return this.informerPhone;
	}

	public void setInformerPhone(String informerPhone) {
		this.informerPhone = informerPhone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLOSED_TIME", length = 11)
	public Date getClosedTime() {
		return this.closedTime;
	}

	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}

	@Column(name = "STATUS", length = 510)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "IS_READ", length = 1)
	public String getIsRead() {
		return this.isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@Column(name = "IS_MULTIPLE", length = 1)
	public String getIsMultiple() {
		return this.isMultiple;
	}

	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BEGIN_DATE", length = 11)
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE", length = 11)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "IS_SUBMIT_SJ", length = 1)
	public String getIsSubmitSj() {
		return this.isSubmitSj;
	}

	public void setIsSubmitSj(String isSubmitSj) {
		this.isSubmitSj = isSubmitSj;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMIT_SJ_TIME", length = 11)
	public Date getSubmitSjTime() {
		return this.submitSjTime;
	}

	public void setSubmitSjTime(Date submitSjTime) {
		this.submitSjTime = submitSjTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
	public Set<SmsSend> getSmsSendses() {
		return this.smsSendses;
	}

	public void setSmsSendses(Set<SmsSend> smsSendses) {
		this.smsSendses = smsSendses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
	public Set<Assessment> getAssessmentses() {
		return this.assessmentses;
	}

	public void setAssessmentses(Set<Assessment> assessmentses) {
		this.assessmentses = assessmentses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
	public Set<Task> getTaskses() {
		return this.taskses;
	}

	public void setTaskses(Set<Task> taskses) {
		this.taskses = taskses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "selfSJ")
	public Set<FsCase> getSubmitCases() {
		return this.submitCases;
	}

	public void setSubmitCases(Set<FsCase> submitCases) {
		this.submitCases = submitCases;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "GENERIC_COMPOSITIVE", joinColumns = { @JoinColumn(name = "COMPOSITIVE_CASE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "GENERIC_CASE_ID", nullable = false, updatable = false) })
	public Set<FsCase> getGenericCases() {
		return this.genericCases;
	}

	public void setGenericCases(Set<FsCase> genericCases) {
		this.genericCases = genericCases;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "GENERIC_COMPOSITIVE", joinColumns = { @JoinColumn(name = "GENERIC_CASE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "COMPOSITIVE_CASE_ID", nullable = false, updatable = false) })
	public Set<FsCase> getCompositiveCases() {
		return this.compositiveCases;
	}

	public void setCompositiveCases(Set<FsCase> compositiveCases) {
		this.compositiveCases = compositiveCases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
	public Set<JointTask> getJointTaskses() {
		return this.jointTaskses;
	}

	public void setJointTaskses(Set<JointTask> jointTaskses) {
		this.jointTaskses = jointTaskses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
	public Set<SmsReceive> getSmsReceiveses() {
		return this.smsReceiveses;
	}

	public void setSmsReceiveses(Set<SmsReceive> smsReceiveses) {
		this.smsReceiveses = smsReceiveses;
	}

}
