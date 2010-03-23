package com.systop.common.webapp.extremecomponents;

import org.extremecomponents.table.limit.Sort;
import org.hibernate.criterion.DetachedCriteria;

import com.systop.common.dao.Page;
import com.systop.common.service.Manager;
import com.systop.common.webapp.struts2.action.BasePagedQueryAction;

/**
 * 用于ExtremeComponents Limit查询的struts2 Action
 * extremetable taglib 必须设置下面的属性：<br><pre>
 * retrieveRowsCallback="limit"
   sortRowsCallback="limit"
   </pre>
 * @author Sam
 * 
 * @param <T> model
 * @param <E> Manager
 */
public abstract class BaseECLimitQueryAction<T, E extends Manager> extends
    BasePagedQueryAction<T, E> {
  /**
   * ExtremeComponents读取的数据在Request中的name
   */
  public static final String EC_DATA_NAME = "items";

  /**
   * 总记录数在Request中的name
   */
  public static final String EC_TOTAL_ROWS_NAME = "totalRows";

  /**
   * @see BasePagedQueryAction#getPageNo()
   */
  @Override
  protected final int getPageNo() {
    return LimitUtil.getPageNo(getHttpServletRequest());
  }

  /**
   * @see BasePagedQueryAction#getPageSize()
   */
  @Override
  protected final int getPageSize() {
    return LimitUtil.getLimit(getHttpServletRequest())
        .getCurrentRowsDisplayed();
  }

  /**
   * 如果子类所使用的名称有别，可以覆盖.
   * @see BasePagedQueryAction#restorePageData()
   */
  @Override
  protected void restorePageData() {
    if (getPage() != null) {
      addObject(EC_DATA_NAME, getPage().getResult());
      addObject(EC_TOTAL_ROWS_NAME, getPage().getTotalCount());
    }
  }

  /**
   * 从extremeTable的Order属性中取得排序信息，并用于设置DetachedCriteria对象
   * @param criteria 被设置的DetachedCriteria对象.
   * @return 设置之后的DetachedCriteria对象.
   */
  protected DetachedCriteria setupSort(DetachedCriteria criteria) {
    Sort sort = getSort();
    if (sort != null && criteria != null) {
      LimitUtil.setSort(criteria, sort.getProperty(), sort);
    }

    return criteria;
  }
  /**
   * 返回EC的Sort对象
   */
  protected final Sort getSort() {
    return LimitUtil.getLimit(getHttpServletRequest()).getSort();
  }
  /**
   * 执行分页查询的Action方法.
   * @return
   */
  @Override
  public String pageQuery() {
    DetachedCriteria criteria = null;
    // 设置查询条件
    try {
      criteria = setupDetachedCriteria();
    } catch (Exception e) {
      log.info(e.getMessage());
    }
    
    Page page = null;
    if (criteria == null) {
      page = getManager().query(setupSQL(), getPageNo(), getPageSize(),
          retrieveQueryParameters());
    } else { //如果不使用Criteria查询，则使用HQL查询.
      page = getManager().query(criteria, getPageNo(), getPageSize());
    }
    
    setPage(page);

    restorePageData(); // 重新将分页查询结果放回到页面
    
    return SUCCESS;
  }
}
