package com.systop.fsmis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;

/**
 * 审核记录表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CHECK_RESULT", schema = "FSMIS")
public class CheckResult extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 审核人
	 */
	private User checker;
	/**
	 * 是否同意
	 */
	private String isAgree;
	/**
	 * 审核意见
	 */
	private String result;
	/**
	 * 审核时间
	 */
	private Date checkTime;
	/**
	 * 应急事件审核对象
	 */
	private UrgentCase urgentCase;

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
  	return id;
  }
	
	public void setId(Integer id) {
  	this.id = id;
  }
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECKER")
	public User getChecker() {
  	return checker;
  }
	
	public void setChecker(User checker) {
  	this.checker = checker;
  }
	
	@Column(name = "IS_AGREE", length = 255)
	public String getIsAgree() {
  	return isAgree;
  }
	
	public void setIsAgree(String isAgree) {
  	this.isAgree = isAgree;
  }
	
	@Column(name = "RESULT", length = 4000)
	public String getResult() {
  	return result;
  }
	
	public void setResult(String result) {
  	this.result = result;
  }
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECK_TIME", length = 11)
	public Date getCheckTime() {
  	return checkTime;
  }
	
	public void setCheckTime(Date checkTime) {
  	this.checkTime = checkTime;
  }
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "URGENT_CASE")
	public UrgentCase getUrgentCase() {
  	return urgentCase;
  }

	public void setUrgentCase(UrgentCase urgentCase) {
  	this.urgentCase = urgentCase;
  }
}
