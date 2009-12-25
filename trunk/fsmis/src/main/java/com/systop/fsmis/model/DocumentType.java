package com.systop.fsmis.model;

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

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 文档类别
 */
@Entity
@Table(name = "DOCUMENT_TYPES", schema="FSMIS")
public class DocumentType extends BaseModel implements java.io.Serializable {

	/** 主键 */
	private Integer id;
	/** 上级栏目 */
	private DocumentType documentType;
	/** 名称 */
	private String name;
	/** 描述 */
	private String descn;
	/** 包含栏目 */
	private Set<DocumentType> documentTypes = new HashSet<DocumentType>(0);
	/** 包含文章 */
	private Set<Document> documents = new HashSet<Document>(0);

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
	@JoinColumn(name = "PARENT")
	public DocumentType getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCN", length = 510)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType")
	public Set<DocumentType> getDocumentTypes() {
		return this.documentTypes;
	}

	public void setDocumentTypes(Set<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType")
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

}
