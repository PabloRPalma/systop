package quake.base.webapp;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;
import org.ecside.util.ExtremeUtils;

import quake.base.BaseConstants;


public class NumberCell extends AbstractCell {
  protected String getCellValue(TableModel model, Column column) {
      String value = DisplayCell.convert(column.getPropertyValueAsString());
      if (StringUtils.isNotBlank(value) && !StringUtils.equals(BaseConstants.NULL, value)) {
          Locale locale = model.getLocale();
          value = ExtremeUtils.formatNumber(column.getFormat(), value, locale);
      }

      return value;
  }
}

