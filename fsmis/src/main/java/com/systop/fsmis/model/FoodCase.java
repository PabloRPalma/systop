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
@Table(name = "FOOD_CASES", schema = "FSMIS")
public class FoodCase extends BaseModel {

	private Integer id;
	private Supervisor supervisor;
	private SendType sendType;
	private FoodCase foodCase;
	private Dept deptsByReportDept;
	private Dept deptsByCounty;
	private CaseType caseType;
	private Corp corp;
	private String title;
	private Date caseTime;
	private String address;
	private String code;
	private String descn;
	private String coordinate;
	private String informer;
	private String informerPhone;
	private Date closedTime;
	private String status;
	private Character isRead;
	private Character isMultiple;
	private Date beginDate;
	private Date endDate;
	private Character isSubmitSj;
	private Date submitSjTime;
	private Set<SmsSend> smsSendses = new HashSet<SmsSend>(0);
	private Set<Assessment> assessmentses = new HashSet<Assessment>(0);
	private Set<Task> taskses = new HashSet<Task>(0);
	private Set<FoodCase> foodCaseses = new HashSet<FoodCase>(0);
	private Set<FoodCase> foodCasesesForGenericCaseId = new HashSet<FoodCase>(0);
	private Set<FoodCase> foodCasesesForCompositiveCaseId = new HashSet<FoodCase>(
			0);
	private Set<JointTask> jointTaskses = new HashSet<JointTask>(0);
	private Set<SmsReceive> smsReceiveses = new HashSet<SmsReceive>(0);

	public FoodCase() {
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

	public void setSupervisors(Supervisor supervisor) {
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
	public FoodCase getFoodCase() {
		return this.foodCase;
	}

	public void setFoodCase(FoodCase foodCase) {
		this.foodCase = foodCase;
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
	public Character getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Character isRead) {
		this.isRead = isRead;
	}

	@Column(name = "IS_MULTIPLE", length = 1)
	public Character getIsMultiple() {
		return this.isMultiple;
	}

	public void setIsMultiple(Character isMultiple) {
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
	public Character getIsSubmitSj() {
		return this.isSubmitSj;
	}

	public void setIsSubmitSj(Character isSubmitSj) {
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCase")
	public Set<SmsSend> getSmsSendses() {
		return this.smsSendses;
	}

	public void setSmsSendses(Set<SmsSend> smsSendses) {
		this.smsSendses = smsSendses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCase")
	public Set<Assessment> getAssessmentses() {
		return this.assessmentses;
	}

	public void setAssessmentses(Set<Assessment> assessmentses) {
		this.assessmentses = assessmentses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCase")
	public Set<Task> getTaskses() {
		return this.taskses;
	}

	public void setTaskses(Set<Task> taskses) {
		this.taskses = taskses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCase")
	public Set<FoodCase> getFoodCaseses() {
		return this.foodCaseses;
	}

	public void setFoodCaseses(Set<FoodCase> foodCaseses) {
		this.foodCaseses = foodCaseses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CASE_COMPOSITIVE", joinColumns = { @JoinColumn(name = "COMPOSITIVE_CASE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "GENERIC_CASE_ID", nullable = false, updatable = false) })
	public Set<FoodCase> getFoodCasesesForGenericCaseId() {
		return this.foodCasesesForGenericCaseId;
	}

	public void setFoodCasesesForGenericCaseId(
			Set<FoodCase> foodCasesesForGenericCaseId) {
		this.foodCasesesForGenericCaseId = foodCasesesForGenericCaseId;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CASE_COMPOSITIVE", joinColumns = { @JoinColumn(name = "GENERIC_CASE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "COMPOSITIVE_CASE_ID", nullable = false, updatable = false) })
	public Set<FoodCase> getFoodCasesesForCompositiveCaseId() {
		return this.foodCasesesForCompositiveCaseId;
	}

	public void setFoodCasesesForCompositiveCaseId(
			Set<FoodCase> foodCasesesForCompositiveCaseId) {
		this.foodCasesesForCompositiveCaseId = foodCasesesForCompositiveCaseId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCase")
	public Set<JointTask> getJointTaskses() {
		return this.jointTaskses;
	}

	public void setJointTaskses(Set<JointTask> jointTaskses) {
		this.jointTaskses = jointTaskses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCase")
	public Set<SmsReceive> getSmsReceiveses() {
		return this.smsReceiveses;
	}

	public void setSmsReceiveses(Set<SmsReceive> smsReceiveses) {
		this.smsReceiveses = smsReceiveses;
	}

}
