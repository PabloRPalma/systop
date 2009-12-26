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

@SuppressWarnings("serial")
@Entity
@Table(name = "MESSAGES", schema="FSMIS")
public class Message extends BaseModel {

	/** 
	 * 主键 
	 */
	private Integer id;
	
	/** 
	 * 收信人 
	 */
	private User receiver;
	
	/** 
	 * 发信人
	 */
	private User sender;
	
	/** 
	 * 内容
	 */
	private String content;
	
	/** 
	 * 创建时间 
	 */
	private Date createTime;
	
	/** 
	 * 接收时间
	 */
	private Date receiveTime;
	
	/** 
	 * 是否最新
	 */
	private String isNew;

	/** 
	 * 默认构造器 
	 */
	public Message() {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECEIVER")
	public User getReceiver() {
		return this.receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SENDER")
	public User getSender() {
		return this.sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	@Column(name = "CONTENT", length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVE_TIME", length = 11)
	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Column(name = "IS_NEW", length = 1)
	public String getIsNew() {
		return this.isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

}
