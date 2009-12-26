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
 * 应急类别
 * 
 * @author yj
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "URGENT_TYPES", schema = "FSMIS")
public class UrgentType extends BaseModel {
	/** 主键 */
	private Integer id;

	/** 名称 */
	private String name;

	/** 备注 */
	private String remark;

	/** 对应的组  ,其实只需要一个组(事故处理组)即可*/
	private Set<UrgentGroup> urgentGroup = new HashSet<UrgentGroup>(0);
	
	public UrgentType() {
	}

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REMARK", length = 255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy="urgentType")
	public Set<UrgentGroup> getUrgentGroup() {
		return urgentGroup;
	}

	public void setUrgentGroup(Set<UrgentGroup> urgentGroup) {
		this.urgentGroup = urgentGroup;
	}

	
}
