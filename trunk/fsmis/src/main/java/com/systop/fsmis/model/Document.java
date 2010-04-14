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
import org.hibernate.annotations.Type;

import com.systop.core.model.BaseModel;

/**
 * 内部文章表
 * @author ZW
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "DOCUMENTS", schema = "FSMIS")
public class Document extends BaseModel {

	/** 
	 * 主键
	 */
	private Integer id;
	
	/** 
	 * 文章类型
	 *  */
	private DocumentType documentType;
	
	/** 
	 * 标题 
	 * */
	private String title;
	
	/** 
	 * 作者 
	 * */
	private String author;
	
	/** 
	 * 内容 
	 * */
	private String content;
	
	/**
	 * 接收时间
	 */
	private Date createTime;
	
	/** 描述
	 *  */
	private String descn;
	
	/**
	 * 默认构造器
	 */
	public Document() {
		
	}
	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_TYPE")
	public DocumentType getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "AUTHOR", length = 200)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Type(type = "text")
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
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "DESCN", length = 510)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

}
