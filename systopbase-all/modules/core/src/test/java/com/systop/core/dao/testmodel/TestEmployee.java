package com.systop.core.dao.testmodel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;


/**
 * The persistent class for the employees database table.
 * 
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "test_employees", uniqueConstraints = { })
public class TestEmployee extends BaseModel implements Serializable {
  /**
   * id
   */
  private Integer id;

  /**
   * 地址
   */
  private String address;

  /**
   * 出身日期
   */
  private Date birthday;


  /**
   * 对应的User
   */
  private TestUser user;

  /**
   * 对应的Dept
   */
  private TestDept dept;

  /**
   * 缺省构造
   */
  public TestEmployee() {
  }

  @Id
  @GeneratedValue(generator = "hilo")
  @GenericGenerator(name = "hilo", strategy = "hilo")
  @Column(name = "ID", unique = true, nullable = false)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "address")
  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "birthday")
  public Date getBirthday() {
    return this.birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

 

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id")
  public TestUser getUser() {
    return this.user;
  }

  public void setUser(TestUser user) {
    this.user = user;
  }

  @ManyToOne(cascade = { }, fetch = FetchType.LAZY)
  @JoinColumn(name = "dept_id")
  public TestDept getDept() {
    return this.dept;
  }

  public void setDept(TestDept dept) {
    this.dept = dept;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof TestEmployee)) {
      return false;
    }
    TestEmployee castOther = (TestEmployee) other;
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
  
  /**
   * 用于测试ReflectUtil的方法
   */
  public static final String TEST_STRING = "TEST";
  /**
   * 用于测试ReflectUtil的方法
   */
  private String testString = TEST_STRING;
  /**
   * 用于测试ReflectUtil的方法
   */
  @Transient
  protected String getTestString() {
    return testString;
  }
  /**
   * 用于测试ReflectUtil的方法
   */
  protected void setTestString(String s) {
    this.testString = s;
  }
}