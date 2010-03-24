package datashare.admin.counter.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.systop.core.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name="set_counter")
public class SetCounter extends BaseModel implements Serializable {

  private Integer id;
  
  /**
   * 访问路径
   */
  private String url;
  
  /**
   * 访问时间
   */
  private Date visiteTime;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="ID",nullable=false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name="URL",nullable=false,length=255)
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Column(name="VISITE_TIME",nullable=false,length=255)
  public Date getVisiteTime() {
    return visiteTime;
  }

  public void setVisiteTime(Date visiteTime) {
    this.visiteTime = visiteTime;
  }
}
