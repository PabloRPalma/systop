package com.systop.common.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;

import com.systop.common.dao.CriteriaSetup;
import com.systop.common.dao.GenericsDAO;
import com.systop.common.dao.Page;
import com.systop.common.util.GenericsUtil;

/**
 * 泛型DAO实现类，用于管理单个实体对象.
 * @see {@link GenericsDAO}
 * @author Sam
 * @param <T>
 * @version 1.0
 */
public class BaseGenericsDAO<T> 
  extends BaseHibernateDAO implements GenericsDAO<T> {
  /**
   * Dao所管理的Entity类型.
   */
  protected Class<T> entityClass;

  /**
   * 取得entityClass的函数.
   * JDK1.4不支持泛型的子类可以抛开Class entityClass,重载此函数达到相同效果。
   */
  protected Class<T> getEntityClass() {
    if (entityClass == null) {
      entityClass = GenericsUtil.getGenericClass(getClass());
    }
    return entityClass;
  }
  
  /**
   * 返回Dao所管理的Entity的Criteria对象. 
   */
  protected Criteria getEntityCriteria() {
    return getEntityCriteria(getEntityClass());
  }
  
  /**
   * 在构造函数中将泛型T.class赋给entityClass
   */
  public BaseGenericsDAO() {
      entityClass = GenericsUtil.getGenericClass(getClass());
  }
  
  /**
   * @see GenericsDAO#findBy(CriteriaSetup)
   * @deprecated
   */
  public List<T> findBy(CriteriaSetup criteriaSetup) {
    return findBy(getEntityClass(), criteriaSetup);
  }

  /**
   * @see GenericsDAO#findBy(String, Object)
   */
  public List<T> findBy(String name, Object value) {
    return findBy(getEntityClass(), name, value);
  }

  /**
   * @see GenericsDAO#findByLike(String, String)
   */
  public List<T> findByLike(String name, String value) {
    return findByLike(getEntityClass(), name, value);
  }

  /**
   * @see GenericsDAO#findUniqueBy(String, Object)
   */
  public T findUniqueBy(String name, Object value) {
    return findUniqueBy(getEntityClass(), name, value);
  }

  /**
   * @see GenericsDAO#pagedQuery(CriteriaSetup, int, int)
   * @deprecated
   */
  public Page pagedQuery(CriteriaSetup criteriaSetup, int pageNo,
      int pageSize) {
    return pagedQuery(getEntityClass(), criteriaSetup, pageNo, pageSize);
  }

  /**
   * @see GenericsDAO#getObject(java.io.Serializable)
   */
  public T getObject(Serializable id) {
    return getObject(getEntityClass(), id);
  }

  /**
   * @see GenericsDAO#getObjects()
   */
  public List<T> getObjects() {
    return getObjects(getEntityClass());
  }

  /**
   * @see GenericsDAO#removeById(java.io.Serializable)
   */
  public void removeById(Serializable id) {
    removeById(getEntityClass(), id);
  }
}
