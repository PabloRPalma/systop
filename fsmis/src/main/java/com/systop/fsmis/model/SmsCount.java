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
@Table(name = "SMS_COUNTS")
public class SmsCount extends BaseModel {

	private Integer id;
	/**
	 * 移动发送数量
	 */
	private Long mobileCount;
	/**
	 * 联通及其他发送数量
	 */
	private Long otherCount;
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
	public Long getMobileCount() {
		return this.mobileCount;
	}

	public void setMobileCount(Long mobileCount) {
		this.mobileCount = mobileCount;
	}

	@Column(name = "OTHER_COUNT")
	public Long getOtherCount() {
		return this.otherCount;
	}

	public void setOtherCount(Long otherCount) {
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
