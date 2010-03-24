package datashare.admin.subject.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

import datashare.admin.method.model.Method;

/**
 * 学科表
 * @author wbb
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "subjects", uniqueConstraints = {})
public class Subject extends BaseModel implements Serializable {
  /**
   * 学科代码
   */
  private String id;

  /**
   * 学科名称
   */
  private String name;

  /**
   * 此学科下的测项
   */
  private Set<Method> methods = new HashSet<Method>(0);

  /**
   * 页面checkboxList提交数据保存
   */
  private String[] methodIds = new String[0];
  
  @Id
  @GeneratedValue(generator = "dsPK")
  @GenericGenerator(name = "dsPK", strategy = "assigned")
  @Column(name = "id", unique = true, nullable = false, length = 50)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToMany(cascade = {}, fetch = FetchType.LAZY, targetEntity = Method.class)
  @JoinTable(name = "method_subject", joinColumns = { @JoinColumn(name = "subject_id") }, inverseJoinColumns = { @JoinColumn(name = "method_id") })
  public Set<Method> getMethods() {
    return methods;
  }

  public void setMethods(Set<Method> methods) {
    this.methods = methods;
  }
  
  @Transient
  public String[] getMethodIds() {
    // 把对象里的'id'解析成数组,应用于checkboxList默认选中
    methodIds = new String[methods.size()];
    int i = 0;
    for (Method ti : methods) {
      methodIds[i] = ti.getId();
      i++;
    }
    return methodIds;
  }
  
  /**
   * 获取学科对应的所有的测项id[字符串]
   * @return
   */
  @Transient
  public String getMethodIdsStr() {
    // 把对象里的'id'解析成数组,应用于checkboxList默认选中
    String ids = "";
    for (Method ti : methods) {
      ids = ids + ti.getId() + ",";
    }
    if (ids.length() != 0){
      ids = ids.substring(0, ids.length()-1);
    }
    return ids;
  }

  /**
   * 得到页面提交的测项'id'数组
   * @return
   */
  @Transient
  public String[] getMethodIdArray() {
    return methodIds;
  }

  public void setMethodIds(String[] methodIds) {
    methods = new HashSet<Method>(0);
    // 把提交上来的'id'数组解析成对象，应用于checkboxList 提交
    for (String id : methodIds) {
      Method ti = new Method();
      ti.setId(id);
      methods.add(ti);
    }
    this.methodIds = methodIds;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Subject)) {
      return false;
    }
    Subject castOther = (Subject) other;
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
