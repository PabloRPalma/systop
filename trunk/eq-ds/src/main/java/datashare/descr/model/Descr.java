package datashare.descr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 说明描述信息
 * @author DU
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "descr", uniqueConstraints = {})
public class Descr extends BaseModel implements Serializable {

  /**
   * 主键
   */
  private Integer id;
  
  /**
   * 数据类型：地震目录、波形等数据
   */
  private String type;
  
  /**
   * 是否有元数据信息
   */
  private String hasMetadata;
  
  /**
   * 标题
   */
  private String title;
  
  /**
   * 描述
   */
  private String descn;
  
  /**
   * 访问地址
   */
  private String accessUrl;

  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", unique = true, nullable = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "title")
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }

  @Column(name = "descn")
  @Lob
  public String getDescn() {
    return descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  @Column(name = "type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
  
  @Column(name = "access_url")
  public String getAccessUrl() {
    return accessUrl;
  }

  public void setAccessUrl(String accessUrl) {
    this.accessUrl = accessUrl;
  }
  
  @Column(name = "has_metadata")
  public String getHasMetadata() {
    return hasMetadata;
  }

  public void setHasMetadata(String hasMetadata) {
    this.hasMetadata = hasMetadata;
  }
  
  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Descr)) {
      return false;
    }
    Descr metaDescribe = (Descr) other;
    return new EqualsBuilder().append(this.getId(), metaDescribe.getId()).isEquals();
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
