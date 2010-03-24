package datashare.metadata.model;

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
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import com.systop.core.model.BaseModel;

/**
 * 元数据服务
 * @author DU
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "metadata", uniqueConstraints = {})
public class Metadata extends BaseModel implements Serializable {

  /**
   * 主键
   */
  private Integer id;
  
  /**
   * 类别 
   */
  private String type;
  
  /**
   * 类型 CZ:测震；QZ:前兆
   */
  private String czOrQz;
  
  /**
   * 数据集
   */
  private String metaSet;
  
  /**
   * 数据文件
   */
  private String dataFile;
  
  /**
   * 数据文件内容
   */
  private String fileContent;

  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", unique = true, nullable = false)
  public Integer getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "type")
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  @Column(name = "meta_set")
  public String getMetaSet() {
    return metaSet;
  }

  /**
   * @param metaSet the metaSet to set
   */
  public void setMetaSet(String metaSet) {
    this.metaSet = metaSet;
  }

  @Column(name = "data_file")
  public String getDataFile() {
    return dataFile;
  }

  /**
   * @param dataFile the dataFile to set
   */
  public void setDataFile(String dataFile) {
    this.dataFile = dataFile;
  }
  
  @Column(name = "cz_or_qz")
  public String getCzOrQz() {
    return czOrQz;
  }

  /**
   * @param czOrQz the czOrQz to set
   */
  public void setCzOrQz(String czOrQz) {
    this.czOrQz = czOrQz;
  }
  
  @Column(name = "file_content")
  @Lob
  @Field(name = "fileContent", index = Index.TOKENIZED, store = Store.YES)
  public String getFileContent() {
    return fileContent;
  }

  /**
   * @param fileContent the fileContent to set
   */
  public void setFileContent(String fileContent) {
    this.fileContent = fileContent;
  }
  
  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Metadata)) {
      return false;
    }
    Metadata metadata = (Metadata) other;
    return new EqualsBuilder().append(this.getId(), metadata.getId()).isEquals();
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
