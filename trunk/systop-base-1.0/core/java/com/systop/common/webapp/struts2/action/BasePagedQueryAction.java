package com.systop.common.webapp.struts2.action;

import org.hibernate.criterion.DetachedCriteria;

import com.systop.common.Constants;
import com.systop.common.dao.Page;
import com.systop.common.service.Manager;

/**
 * 用于分页查询的Action，继承<code>BaseModelAction</code>可以 获得基本的CURD action方法。
 * @author Sam
 * 
 * @param <T> model
 * @param <E> Manager
 */
public abstract class BasePagedQueryAction<T, E extends Manager> extends
    BaseModelAction<T, E> {
  /**
   * 包含分页数据的<code>Page</code>对象
   */
  private Page page;

  /**
   * @return the page
   */
  public Page getPage() {
    return page;
  }

  /**
   * @param page the page to set
   */
  public void setPage(Page page) {
    this.page = page;
  }

  /**
   * 执行分页查询的Action方法.
   * @return
   */
  public String pageQuery() {
    DetachedCriteria criteria = null;
    // 设置查询条件
    try {
      criteria = setupDetachedCriteria();
    } catch (Exception e) {
      log.info(e.getMessage());
    }
 
    if (criteria == null) {
      page = getManager().query(setupSQL(), getPageNo(), getPageSize(),
          retrieveQueryParameters());
    } else { //如果不使用Criteria查询，则使用HQL查询.
      page = getManager().query(criteria, getPageNo(), getPageSize());
    }

    restorePageData(); // 重新将分页查询结果放回到页面
    
    return SUCCESS;
  }

  /**
   * 返回分页容量，缺省的是定义在application.properties中.子类可以覆盖， 以获取实际的分页容量.
   */
  protected int getPageSize() {
    return Constants.DEFAULT_PAGE_SIZE;
  }

  /**
   * 返回当前页码.子类必须实现本方法，以确定当前页数.
   */
  protected abstract int getPageNo();

  /**
   * 将分页数据重新设置到页面.
   */
  protected void restorePageData() {
    // do nothing
  }
}
