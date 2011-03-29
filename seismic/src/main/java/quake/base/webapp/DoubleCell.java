package quake.base.webapp;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quake.base.BaseConstants;


/**
 * ECSide表格显示数字，保留两位小数
 * @author sam
 *
 */
public class DoubleCell extends AbstractCell {
  
  protected Logger logger = LoggerFactory.getLogger(getClass());
  
  /**
   * 地震数据中，用于代表NULL值的数据。
   */  
  @Override
  protected String getCellValue(TableModel model, Column col) {
    Object colName = col.getProperty();
    Object obj = col.getPropertyValue();
    //logger.debug("要转换的列名："+colName.toString());
    if(obj == null) {
      return  null;
    }
    String value = DisplayCell.convert(colName.toString(), obj.toString());
    logger.debug("转换的列的值："+ value);
    if(StringUtils.equals(BaseConstants.NULL, value)) {
      return value;
    }
    return NumberFormatUtil.format(obj, 2);
  }  
}
