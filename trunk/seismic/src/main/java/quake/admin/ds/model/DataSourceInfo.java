package quake.admin.ds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户自定义的数据源属性
 * @author SAM
 *
 */
@Entity
@Table(name = "datasource_info")
public class DataSourceInfo {
  /**
   * 主键
   * @see {@link eq.admin.ds.DsConstants#PK}
   */
  private String id;
  /**
   * 测震数据库类型,取值为DsConstants#DB_CZ_MYSQL或DsConstants#DB_CZ_ORACLE
   * @see {@link DsConstants#DB_CZ_MYSQL}
   * @see {@link DsConstants#DB_CZ_ORACLE}
   */
  private String czType;
  /**
   * 测震数据库服务器地址
   */
  private String czHost;
  /**
   * 测震数据库端口
   */
  private String czPort;
  /**
   * 测震数据库实例名
   */
  private String czInstance;
  /**
   * 测震归档事件波形数据存储路径
   */
  private String czPath;
  
  /**
   * 测震数据库用户名
   */
  private String czUser;
  /**
   * 测震数据库密码
   */
  private String czPwd;
  
  /**
   * 测震使用Oracle的时候的Schema
   */
  private String czSchema;
  
  /**
   * 前兆Schema
   */
  private String qzSchema;
  /**
   * 前兆数据库地址
   */
  private String qzHost;
  /**
   * 前兆数据库端口
   */
  private String qzPort;
  
  /**
   * 前兆数据库实例
   */
  private String qzInstance;
  /**
   * 前兆数据库用户名
   */
  private String qzUser;
  /**
   * 前兆数据库密码
   */
  private String qzPwd;
  /**
   * 版本标记
   */
  private Integer version;

  /**
   * @return the czType
   */
  @Column(name = "cz_type")
  public String getCzType() {
    return czType;
  }
  /**
   * @param czType the czType to set
   */
  public void setCzType(String czType) {
    this.czType = czType;
  }
  /**
   * @return the czHost
   */
  @Column(name = "cz_host")
  public String getCzHost() {
    return czHost;
  }
  /**
   * @param czHost the czHost to set
   */
  public void setCzHost(String czHost) {
    this.czHost = czHost;
  }
  /**
   * @return the czPort
   */
  @Column(name = "cz_port")
  public String getCzPort() {
    return czPort;
  }
  /**
   * @param czPort the czPort to set
   */
  public void setCzPort(String czPort) {
    this.czPort = czPort;
  }
  /**
   * @return the czInstance
   */
  @Column(name = "cz_instance")
  public String getCzInstance() {
    return czInstance;
  }
  /**
   * @param czInstance the czInstance to set
   */
  public void setCzInstance(String czInstance) {
    this.czInstance = czInstance;
  }
  /**
   * @return the czPath
   */
  @Column(name = "cz_path")
  public String getCzPath() {
    return czPath;
  }
  /**
   * @param czPath the czPath to set
   */
  public void setCzPath(String czPath) {
    this.czPath = czPath;
  }
  /**
   * @return the czUser
   */
  @Column(name = "cz_user")
  public String getCzUser() {
    return czUser;
  }
  /**
   * @param czUser the czUser to set
   */
  public void setCzUser(String czUser) {
    this.czUser = czUser;
  }
  /**
   * @return the czPwd
   */
  @Column(name = "cz_pwd")
  public String getCzPwd() {
    return czPwd;
  }
  /**
   * @param czPwd the czPwd to set
   */
  public void setCzPwd(String czPwd) {
    this.czPwd = czPwd;
  }
  /**
   * @return the qzHost
   */
  @Column(name = "qz_host")
  public String getQzHost() {
    return qzHost;
  }
  /**
   * @param qzHost the qzHost to set
   */
  public void setQzHost(String qzHost) {
    this.qzHost = qzHost;
  }
  /**
   * @return the qzPort
   */
  @Column(name = "qz_port")
  public String getQzPort() {
    return qzPort;
  }
  /**
   * @param qzPort the qzPort to set
   */
  public void setQzPort(String qzPort) {
    this.qzPort = qzPort;
  }
  /**
   * @return the qzUser
   */
  @Column(name = "qz_user")
  public String getQzUser() {
    return qzUser;
  }
  /**
   * @param qzUser the qzUser to set
   */
  public void setQzUser(String qzUser) {
    this.qzUser = qzUser;
  }
  /**
   * @return the qzPwd
   */
  @Column(name = "qz_pwd")
  public String getQzPwd() {
    return qzPwd;
  }
  /**
   * @param qzPwd the qzPwd to set
   */
  public void setQzPwd(String qzPwd) {
    this.qzPwd = qzPwd;
  }
  /**
   * @return the version
   */
  @Version
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
   * @return the pk
   */
  @Id
  @GeneratedValue(generator = "dsPK")
  @GenericGenerator(name = "dsPK", strategy = "assigned")
  @Column(name = "id")
  public String getId() {
    return id;
  }
  /**
   * @param pk the pk to set
   */
  public void setId(String id) {
    this.id = id;
  }
  
  @Override
  public String toString() {
    return new StringBuffer().append(czHost)
    .append(czInstance).append(czPort).append(czSchema)
    .append(czType).append(czUser).append(qzHost)
    .append(qzInstance).append(qzPort).append(qzPwd)
    .append(qzSchema).append(czType).append(qzUser).toString();
  }
  /**
   * @return the qzInstance
   */
  @Column(name = "qz_instance")
  public String getQzInstance() {
    return qzInstance;
  }
  /**
   * @param qzInstance the qzInstance to set
   */
  public void setQzInstance(String qzInstance) {
    this.qzInstance = qzInstance;
  }
  /**
   * @return the czSchema
   */
  @Column(name = "cz_schema")
  public String getCzSchema() {
    return czSchema;
  }
  /**
   * @param czSchema the czSchema to set
   */
  public void setCzSchema(String czSchema) {
    this.czSchema = czSchema;
  }
  /**
   * @return the qzSchema
   */
  @Column(name = "qz_schema")
  public String getQzSchema() {
    return qzSchema;
  }
  /**
   * @param qzSchema the qzSchema to set
   */
  public void setQzSchema(String qzSchema) {
    this.qzSchema = qzSchema;
  }
}
