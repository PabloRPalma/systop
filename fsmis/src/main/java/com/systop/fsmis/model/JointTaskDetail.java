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
 * 联合任务明细表 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JOINT_TASK_DETAILS", schema="FSMIS")
public class JointTaskDetail extends BaseModel {

	private Integer id;
	/**
	 * 录入人
	 */
	private User inputer;
	
	/**
	 * 执行部门
	 */
	private Dept dept;
	
	/**
	 * 处理人
	 */
	private String processor;
	private String process;
	private String result;
	private String basis;
	private String isLeader;
	private String status;
	private Date receiveTime;
	private Date completionTime;

	private JointTask jointTask;
	
	private Set<JointTaskDetailAttach> taskDetailAttachses = new HashSet<JointTaskDetailAttach>(0);

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
	@JoinColumn(name = "INPUTER")
	public User getInputer() {
		return this.inputer;
	}

	public void setInputer(User inputer) {
		this.inputer = inputer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPT")
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOINT_TASK")
	public JointTask getJointTask() {
		return this.jointTask;
	}

	public void setJointTask(JointTask jointTask) {
		this.jointTask = jointTask;
	}

	@Column(name = "PROCESSOR", length = 255)
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

	@Column(name = "RESULT", length = 255)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "BASIS", length = 255)
	public String getBasis() {
		return this.basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	@Column(name = "IS_LEADER", length = 1)
	public String getIsLeader() {
		return this.isLeader;
	}

	public void setIsLeader(String isLeader) {
		this.isLeader = isLeader;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVE_TIME", length = 11)
	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMPLETION_TIME", length = 11)
	public Date getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jointTaskDetail")
	public Set<JointTaskDetailAttach> getTaskDetailAttachses() {
		return taskDetailAttachses;
	}

	public void setTaskDetailAttachses(
			Set<JointTaskDetailAttach> taskDetailAttachses) {
		this.taskDetailAttachses = taskDetailAttachses;
	}
}
