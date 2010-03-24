package datashare.seismic.data.seed.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.systop.core.model.BaseModel;

/**
 * SeedPlots  Model
 * @author dhm
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="seed_plots",uniqueConstraints={})
public class SeedPlots extends BaseModel implements Serializable{

  private Integer id;
  
  /**
   * SEED文件名
   */
  private String seedFile;
  
  /**
   * SEED图片路径
   */
  private String mapFile;
  
  /**
   * 台站中文名
   */
  private String station;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="ID",nullable=false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name="SeedFile",length=255)
  public String getSeedFile() {
    return seedFile;
  }

  public void setSeedFile(String seedFile) {
    this.seedFile = seedFile;
  }

  @Column(name="MapFile",length=255)
  public String getMapFile() {
    return mapFile;
  }

  public void setMapFile(String mapFile) {
    this.mapFile = mapFile;
  }

  @Column(name="Station",length=255)
  public String getStation() {
    return station;
  }

  public void setStation(String station) {
    this.station = station;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mapFile == null) ? 0 : mapFile.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final SeedPlots other = (SeedPlots) obj;
    if (mapFile == null) {
      if (other.mapFile != null)
        return false;
    } else if (!mapFile.equals(other.mapFile))
      return false;
    return true;
  }
  
}
