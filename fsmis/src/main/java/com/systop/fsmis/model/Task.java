package com.systop.fsmis.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 任务表 The persistent class for the TASK database table.
 * 
 * @author zw
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TASK")
public class Task extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 一般案件
	 */
	private FsCase fsCase;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 描述
	 */
	private String descn;

	/**
	 * 派遣时间
	 */
	private Date dispatchTime;

	/**
	 * 规定完成时间
	 */
	private Date presetTime;

	/**
	 * 任务状态
	 */
	private String status;

	/**
	 * 任务明细
	 */
	private Set<TaskDetail> taskDetails = new HashSet<TaskDetail>(0);

	/**
	 * 任务附件
	 */
	private Set<TaskAtt> taskAtts = new HashSet<TaskAtt>(0);

	/**
	 * 缺省构造方法
	 */
	public Task() {
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
	public FsCase getFsCase() {
		return this.fsCase;
	}

	public void setFsCase(FsCase fsCase) {
		this.fsCase = fsCase;
	}

	@Column(name = "TITLE", length = 510)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "DESCN", length = 510)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DISPATCH_TIME", length = 11)
	public Date getDispatchTime() {
		return this.dispatchTime;
	}

	public void setDispatchTime(Date dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRESET_TIME", length = 11)
	public Date getPresetTime() {
		return this.presetTime;
	}

	public void setPresetTime(Date presetTime) {
		this.presetTime = presetTime;
	}

	@Column(name = "STATUS", length = 510)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "task")
	public Set<TaskDetail> getTaskDetails() {
		return this.taskDetails;
	}

	public void setTaskDetails(Set<TaskDetail> taskDetails) {
		this.taskDetails = taskDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	public Set<TaskAtt> getTaskAtts() {
		return this.taskAtts;
	}

	public void setTaskAtts(Set<TaskAtt> taskAtts) {
		this.taskAtts = taskAtts;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Task)) {
			return false;
		}
		Task task = (Task) other;
		return new EqualsBuilder().append(this.getId(), task.getId()).isEquals();
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
