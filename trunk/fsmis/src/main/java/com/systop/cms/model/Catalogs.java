package com.systop.cms.model;

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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.cms.CmsConstants;
import com.systop.core.model.BaseModel;

/**
 * 文章类别表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "catalogs")
public class Catalogs extends BaseModel {

	/** 主键 */
	private Integer id;

	/** 栏目名称 */
	private String name;

	/** 栏目存放目录 */
	private String root;

	/** 栏目目录地址 * */
	private String rootPath;

	/** 栏目描述 */
	private String descn;

	/** 栏目类别 */
	private String type;

	/** 外部地址 */
	private String linkUrl;
	
	/** 打开栏目时的目标:_blank;_parent */
	private String target = "_self";

	/** 上级栏目 */
	private Catalogs parentCatalog;

	/** 所属组 */
	private Integer groupId;

	/** 排序ID */
	private Integer orderId;

	/** 栏目图片 */
	private String pic;

	/** 栏目模板 */
	private Templates cataTemplate;

	/** 文章模板 */
	private Templates artTemplate;

	/** 顶部导航显示 */
	private String showOnTop;

	/** 是否主页显示 */
	private String showOnIndex;

	/** 是否在父栏目列表显示 */
	private String showOnParlist;

	/** 栏目是否可用 */
	private String isEnable;

	/** 乐观锁 */
	private Integer version;

	/** 所有子栏目 */
	private Set<Catalogs> subCatalogs = new HashSet<Catalogs>(0);

	/** 栏目下所有文章 */
	private Set<Articles> articles = new HashSet<Articles>(0);

	/** 构造方法 */
	public Catalogs() {
	}

	/** 构造方法 */
	public Catalogs(Integer id) {
		this.id = id;
	}
	
	/** 返回栏目访问主页 */
	@Transient
	public String getCatalogURL() {
		if (StringUtils.isBlank(getRootPath())) {
			return getRoot() + CmsConstants.INDEX 
						+ "." + CmsConstants.POSTFIX;
		} else {
			return "";
		}
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

	@Version
	@Column(name = "VERSION", precision = 22, scale = 0)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT")
	public Catalogs getParentCatalog() {
		return this.parentCatalog;
	}

	public void setParentCatalog(Catalogs parentCatalog) {
		this.parentCatalog = parentCatalog;
	}

	@Column(name = "GROUPID")
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@ManyToOne(cascade = { }, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEMPLATE")
	public Templates getCataTemplate() {
		return this.cataTemplate;
	}

	public void setCataTemplate(Templates cataTemplate) {
		this.cataTemplate = cataTemplate;
	}

	@ManyToOne(cascade = { }, fetch = FetchType.LAZY)
	@JoinColumn(name = "ART_TEMPLATE")
	public Templates getArtTemplate() {
		return artTemplate;
	}

	public void setArtTemplate(Templates artTemplate) {
		this.artTemplate = artTemplate;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ROOT", length = 25)
	public String getRoot() {
		return this.root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@Column(name = "ROOTPATH", length = 220)
	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	@Column(name = "DESCN", length = 255)
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "TYPE", length = 2)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "LINK_URL", length = 255)
	public String getLinkUrl() {
		return this.linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	@Column(name = "target", length = 255)
  public String getTarget() {
	  if (StringUtils.isBlank(target)){
	    target = "_self";
	  }
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  @Column(name = "ORDER_ID", precision = 22, scale = 0)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "PIC", length = 255)
	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Column(name = "SHOW_ON_TOP", length = 2)
	public String getShowOnTop() {
		return this.showOnTop;
	}

	public void setShowOnTop(String showOnTop) {
		this.showOnTop = showOnTop;
	}

	@Column(name = "SHOW_ON_INDEX", length = 2)
	public String getShowOnIndex() {
		return this.showOnIndex;
	}

	public void setShowOnIndex(String showOnIndex) {
		this.showOnIndex = showOnIndex;
	}

	@Column(name = "SHOW_ON_PARLIST", length = 2)
	public String getShowOnParlist() {
		return this.showOnParlist;
	}

	public void setShowOnParlist(String showOnParlist) {
		this.showOnParlist = showOnParlist;
	}

	@Column(name = "IS_ENABLE", length = 2)
	public String getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@OneToMany(cascade = { }, fetch = FetchType.LAZY, 
							mappedBy = "parentCatalog")
	public Set<Catalogs> getSubCatalogs() {
		return this.subCatalogs;
	}

	public void setSubCatalogs(Set<Catalogs> subCatalogs) {
		this.subCatalogs = subCatalogs;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "catalog")
	public Set<Articles> getArticles() {
		return this.articles;
	}

	public void setArticles(Set<Articles> articles) {
		this.articles = articles;
	}


	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Catalogs)) {
			return false;
		}
		Catalogs castOther = (Catalogs) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
