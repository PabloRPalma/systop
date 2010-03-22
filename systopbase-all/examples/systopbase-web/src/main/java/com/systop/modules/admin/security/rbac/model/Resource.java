package com.systop.modules.admin.security.rbac.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.GrantedAuthority;

import com.systop.core.model.BaseModel;
import com.systop.modules.admin.security.resourcedetails.ResourceDetails;

/**
 * The persistent class for the resources database table.
 * 
 * @author Sam Lee
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "resources", uniqueConstraints = { })
public class Resource extends BaseModel implements ResourceDetails, Serializable {
  /**
   * id
   */
  private Integer id;

  /**
   * 资源描述
   */
  private String descn;

  /**
   * 资源名称
   */
  private String name;

  /**
   * 资源字符串
   */
  private String resString;

  /**
   * 资源类型
   */
  private String resType;

  /**
   * 上级资源
   */
  private Resource parentRes;
  
  /**
   * 是否叶子
   */
  private String isLeaf;
  
  /**
   * 本级排序
   */
  private Integer seque;
  
  /**
   * 是否顶级
   */
  private String isTop;
  
  /**
   * 显示文字
   */
  private String dispText;
  
  /**
   * 图标
   */
  private String icon;
  
  /**
   * 是否快捷方式
   */
  private String isShortcut;
  
  /**
   * 快捷方式顺序
   */
  private Integer shortcutSeque;
  
  /**
   * 版本
   */
  private Integer version;
  
  /**
   * 子资源记录
   */
  private Set<Resource> childResources = new HashSet<Resource>(0);

  /**
   * 可访问此资源的权限
   */
  private Set<Role> roles = new HashSet<Role>(0);

  /**
   * 缺省构造
   */
  public Resource() {
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

  @Column(name = "res_string")
  public String getResString() {
    return this.resString;
  }

  public void setResString(String resString) {
    this.resString = resString;
  }

  @Column(name = "res_type")
  public String getResType() {
    return this.resType;
  }

  public void setResType(String resType) {
    this.resType = resType;
  }

  @ManyToOne(cascade = { }, fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  public Resource getParentRes() {
    return this.parentRes;
  }
  
  public void setParentRes(Resource parentRes) {
    this.parentRes = parentRes;
  }
  
  @Column(name = "is_leaf")
  public String getIsLeaf() {
    return this.isLeaf;
  }
  
  public void setIsLeaf(String isLeaf) {
    this.isLeaf = isLeaf;
  }
  
  @Column(name = "seque")
  public Integer getSeque() {
    return this.seque;
  }
  
  public void setSeque(Integer seque) {
    this.seque = seque;
  }
  
  @Column(name = "is_top")
  public String getIsTop() {
    return isTop;
  }
  
  public void setIsTop(String isTop) {
    this.isTop = isTop;
  }
  
  @Column(name = "disp_text")
  public String getDispText() {
    return this.dispText;
  }
  
  public void setDispText(String dispText) {
    this.dispText = dispText;
  }
  
  @Column(name = "icon")
  public String getIcon() {
    return this.icon;
  }
  
  public void setIcon(String icon) {
    this.icon = icon;
  }
  
  @Column(name = "is_shortcut")
  public String getIsShortcut() {
    return this.isShortcut;
  }
  
  public void setIsShortcut(String isShortcut) {
    this.isShortcut = isShortcut;
  }
  
  @Column(name = "shortcut_seque")
  public Integer getShortcutSeque() {
    return this.shortcutSeque;
  }
  
  public void setShortcutSeque(Integer shortcutSeque) {
    this.shortcutSeque = shortcutSeque;
  }
  
  @Version
  @Column(name = "version")
  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @OneToMany(cascade = { }, fetch = FetchType.LAZY, mappedBy = "parentRes")
  public Set<Resource> getChildResources() {
    return this.childResources;
  }
  
  public void setChildResources(Set<Resource> childResources) {
    this.childResources = childResources;
  }
  
  @ManyToMany(cascade = { }, fetch = FetchType.LAZY, mappedBy = "resources")
  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Resource)) {
      return false;
    }
    Resource castOther = (Resource) other;
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
  
  //Method from ResourceDetails
  /**
   * 可以访问该资源的<code>GrantedAuthority</code>
   */
  private transient GrantedAuthority[] authorities;
  /**
   * @param authorities the authorities to set
   */
  public void setAuthorities(GrantedAuthority[] authorities) {
    this.authorities = authorities;
  }

  /** 
   * @see ResourceDetails#getAuthorities()
   */
  @Transient
  public GrantedAuthority[] getAuthorities() {
    if (authorities != null) {
      return authorities;
    }
    // 将Role转化为GrantedAuthority
    Set<Role> roles = getRoles();
    if (roles != null) {
      List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>(roles.size());

      for (Role role : roles) {
        auths.add(role.toGrantedAuthority());
      }
      return (GrantedAuthority[]) auths.toArray(new GrantedAuthority[0]);
    }

    return new GrantedAuthority[] {};
  }
}