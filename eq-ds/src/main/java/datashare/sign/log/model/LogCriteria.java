package datashare.sign.log.model;

import datashare.GlobalConstants;
import datashare.sign.data.model.Criteria;

/**
 * 前兆日志查询条件和前兆数据查询条件基本相同，至少组合查询目的表的规则不一样。
 * 所以在继承datashare.sign.data.model.Criteria的基础上增加getLogTableName()方法
 * 
 * @author Lunch
 * 
 */
public class LogCriteria extends Criteria {
  /**
   * 获得日志查询的表名
   * 
   * @return
   */
  public String getLogTableName() {
    return new StringBuilder(50).append(QZ_TABLE_PREFIX).append(GlobalConstants.SPLITTER).append(
        getMethodId()) // 一定要用getMethodId而不要用methodId，因为methodId可能为空
        .append(GlobalConstants.SPLITTER).append(getTableCategory()).toString();
  }

}
