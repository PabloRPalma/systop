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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 联合任务明细表 The persistent class for the JOINT_TASK_DETAIL database table.
 * @author zw
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JOINT_TASK_DETAIL")
public class JointTaskDetail extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 所属联合任务
	 */
	private JointTask jointTask;
	
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
	 * 处理结果
	 */
	private String result;
	
	/**
	 * 处理依据
	 */
	private String basis;
	
	/**
	 * 是否牵头
	 */
	private Character isLeader;
	
	/**
	 * 任务状态
	 */
	private Character status;
	
	/**
	 * 任务完成时间
	 */
	private Date completionTime;
	
	/**
	 * 所属部门
	 */
	private Long dept;

	/**
	 * 缺省构造方法
	 */
	public JointTaskDetail() {
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
	@JoinColumn(name = "JOINT_TASK")
	public JointTask getJointTask() {
		return this.jointTask;
	}

	public void setJointTask(JointTask jointTask) {
		this.jointTask = jointTask;
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

	@Column(name = "RESULT", length = 510)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "BASIS", length = 510)
	public String getBasis() {
		return this.basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	@Column(name = "IS_LEADER", length = 1)
	public Character getIsLeader() {
		return this.isLeader;
	}

	public void setIsLeader(Character isLeader) {
		this.isLeader = isLeader;
	}

	@Column(name = "STATUS", length = 1)
	public Character getStatus() {
		return this.status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMPLETION_TIME", length = 11)
	public Date getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}

	@Column(name = "DEPT", precision = 10, scale = 0)
	public Long getDept() {
		return this.dept;
	}

	public void setDept(Long dept) {
		this.dept = dept;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JointTaskDetail)) {
			return false;
		}
		JointTaskDetail jointTaskDetail = (JointTaskDetail) other;
		return new EqualsBuilder().append(this.getId(), jointTaskDetail.getId())
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
}
