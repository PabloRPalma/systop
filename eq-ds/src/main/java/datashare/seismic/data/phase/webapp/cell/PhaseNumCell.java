package datashare.seismic.data.phase.webapp.cell;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;

import datashare.base.BaseConstants;
import datashare.base.webapp.DisplayCell;
import datashare.base.webapp.NumberFormatUtil;

/**
 * 震相中振幅数据，保留三位小数
 * @author DU
 *
 */
public class PhaseNumCell extends AbstractCell{

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
    return NumberFormatUtil.format(obj, 3);
  }
}
