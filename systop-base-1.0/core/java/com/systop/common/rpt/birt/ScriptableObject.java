package com.systop.common.rpt.birt;

import java.util.List;
/**
 * BIRT使用scriptable datasource时所需的对象。
 * @author SAM
 *
 */
public class ScriptableObject {
  /**
   * 报表数据
   */
   private List dataModel;

  /**
   * @return the dataModel
   */
  public List getDataModel() {
    return dataModel;
  }

  /**
   * @param dataModel the dataModel to set
   */
  public void setDataModel(List dataModel) {
    this.dataModel = dataModel;
  }
}
