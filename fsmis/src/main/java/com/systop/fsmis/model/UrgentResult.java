package com.systop.fsmis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.model.BaseModel;

/**
 * 应急事件处理结果表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "URGENT_RESULTS", schema="FSMIS")
public class UrgentResult extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	/** 
	 * 显示内容
	 */
	private String displays;
	/**
	 * 处理时间
	 */
	private Date handleTime;
	/**
	 * 处理内容
	 */
	private String content;
	/**
	 * 应急指挥组
	 */
	private UrgentGroup urgentGroup;
	/**
	 * 应急事件
	 */
	private UrgentCase urgentCase;
	/**
	 * 所属区县
	 */
	private Dept county;

	public UrgentResult(){
	}
	
	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", nullable = false, precision = 10, scale = 0)
	public Integer getId() {
  	return id;
  }

	public void setId(Integer id) {
  	this.id = id;
  }

	@Column(name = "DISPLAYS", length = 255)
	public String getDisplays() {
  	return displays;
  }

	public void setDisplays(String displays) {
  	this.displays = displays;
  }

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HANDLE_TIME", length = 11)
	public Date getHandleTime() {
  	return handleTime;
  }

	public void setHandleTime(Date handleTime) {
  	this.handleTime = handleTime;
  }

	@Column(name = "CONTENT")
  @Lob
	public String getContent() {
  	return content;
  }

	public void setContent(String content) {
  	this.content = content;
  }	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "URGENT_GROUP")
	public UrgentGroup getUrgentGroup() {
  	return urgentGroup;
  }

	public void setUrgentGroup(UrgentGroup urgentGroup) {
  	this.urgentGroup = urgentGroup;
  }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "URGENT_CASE")
	public UrgentCase getUrgentCase() {
  	return urgentCase;
  }

	public void setUrgentCase(UrgentCase urgentCase) {
  	this.urgentCase = urgentCase;
  }
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY")
	public Dept getCounty() {
  	return county;
  }

	public void setCounty(Dept county) {
  	this.county = county;
  }
}
