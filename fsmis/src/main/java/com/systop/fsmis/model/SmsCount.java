package com.systop.fsmis.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 短信发送数量实体
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "SMS_COUNTS", schema="FSMIS")
public class SmsCount extends BaseModel {

	private Integer id;
	/**
	 * 移动发送数量
	 */
	private Integer mobileSendCount;
	/**
	 * 移动接收数量
	 */
	private Integer mobileReceiveCount;
	/**
	 * 联通发送数量
	 */
	private Integer unicomSendCount;
	/**
	 * 联通接收数量
	 */
	private Integer unicomReceiveCount;
	/**
	 * 其他发送数量
	 */
	private Integer otherSendCount;
	/**
	 * 其他接收数量
	 */
	private Integer otherReceiveCount;
	/**
	 * 收发日期
	 */
	private Date smsDate;

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

	@Column(name = "MOBILE_SEND_COUNT")
	public Integer getMobileSendCount() {
		return mobileSendCount;
	}

	public void setMobileSendCount(Integer mobileSendCount) {
		this.mobileSendCount = mobileSendCount;
	}

	@Column(name = "MOBILE_RECEIVE_COUNT")
	public Integer getMobileReceiveCount() {
		return mobileReceiveCount;
	}

	public void setMobileReceiveCount(Integer mobileReceiveCount) {
		this.mobileReceiveCount = mobileReceiveCount;
	}

	@Column(name = "OTHER_SEND_COUNT")
	public Integer getOtherSendCount() {
		return otherSendCount;
	}

	public void setOtherSendCount(Integer otherSendCount) {
		this.otherSendCount = otherSendCount;
	}
	
	@Column(name = "OTHER_RECEIVE_COUNT")
	public Integer getOtherReceiveCount() {
		return otherReceiveCount;
	}

	public void setOtherReceiveCount(Integer otherReceiveCount) {
		this.otherReceiveCount = otherReceiveCount;
	}

	@Column(name = "SMS_DATE")
	public Date getSmsDate() {
		return smsDate;
	}

	public void setSmsDate(Date smsDate) {
		this.smsDate = smsDate;
	}

	@Column(name = "UNICOM_SEND_COUNT")
	public Integer getUnicomSendCount() {
		return unicomSendCount;
	}

	public void setUnicomSendCount(Integer unicomSendCount) {
		this.unicomSendCount = unicomSendCount;
	}

	@Column(name = "UNICOM_RECEIVE_COUNT")
	public Integer getUnicomReceiveCount() {
		return unicomReceiveCount;
	}

	public void setUnicomReceiveCount(Integer unicomReceiveCount) {
		this.unicomReceiveCount = unicomReceiveCount;
	}
}
