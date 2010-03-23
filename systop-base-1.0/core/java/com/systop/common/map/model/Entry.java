package com.systop.common.map.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;


/** 
 *        @hibernate.class
 *         table="ENTRIES"
 *         lazy="true"
 *     
*/
@Entity
@Table(name = "ENTRIES")
public class Entry implements Serializable {
    /** default serial version id, required for serializable classes */
    private static final long serialVersionUID = 1L;

    /** identifier field */
    private Integer entryId;

    /** nullable persistent field */
    private String entryDescn;

    /** nullable persistent field */
    private String refValue;

    /** nullable persistent field */
    private String viewText;

    /** nullable persistent field */
    private Integer version;

    /** nullable persistent field */
    private com.systop.common.map.model.Map map;

    /** full constructor */
    public Entry(String entryDescn, String refValue, String viewText
    		, Integer version, com.systop.common.map.model.Map map) {
        this.entryDescn = entryDescn;
        this.refValue = refValue;
        this.viewText = viewText;
        this.version = version;
        this.map = map;
    }

    /** default constructor */
    public Entry() {
    }

    /** 
     *            @hibernate.id
     *             generator-class="hilo"
     *             type="java.lang.Integer"
     *             column="ENTRY_ID"
     */
    @Id
  	@GeneratedValue(generator = "hibseq")
  	@GenericGenerator(name = "hibseq", strategy = "hilo")
  	@Column(name = "ENTRY_ID", unique = true, nullable = false)
    public Integer getEntryId() {
        return this.entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    /** 
     *            @hibernate.property
     *             column="ENTRY_DESCN"
     *             length="255"
     */
    @Column(name = "ENTRY_DESCN")
    public String getEntryDescn() {
        return this.entryDescn;
    }

    public void setEntryDescn(String entryDescn) {
        this.entryDescn = entryDescn;
    }

    /** 
     *            @hibernate.property
     *             column="REF_VALUE"
     *             length="10"
     */
    @Column(name = "REF_VALUE")
    public String getRefValue() {
        return this.refValue;
    }

    public void setRefValue(String refValue) {
        this.refValue = refValue;
    }

    /** 
     *            @hibernate.property
     *             column="VIEW_TEXT"
     *             length="255"
     */
    @Column(name = "VIEW_TEXT")
    public String getViewText() {
        return this.viewText;
    }

    public void setViewText(String viewText) {
        this.viewText = viewText;
    }

    /** 
     *            @hibernate.version
     *             column="VERSION"
     */
  	@Version
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /** 
     *            @hibernate.many-to-one
     *            @hibernate.column name="MAP_ID"           
     */
    @ManyToOne(fetch = FetchType.LAZY)
  	@JoinColumn(name = "MAP_ID")
    public com.systop.common.map.model.Map getMap() {
        return this.map;
    }

    public void setMap(com.systop.common.map.model.Map map) {
        this.map = map;
    }

    /**
     * 
     */
    public String toString() {
        return new ToStringBuilder(this)
            .append("entryId", getEntryId())
            .toString();
    }

    /**
     * 
     */
    public boolean equals(Object other) {
        if ((this == other)) {
        	return true;
        }

        if (!(other instanceof Entry)) {
        	return false;
        } 
        Entry castOther = (Entry) other;
        return new EqualsBuilder()
            .append(this.getEntryId(), castOther.getEntryId())
            .isEquals();
    }

    /***/
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getEntryId())
            .toHashCode();
    }

}