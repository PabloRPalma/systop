package com.systop.common.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.systop.common.dao.Page;
import com.systop.common.dao.impl.BaseGenericsDAO;

/**
 * 利用BaseDAOHibernate实现Manager接口
 * 
 * @author Sam
 * 
 * @param <T>
 */
public class BaseManager<T> extends BaseGenericsDAO<T> implements Manager<T> {
	/**
	 * @see Manager#query(org.hibernate.criterion.DetachedCriteria, int, int)
	 */
	public Page query(DetachedCriteria criteria, int pageNo, int pageSize) {
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * @see Manager#query(java.lang.String, int, int, java.lang.Object[])
	 */
	public Page query(String hql, int pageNo, int pageSize, Object... objects) {
		return pagedQuery(hql, pageNo, pageSize, objects);
	}

	/**
	 * @see Manager#query(org.hibernate.criterion.DetachedCriteria)
	 */
	public List query(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * Log for class.
	 */
	protected Log log = LogFactory.getLog(getClass());

	/**
	 * @see Manager#get()
	 */
	public List<T> get() {
		return getObjects();
	}

	/**
	 * @see Manager#get(java.io.Serializable)
	 */
	public T get(Serializable id) {
		return getObject(id);
	}

	/**
	 * @see Manager#remove(java.lang.Object)
	 */
	public void remove(T object) {
		removeObject(object);
	}

	/**
	 * @see Manager#save(java.lang.Object)
	 */
	public void save(T object) {
		saveObject(object);
	}
  
  /**
   * @see {@link Manager#query(String, Object[])}
   */
  public List query(String hql, Object... objects) {
    return find(hql, objects);
  }
  /**
   * @see {@link Manager#exists(Object, String[])}
   */
  public boolean exists(T entity, String... propertyNames) {
    return isAlreadyExists(entity, propertyNames);
  }

  
}
