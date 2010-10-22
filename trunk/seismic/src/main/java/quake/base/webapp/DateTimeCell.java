package quake.base.webapp;

import java.util.Date;

import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;

import com.systop.core.util.DateUtil;

/**
 * 根据采样率转换采样时间
 * @author Sam
 *
 */
public class DateTimeCell extends AbstractCell {

  @Override
  protected String getCellValue(TableModel model, Column col) {
    Date date = (Date) col.getPropertyValue();
    if(date == null) {
      return null;
    }
    return DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", date);
    
  }
 
}
