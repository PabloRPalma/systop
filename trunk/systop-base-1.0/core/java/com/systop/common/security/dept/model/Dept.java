package com.systop.common.security.dept.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.systop.common.security.user.model.User;


/** 
 *        @hibernate.class
 *         table="DEPTS"
 *         lazy="true"
*/
@Entity
@Table(name = "DEPTS", schema = "PUBLIC")
public class Dept implements Serializable {
    /** default serial version id, required for serializable classes */
    private static final long serialVersionUID = 1L;

    /** 部门ＩＤ */
    private Integer id;

    /** 描述 */
    private String descn;

    /** 部门名称 */
    private String name;

    /** 所属父部门 */
    private Dept parent;
    
    /** 子部门S */
    private Set<Dept> depts = new HashSet<Dept>(0);

    /** 录属部门用户 */
    private Set<User> users =  new HashSet<User>(0);;


    /** default constructor */
    public Dept() {
    }

    /** minimal constructor */
    public Dept(Set<User> users) {
        this.users = users;
    }

    /** 
     *            @hibernate.id
     *             generator-class="hilo"
     *             type="java.lang.Integer"
     *             column="ID"
     */
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

    /** 
     *            @hibernate.property
     *             column="DESCN"
     *             length="255"
     */
    @Column(name = "DESCN")
    public String getDescn() {
        return this.descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="255"
     */
    @Column(name = "NAME")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *            @hibernate.many-to-one
     *            @hibernate.column name="PARENT_ID"           
     */
    @ManyToOne(fetch = FetchType.LAZY)
  	@JoinColumn(name = "PARENT_ID")
    public Dept getParent() {
        return this.parent;
    }

    public void setParent(Dept parent) {
        this.parent = parent;
    }
    
    /** 
     *            @hibernate.set
     *             inverse="true"
     *            @hibernate.collection-key
     *             column="PARENT_ID"
     *            @hibernate.collection-one-to-many
     *             class="com.systop.common.security.dept.model.Dept"
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    		mappedBy = "parent")
    public Set<Dept> getDepts() {
        return this.depts;
    }

    public void setDepts(Set<Dept> depts) {
        this.depts = depts;
    }

    /** 
     *            @hibernate.set
     *             inverse="true"
     *            @hibernate.collection-key
     *             column="DEPT_ID"
     *            @hibernate.collection-one-to-many
     *             class="com.systop.common.security.user.model.User"
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    		mappedBy = "dept", 
    		targetEntity = com.systop.common.security.user.model.User.class)
    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        if ((this == other)) {
          return true;
        }
        if (!(other instanceof Dept)) {
          return false;
        }
        Dept castOther = (Dept) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}