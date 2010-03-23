package com.systop.common.map.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;


/** 
 *        @hibernate.class
 *         table="MAPS"
 *         lazy="true"
 *     
*/
@Entity
@Table(name = "MAPS")
public class Map implements Serializable {
    /** default serial version id, required for serializable classes */
    private static final long serialVersionUID = 1L;

    /** identifier field */
    private Integer mapId;

    /** nullable persistent field */
    private String mapDescn;

    /** nullable persistent field */
    private String mapSign;

    /** nullable persistent field */
    private String mapTitle;

    /** nullable persistent field */
    private Integer version;

    /** persistent field */
    private Set<Entry> entries;

    /** full constructor */
    public Map(String mapDescn, String mapSign, String mapTitle
    		, Integer version, Set entries) {
        this.mapDescn = mapDescn;
        this.mapSign = mapSign;
        this.mapTitle = mapTitle;
        this.version = version;
        this.entries = entries;
    }

    /** default constructor */
    public Map() {
    }

    /** minimal constructor */
    public Map(Set entries) {
        this.entries = entries;
    }

    /** 
     *            @hibernate.id
     *             generator-class="hilo"
     *             type="java.lang.Integer"
     *             column="MAP_ID"
     */
    @Id
    @GeneratedValue(generator = "hibseq")
  	@GenericGenerator(name = "hibseq", strategy = "hilo")
  	@Column(name = "MAP_ID", unique = true, nullable = false)
    public Integer getMapId() {
        return this.mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    /** 
     *            @hibernate.property
     *             column="MAP_DESCN"
     *             length="255"
     */
  	@Column(name = "MAP_DESCN")
    public String getMapDescn() {
        return this.mapDescn;
    }

    public void setMapDescn(String mapDescn) {
        this.mapDescn = mapDescn;
    }

    /** 
     *            @hibernate.property
     *             column="MAP_SIGN"
     *             length="255"
     */
    @Column(name = "MAP_SIGN")
    public String getMapSign() {
        return this.mapSign;
    }

    public void setMapSign(String mapSign) {
        this.mapSign = mapSign;
    }

    /** 
     *            @hibernate.property
     *             column="MAP_TITLE"
     *             length="255"
     */
    @Column(name = "MAP_TITLE")
    public String getMapTitle() {
        return this.mapTitle;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    /** 
     *            @hibernate.version
     *             column="VERSION"
     */
    @Column(name = "VERSION")
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /** 
     *            @hibernate.set
     *             inverse="true"
     *            @hibernate.collection-key
     *             column="MAP_ID"
     *            @hibernate.collection-one-to-many
     *             class="com.systop.common.map.model.Entry"
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
    		mappedBy = "map", 
    		targetEntity = com.systop.common.map.model.Entry.class)
    public Set getEntries() {
        return this.entries;
    }

    public void setEntries(Set entries) {
        this.entries = entries;
    }

    /***/
    public String toString() {
        return new ToStringBuilder(this)
            .append("mapId", getMapId())
            .toString();
    }

    /***/
    public boolean equals(Object other) {
        if ((this == other)) {
        	return true;
        } 
        if (!(other instanceof Map)) {
        	return false;
        }
        Map castOther = (Map) other;
        return new EqualsBuilder()
            .append(this.getMapId(), castOther.getMapId())
            .isEquals();
    }

    /***/
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getMapId())
            .toHashCode();
    }

}