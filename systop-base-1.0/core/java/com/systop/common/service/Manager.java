package com.systop.common.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.systop.common.dao.Page;



/**
 * 所有Service层的基础接口.提供基于泛型的CRUD功能。
 * @author Sam
 * @param <T>
 * @version 2.0
 */
public interface Manager<T> {
  /**
   * 判断一个实体对象的某几个字段值是否在数据库中存在
   * @param entity 给定的实体对象
   * @param propertyNames 字段名称s
   * @return 如果已经存在，返回true，否则，false
   * @see {@link 
   *   com.systop.common.dao.BasicCRUD#isAlreadyExists(Object, String[])}
   */
  boolean exists(T entity, String... propertyNames);
  /**
   * 返回泛型参数所指出的类别全部对象
   */
  List<T> get();
  
  /**
   * 根据id(prmary key),得到泛型参数所指定的类别的某个对象
   * @param id 给出的id
   * @return id和T所指定的对象，如果不存在，返回null
   */
  T get(Serializable id);
  
  /**
   * 保存或更新泛型参数指定的对象
   * @param object 被保存或更新的对象
   */
  void save(T object);
  
  /**
   * 删除泛型参数指定的对象
   * @param object 被删除的对象
   */
  void remove(T object);
  
  /**
   * 执行Criteria查询.
   * @param criteria 给定的<code>DetachedCriteria</code>对象.
   * @return 查询结果
   */
  List query(DetachedCriteria criteria);
  
  /**
   * 执行HQL查询
   * @param hql 给出HQL
   * @param objects 所需的查询参数.
   * @return 查询结果.
   */
  List query(String hql, Object ...objects);
  
  /**
   * 执行DetachedCriteria分页查询
   * @param criteria 给定的<code>DetachedCriteria</code>对象.
   * @param pageNo 页面
   * @param pageSize 每页多数记录.
   * @return 查询结果，包括总记录数和查询数据.
   * @see {@link Page}
   */
  Page query(DetachedCriteria criteria, int pageNo, int pageSize);
  
  /**
   * 执行HQL分页查询.
   * @param hql 给出查询所需的HSQL
   * @param pageNo 页码
   * @param pageSize 每页最多多少记录
   * @param objects 查询参数
   * @return 查询结果，包括总记录数和查询数据.
   * @see {@link Page}
   */
  Page query(String hql, int pageNo, int pageSize, Object ...objects);
}
