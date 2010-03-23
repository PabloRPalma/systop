package eq.datasource;

/**
 * 用户自定义的数据源属性
 * @author SAM
 *
 */
public class DataSourceInfo {
  /**
   * 数据库名称
   */
  private String type;
  /**
   * url
   */
  private String url;
  
  /**
   * username
   */
  private String username;
  
  /**
   * password
   */
  private String password;
  /**
   * @return the dataBaseName
   */
  public String getType() {
    return type;
  }
  /**
   * @param dataBaseName the dataBaseName to set
   */
  public void setType(String type) {
    this.type = type;
  }
  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }
  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }
  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }
  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }
  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }
  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
