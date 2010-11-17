package quake.seismic.station.model;

import quake.base.model.PageSchemaAware;

public class Loc extends PageSchemaAware {
  /**
   * 台网名称
   */
  private String netCode;

  /**
   * 台站代码
   */
  private String staCode;
  
  private String Loc_id;

  public String getNetCode() {
    return netCode;
  }

  public void setNetCode(String netCode) {
    this.netCode = netCode;
  }

  public String getStaCode() {
    return staCode;
  }

  public void setStaCode(String staCode) {
    this.staCode = staCode;
  }

  public String getLoc_id() {
    return Loc_id;
  }

  public void setLoc_id(String locId) {
    Loc_id = locId;
  }
}
