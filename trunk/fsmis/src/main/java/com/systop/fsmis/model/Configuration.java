package com.systop.fsmis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 配置表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CONFIGURATION", schema = "FSMIS")
public class Configuration extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 记录数
	 */
	private Integer records;
	
	/**
	 * 天数
	 */
	private Integer days;
	
	/**
	 * 关键字1
	 */
	private String keyWord1;
	
	/**
	 * 关键字2
	 */
	private String keyWord2;
	
	/**
	 * 关键字3
	 */
	private String keyWord3;
	
	/**
	 * 级别标识（市级、区县）
	 */
	private String level; 
	
	public Configuration() {
		
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
	
	public String getName() {
		return name;
	}

	@Column(name = "NAME", length = 255)
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "RECORDS", length = 11)
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	
	@Column(name = "DAYS", length = 11)
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	
	@Column(name = "KEY_1", length = 50)
	public String getKeyWord1() {
		return keyWord1;
	}
	public void setKeyWord1(String keyWord1) {
		this.keyWord1 = keyWord1;
	}
	
	@Column(name = "KEY_2", length = 50)
	public String getKeyWord2() {
		return keyWord2;
	}
	public void setKeyWord2(String keyWord2) {
		this.keyWord2 = keyWord2;
	}
	
	@Column(name = "KEY_3", length = 50)
	public String getKeyWord3() {
		return keyWord3;
	}
	public void setKeyWord3(String keyWord3) {
		this.keyWord3 = keyWord3;
	}

	@Column(name = "LEVEL", length = 1)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
