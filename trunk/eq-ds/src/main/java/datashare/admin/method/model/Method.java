package datashare.admin.method.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.modules.security.user.model.Role;
import com.systop.core.model.BaseModel;

import datashare.admin.subject.model.Subject;

/**
 * 测项管理表
 * @author wbb
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "methods", uniqueConstraints = {})
public class Method extends BaseModel implements Serializable {
  /**
   * 测项代码
   */
  private String id;
  
  /**
   * 测项名称
   */
  private String name;
  
  /**
   * 测项的角色
   */
  private Set<Role> roles = new HashSet<Role>(0);
  
  /**
   * 测项的学科
   */
  private Set<Subject> subjects = new HashSet<Subject>(0);

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
  
  @ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "methods", targetEntity = Role.class)
  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  
  @ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "methods", targetEntity = Subject.class)
  public Set<Subject> getSubjects() {
    return subjects;
  }

  public void setSubjects(Set<Subject> subjects) {
    this.subjects = subjects;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Method)) {
      return false;
    }
    Method castOther = (Method) other;
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
