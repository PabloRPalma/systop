package com.systop.cms.model;

// Generated 2008-1-29 14:57:21 by Hibernate Tools 3.2.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 链接表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "links")
public class Links extends BaseModel {

	/** 主键 */
	private Integer id;

	/**　链接类别 */
	private LinkCatas linkCatas;

	/**　站点名称 */
	private String siteName;

	/**　站点URL */
	private String siteUrl;

	/** 站点logo */
	private String siteLogo;

	/** 站长信箱 */
	private String sitMail;

	/**　站点介绍 */
	private String siteInfo;

	/**　是否推荐 */
	private String isElite;

	/** 是否通过　*/
	private String isPassed;

	/**　排序id */
	private Integer orderId;

	/** 构造方法 */
	public Links() {
	}

	/**　构造方法 */
	public Links(Integer id) {
		this.id = id;
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

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "LINK_CATA")
	public LinkCatas getLinkCatas() {
		return this.linkCatas;
	}

	public void setLinkCatas(LinkCatas linkCatas) {
		this.linkCatas = linkCatas;
	}

	@Column(name = "SITE_NAME", length = 50)
	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	@Column(name = "SITE_URL", length = 255)
	public String getSiteUrl() {
		return this.siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	@Column(name = "SITE_LOGO", length = 255)
	public String getSiteLogo() {
		return this.siteLogo;
	}

	public void setSiteLogo(String siteLogo) {
		this.siteLogo = siteLogo;
	}

	@Column(name = "SIT_MAIL")
	public String getSitMail() {
		return this.sitMail;
	}

	public void setSitMail(String sitMail) {
		this.sitMail = sitMail;
	}

	@Column(name = "SITE_INFO", length = 500)
	public String getSiteInfo() {
		return this.siteInfo;
	}

	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}

	@Column(name = "IS_ELITE", length = 1)
	public String getIsElite() {
		return this.isElite;
	}

	public void setIsElite(String isElite) {
		this.isElite = isElite;
	}

	@Column(name = "IS_PASSED", length = 1)
	public String getIsPassed() {
		return this.isPassed;
	}

	public void setIsPassed(String isPassed) {
		this.isPassed = isPassed;
	}

	@Column(name = "ORDER_ID", precision = 22, scale = 0)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BaseModel)) {
			return false;
		}
		Links castOther = (Links) other;
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
