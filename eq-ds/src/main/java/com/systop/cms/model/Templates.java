package com.systop.cms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 模板表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "templates")
public class Templates extends BaseModel {

	/** 主键 */
	private Integer id;

	/** 乐观锁 */
	private Integer version;

	/** 模板名称 */
	private String name;

	/** 模板类型 */
	private String type;

	/** 是否默认模板 */
	private String isDef;

	/** 模板内容 */
	private String content;

	/** 是否删除 */
	private String isDel;

	/** 是否公共模板 */
	private String isComm;
	
	/** 模板描述 */
	private String descn;

	/** 使用该模板的文章 */
	private Set<Articles> articles = new HashSet<Articles>(0);

	/** 使用该模板的栏目 */
	private Set<Catalogs> templateForCatalogs = new HashSet<Catalogs>(0);

	/** 使用该模板的文章-栏目表 */
	private Set<Catalogs> templateForArtrticles = new HashSet<Catalogs>(0);

	/** 构造方法 */
	public Templates() {
	}

	/** 构造方法 */
	public Templates(Integer id) {
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

	@Version
	@Column(name = "VERSION", precision = 22, scale = 0)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "IS_DEF", length = 1)
	public String getIsDef() {
		return isDef;
	}

	public void setIsDef(String isDef) {
		this.isDef = isDef;
	}

	//oracle Clob 支持@Type(type = "org.springframework.orm.hibernate3
  //.support.ClobStringType")
	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "IS_DEL", length = 1)
	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Column(name = "IS_COMM", length = 1)
	public String getIsComm() {
		return isComm;
	}

	public void setIsComm(String isComm) {
		this.isComm = isComm;
	}
	
	@Column(name = "DESCN", length = 255)
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	

	@OneToMany(cascade = {}, fetch = FetchType.LAZY,
			mappedBy = "template")
	public Set<Articles> getArticles() {
		return this.articles;
	}

	public void setArticles(Set<Articles> articles) {
		this.articles = articles;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, 
			mappedBy = "cataTemplate")
	public Set<Catalogs> getTemplateForCatalogs() {
		return this.templateForCatalogs;
	}

	public void setTemplateForCatalogs(Set<Catalogs> templateForCatalogs) {
		this.templateForCatalogs = templateForCatalogs;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, 
			mappedBy = "artTemplate")
	public Set<Catalogs> getTemplateForArtrticles() {
		return templateForArtrticles;
	}

	public void setTemplateForArtrticles(Set<Catalogs> templateForArtrticles) {
		this.templateForArtrticles = templateForArtrticles;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Templates)) {
			return false;
		}
		Templates castOther = (Templates) other;
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
