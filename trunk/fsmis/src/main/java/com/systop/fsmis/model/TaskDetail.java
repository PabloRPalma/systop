package com.systop.fsmis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.model.BaseModel;
import com.systop.core.util.RemaindaysUtil;

/**
 * 任务明细表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TASK_DETAILS")
public class TaskDetail extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 对应任务
	 */
	private Task task;

	/**
	 * 对应部门
	 */
	private Dept dept;

	/**
	 * 填写人
	 */
	private String inputer;

	/**
	 * 处理人
	 */
	private String processor;

	/**
	 * 处理过程
	 */
	private String process;

	/**
	 * 处理依据
	 */
	private String basis;

	/**
	 * 处理结果
	 */
	private String result;

	/**
	 * 任务完成时间
	 */
	private Date completionTime;

	/**
	 * 任务状态
	 */
	private String status;

	/**
	 * 退回负责人
	 */
	private String returnPeople;

	/**
	 * 退回原因
	 */
	private String returnReason;

	/**
	 * 缺省构造方法
	 */
	public TaskDetail() {
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
	@JoinColumn(name = "TASK")
	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPT")
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "INPUTER", length = 110)
	public String getInputer() {
		return this.inputer;
	}

	public void setInputer(String inputer) {
		this.inputer = inputer;
	}

	@Column(name = "PROCESSOR", length = 110)
	public String getProcessor() {
		return this.processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	@Column(name = "PROCESS", length = 1000)
	public String getProcess() {
		return this.process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	@Column(name = "BASIS", length = 255)
	public String getBasis() {
		return this.basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	@Column(name = "RESULT", length = 255)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMPLETION_TIME", length = 11)
	public Date getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "RETURN_PEOPLE", length = 255)
	public String getReturnPeople() {
		return this.returnPeople;
	}

	public void setReturnPeople(String returnPeople) {
		this.returnPeople = returnPeople;
	}

	@Column(name = "RETURN_REASON", length = 255)
	public String getReturnReason() {
		return this.returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TaskDetail)) {
			return false;
		}
		TaskDetail taskDetail = (TaskDetail) other;
		return new EqualsBuilder().append(this.getId(), taskDetail.getId())
				.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * 得到任务明细逾期天数方法
	 */
	@Transient
	public double getRemainDays() {
		if (getTask() == null || getTask().getPresetTime() == null) {
			return 0.0d;
		}
		return RemaindaysUtil.getRemainDays(getTask().getPresetTime());
	}
}
