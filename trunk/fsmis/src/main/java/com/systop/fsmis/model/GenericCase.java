package com.systop.fsmis.model;

// Generated 2009-12-16 9:41:03 by Hibernate Tools 3.2.4.GA

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

import com.systop.core.model.BaseModel;

/**
 * 一般案件
 * @author shaozhiyuan
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GENERIC_CASE", schema = "FSMIS")
public class GenericCase extends BaseModel {

	/** 主键 */
	private Integer id;
	
	/** 本一般案件对应信息员 */
	private Supervisor supervisor;
	
	/** 本一般案件对应部门*/
	private Depts depts;
	
	/** 本一般案件对应案件类别*/
	private CaseType caseType;
	
	/** 本一般案件对应企业*/
	private Enterprise enterprise;
	
	/** 标题*/
	private String title;
	
	/** 事发时间*/
	private Date eventDate;
	
	/** 事发地点*/
	private String address;
	
	/** 事件编号*/
	private String code;
	
	/** 事件描述*/
	private String descn;
	
	/** 地理坐标*/
	private String coordinate;
	
	/** 系统提交时间*/
	private Date submitTime;
	
	/** 举报人*/
	private String informer;
	
	/** 举报人电话*/
	private String informerPhone;
	
	/** 结案时间*/
	private Date closedTime;
	
	/** 事件状态*/
	private String status;
	
	/** 是否提交市级*/
	private Character isSubmitSj;
	
	/**
	 * 本一般事件对应的短信接收
	 */
	private Set<SmsReceive> smsReceives = new HashSet<SmsReceive>(0);
	
	/**
	 * 本一般事件对应的评估任务
	 */
	private Set<Assessment> assessments = new HashSet<Assessment>(0);
	
	/**
	 * 本一般事件对应的综合事件
	 */
	private Set<CompositiveCase> compositiveCases = new HashSet<CompositiveCase>(
			0);
	
	/**
	 * 本一般事件对应的一般任务
	 */
	private Set<Task> tasks = new HashSet<Task>(0);
	
	/**
	 * 本一般事件对应的联合任务
	 */
	private Set<JointTask> jointTasks = new HashSet<JointTask>(0);
	
	/**
	 * 本一般事件对应的短信发送
	 */
	private Set<SmsSend> smsSends = new HashSet<SmsSend>(0);

	public GenericCase() {
	}

	public GenericCase(Integer id) {
		this.id = id;
	}

	public GenericCase(Integer id, Supervisor supervisor, Depts depts,
			CaseType caseType, Enterprise enterprise, String title,
			Date eventDate, String address, String code, String descn,
			String coordinate, Date submitTime, String informer,
			String informerPhone, Date closedTime, String status,
			Character isSubmitSj, Set<SmsReceive> smsReceives,
			Set<Assessment> assessments, Set<CompositiveCase> compositiveCases,
			Set<Task> tasks, Set<JointTask> jointTasks, Set<SmsSend> smsSends) {
		this.id = id;
		this.supervisor = supervisor;
		this.depts = depts;
		this.caseType = caseType;
		this.enterprise = enterprise;
		this.title = title;
		this.eventDate = eventDate;
		this.address = address;
		this.code = code;
		this.descn = descn;
		this.coordinate = coordinate;
		this.submitTime = submitTime;
		this.informer = informer;
		this.informerPhone = informerPhone;
		this.closedTime = closedTime;
		this.status = status;
		this.isSubmitSj = isSubmitSj;
		this.smsReceives = smsReceives;
		this.assessments = assessments;
		this.compositiveCases = compositiveCases;
		this.tasks = tasks;
		this.jointTasks = jointTasks;
		this.smsSends = smsSends;
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
	@JoinColumn(name = "SUPERVISOR")
	public Supervisor getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORT_DEPT")
	public Depts getDepts() {
		return this.depts;
	}

	public void setDepts(Depts depts) {
		this.depts = depts;
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
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@Column(name = "TITLE", length = 510)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EVENT_DATE", length = 11)
	public Date getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMIT_TIME", length = 11)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
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

	@Column(name = "IS_SUBMIT_SJ", length = 1)
	public Character getIsSubmitSj() {
		return this.isSubmitSj;
	}

	public void setIsSubmitSj(Character isSubmitSj) {
		this.isSubmitSj = isSubmitSj;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genericCase")
	public Set<SmsReceive> getSmsReceives() {
		return this.smsReceives;
	}

	public void setSmsReceives(Set<SmsReceive> smsReceives) {
		this.smsReceives = smsReceives;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genericCase")
	public Set<Assessment> getAssessments() {
		return this.assessments;
	}

	public void setAssessments(Set<Assessment> assessments) {
		this.assessments = assessments;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CASE_COMPOSITIVE", joinColumns = { @JoinColumn(name = "GENERIC_CASE", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "COMPOSITIVE_CASE_ID", nullable = false, updatable = false) })
	public Set<CompositiveCase> getCompositiveCases() {
		return this.compositiveCases;
	}

	public void setCompositiveCases(Set<CompositiveCase> compositiveCases) {
		this.compositiveCases = compositiveCases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genericCase")
	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genericCase")
	public Set<JointTask> getJointTasks() {
		return this.jointTasks;
	}

	public void setJointTasks(Set<JointTask> jointTasks) {
		this.jointTasks = jointTasks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genericCase")
	public Set<SmsSend> getSmsSends() {
		return this.smsSends;
	}

	public void setSmsSends(Set<SmsSend> smsSends) {
		this.smsSends = smsSends;
	}

}
