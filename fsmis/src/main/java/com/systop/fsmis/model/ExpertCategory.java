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

import com.systop.core.model.BaseModel;

/**
 * 专家类别表 The persistent class for the EXPERT_CATEGORY database table.
 * @author DU
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXPERT_CATEGORY")
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
	private String descb;
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
  @Column(name = "ID", unique = true, nullable = false)
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

	@Column(name = "DESCB", length = 500)
	public String getDescb() {
		return this.descb;
	}

	public void setDescb(String descb) {
		this.descb = descb;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "expertCategory")
	public Set<Expert> getExperts() {
		return this.experts;
	}

	public void setExperts(Set<Expert> experts) {
		this.experts = experts;
	}
}
