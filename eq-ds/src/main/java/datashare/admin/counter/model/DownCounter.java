package datashare.admin.counter.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.systop.core.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name="down_counter")
public class DownCounter extends BaseModel implements Serializable {

  private Integer Id;
  
  /**
   * 数据下载时间
   */
  private Date time;
  
  /**
   * 下载路径
   */
  private String pattern;
  
  /**
   *计数器
   */
  private AtomicInteger atomicInteger = new AtomicInteger();

  @Column(name="TIME")
  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }



  @Column(name = "PATTERN",nullable=false)
  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
  
  @Transient
  public int getCounter(){
    return atomicInteger.get();
  }
  
  @Transient
  public int inc(){
    return atomicInteger.getAndIncrement();
  }
  
  @Transient
  public void clear(){
    atomicInteger = new AtomicInteger();
  }
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Integer getId() {
    return Id;
  }

  public void setId(Integer id) {
    Id = id;
  }

}
