package com.systop.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 附件表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "attachments")
public class Attachments extends BaseModel {

	/** 主键 */
	private Integer id;

	/** 乐观锁 */
	private Integer version;

	/** 所属文章 */
	private Articles article;

	/** 该附件存放路径 */
	private String path;

	/** 该附件是否删除 */
	private String isDel;
  
  /**　附件别名 */
	private String name;
  
	/** 构造方法 */
	public Attachments() {
	}

  @Column(name = "Name")
	public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /** 构造方法 */
	public Attachments(Integer id) {
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

	@Version
	@Column(name = "VERSION", precision = 22, scale = 0)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(cascade = { }, fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE")
	public Articles getArticle() {
		return this.article;
	}

	public void setArticle(Articles article) {
		this.article = article;
	}

	@Column(name = "PATH", length = 255)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "IS_DEL", length = 1)
	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Attachments)) {
			return false;
		}
		Attachments castOther = (Attachments) other;
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
