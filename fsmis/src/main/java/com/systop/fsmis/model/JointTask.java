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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 联合任务表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JOINT_TASKS", schema="FSMIS")
public class JointTask extends BaseModel {

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
	 * 申请人
	 */
	private String proposer;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 规定完成时间
	 */
	private Date presetTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 任务状态
	 */
	private Character status;

	/**
	 * 审核人
	 */
	private Long auditor;

	/**
	 * 是否审核
	 */
	private Character isAgree;

	/**
	 * 拟办意见
	 */
	private String opinion;

	/**
	 * 审核时间
	 */
	private Date auditDate;

	/**
	 * 联合任务明细
	 */
	private Set<JointTaskDetail> jointTaskDetails = new HashSet<JointTaskDetail>(
			0);

	/**
	 * 联合任务附件
	 */
	private Set<JointTaskAttach> jointTaskAttachs = new HashSet<JointTaskAttach>(
			0);

	/**
	 * 缺省构造方法
	 */
	public JointTask() {
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

	@Column(name = "DESCN", length = 4000)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "PROPOSER", length = 110)
	public String getProposer() {
		return this.proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 11)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRESET_TIME", length = 11)
	public Date getPresetTime() {
		return this.presetTime;
	}

	public void setPresetTime(Date presetTime) {
		this.presetTime = presetTime;
	}

	@Column(name = "REMARK", length = 510)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATUS", length = 1)
	public Character getStatus() {
		return this.status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Column(name = "AUDITOR", precision = 10, scale = 0)
	public Long getAuditor() {
		return this.auditor;
	}

	public void setAuditor(Long auditor) {
		this.auditor = auditor;
	}

	@Column(name = "IS_AGREE", length = 1)
	public Character getIsAgree() {
		return this.isAgree;
	}

	public void setIsAgree(Character isAgree) {
		this.isAgree = isAgree;
	}

	@Column(name = "OPINION", length = 510)
	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDIT_DATE", length = 11)
	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jointTask")
	public Set<JointTaskDetail> getJointTaskDetails() {
		return this.jointTaskDetails;
	}

	public void setJointTaskDetails(Set<JointTaskDetail> jointTaskDetails) {
		this.jointTaskDetails = jointTaskDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jointTask")
	public Set<JointTaskAttach> getJointTaskAttachs() {
		return this.jointTaskAttachs;
	}

	public void setJointTaskAttachs(Set<JointTaskAttach> jointTaskAttachs) {
		this.jointTaskAttachs = jointTaskAttachs;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JointTask)) {
			return false;
		}
		JointTask jointTask = (JointTask) other;
		return new EqualsBuilder().append(this.getId(), jointTask.getId())
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
