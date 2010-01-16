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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;
import com.systop.core.util.RemaindaysUtil;

/**
 * 联合任务表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JOINT_TASKS", schema = "FSMIS")
public class JointTask extends BaseModel {

	private Integer id;
	private FsCase fsCase;
	private User proposer;

	private String title;
	private String descn;
	private Date createDate;
	private Date presetTime;
	private String status;

	private String opinion;


	private Set<JointTaskDetail> taskDetailses = new HashSet<JointTaskDetail>(0);
	private Set<JointTaskAttach> taskAttachses = new HashSet<JointTaskAttach>(0);
	private Set<SmsSend> smsSendses = new HashSet<SmsSend>(0);
	
	private Set<CheckResult> checkResults = new HashSet<CheckResult>(0);

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
	@JoinColumn(name = "FS_CASE")
	public FsCase getFsCase() {
		return this.fsCase;
	}

	public void setFsCase(FsCase fsCase) {
		this.fsCase = fsCase;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSER")
	public User getProposer() {
		return this.proposer;
	}

	public void setProposer(User proposer) {
		this.proposer = proposer;
	}

	@Column(name = "TITLE", length = 255)
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

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "OPINION", length = 255)
	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jointTask")
	public Set<JointTaskDetail> getTaskDetailses() {
		return this.taskDetailses;
	}

	public void setTaskDetailses(Set<JointTaskDetail> taskDetailses) {
		this.taskDetailses = taskDetailses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jointTask")
	public Set<JointTaskAttach> getTaskAttachses() {
		return this.taskAttachses;
	}

	public void setTaskAttachses(Set<JointTaskAttach> taskAttachses) {
		this.taskAttachses = taskAttachses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jointTask")
	public Set<SmsSend> getSmsSendses() {
		return this.smsSendses;
	}

	public void setSmsSendses(Set<SmsSend> smsSendses) {
		this.smsSendses = smsSendses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jointTask")
	public Set<CheckResult> getCheckResults() {
  	return checkResults;
  }

	public void setCheckResults(Set<CheckResult> checkResults) {
  	this.checkResults = checkResults;
  }
	
	/**
	 * 得到任务逾期天数方法
	 */
	@Transient
	public double getRemainDays() {
		return RemaindaysUtil.getRemainDays(getPresetTime());
	}
}
