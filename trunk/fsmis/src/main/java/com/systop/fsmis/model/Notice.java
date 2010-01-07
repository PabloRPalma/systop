package com.systop.fsmis.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;

/**
 * 通知
 * @author ZW
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NOTICES", schema = "FSMIS")
public class Notice extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 发布人
	 */
	private User publisher;
	
	/**
	 * 发布部门
	 */
	private Dept pubDept;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 附件路径
	 */
	private String att;
	
	/**
	 * 部门接收记录
	 */
	private Set<ReceiveRecord> recRecordses = new HashSet<ReceiveRecord>(0);
	
	/**
	 * 默认构造器
	 */
	public Notice() {
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
	@JoinColumn(name = "PUBLISHER")
	public User getPublisher() {
		return this.publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PUB_DEPT")
	public Dept getPubDept() {
		return this.pubDept;
	}

	public void setPubDept(Dept pubDept) {
		this.pubDept = pubDept;
	}

	@Column(name = "TITLE", length = 240)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Type (type = "text")
	@Column(name = "CONTENT")
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

	@Column(name = "ATT", length = 255)
	public String getAtt() {
		return this.att;
	}

	public void setAtt(String att) {
		this.att = att;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
	public Set<ReceiveRecord> getRecRecordses() {
		return this.recRecordses;
	}

	public void setRecRecordses(Set<ReceiveRecord> recRecordses) {
		this.recRecordses = recRecordses;
	}

}
