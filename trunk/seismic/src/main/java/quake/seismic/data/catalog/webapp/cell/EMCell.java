package quake.seismic.data.catalog.webapp.cell;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quake.base.BaseConstants;
import quake.base.webapp.DisplayCell;
import quake.base.webapp.NumberFormatUtil;


public class EMCell extends AbstractCell {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 震级，保留一位小数
   */
  @Override
  protected String getCellValue(TableModel model, Column col) {
    Object obj = col.getPropertyValue();
    if(obj == null) {
      return  null;
    }
    String value = DisplayCell.convert(obj.toString());
    if(StringUtils.equals(BaseConstants.NULL, value) || ((Number) obj).doubleValue() < -10) {
      return BaseConstants.NULL;
    }
    return NumberFormatUtil.format(obj, 1);
  }

}
