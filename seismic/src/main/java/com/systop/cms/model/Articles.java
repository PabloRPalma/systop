package com.systop.cms.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.model.BaseModel;

/**
 * 文章信息表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "articles")
@Indexed(index = "indexes/articles")
public class Articles extends BaseModel {

  /** 主键 */
  private Integer id;

  /** 标题 */
  private String title;

  /** 简短标题 */
  private String shortTitle;

  /** 子标题 */
  private String subtitle;

  /** 摘要 */
  private String summary;

  /** 关键字 */
  private String keyWord;

  /** 内容 */
  private String content;

  /** 创建时间 */
  private Date createTime;

  /** 有效日期 */
  private Date expireDate;

  /** 修改时间 */
  private Date updateTime;

  /** 转向链接 */
  private String linkUrl;

  /** 作者 */
  private String author;

  /** 录入者 */
  private User inputer;

  /** 修改人 */
  private User updater;

  /** 审核人 */
  private User auditor;

  /** 是否可用 */
  private String available;

  /** 是否审核 */
  private String audited;

  /** 是否草稿 */
  private String isDraft;

  /** 是否固顶 */
  private String onTop;

  /** 是否推荐 */
  private String isElite;
  
  /** 首页Flash显示图片路径 */
  private String flashImg;

  /** 点击数 */
  private Integer hits;

  /** 分页方式 */
  private String paginationType;

  /** 分页最大字数 */
  private Integer maxCharPage;

  /** 文章所属栏目 */
  private Catalogs catalog;

  /** 模板 */
  private Templates template;

  /** 备注 */
  private String descn;

  /** 排序ID */
  private Integer serialNo;

  /** 该文章的所有附件 */
  private Set<Attachments> attachmentses = new HashSet<Attachments>(0);

  /** 文章路径 */
  private String path;

  /** 乐观锁 */
  private Integer version;

  @Column(name = "PATH")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  /** 构造方法 */
  public Articles() {
  }

  /** 构造方法 */
  public Articles(Integer id) {
    this.id = id;
  }

  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", unique = true, nullable = false)
  @DocumentId
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
  @JoinColumn(name = "INPUTER")
  public User getInputer() {
    return this.inputer;
  }

  public void setInputer(User inputer) {
    this.inputer = inputer;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "AUDITOR")
  public User getAuditor() {
    return this.auditor;
  }

  public void setAuditor(User auditor) {
    this.auditor = auditor;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "UPDATER")
  public User getUpdater() {
    return this.updater;
  }

  public void setUpdater(User updater) {
    this.updater = updater;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "CATALOG")
  public Catalogs getCatalog() {
    return this.catalog;
  }

  public void setCatalog(Catalogs catalog) {
    this.catalog = catalog;
  }

  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "TEMPLATE")
  public Templates getTemplate() {
    return this.template;
  }

  public void setTemplate(Templates template) {
    this.template = template;
  }

  @Column(name = "TITLE", length = 255)
  @Field(name = "title", index = Index.TOKENIZED, store = Store.YES)
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Column(name = "SHORT_TITLE", length = 255)
  public String getShortTitle() {
    return this.shortTitle;
  }

  public void setShortTitle(String shortTitle) {
    this.shortTitle = shortTitle;
  }

  @Column(name = "SUBTITLE", length = 255)
  public String getSubtitle() {
    return this.subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  @Column(name = "SUMMARY", length = 500)
  public String getSummary() {
    return this.summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  @Column(name = "KEY_WORD", length = 255)
  @Field(name = "key_word", index = Index.TOKENIZED, store = Store.YES)
  public String getKeyWord() {
    return this.keyWord;
  }

  public void setKeyWord(String keyWord) {
    this.keyWord = keyWord;
  }

  // oracle Clob 支持 @Type(type = "org.springframework.orm.hibernate3.support
  // .ClobStringType")
  @Column(name = "CONTENT")
  @Field(name = "content", index = Index.TOKENIZED, store = Store.YES)
  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Column(name = "CREATE_TIME")
  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Column(name = "EXPIRE_DATE")
  public Date getExpireDate() {
    return this.expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  @Column(name = "UPDATE_TIME")
  public Date getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @Column(name = "LINK_URL", length = 255)
  public String getLinkUrl() {
    return this.linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

  @Column(name = "AUTHOR", length = 100)
  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Column(name = "AVAILABLE", length = 1)
  public String getAvailable() {
    return this.available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }

  @Column(name = "AUDITED", length = 1)
  public String getAudited() {
    return this.audited;
  }

  public void setAudited(String audited) {
    this.audited = audited;
  }

  @Column(name = "IS_DRAFT", length = 1)
  public String getIsDraft() {
    return this.isDraft;
  }

  public void setIsDraft(String isDraft) {
    this.isDraft = isDraft;
  }

  @Column(name = "ON_TOP", length = 1)
  public String getOnTop() {
    return this.onTop;
  }

  public void setOnTop(String onTop) {
    this.onTop = onTop;
  }

  @Column(name = "IS_ELITE", length = 1)
  public String getIsElite() {
    return this.isElite;
  }

  public void setIsElite(String isElite) {
    this.isElite = isElite;
  }
  
  @Column(name = "FLASH_IMG", length = 255)
  public String getFlashImg() {
    return flashImg;
  }

  public void setFlashImg(String flashImg) {
    this.flashImg = flashImg;
  }

  @Column(name = "HITS", precision = 22, scale = 0)
  public Integer getHits() {
    return this.hits;
  }

  public void setHits(Integer hits) {
    this.hits = hits;
  }

  @Column(name = "PAGINATION_TYPE", length = 1)
  public String getPaginationType() {
    return this.paginationType;
  }

  public void setPaginationType(String paginationType) {
    this.paginationType = paginationType;
  }

  @Column(name = "MAX_CHAR_PAGE", precision = 22, scale = 0)
  public Integer getMaxCharPage() {
    return this.maxCharPage;
  }

  public void setMaxCharPage(Integer maxCharPage) {
    this.maxCharPage = maxCharPage;
  }

  @Column(name = "DESCN")
  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  @Column(name = "SERIAL_NO", precision = 22, scale = 0)
  public Integer getSerialNo() {
    return this.serialNo;
  }

  public void setSerialNo(Integer serialNo) {
    this.serialNo = serialNo;
  }

  @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "article")
  public Set<Attachments> getAttachmentses() {
    return this.attachmentses;
  }

  public void setAttachmentses(Set<Attachments> attachmentses) {
    this.attachmentses = attachmentses;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Articles)) {
      return false;
    }
    Articles castOther = (Articles) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
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
