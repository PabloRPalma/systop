package com.systop.fsmis.model;

// Generated 2009-12-16 9:15:02 by Hibernate Tools 3.2.4.GA

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 综合案件
 * @author shaozhiyuan
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "COMPOSITIVE_CASE", schema = "FSMIS")
public class CompositiveCase extends BaseModel {

	/** 主键 */
	private Integer id;
	/** 标题 */
	private String title;
	/** 开始统计时间 */
	private Date beginDate;
	/** 终止统计时间 */
	private Date endDate;
	/** 是否查看 */
	private Character isRead;
	/** 描述 */
	private String descn;
	/** 状态 */
	private Character status;
	/**
	 * 本综合事件对应的联合任务
	 */
	private Set<JointTask> jointTasks = new HashSet<JointTask>(0);
	/**
	 * 本综合事件对应的综合任务
	 */
	private Set<Task> tasks = new HashSet<Task>(0);
	/**
	 * 本综合事件对应的评估任务
	 */
	private Set<Assessment> assessments = new HashSet<Assessment>(0);
	/**
	 * 本综合事件对应的短信发送
	 */
	private Set<SmsSend> smsSends = new HashSet<SmsSend>(0);
	/**
	 * 本综合事件对应的一般事件
	 */
	private Set<GenericCase> genericCases = new HashSet<GenericCase>(0);



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

	@Column(name = "TITLE", length = 510)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Column(name = "IS_READ", length = 1)
	public Character getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Character isRead) {
		this.isRead = isRead;
	}

	@Column(name = "DESCN", length = 1000)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "STATUS", length = 1)
	public Character getStatus() {
		return this.status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compositiveCase")
	public Set<JointTask> getJointTasks() {
		return this.jointTasks;
	}

	public void setJointTasks(Set<JointTask> jointTasks) {
		this.jointTasks = jointTasks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compositiveCase")
	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compositiveCase")
	public Set<Assessment> getAssessments() {
		return this.assessments;
	}

	public void setAssessments(Set<Assessment> assessments) {
		this.assessments = assessments;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compositiveCase")
	public Set<SmsSend> getSmsSends() {
		return this.smsSends;
	}

	public void setSmsSends(Set<SmsSend> smsSends) {
		this.smsSends = smsSends;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CASE_COMPOSITIVE", joinColumns = { @JoinColumn(name = "COMPOSITIVE_CASE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "GENERIC_CASE_ID", nullable = false, updatable = false) })
	public Set<GenericCase> getGenericCases() {
		return this.genericCases;
	}

	public void setGenericCases(Set<GenericCase> genericCases) {
		this.genericCases = genericCases;
	}

}
