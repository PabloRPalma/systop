package com.systop.fsmis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	private String descn;
	/** 显示内容 */
	private String display;
	/** 类别 内部组还是外部组 */
	private String type;
	/** 本组对应模板 */
	private String template;
	/** 是否公用数据 Y/N */
	private String isPublic;
	/** 类别 */
	private UrgentType urgentType;

	private UrgentCase urgentCases;
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

	@Column(name = "DESCN", length = 500)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "DISPLAY", length = 500)
	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "URGENT_CASE")
	public UrgentCase getUrgentCases() {
		return this.urgentCases;
	}

	public void setUrgentCases(UrgentCase urgentCases) {
		this.urgentCases = urgentCases;
	}
}
