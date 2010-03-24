package datashare.seismic.data.catalog.webapp.cell;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepthFomat extends AbstractCell {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 地震数据中，用于代表NULL值的数据。
   */
  public static final String[] SPECIAL_STRINGS = {"-99999.0", "-99,999.0", "-99999"};
  
  /**
   * 取整数，四舍五入
   */
  @Override
  protected String getCellValue(TableModel arg0, Column col) {
    String depthStr = "";
    try {
      Double depth = (Double) col.getValue();
      depthStr = String.valueOf(Math.round(depth));
      for(int i = 0; i < SPECIAL_STRINGS.length; i++ ) {
        if(StringUtils.equals(SPECIAL_STRINGS[i], depthStr.trim())) {
          return null;
        }
      }
    } catch (Exception e) {
      logger.warn("数字格式化错误。原值可能为empty string");
    }
    return depthStr;
  }
}
