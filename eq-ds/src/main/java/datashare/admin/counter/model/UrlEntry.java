package datashare.admin.counter.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

/**
 * 网站统计model
 * @author SAM
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="url_entries")
public class UrlEntry extends BaseModel implements Serializable {


  /**
   * URL匹配路径，例如:"/main.shtml"
   */
  private String pattern;
  /**
   * 名称
   */
  private String name;
  
  /**
   * 命中次数
   */
  private Integer hits;
  
  /**
   * 当前点击量，用于计数，初始化为{@code 0}
   */
  private AtomicInteger counter = new AtomicInteger();

  /**
   * @return the pattern
   */
  @Id
  @GeneratedValue(generator = "assign")
  @GenericGenerator(name = "assign", strategy = "assigned")
  @Column(name = "pattern")
  public String getPattern() {
    return pattern;
  }

  /**
   * @param pattern the pattern to set
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the hits
   */
  public Integer getHits() {
    return hits;
  }

  /**
   * @param hits the hits to set
   */
  public void setHits(Integer hits) {
    this.hits = hits;
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, new String[]{"name", "hits"});
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, new String[]{"name", "hits"});
  }

  /**
   * @return the counter
   */
  @Transient
  public int getCounter() {
    return counter.get();
  }

  /**
   * @param 计数器{{@link #counter}增加{@code 1}，并返回当前value.
   */
  @Transient
  public int inc() {
    return counter.getAndIncrement();
  }
  
  /**
   * @param 计数器{{@link #counter}清空.
   */
  @Transient
  public void clear() {
    counter = new AtomicInteger();
  }
}
