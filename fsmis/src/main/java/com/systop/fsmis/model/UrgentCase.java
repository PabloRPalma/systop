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

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;

/**
 * 应急事件表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "URGENT_CASES", schema="FSMIS")
public class UrgentCase extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 对应的市级应急事件
	 */
	private UrgentCase urgentCases;
	/**
	 * 所属区县
	 */
	private Dept county;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 事发时间
	 */
	private Date caseTime;
	/**
	 * 事件编号
	 */
	private String code;
	/**
	 * 事发地点
	 */
	private String address;
	/**
	 * 地理坐标
	 */
	private String coordinate;
	/**
	 * 事发原因
	 */
	private String cause;
	/**
	 * 事件描述
	 */
	private String descn;
	/**
	 * 受害人数
	 */
	private String harmNum;
	/**
	 * 死亡人数
	 */
	private String deathNum;
	/**
	 * 波及范围
	 */
	private String caseRange;
	/**
	 * 预案等级
	 */
	private String plansLevel;
	/**
	 * 周边医院情况
	 */
	private String hospitalInf;
	/**
	 * 周边交通情况
	 */
	private String trafficInf;
	/**
	 * 报告人
	 */
	private String reporter;
	/**
	 * 报告人单位
	 */
	private String reporterUnits;
	/**
	 * 报告人电话
	 */
	private String reporterPhone;
	/**
	 * 事件状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 审核人
	 */
	private User auditor;
	/**
	 * 审核时间
	 */
	private Date auditDate;
	/**
	 * 审核意见
	 */
	private String auditOpinion;
	/**
	 * 是否审核
	 */
	private String isAgree;
	/**
	 * 是否上报市级
	 */
	private String isSubmitted;
	/**
	 * 上报时间
	 */
	private Date submitTime;
	/**
	 * 对应的应急组
	 */
	private Set<UrgentGroup> urgentGroupses = new HashSet<UrgentGroup>(0);
	
	private Set<UrgentCase> urgentCaseses = new HashSet<UrgentCase>(0);

	public UrgentCase() {
	}

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SELF_CASE")
	public UrgentCase getUrgentCases() {
		return this.urgentCases;
	}

	public void setUrgentCases(UrgentCase urgentCases) {
		this.urgentCases = urgentCases;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY")
	public Dept getCounty() {
		return this.county;
	}

	public void setCounty(Dept dept) {
		this.county = dept;
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

	@Column(name = "CODE", length = 110)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "ADDRESS", length = 510)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "COORDINATE", length = 510)
	public String getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	@Column(name = "CAUSE", length = 510)
	public String getCause() {
		return this.cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	@Column(name = "DESCN", length = 4000)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "HARM_NUM", length = 110)
	public String getHarmNum() {
		return this.harmNum;
	}

	public void setHarmNum(String harmNum) {
		this.harmNum = harmNum;
	}

	@Column(name = "DEATH_NUM", length = 110)
	public String getDeathNum() {
		return this.deathNum;
	}

	public void setDeathNum(String deathNum) {
		this.deathNum = deathNum;
	}

	@Column(name = "CASE_RANGE", length = 510)
	public String getCaseRange() {
		return this.caseRange;
	}

	public void setCaseRange(String caseRange) {
		this.caseRange = caseRange;
	}

	@Column(name = "PLANS_LEVEL", length = 110)
	public String getPlansLevel() {
		return this.plansLevel;
	}

	public void setPlansLevel(String plansLevel) {
		this.plansLevel = plansLevel;
	}

	@Column(name = "HOSPITAL_INF", length = 510)
	public String getHospitalInf() {
		return this.hospitalInf;
	}

	public void setHospitalInf(String hospitalInf) {
		this.hospitalInf = hospitalInf;
	}

	@Column(name = "TRAFFIC_INF", length = 510)
	public String getTrafficInf() {
		return this.trafficInf;
	}

	public void setTrafficInf(String trafficInf) {
		this.trafficInf = trafficInf;
	}

	@Column(name = "REPORTER", length = 510)
	public String getReporter() {
		return this.reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	@Column(name = "REPORTER_UNITS", length = 510)
	public String getReporterUnits() {
		return this.reporterUnits;
	}

	public void setReporterUnits(String reporterUnits) {
		this.reporterUnits = reporterUnits;
	}

	@Column(name = "REPORTER_PHONE", length = 110)
	public String getReporterPhone() {
		return this.reporterPhone;
	}

	public void setReporterPhone(String reporterPhone) {
		this.reporterPhone = reporterPhone;
	}

	@Column(name = "STATUS", length = 110)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUDITOR")
	public User getAuditor() {
		return this.auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDIT_DATE", length = 11)
	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Column(name = "AUDIT_OPINION", length = 510)
	public String getAuditOpinion() {
		return this.auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Column(name = "IS_AGREE", length = 1)
	public String getIsAgree() {
		return this.isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	@Column(name = "IS_SUBMITTED", length = 1)
	public String getIsSubmitted() {
		return this.isSubmitted;
	}

	public void setIsSubmitted(String isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMIT_TIME", length = 11)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "urgentCases")
	public Set<UrgentGroup> getUrgentGroupses() {
		return this.urgentGroupses;
	}

	public void setUrgentGroupses(Set<UrgentGroup> urgentGroupses) {
		this.urgentGroupses = urgentGroupses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "urgentCases")
	public Set<UrgentCase> getUrgentCaseses() {
		return this.urgentCaseses;
	}

	public void setUrgentCaseses(Set<UrgentCase> urgentCaseses) {
		this.urgentCaseses = urgentCaseses;
	}

}
