package com.systop.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.cms.advisory.AdvisoryConstants;
import com.systop.core.model.BaseModel;

/**
 * 咨询反馈
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "advisorys")
public class Advisorys extends BaseModel {

	/** 主键 */
	private Integer id;

	/** 标题 */
	private String title;

	/** 内容 */
	private String content;

	/** 创建日期 */
	private Date creatDate;

	/** 咨询人 */
	private String name;

	/** 咨询人电话 */
	private String phone;

	/** 咨询人邮编 */
	private String posCode;

	/** 咨询人地址 */
	private String address;

	/** 回复内容 */
	private String reContent;

	/** 回复日期 */
	private Date reDate;

	/** 状态 */
	private String status = AdvisoryConstants.UNANSWER;

	/** 构造方法 */
	public Advisorys() {
	}

	/** 构造方法 */
	public Advisorys(Integer id) {
		this.id = id;
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

	@Column(name = "TITLE", length = 255)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", length = 2000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CREAT_DATE")
	public Date getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "POS_CODE", length = 20)
	public String getPosCode() {
		return this.posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	@Column(name = "ADDRESS", length = 255)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "RE_CONTENT", length = 2000)
	public String getReContent() {
		return this.reContent;
	}

	public void setReContent(String reContent) {
		this.reContent = reContent;
	}

	@Column(name = "RE_DATE")
	public Date getReDate() {
		return this.reDate;
	}

	public void setReDate(Date reDate) {
		this.reDate = reDate;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Advisorys)) {
			return false;
		}
		Advisorys castOther = (Advisorys) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
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
