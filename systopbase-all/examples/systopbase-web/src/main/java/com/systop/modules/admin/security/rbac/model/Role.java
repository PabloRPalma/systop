package com.systop.modules.admin.security.rbac.model;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import com.systop.core.model.BaseModel;

/**
 * 角色表 The persistent class for the roles database table.
 * 
 * @author Sam Lee
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "roles", uniqueConstraints = {})
public class Role extends BaseModel implements Serializable {

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 角色描述
	 */
	private String descn;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 版本
	 */
	private Integer version;

	/**
	 * 具有此角色的用户
	 */
	private Set<User> users = new HashSet<User>(0);

	/**
	 * 此角色可以访问的资源
	 */
	private Set<Resource> resources = new HashSet<Resource>(0);
  
	/**
	 * 缺省构造器
	 */
	public Role() {
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

	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "roles", targetEntity = User.class)
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany(targetEntity = Resource.class, cascade = {}, fetch = FetchType.LAZY)
	@JoinTable(name = "role_res", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "res_id") })
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	/**
	 * 将Role对象转化为<code>GrantedAuthority</code>
	 */
	public GrantedAuthority toGrantedAuthority() {
		return new GrantedAuthorityImpl(this.name);
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Role)) {
			return false;
		}
		Role castOther = (Role) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
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
