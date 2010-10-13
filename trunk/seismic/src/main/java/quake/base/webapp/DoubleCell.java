package quake.base.webapp;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;

import quake.base.BaseConstants;


/**
 * ECSide表格显示数字，保留两位小数
 * @author sam
 *
 */
public class DoubleCell extends AbstractCell {

  @Override
  protected String getCellValue(TableModel model, Column col) {
    Object obj = col.getPropertyValue();
    if(obj == null) {
      return  null;
    }
    String value = DisplayCell.convert(obj.toString());
    if(StringUtils.equals(BaseConstants.NULL, value)) {
      return value;
    }
    return NumberFormatUtil.format(obj, 2);
  }

}
