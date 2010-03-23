package com.systop.cms.model;

import java.io.Serializable;

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

/**
 * @hibernate.class table="ATTACHMENTS" schema="PUBLIC" lazy="true"
 */
@Entity
@Table(name = "ATTACHMENTS", schema = "PUBLIC")
public class Attachment implements Serializable {
	/** default serial version id, required for serializable classes */
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Integer id;

	/** 路径 */
	private String path;

	/** 是否可以删除 */
	private String isDel;

	/** nullable persistent field */
	private Integer version;

	/** nullable persistent field */
	private Content content;

	/** default constructor */
	public Attachment() {
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
	 * @hibernate.property column="PATH" length="255"
	 */
	@Column(name = "PATH")
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
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
	 * @hibernate.column name="CONTENT_ID"
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENT_ID")
	public Content getContent() {
		return this.content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	/**
	 * @hibernate.property column="is_del" length="1"
	 */
	@Column(name = "IS_DEL", length = 1)
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if (!(other instanceof Attachment)) {
			return false;
		}
		Attachment castOther = (Attachment) other;
		return new EqualsBuilder().append(this.getId(), 
				castOther.getId()).append(
				this.getPath(), castOther.getPath()).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).
								append(getPath()).toHashCode();
	}

}
