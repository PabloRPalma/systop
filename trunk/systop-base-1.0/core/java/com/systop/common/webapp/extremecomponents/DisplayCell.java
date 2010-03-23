package com.systop.common.webapp.extremecomponents;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import com.systop.common.util.StringUtil;

/**
 * 解决null对象的显示问题。
 * @author Sam
 *
 */
public final class DisplayCell extends AbstractCell {
  /**
   * @see AbstractCell#getExportDisplay(TableModel, Column)
   */
  public String getExportDisplay(TableModel model, Column column) {
      return column.getPropertyValueAsString();
  }
  
  /**
   * 取得Cell Value，如果是null，则输出&nbsp;
   */
  protected String getCellValue(TableModel model, Column column) {
    String str = column.getValueAsString();
    if (column.getValue() == null || "null".equals(str)) {
      return "&nbsp;";
    }
    return StringUtil.stripTags(str);
  }
}
