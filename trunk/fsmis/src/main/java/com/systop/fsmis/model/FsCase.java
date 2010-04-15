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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.model.BaseModel;

/**
 * 食品投诉案件
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FS_CASES", schema = "FSMIS")
public class FsCase extends BaseModel {

  private Integer id;

  /** 关联监管员 */
  private Supervisor supervisor;

  /** 事件派遣快捷方式 */
  private SendType sendType;

  /** 对应上报市级后的案件 */
  private FsCase submitedCase;

  /** 部门上报 */
  private Dept reportDept;

  /** 关联部门信息 */
  private Dept county;

  /** 事件类别 */
  private CaseType caseType;

  /** 关联企业 */
  private Corp corp;

  /** 事件标题 */
  private String title;

  /** 案发时间 */
  private Date caseTime;

  /** 事件地址 */
  private String address;

  /** 事件编号 */
  private String code;

  /** 事件描述 */
  private String descn;

  /** 事件地理坐标 */
  private String coordinate;

  /** 事件报告人 */
  private String informer;

  /** 事件报告人电话 */
  private String informerPhone;

  /** 结案时间 */
  private Date closedTime;

  /** 事件状态 */
  private String status;

  /** 是否查看（综合案件使用） */
  private String isRead;

  /** 是否综合案件 */
  private String isMultiple;

  /** 综合开始时间 */
  private Date beginDate;

  /** 综合结束时间 */
  private Date endDate;

  /** 是否上报市级 */
  private String isSubmited;

  /** 上报市级时间 */
  private Date submitTime;

  /** 关联汇总配置条件 */
  private GatherConfiger gatherConfiger;

  /** 关联任务信息 */
  private Set<Task> taskses = new HashSet<Task>(0);

  /** 关联联合整治任务信息 */
  private Set<JointTask> jointTaskses = new HashSet<JointTask>(0);

  /** 关联评估信息 */
  private Set<Assessment> assessmentses = new HashSet<Assessment>(0);

  /** 关联上报市级信息 */
  private Set<FsCase> casesBySubmitedCase = new HashSet<FsCase>(0);

  /** 若为一般案件，代表所属的综合案件集合 */
  private Set<FsCase> compositiveCases = new HashSet<FsCase>(0);

  /** 若为综合案件，代表包含一般案件的集合 */
  private Set<FsCase> genericCases = new HashSet<FsCase>(0);

  /** 关联事件对应的短信发送信息 */
  private Set<SmsSend> smsSendses = new HashSet<SmsSend>(0);

  /** 关联事件对应的短信接收信息 */
  private Set<SmsReceive> smsReceiveses = new HashSet<SmsReceive>(0);

  /** 短信核实判断标识: 0代表未核实【默认值】，1代表已核实 */
  private String msgCheckedFlag = "0";

  /** 反馈确认信息 **/
  private String checkedConfirmMsg;
  /** 处理类型 **/
  private String processType;
  /** 事件来源类型(直接添加/上报) */
  private String caseSourceType;

  /**
   * 默认构造
   */
  public FsCase() {
  }

  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", nullable = false)
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
  @JoinColumn(name = "SEND_TYPE")
  public SendType getSendType() {
    return this.sendType;
  }

  public void setSendType(SendType sendType) {
    this.sendType = sendType;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SUBMITED_CASE")
  public FsCase getSubmitedCase() {
    return this.submitedCase;
  }

  public void setSubmitedCase(FsCase submitedCase) {
    this.submitedCase = submitedCase;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REPORT_DEPT")
  public Dept getReportDept() {
    return this.reportDept;
  }

  public void setReportDept(Dept reportDept) {
    this.reportDept = reportDept;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "COUNTY")
  public Dept getCounty() {
    return this.county;
  }

  public void setCounty(Dept county) {
    this.county = county;
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
  @JoinColumn(name = "CORP")
  public Corp getCorp() {
    return this.corp;
  }

  public void setCorp(Corp corp) {
    this.corp = corp;
  }

  @Column(name = "TITLE", length = 255)
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

  @Column(name = "ADDRESS", length = 255)
  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "CODE", length = 255)
  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Type(type = "text")
  @Column(name = "DESCN")
  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  @Column(name = "COORDINATE", length = 255)
  public String getCoordinate() {
    return this.coordinate;
  }

  public void setCoordinate(String coordinate) {
    this.coordinate = coordinate;
  }

  @Column(name = "INFORMER", length = 255)
  public String getInformer() {
    return this.informer;
  }

  public void setInformer(String informer) {
    this.informer = informer;
  }

  @Column(name = "INFORMER_PHONE", length = 255)
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

  @Column(name = "STATUS", length = 1)
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Column(name = "IS_READ", length = 1)
  public String getIsRead() {
    return this.isRead;
  }

  public void setIsRead(String isRead) {
    this.isRead = isRead;
  }

  @Column(name = "IS_MULTIPLE", length = 1)
  public String getIsMultiple() {
    return this.isMultiple;
  }

  public void setIsMultiple(String isMultiple) {
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

  @Column(name = "IS_SUBMITED", length = 1)
  public String getIsSubmited() {
    return this.isSubmited;
  }

  public void setIsSubmited(String isSubmited) {
    this.isSubmited = isSubmited;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "SUBMI_TTIME", length = 11)
  public Date getSubmitTime() {
    return this.submitTime;
  }

  public void setSubmitTime(Date submitTime) {
    this.submitTime = submitTime;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GATHER_CONF")
  public GatherConfiger getGatherConfiger() {
    return gatherConfiger;
  }

  public void setGatherConfiger(GatherConfiger gatherConfiger) {
    this.gatherConfiger = gatherConfiger;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
  public Set<SmsSend> getSmsSendses() {
    return this.smsSendses;
  }

  public void setSmsSendses(Set<SmsSend> smsSendses) {
    this.smsSendses = smsSendses;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
  public Set<Assessment> getAssessmentses() {
    return this.assessmentses;
  }

  public void setAssessmentses(Set<Assessment> assessmentses) {
    this.assessmentses = assessmentses;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
  @OrderBy("id")
  public Set<Task> getTaskses() {
    return this.taskses;
  }

  public void setTaskses(Set<Task> taskses) {
    this.taskses = taskses;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "submitedCase")
  public Set<FsCase> getCasesBySubmitedCase() {
    return this.casesBySubmitedCase;
  }

  public void setCasesBySubmitedCase(Set<FsCase> casesBySubmitedCase) {
    this.casesBySubmitedCase = casesBySubmitedCase;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "GENERIC_COMPOSITIVE", joinColumns = { @JoinColumn(name = "COMPOSITIVE_CASE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "GENERIC_CASE_ID", nullable = false, updatable = false) })
  public Set<FsCase> getGenericCases() {
    return this.genericCases;
  }

  public void setGenericCases(Set<FsCase> genericCases) {
    this.genericCases = genericCases;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "GENERIC_COMPOSITIVE", joinColumns = { @JoinColumn(name = "GENERIC_CASE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "COMPOSITIVE_CASE_ID", nullable = false, updatable = false) })
  public Set<FsCase> getCompositiveCases() {
    return this.compositiveCases;
  }

  public void setCompositiveCases(Set<FsCase> compositiveCases) {
    this.compositiveCases = compositiveCases;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
  public Set<JointTask> getJointTaskses() {
    return this.jointTaskses;
  }

  public void setJointTaskses(Set<JointTask> jointTaskses) {
    this.jointTaskses = jointTaskses;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCase")
  public Set<SmsReceive> getSmsReceiveses() {
    return this.smsReceiveses;
  }

  public void setSmsReceiveses(Set<SmsReceive> smsReceiveses) {
    this.smsReceiveses = smsReceiveses;
  }

  @Column(name = "MSG_CHECKED_FLAG", length = 1)
  public String getMsgCheckedFlag() {
    return msgCheckedFlag;
  }

  public void setMsgCheckedFlag(String msgCheckedFlag) {
    this.msgCheckedFlag = msgCheckedFlag;
  }

  @Column(name = "CHECKED_CONFIRM_MSG")
  @Type(type = "text")
  public String getCheckedConfirmMsg() {
    return checkedConfirmMsg;
  }

  public void setCheckedConfirmMsg(String checkedConfirmMsg) {
    this.checkedConfirmMsg = checkedConfirmMsg;
  }

  @Column(name = "PROCESS_TYPE", length = 255)
  public String getProcessType() {
    return processType;
  }

  public void setProcessType(String processType) {
    this.processType = processType;
  }

  @Column(name = "CASE_SOURCE_TYPE", length = 255)
  public String getCaseSourceType() {
    return caseSourceType;
  }

  public void setCaseSourceType(String caseSourceType) {
    this.caseSourceType = caseSourceType;
  }
}
