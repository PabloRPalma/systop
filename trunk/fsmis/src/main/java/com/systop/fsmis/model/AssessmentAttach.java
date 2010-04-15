package com.systop.fsmis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 评估附件表
 */
@Entity
@Table(name = "ASSESSMENT_ATTACHS", schema = "FSMIS")
@SuppressWarnings("serial")
public class AssessmentAttach extends BaseModel {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 评估
	 */
	private Assessment assessment;
	/**
	 * 资源标题
	 */
	private String title;
	/**
	 * 完成人
	 */
	private String creator;
	/**
	 * 保存路径
	 */
	private String path;

	public AssessmentAttach() {
	}

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	@Column(name = "TITLE",length = 255)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CREATOR", length = 255)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "PATH", length = 255)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
