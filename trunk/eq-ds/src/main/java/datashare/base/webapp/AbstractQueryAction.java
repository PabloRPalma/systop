package datashare.base.webapp;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableConstants;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.RequestUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.AbstractCrudAction;
import com.systop.core.webapp.struts2.action.BaseAction;

/**
 * 用于查询的Action基类
 * @author Sam
 *
 * @param <T>
 */
public abstract class AbstractQueryAction<T> extends BaseAction implements ModelDriven<T> {
  /**
   * 最大行数，用于设置limit
   */
  public static final int MAX_ROWS = 10000000;
  
  protected Page getPage() {
    return new Page(Page.start(getPageNo(), getPageSize()), getPageSize());
  }
  
  /**
   * 得到由EcSide生成的page No.
   */
  protected int getPageNo() {
    HttpServletRequest request = getRequest();
    if(request == null) {
      logger.error("HttpServletRequest为null.");
      return Page.FIRST_PAGE_INDEX;
    }
    int pageNo = RequestUtils.getPageNo(request);

    return pageNo;
  }

  /**
   * 得到由EcSide生成的page size
   */
  protected int getPageSize() {
    int pageSize = RequestUtils.getCurrentRowsDisplayed(getRequest());
    int[] rowStartEnd = RequestUtils.getRowStartEnd(getRequest(), MAX_ROWS,
        Constants.DEFAULT_PAGE_SIZE);
    pageSize = rowStartEnd[1] - rowStartEnd[0];

    return pageSize;
  }
  
  /**
   * @return 返回排序字段名,如果不需要排序，则返回零长度String.
   */
  protected String getSortProperty() {
    Sort sort = getSort();
    return (sort == null) ? StringUtils.EMPTY : sort.getProperty();    
  }
  
  /**
   * @return 返回"asc"或"desc",如果不需要排序，则返回零长度String.
   */
  protected String getSortDir() {
    Sort sort = getSort();
    return (sort == null) ? StringUtils.EMPTY : sort.getSortOrder();    
  }
  /**
   * @return <code>Sort</code> of ec.
   */
  private Sort getSort() {
    Limit limit = RequestUtils.getLimit(getRequest(),
        TableConstants.EXTREME_COMPONENTS, MAX_ROWS,
        Constants.DEFAULT_PAGE_SIZE);

    return limit.getSort();
  }
  
  /**
   * 将按照ECSide的要求，将分页数据放到Request中.
   * <B>Note:</B>如果RequestHeader的Accept属性包含"x-json"，restorePageData方法不做任何操作.
   * @see {@link AbstractCrudAction#restorePageData(Page)}
   */
  protected void restorePageData(Integer totalRows, Integer pageSize) {    
    RequestUtils.initLimit(getRequest(), TableConstants.EXTREME_COMPONENTS,
        totalRows, pageSize);
  }
  
  /**
   * 如果没有查询到任何数据（比如，表名不存在等错误），设置空的分页数据到页面
   */
  protected void clean() {
    getRequest().setAttribute("items", Collections.EMPTY_LIST);
    restorePageData(0, Constants.DEFAULT_PAGE_SIZE);

  }
  
  /**
   * 得到当前登录的用户的{@link User}对象，可以从这个对象中取得它的各种属性，
   * 但是，如果这个对象是游离状态的，那么需要重新与Hibernate Session关联。
   */
  protected User getUser() {
    return UserUtil.getPrincipal(getRequest());
  }
  
}
