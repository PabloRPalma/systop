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
	private Integer mobileCount;
	/**
	 * 联通及其他发送数量
	 */
	private Integer otherCount;
	/**
	 * 发送日期
	 */
	private Date sendDate;

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

	@Column(name = "MOBILE_COUNT")
	public Integer getMobileCount() {
		return this.mobileCount;
	}

	public void setMobileCount(Integer mobileCount) {
		this.mobileCount = mobileCount;
	}

	@Column(name = "OTHER_COUNT")
	public Integer getOtherCount() {
		return this.otherCount;
	}

	public void setOtherCount(Integer otherCount) {
		this.otherCount = otherCount;
	}

	@Column(name = "SEND_DATE")
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

}
