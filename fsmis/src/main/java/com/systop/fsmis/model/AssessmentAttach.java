package com.systop.fsmis.model;

// Generated 2009-12-16 9:41:03 by Hibernate Tools 3.2.4.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AssessmentAttach generated by hbm2java
 */
@Entity
@Table(name = "ASSESSMENT_ATTACH")
public class AssessmentAttach implements java.io.Serializable {

	private long id;
	private Assessment assessment;
	private String title;
	private String creator;
	private String path;

	public AssessmentAttach() {
	}

	public AssessmentAttach(long id) {
		this.id = id;
	}

	public AssessmentAttach(long id, Assessment assessment, String title,
			String creator, String path) {
		this.id = id;
		this.assessment = assessment;
		this.title = title;
		this.creator = creator;
		this.path = path;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSESSMENT")
	public Assessment getAssessment() {
		return this.assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	@Column(name = "TITLE", length = 510)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CREATOR", length = 510)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "PATH", length = 510)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
