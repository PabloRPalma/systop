package quake.admin.seedpath.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * seed路径配置表
 * @author DU
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "seedpath", uniqueConstraints = {})
public class Seedpath extends BaseModel implements Serializable {

  /**
   * 主键
   */
  private String id;
  
  /**
   * 测震归档事件波形数据存储路径
   */
  private String path;

  /**
   * 版本标记
   */
  private Integer version;
  
  @Id
  @GeneratedValue(generator = "dsPK")
  @GenericGenerator(name = "dsPK", strategy = "assigned")
  @Column(name = "id")
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the qzInstance
   */
  @Column(name = "path")
  public String getPath() {
    return path;
  }

  /**
   * @param path the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }
  
  /**
   * @return the qzInstance
   */
  @Column(name = "version")
  public Integer getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(Integer version) {
    this.version = version;
  }
  
  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Seedpath)) {
      return false;
    }
    Seedpath castOther = (Seedpath) other;
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
