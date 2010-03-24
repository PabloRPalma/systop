package datashare.admin.sitecfg.model;

import java.io.Serializable;

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

import datashare.admin.czcatalog.model.CzCatalog;

/**
 * 网站配置管理model
 * @author wbb
 */
@Entity
@Table(name = "site_cfg", uniqueConstraints = {})
public class SiteCfg extends BaseModel implements Serializable {
  private Integer id;
  /**
   * 网站名称
   */
  private String cmsName;
  /**
   * 本省名称
   */
  private String provinceName;
  /**
   * 网站代码
   */
  private String cmsCode;
  /**
   * 本省地震目录
   */
  private CzCatalog provinceCat;
  
  /**
   * 地震目录最小震级
   */
  private Double minM;
  
  /**
   * 邮编
   */
  private String zipCode;
  
  /**
   * 地址
   */
  private String address;
  
  /**
   * 版权
   */
  private String copyright;
  
  /**
   * ICP备号
   */
  private String icpCode;
  
  /**
   * 电子邮件
   */
  private String email;
  
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
  
  @Column(name = "cms_name")
  public String getCmsName() {
    return cmsName;
  }
  
  public void setCmsName(String cmsName) {
    this.cmsName = cmsName;
  }
  
  @Column(name = "province_name")
  public String getProvinceName() {
    return provinceName;
  }
  
  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }
  
  @Column(name = "cms_code")
  public String getCmsCode() {
    return cmsCode;
  }
  
  public void setCmsCode(String cmsCode) {
    this.cmsCode = cmsCode;
  }

  @ManyToOne(cascade = { }, fetch = FetchType.LAZY)
  @JoinColumn(name = "cz_catalog")
  public CzCatalog getProvinceCat() {
    return provinceCat;
  }

  public void setProvinceCat(CzCatalog provinceCat) {
    this.provinceCat = provinceCat;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final SiteCfg other = (SiteCfg) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  /**
   * @return the minM
   */
  @Column(name = "min_m")
  public Double getMinM() {
    return minM;
  }

  /**
   * @param minM the minM to set
   */
  public void setMinM(Double minM) {
    this.minM = minM;
  }

  @Column(name="zipCode")
  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Column(name="address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name="copyright")
  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  @Column(name="ICPCode")
  public String getIcpCode() {
    return icpCode;
  }

  public void setIcpCode(String code) {
    icpCode = code;
  }

  @Column(name="email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  
}
