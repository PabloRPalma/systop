package quake.base.webapp;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.Cell;
import org.ecside.util.ECSideUtils;
import org.ecside.view.html.ColumnBuilder;

/**
 * 替换Ecside缺省的DisplayCell,用于将-99999.0转换为null,因为这样的数据非常多，所以
 * 这里采用直接替换DisplayCell的方式。
 * @author sam
 *
 */
public class DisplayCell implements Cell {
    
  /**
   * 地震数据中，用于代表NULL值的数据。
   */
  public static final String[] SPECIAL_STRINGS = {"-99999.0", "-99,999.0", "-99999", "-99.9", "-999.9", "-99.0", "-999.0", "-9999.0","-99", "-999", "-9999"};
  public static final String[] SPECIAL_STRINGS_LON = {"-99999.0", "-99,999.0", "-99999", "-999.9", "-999.0", "-9999.0", "-999", "-9999"};
  
  public String getHtmlDisplay(TableModel model, Column column) {
    ColumnBuilder columnBuilder = new ColumnBuilder(column);
    columnBuilder.tdStart();
    columnBuilder.tdBody(getCellValue(model, column));
    columnBuilder.tdEnd();
    return columnBuilder.toString();
  }

  public String getExportDisplay(TableModel model, Column column) {
    return convert(column.getProperty().toString(), column.getPropertyValueAsString());
  }

  protected String getCellValue(TableModel model, Column column) {
    boolean useEllipsis = column.getEllipsis() == null ? false : column.getEllipsis()
        .booleanValue();
    String value = convert(column.getProperty().toString(), column.getValueAsString());
    
    if (useEllipsis) {
      StringBuffer cellHtml = new StringBuffer();

      cellHtml.append("<div class=\"ellipsis\" ");
      String width = column.getWidth();
      if (StringUtils.isNotBlank(width) && !width.endsWith("%")) {
        cellHtml.append(" style=\"width:").append(width).append("px\" ");
      }
      cellHtml.append("title=\"").append(ECSideUtils.HTMLToTEXT(value)).append(
          "\" >");
      cellHtml.append(value);
      cellHtml.append("</div>");
      return cellHtml.toString();
    }
    return value;
  }
  
 public static String convert(String name, String value) {
    if(value == null) {
      return value;
    }
    if ("EPI_LAT".equals(name)) {
      for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
        if(StringUtils.equals(SPECIAL_STRINGS[i], value.trim())) {
          return null;
        }
      }
    }else if ("EPI_LON".equals(name)) {
      for(int i = 0; i < SPECIAL_STRINGS_LON.length; i++ ) {
        if(StringUtils.equals(SPECIAL_STRINGS_LON[i], value.trim())) {
          return null;
        }
      }
    } else {
      for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
        if(StringUtils.equals(SPECIAL_STRINGS[i], value.trim())) {
          return null;
        }
      }
    }
    return value;
  }
}
