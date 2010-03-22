package com.systop.core.dao.testmodel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;


/**
 * The persistent class for the depts database table.
 * 
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "test_depts", uniqueConstraints = { })
public class TestDept extends BaseModel implements Serializable {
  /**
   * id
   */
  private Integer id;

  /**
   * 部门描述
   */
  private String descn;

  /**
   * 部门名称
   */
  private String name;

  /**
   * 部门编号规则：两位数字，从1自动排；
   */
  private String serialNo;


  
  /**
   * 上级部门
   */
  private TestDept parentDept;

  /**
   * 部门记录
   */
  private Set<TestDept> childDepts = new HashSet<TestDept>(0);

  /**
   * 部门下的员工
   */
  private Set<TestEmployee> employees = new HashSet<TestEmployee>(0);

  /**
   * 缺省构造
   */
  public TestDept() {
  }

  @Id
  @GeneratedValue(generator = "hibseq")
  @GenericGenerator(name = "hibseq", strategy = "hilo")
  @Column(name = "ID", unique = true, nullable = false)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "descn")
  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  @Column(name = "name")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "serial_no")
  public String getSerialNo() {
    return this.serialNo;
  }

  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }

  @ManyToOne(cascade = { }, fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  public TestDept getParentDept() {
    return this.parentDept;
  }

  public void setParentDept(TestDept parentDept) {
    this.parentDept = parentDept;
  }

  @OneToMany(cascade = { }, fetch = FetchType.LAZY, mappedBy = "parentDept")
  public Set<TestDept> getChildDepts() {
    return this.childDepts;
  }

  public void setChildDepts(Set<TestDept> childDepts) {
    this.childDepts = childDepts;
  }

  @OneToMany(cascade = { }, fetch = FetchType.LAZY, mappedBy = "dept")
  public Set<TestEmployee> getEmployees() {
    return this.employees;
  }

  public void setEmployees(Set<TestEmployee> employees) {
    this.employees = employees;
  }
  
  @Transient
  public boolean getHasChild() {
    return this.getChildDepts().size() > 0;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof TestDept)) {
      return false;
    }
    TestDept castOther = (TestDept) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId())
        .isEquals();
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }
}