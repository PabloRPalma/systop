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

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 
 * 发送短信实体
 * 
 * @author Workshopers
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SMS_SENDS")
public class SmsSend extends BaseModel {

	private Integer id;
	/**
	 * 一般案件
	 */
	private GenericCase genericCase;
	/**
	 * 综合案件
	 */
	private CompositiveCase compositiveCase;
	/**
	 * 接收手机号码
	 */
	private String mobileNum;
	/**
	 * 系统创建时间
	 */
	private Date createTime;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 短信内容
	 */
	private String content;
	/**
	 * 是否最新
	 */
	private String isNew;
	/**
	 * 短信是否被接收
	 */
	private String isReceive;
	/**
	 * 发送姓名
	 */
	private String name;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 移动代理服务器Id
	 */
	private Long masid;

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

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPOSITIVE_CASE")
	public CompositiveCase getCompositiveCase() {
		return this.compositiveCase;
	}

	public void setCompositiveCase(CompositiveCase compositiveCase) {
		this.compositiveCase = compositiveCase;
	}

	@Column(name = "MOBILE_NUM", length = 40)
	public String getMobileNum() {
		return this.mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "SEND_TIME")
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "CONTENT", length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "IS_NEW", columnDefinition = "char(1) default '1'")
	public String getIsNew() {
		return this.isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	@Column(name = "IS_RECEIVE", columnDefinition = "char(1) default '0'")
	public String getIsReceive() {
		return this.isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "MASID")
	public Long getMasid() {
		return this.masid;
	}

	public void setMasid(Long masid) {
		this.masid = masid;
	}

}
