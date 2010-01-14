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
@Table(name = "GATHER_CONFIGER", schema = "FSMIS")
public class GatherConfiger extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 关键字
	 */
	private String keyWord;
	
	/**
	 * 记录数
	 */
	private Integer records;
	
	/**
	 * 天数
	 */
	private Integer days;
	
	/**
	 * 级别标识（市级、区县）
	 */
	private String level; 
	
	public GatherConfiger() {
		
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
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Column(name = "LEVEL", length = 1)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
