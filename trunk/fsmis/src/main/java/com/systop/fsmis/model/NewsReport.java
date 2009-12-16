package com.systop.fsmis.model;

// Generated 2009-12-16 9:41:03 by Hibernate Tools 3.2.4.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NewsReport generated by hbm2java
 */
@Entity
@Table(name = "NEWS_REPORT")
public class NewsReport implements java.io.Serializable {

	private long id;
	private Date handleDate;
	private String newsMedium;
	private String newsQuantity;
	private String process;
	private String result;

	public NewsReport() {
	}

	public NewsReport(long id) {
		this.id = id;
	}

	public NewsReport(long id, Date handleDate, String newsMedium,
			String newsQuantity, String process, String result) {
		this.id = id;
		this.handleDate = handleDate;
		this.newsMedium = newsMedium;
		this.newsQuantity = newsQuantity;
		this.process = process;
		this.result = result;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HANDLE_DATE", length = 11)
	public Date getHandleDate() {
		return this.handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	@Column(name = "NEWS_MEDIUM", length = 510)
	public String getNewsMedium() {
		return this.newsMedium;
	}

	public void setNewsMedium(String newsMedium) {
		this.newsMedium = newsMedium;
	}

	@Column(name = "NEWS_QUANTITY", length = 510)
	public String getNewsQuantity() {
		return this.newsQuantity;
	}

	public void setNewsQuantity(String newsQuantity) {
		this.newsQuantity = newsQuantity;
	}

	@Column(name = "PROCESS", length = 510)
	public String getProcess() {
		return this.process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	@Column(name = "RESULT", length = 510)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
