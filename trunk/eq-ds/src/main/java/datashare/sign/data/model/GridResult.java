package datashare.sign.data.model;

import java.util.List;

import datashare.base.model.PageSchemaAware;


/**
 * 当分页数据查询，并用于表格显示的时候，使用<code>GridResult</code>
 * 作为查询结果.
 * @author Sam
 *
 */
public class GridResult extends PageSchemaAware {
  
  /**
   * 根据查询结果加工后的数据
   */
  private List<DataSeries> dataSeries;
  /**
   * 采样率，用于EC表格动态计算时间格式
   */
  private String sampleRate;
  
  /**
   * 台站名称
   */
  private String stationName;
  
  /**
   * 测项分量名称
   */
  private String itemName;
  
  /**
   * 默认构造
   */
  public GridResult(){
    
  }
  
  /**
   * 默认构造
   */
  public GridResult(List<DataSeries> dataSeries){
    this.dataSeries = dataSeries;
  }
    
    
  /**
   * @return the dataSeries
   */
  public List<DataSeries> getDataSeries() {
    return dataSeries;
  }
  /**
   * @param dataSeries the dataSeries to set
   */
  public void setDataSeries(List<DataSeries> dataSeries) {
    this.dataSeries = dataSeries;
  }
  /**
   * @return the sampleRate
   */
  public String getSampleRate() {
    return sampleRate;
  }
  /**
   * @param sampleRate the sampleRate to set
   */
  public void setSampleRate(String sampleRate) {
    this.sampleRate = sampleRate;
  }
  /**
   * @return the stationName
   */
  public String getStationName() {
    return stationName;
  }
  /**
   * @param stationName the stationName to set
   */
  public void setStationName(String stationName) {
    this.stationName = stationName;
  }
  /**
   * @return the itemName
   */
  public String getItemName() {
    return itemName;
  }
  /**
   * @param itemName the itemName to set
   */
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }
}
