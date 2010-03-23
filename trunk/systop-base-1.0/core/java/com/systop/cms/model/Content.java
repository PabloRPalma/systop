package com.systop.cms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.model.BaseEditableObject;
import com.systop.common.security.user.model.User;


/**
 * @hibernate.class table="CONTENTS" schema="PUBLIC" lazy="true"
 */
@Entity
@Table(name = "CONTENTS", schema = "PUBLIC")
public class Content extends BaseEditableObject implements Serializable {
	/** default serial version id, required for serializable classes */
	private static final long serialVersionUID = 1L;

	/** 内容Id */
	private Integer id;

	/** 内容主体 */
	private String body;

	/** 所属类型 1为目录，0为普通内容 */
	private String type;

	/** 描述 */
	private String descn;

	/** 创建时间　*/
	private Date createTime;

	/** 期限(过期时间) */
	private Date expireDate;

	/** 是否审核 */
	private String audited;

	/** 是否有效(有效性) */
	private String available;

	/** 草稿标识 */
	private String isDraft;

	/** 是否显示 */
	private String visible;

	/** 显示序号 */
	private Integer serialNo;

	/** 子标题 */
	private String subtitle;

	/** 摘要 */
	private String summary;

	/** 内容标题 */
	private String title;

	/** 修改时间 */
	private Date updateTime;

	/** 版本号（乐观锁标识） */
	private Integer version;

	/** nullable persistent field */
	private Content originalContent;

	/** 父目录 */
	private Content parent;

	/** 作者 */
	private User author;

	/** 修改人 */
	private User updater;

	/** 审合人 */
	private User auditor;

	/** 附件 */
	private Set<Attachment> attachments = new HashSet<Attachment>(0);

	/** 快捷方式 */
	private Set<Content> shortcuts = new HashSet<Content>(0);

	/** 子内容（指属于该目录下的所有内容和目录） */
	private Set<Content> children = new HashSet<Content>(0);
	
	/** 默认构造 */
	public Content() {
	}
	
	/**
	 * @hibernate.id generator-class="hilo" type="java.lang.Integer" column="ID"
	 */
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
	
	/**
	 * @hibernate.property column="BODY" length="2147483647" lazy="true"
	 */
	@Column(name = "BODY")
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}


	/**
	 * @hibernate.set inverse="true"
	 * @hibernate.key column="PARENT_ID"
	 * @hibernate.one-to-many class="com.systop.cms.model.Content"
	 * @see TreeItem#getChildren()
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
						 mappedBy = "children")
	public Set<Content> getChildren() {
		return children;
	}

	public void setChildren(Set<Content> children) {
		this.children = children;
	}

	/**
	 * @hibernate.many-to-one
	 * @hibernate.column name="PARENT_ID"
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	public Content getParent() {
		return parent;
	}

	public void setParent(Content parent) {
		this.parent = parent;
	}

	/**
	 * @hibernate.property column="TYPE" length="255"
	 */
	@Column(name = "TYPE")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @hibernate.property column="CREATE_TIME"
	 */
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @hibernate.property column="EXPIRE_DATE"
	 */
	@Column(name = "EXPIRE_DATE")
	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @hibernate.property column="AUDITED" length="1"
	 */
	@Column(name = "AUDITED")
	public String getAudited() {
		return this.audited;
	}

	public void setAudited(String audited) {
		this.audited = audited;
	}

	/**
	 * @hibernate.property column="AVAILABLE" length="1"
	 */
	@Column(name = "AVAILABLE")
	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	/**
	 * @hibernate.property column="IS_DRAFT" length="1"
	 */
	@Column(name = "IS_DRAFT")
	public String getIsDraft() {
		return this.isDraft;
	}

	public void setIsDraft(String isDraft) {
		this.isDraft = isDraft;
	}

	/**
	 * @hibernate.property column="VISIBLE" length="1"
	 */
	@Column(name = "VISIBLE")
	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 * @hibernate.property column="SERIAL_NO"
	 */
	@Column(name = "SERIAL_NO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @hibernate.property column="SUBTITLE" length="255"
	 */
	@Column(name = "SUBTITLE")
	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * @hibernate.property column="DESCN" length="255"
	 */
	@Column(name = "DESCN")
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}
	/**
	 * @hibernate.property column="SUMMARY" length="1000"
	 */
	@Column(name = "SUMMARY")
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @hibernate.property column="TITLE" length="255"
	 */
	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @hibernate.property column="UPDATE_TIME"
	 */
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @hibernate.version column="VERSION"
	 */
	@Column(name = "VERSION")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @hibernate.many-to-one
	 * @hibernate.column name="SHORTCUT_ID"
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHORTCUT_ID")
	public Content getOriginalContent() {
		return this.originalContent;
	}

	public void setOriginalContent(Content originalContent) {
		this.originalContent = originalContent;
	}

	/**
	 * @hibernate.many-to-one
	 * @hibernate.column name="AUTHOR"
	 */
	 @ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "AUTHOR")
	public User getAuthor() {
		return this.author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * @hibernate.many-to-one
	 * @hibernate.column name="UPDATER"
	 */
	 @ManyToOne(fetch = FetchType.LAZY)
	 	@JoinColumn(name = "UPDATER")
	public User getUpdater() {
		return this.updater;
	}

	public void setUpdater(User updater) {
		this.updater = updater;
	}

	/**
	 * @hibernate.many-to-one
	 * @hibernate.column name="AUDITOR"
	 */
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "AUDITOR")
	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	/**
	 * @hibernate.set inverse="true" cascade="all"
	 * @hibernate.key column="CONTENT_ID"
	 * @hibernate.one-to-many class="com.systop.cms.model.Attachment"
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
							mappedBy = "content")
	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @hibernate.set inverse="true"
	 * @hibernate.key column="SHORTCUT_ID"
	 * @hibernate.one-to-many class="com.systop.cms.model.Content"
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
			mappedBy = "originalContent")
	public Set<Content> getShortcuts() {
		return this.shortcuts;
	}

	public void setShortcuts(Set<Content> shortcuts) {
		this.shortcuts = shortcuts;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).append("title",
				getTitle()).toString();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if (!(other instanceof Content)) {
			return false;
		}
		Content castOther = (Content) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

}
