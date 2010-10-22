package com.systop.cms.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name="softcatas")
public class SoftCatas extends BaseModel {

  /**Id**/
  private Integer id;
  
  /**软件类别名称**/
  private String name;
  
  /**软件类别描述**/
  private String description;
  
  /**包含软件**/
  private Set<Software> softs;

  
  public SoftCatas() {
  }

  public SoftCatas(Integer id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  @Id
  @GeneratedValue(generator="hibseq")
  @GenericGenerator(name="hibseq",strategy="hilo")
  @Column(name="ID",unique=true,nullable=false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name="NAME",length=255,nullable=false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name="DESCRIPTION",length=2000)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="softCatalog")
  public Set<Software> getSofts() {
    return softs;
  }

  public void setSofts(Set<Software> softs) {
    this.softs = softs;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final SoftCatas other = (SoftCatas) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
