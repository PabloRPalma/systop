package datashare.admin.googlemap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.systop.core.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name="google_map")
public class GoogleMap extends BaseModel {

  /**
   * ID
   */
  private String id;
  
  /**
   * googleMap注册key
   */
  private String googleMapId;


  @Id
  @GeneratedValue(generator = "dsPK")
  @GenericGenerator(name = "dsPK", strategy = "assigned")
  @Column(name="ID",nullable=false)
  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  @Column(name="googlemap_id",length=255,nullable=false)
  public String getGoogleMapId() {
    return googleMapId;
  }


  public void setGoogleMapId(String googleMapId) {
    this.googleMapId = googleMapId;
  }
  
  
}
