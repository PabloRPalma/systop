package agileweb.support.et;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.limit.Limit;
import org.extremecomponents.table.limit.LimitFactory;
import org.extremecomponents.table.limit.Sort;
import org.extremecomponents.table.limit.TableLimit;
import org.extremecomponents.table.limit.TableLimitFactory;

import agileweb.Constants;

/**
 * 辅助ExtremeTable获取分页信息的Util类
 * @author Sam
 * 
 */
@SuppressWarnings("unchecked")
public final class LimitUtil {
  /**
   * 最大行数，用于设置limit
   */
  public static final int MAX_ROWS = 1000000000;

  /**
   * private constructor
   */
  private LimitUtil() {
  }

  /**
   * 返回Limit，用缺省的pageSize
   * @see {@link #getLimit(HttpServletRequest, int)}
   */
  static public Limit getLimit(HttpServletRequest request) {
    return getLimit(request, Constants.DEFAULT_PAGE_SIZE);
  }

  /**
   * 从request构造Limit对象实例.
   */
  public static Limit getLimit(HttpServletRequest request, int defautPageSize) {
    LimitFactory limitFactory = getLimitFactory(request);
    TableLimit limit = new TableLimit(limitFactory);
    // 先给出一个“假的”totalRows，等真的出来了，在弄回去
    limit.setRowAttributes(MAX_ROWS, defautPageSize);
    return limit;
  }

  /**
   * request中取得分页页码
   */
  public static int getPageNo(HttpServletRequest request) {
    return getLimitFactory(request).getPage();
  }

  /**
   * 从Request中提取limitFactory
   */
  public static LimitFactory getLimitFactory(HttpServletRequest request) {
    // 将request中的参数转换到extremetable的Context
    Context context = new HttpServletRequestContext(request);
    return new TableLimitFactory(context);
  }

  /**
   * 将Limit中的排序信息转化为Map{columnName,升序/降序}
   */
  public static Map getSort(Limit limit) {
    Map sortMap = new HashMap();
    if (limit != null) {
      Sort sort = limit.getSort();
      if (sort != null && sort.isSorted()) {
        sortMap.put(sort.getProperty(), sort.getSortOrder());
      }
    }
    return sortMap;
  }

  /**
   * 从ServletRequest中获取排序条件，并以Map{columnName,升序/降序}返回
   * @param request
   * @return
   */
  public static Map getSort(HttpServletRequest request) {
    Sort sort = getLimitFactory(request).getSort();
    Map sortMap = new HashMap();
    if (sort != null && sort.isSorted()) {
      sortMap.put(sort.getProperty(), sort.getSortOrder());
    }
    return sortMap;
  }
}
