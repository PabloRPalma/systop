package datashare.admin.czcatalog.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

import datashare.admin.sitecfg.model.SiteCfg;

/**
 * 地震目录表
 * 
 * @author wbb
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "cz_catalog", uniqueConstraints = {})
public class CzCatalog extends BaseModel implements Serializable {
  /**
   * 主键
   */
  private Integer id;
  /**
   * 地震目录表名
   */
  private String cltName;

  /**
   * 地震目录名称
   */
  private String clcName;

  /**
   * 地震目录描述
   */
  private String clDescn;

  /**
   * 与地震目录相关的震级表名
   */
  private String magTname;

  /**
   * 与地震目录相关的震相表名
   */
  private String phaseTname;
  
  /**
   * 地震目录列表中ML或Ms类型显示
   */
  private String disType;
  
  /**
   * 是否显示有事件波形的地震目录
   */
  private String seedDis;
  
  /**
   * 与网站配置的关系
   */
  private Set<SiteCfg> SiteCfgs = new HashSet<SiteCfg>(0);

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

  @Column(name = "cl_tname")
  public String getCltName() {
    return cltName;
  }

  public void setCltName(String cltName) {
    this.cltName = cltName;
  }

  @Column(name = "cl_cname")
  public String getClcName() {
    return clcName;
  }

  public void setClcName(String clcName) {
    this.clcName = clcName;
  }

  @Column(name = "cl_descn")
  public String getClDescn() {
    return clDescn;
  }

  public void setClDescn(String clDescn) {
    this.clDescn = clDescn;
  }

  @Column(name = "mag_tname")
  public String getMagTname() {
    return magTname;
  }

  public void setMagTname(String magTname) {
    this.magTname = magTname;
  }

  @Column(name = "phase_tname")
  public String getPhaseTname() {
    return phaseTname;
  }

  public void setPhaseTname(String phaseTname) {
    this.phaseTname = phaseTname;
  }
  
  @Column(name = "dis_type")
  public String getDisType() {
    return disType;
  }

  public void setDisType(String disType) {
    this.disType = disType;
  }
  
  @Column(name = "seed_dis")
  public String getSeedDis() {
    return seedDis;
  }

  public void setSeedDis(String seedDis) {
    this.seedDis = seedDis;
  }
  
  @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "provinceCat")
  public Set<SiteCfg> getSiteCfgs() {
    return SiteCfgs;
  }

  public void setSiteCfgs(Set<SiteCfg> siteCfgs) {
    SiteCfgs = siteCfgs;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof CzCatalog)) {
      return false;
    }
    CzCatalog czCatalog = (CzCatalog) other;
    return new EqualsBuilder().append(this.getId(), czCatalog.getId()).isEquals();
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
