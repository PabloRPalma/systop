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

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 接收短信实体
 * 
 * @author Workshopers
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "SMS_RECEIVES")
public class SmsReceive extends BaseModel {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 关联案件
	 */
	private GenericCase genericCase;
	/**
	 * 短信内容
	 */
	private String content;
	/**
	 * 接收的号码
	 */
	private String mobileNum;
	/**
	 * 是否最新
	 */
	private String isNew;
	/**
	 * 系统接收时间
	 */
	private Date receiveTime;
	/**
	 * 用户(短信)发送时间
	 */
	private Date sendTime;
	/**
	 * 是否已分析
	 */
	private String isParsed;
	/**
	 * 是否举报短信
	 */
	private String isReport;
	/**
	 * 是否处理
	 */
	private String isTreated;
	/**
	 * 是否核实短信
	 */
	private String isVerify;
	/**
	 * 备注
	 */
	private String remark;

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

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "GENERIC_CASE")
	public GenericCase getGenericCase() {
		return this.genericCase;
	}

	public void setGenericCase(GenericCase genericCase) {
		this.genericCase = genericCase;
	}

	@Column(name = "CONTENT", length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "MOBILE_NUM", length = 40)
	public String getMobileNum() {
		return this.mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	@Column(name = "IS_NEW", columnDefinition = "char(1) default '1'")
	public String getIsNew() {
		return this.isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
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
	@Column(name = "SEND_TIME", length = 11)
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "IS_PARSED", columnDefinition = "char(1) default '0'")
	public String getIsParsed() {
		return this.isParsed;
	}

	public void setIsParsed(String isParsed) {
		this.isParsed = isParsed;
	}

	@Column(name = "IS_REPORT", columnDefinition = "char(1) default '0'")
	public String getIsReport() {
		return this.isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	@Column(name = "IS_TREATED", columnDefinition = "char(1) default '0'")
	public String getIsTreated() {
		return this.isTreated;
	}

	public void setIsTreated(String isTreated) {
		this.isTreated = isTreated;
	}

	@Column(name = "IS_VERIFY", columnDefinition = "char(1) default '0'")
	public String getIsVerify() {
		return this.isVerify;
	}

	public void setIsVerify(String isVerify) {
		this.isVerify = isVerify;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
