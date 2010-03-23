package com.systop.common.webapp.struts2.action;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.common.catalog.CatalogUtil;
import com.systop.common.exception.ApplicationException;
import com.systop.common.service.Manager;
import com.systop.common.util.GenericsUtil;
import com.systop.common.util.ReflectUtil;

/**
 * 支持Model驱动的Acion，通过泛型的支持，简化客户端编程(简化getModel).
 * <code>BaseModelAction</code>提供了常用的CRUD action方法的实现<br>
 * <pre>
 * list();//用于列出所有数据,数据保存在availableItems属性中。
 * query();//用于条件查询，子类必须实现setupDetachedCriteria方法，
 * //数据保存在availableItems属性中.
 * remove();//删除多个数据，被删除数据的id保存在toRemove属性中.
 * edit();//查询单个数据，id通过model传入，查询结果就保存为model.
 * save();//新增或更新model数据.
 * </pre>
 * @author Sam
 * 
 * @param <T> Model
 * @param <E> Manager

 */
public abstract class BaseModelAction<T, E extends Manager> extends
    BaseAction<E> implements ModelDriven {
  /**
   * <code>CatalogUtil</code>用于类别查询.
   */
  private CatalogUtil catalogUtil;
  /**
   * Model的Class
   */
  private Class<T> entityClass;

  /**
   * Action的Model
   */
  protected T model;

  /**
   * 用于保存查询结果集合.
   */
  protected Collection availableItems = Collections.EMPTY_LIST;

  /**
   * 用于批量删除的时候保存被删除记录的id.
   */
  protected Serializable[] selectedItems;

  /**
   * @return 泛型所指向的Class
   */
  protected final Class<T> getEntityClass() {
    if (entityClass == null) {
      entityClass = GenericsUtil.getGenericClass(getClass());
    }
    return entityClass;
  }

  /**
   * Struts ModelDriven 接口方法, 此方法由interceptor:model-driven自动调用 
   * 用于获取Model对象。
   */
  public final Object getModel() {
    if (model == null) {
      model = (T) ReflectUtil.newInstance(getEntityClass());
    }

    return model;
  }

  /**
   * @return the toRemove
   */
  public Serializable[] getSelectedItems() {
    return selectedItems;
  }

  /**
   * @param selectedItems the selected items to set
   */
  public void setSelectedItems(Serializable[] selectedItems) {
    this.selectedItems = selectedItems;
  }

  /**
   * @return the availableItems
   */
  public Collection getAvailableItems() {
    return availableItems;
  }

  /**
   * @param availableItems the availableItems to set
   */
  public void setAvailableItems(Collection availableItems) {
    this.availableItems = availableItems;
  }

  /**
   * 修改某个记录之前，必须先根据id查询该数据，然后再定位到编辑页面， 
   * <code>edit()</code> 方法实现了这个过程。
   */
  public String edit() {
    Serializable id = (Serializable) ReflectUtil.get(model, "id");
    if (id != null) {
      model = (T) getManager().get(id);
      if (log.isDebugEnabled()) {
        log.debug("Prepare editing object '" + model + "'");
      }

      return SUCCESS;
    }

    return INPUT;
  }

  /**
   * 用于保存一个对象的Action方法.
   * @return Result name of struts2.
   */
  public String save() {
    try {
      getManager().save(model);
    } catch (ApplicationException e) {
      addActionError(e.getMessage());
      return INPUT;
    }
    if (log.isDebugEnabled()) {
      log.debug("Save object '" + model + "'");
    }

    return SUCCESS;
  }

  /**
   * 用于列出所有记录的convenient方法.
   * @return the result name of struts2
   * @throws Exception throws by {@link #execute()}.
   */
  public String list() throws Exception {
    availableItems = getManager().get();
    if (availableItems != null && availableItems.size() > 0) {
      if (log.isDebugEnabled()) {
        log.debug("List all " + availableItems.size() + " items listed.");
      }
    }
    return SUCCESS;
  }

  /**
   * 用于删除多个数据的convenient方法.
   * @see {@link #selectedItems}
   * @return the result name of struts2
   */
  public String remove() {
    if (selectedItems != null) {
      for (Serializable id : selectedItems) {
        if (id != null) {
          Object object = getManager().get(Integer.valueOf(id.toString()));
          getManager().remove(object);
        }
      }

      if (log.isDebugEnabled()) {
        log.debug(selectedItems.length + " items removed.");
      }
    }

    return SUCCESS;
  }

  /**
   * 执行查询的Action方法，子类必须实现{@link #setupDetachedCriteria()}方法,
   * 或{@link #setupSQL()}和{@link retrieveQueryParameters()}方法。query()
   * 会根据setupSQL返回的sql执行查询，如果返回null，这执行setupDetachedCriteria
   * 返回的DetachedCriteria。
   */
  public String query() {
       
    if (setupSQL() != null) {
      availableItems = getManager()
        .query(setupSQL(), retrieveQueryParameters());
    } else {
      availableItems = getManager().query(setupDetachedCriteria());
    }
    
        
    return SUCCESS;
  }

  /**
   * 设置<code>DetachedCriteria</code>对象.具体的实现留给子类.
   * 子类根据自身情况，创建并设置<code>DetachedCriteria</code>对象，
   * <code>BaseModelAction</code>调用本方法。
   */
  protected DetachedCriteria setupDetachedCriteria() {
    return null;
  }
  
  /**
   * 设置查询所需的SQL，具体实现留给子类.必须和{@link #retrieveQueryParameters()}方法
   * 联合使用.
   */
  protected String setupSQL() {
    return null;
  }
  
  /**
   * 返回SQL查询所需的参数.具体实现由子类决定.通常与{@link #setupSQL()}联合使用.
   * @return Array of parameters or zero length array.
   */
  protected Object[] retrieveQueryParameters() {
	  throw new UnsupportedOperationException("You must override"
		        + " 'retrieveQueryParameters()' method.");
  }

  /**
   * @return the catalogUtil
   */
  protected CatalogUtil getCatalogUtil() {
  	if (catalogUtil == null) {
  		log.warn("CatalogUtil is null.");
  	  catalogUtil = 
  			(CatalogUtil) getApplicationContext().getBean("catalogUtil");
  	}
    return catalogUtil;
  }

  /**
   * @param catalogUtil the catalogUtil to set
   */
  public void setCatalogUtil(CatalogUtil catalogUtil) {
  	log.debug("setting catalog util.");
    this.catalogUtil = catalogUtil;
  }

}
