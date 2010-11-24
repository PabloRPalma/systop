package quake.seismic.data.phase.model;

import java.util.HashMap;
import java.util.Map;

import quake.base.model.PageSchemaAware;


/**
 * 震相查询条件
 * @author DU
 */
public class PhaseCriteria extends PageSchemaAware {
  /**
   * 震相表名
   */
  private String tableName;
  /**
   * 台网名称
   */
  private String netName;
  /**
   * 台站名称
   */
  private String staName;
  /**
   * 震相类型
   */
  private String phaseType;
  /**
   * 震相名称
   */
  private String phaseName;
  /**
   * 地震目录ID
   */
  private String catId;
  
  private Map<String, String> catalog = new HashMap<String, String>();

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getCatId() {
    return catId;
  }

  public void setCatId(String catId) {
    this.catId = catId;
  }

  public Map<String, String> getCatalog() {
    return catalog;
  }

  public void setCatalog(Map<String, String> catalog) {
    this.catalog = catalog;
  }

  public String getNetName() {
    return netName;
  }

  public void setNetName(String netName) {
    this.netName = netName;
  }

  public String getStaName() {
    return staName;
  }

  public void setStaName(String staName) {
    this.staName = staName;
  }

  public String getPhaseType() {
    return phaseType;
  }

  public void setPhaseType(String phaseType) {
    this.phaseType = phaseType;
  }

  public String getPhaseName() {
    return phaseName;
  }

  public void setPhaseName(String phaseName) {
    this.phaseName = phaseName;
  }
}
