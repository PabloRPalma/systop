package com.systop.fsmis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

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
	private String checker;
	/**
	 * 是否同意
	 */
	private String is_agree;
	/**
	 * 审核意见
	 */
	private String result;
	/**
	 * 审核时间
	 */
	private Date check_time;
	/**
	 * 审核对象ID
	 */
	private String target_id;
	
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
	
	@Column(name = "CHECKER", length = 510)
	public String getChecker() {
  	return checker;
  }
	
	public void setChecker(String checker) {
  	this.checker = checker;
  }
	
	@Column(name = "IS_AGREE", length = 510)
	public String getIs_agree() {
  	return is_agree;
  }
	
	public void setIs_agree(String isAgree) {
  	is_agree = isAgree;
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
	public Date getCheck_time() {
  	return check_time;
  }
	
	public void setCheck_time(Date checkTime) {
  	check_time = checkTime;
  }
	
	@Column(name = "TARGET_ID", length = 510)
	public String getTarget_id() {
  	return target_id;
  }
	
	public void setTarget_id(String targetId) {
  	target_id = targetId;
  }
}
