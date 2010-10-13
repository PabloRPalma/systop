package quake;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;

/**
 * 地震数据项目全局常量
 * @author Sam
 *
 */
public final class GlobalConstants {
  /**
   * 分割符号，用于表名等名称的构造
   */
  public static final String SPLITTER = "_";
  
  public static final int MAX_RESULTS = DefaultCrudAction.MAX_ROWS;
  
  public static final String ORDER_ASC = "ASC";
  
  public static final String ORDER_DESC = "DESC";
  /**
   * 防止实例化
   */
  private GlobalConstants() {    
  }
}
