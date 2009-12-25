package com.systop.fsmis.model;

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NOTICES", schema="FSMIS")
public class Notice implements java.io.Serializable {

	private Integer id;
	private String title;
	private Clob content;
	private Long publisher;
	private Date createTime;
	private String att;
	private Set<ReceiveRecord> receiveRecords = new HashSet<ReceiveRecord>(0);

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

	@Column(name = "TITLE", length = 240)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT")
	public Clob getContent() {
		return this.content;
	}

	public void setContent(Clob content) {
		this.content = content;
	}

	@Column(name = "PUBLISHER", precision = 10, scale = 0)
	public Long getPublisher() {
		return this.publisher;
	}

	public void setPublisher(Long publisher) {
		this.publisher = publisher;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 11)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "ATT", length = 510)
	public String getAtt() {
		return this.att;
	}

	public void setAtt(String att) {
		this.att = att;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
	public Set<ReceiveRecord> getReceiveRecords() {
		return this.receiveRecords;
	}

	public void setReceiveRecords(Set<ReceiveRecord> receiveRecords) {
		this.receiveRecords = receiveRecords;
	}

}
