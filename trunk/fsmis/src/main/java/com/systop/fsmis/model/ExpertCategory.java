package com.systop.fsmis.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.systop.core.model.BaseModel;

/**
 * 专家类别表
 */
@Entity
@Table(name = "EXPERT_CATEGORYS", schema = "FSMIS")
@SuppressWarnings("serial")
public class ExpertCategory extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String descn;
	/**
	 * 专家
	 */
	private Set<Expert> experts = new HashSet<Expert>(0);

	/**
	 * 缺省构造器
	 */
	public ExpertCategory() {
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

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCN")
	@Type(type = "text")
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "expertCategory")
	public Set<Expert> getExperts() {
		return this.experts;
	}

	public void setExperts(Set<Expert> experts) {
		this.experts = experts;
	}
}
