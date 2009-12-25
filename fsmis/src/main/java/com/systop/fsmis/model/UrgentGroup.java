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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.security.user.model.User;

/**
 */
@Entity
@Table(name = "URGENT_GROUPS", schema="FSMIS")
public class UrgentGroup implements java.io.Serializable {

	private Integer id;
	private UrgentCase urgentCases;
	private String name;
	private String descn;
	private String display;
	private String type;
	private Date handleDate;
	private Clob handleContent;
	private String template;
	private Set<User> user = new HashSet<User>(0);

	public UrgentGroup() {
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
	@JoinColumn(name = "URGENT_CASE")
	public UrgentCase getUrgentCases() {
		return this.urgentCases;
	}

	public void setUrgentCases(UrgentCase urgentCases) {
		this.urgentCases = urgentCases;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCN", length = 500)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "DISPLAY", length = 500)
	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	@Column(name = "TYPE")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HANDLE_DATE", length = 11)
	public Date getHandleDate() {
		return this.handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	@Column(name = "HANDLE_CONTENT")
	public Clob getHandleContent() {
		return this.handleContent;
	}

	public void setHandleContent(Clob handleContent) {
		this.handleContent = handleContent;
	}

	@Column(name = "TEMPLATE")
	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "urgentGroup")
	public Set<User> getUser() {
		return this.user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

}
