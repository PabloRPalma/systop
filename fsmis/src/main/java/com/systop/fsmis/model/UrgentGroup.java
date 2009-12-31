package com.systop.fsmis.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.model.BaseModel;

/**
 * 应急事件指挥组
 * 
 * @author yj
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "URGENT_GROUPS", schema = "FSMIS")
public class UrgentGroup extends BaseModel {
	/** 主键 */
	private Integer id;
	/** 组名 */
	private String name;
	/** 描述 */
	private String descr;
	/** 显示内容 */
	private String displays;
	/** 类别 内部组还是外部组 */
	private String type;
	/** 本组对应模板 */
	private String template;
	/** 是否公用数据 Y/N */
	private String isPublic;
	/**派遣类别 */
	private UrgentType urgentType;
	/** 对应的结果对象名称 */
	private String category;
	/**事件*/
	private Set<UrgentResult> urgentResults = new HashSet<UrgentResult>(0);
	/**
	 * 所属区县
	 */
	private Dept county;
	/**
	 * 是否原始数据  0:否，1:是
	 */
	private String isOriginal;

	public UrgentGroup() {
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

	@Column(name = "TYPE")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "TEMPLATE")
	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Column(name = "IS_PUBLIC")
	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "UCTYPE_ID")
	public UrgentType getUrgentType() {
		return urgentType;
	}

	public void setUrgentType(UrgentType urgentType) {
		this.urgentType = urgentType;
	}
	
	@Column(name = "DISPLAYS", length = 500)
	public String getDisplays() {
		return displays;
	}

	public void setDisplays(String displays) {
		this.displays = displays;
	}
	@Column(name = "DESCR", length = 500)
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY")
	public Dept getCounty() {
		return county;
	}

	public void setCounty(Dept county) {
		this.county = county;
	}
	@Column(name = "CATEGORY", length = 500)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	@Column(name = "IS_ORIGINAL", length = 255)
	public String getIsOriginal() {
  	return isOriginal;
  }

	public void setIsOriginal(String isOriginal) {
  	this.isOriginal = isOriginal;
  }
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "urgentGroup")
	public Set<UrgentResult> getUrgentResults() {
  	return urgentResults;
  }

	public void setUrgentResults(Set<UrgentResult> urgentResults) {
  	this.urgentResults = urgentResults;
  }
}
