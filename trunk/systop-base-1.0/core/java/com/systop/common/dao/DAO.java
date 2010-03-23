package com.systop.common.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;


/**
 * 用于访问数据库的接口，DAO层通过本接口提供服务.DAO接口扩展了<code>BasicCRUD</code>
 * 接口，提供了一系列用于查询和分页查询的方法.
 * @author Sam
 * 
 * @version 1.5.0
 */

public interface DAO extends BasicCRUD {
  /**
   * 执行包含多个条件的SQL查询（变长参数）.
   * 
   * @param hql 包含条件(x=?)的SQL
   * @param values 各个条件的预期值
   * @return  包含查询结果Pojo的List，或EMPTY_LIST
   */
  List find(String hql, Object... values);
  
  /**
   * 根据某个字段和预期值，查询单个对象
   * @param entityClass 实体对象对应的Class对象，Hibernate用于创建Criteria
   * @param name 字段名称
   * @param value 预期值
   * @return 所指定类别的对象
   */

  <T> T findUniqueBy(Class<T> entityClass, String name, Object value);

  /**
   * 根据某个字段和预期值，查询多个对象
   * @param entityClass 实体对象对应的Class对象，Hibernate用于创建Criteria
   * @param name 字段名称
   * @param value 预期值
   * @return 包含查询结果Pojo的List，或EMPTY_LIST
   */
  <T> List<T> findBy(Class<T> entityClass, String name, Object value);
  
  /**
   * 根据某个字段和预期值,执行Like查询，返回多个对象
   * @param entityClass 实体对象对应的Class对象，Hibernate用于创建Criteria
   * @param name 字段名称
   * @param value 预期值
   * @return 包含查询结果Pojo的List，或EMPTY_LIST
   */
  <T> List<T> findByLike(Class<T> entityClass, String name, String value);
  
  /**
   * 根据<code>CriteriaSetup</code>接口执行Criteria查询.
   * 调用者通常采用内部匿名类实现<code>CriteriaSetup</code>接口.
   * {@link #findBy(CriteriaSetup)}的常见用法：<br/>
   * <pre>
   * List list = findyBy(new CriteriaSetup() {
   *     Criteria setupCriteria(Criteria criteria) {
   *       criteria.add(Restriction.eq("name", name);
   *       //...
   *       return criteria;
   *     }
   *   }
   * )
   * </pre>
   * @param entityClass 实体对象对应的Class对象，Hibernate用于创建Criteria
   * @param criteriaSetup criteriaSetup设置器,如果为null，调用
   * 缺省的实现。
   * @return List of T
   * @deprecated please use 
   * <code>getHibernateTemplate().findByCriteria(DetachedCriteria)</code>
   * instead.
   */
  <T> List<T> findBy(Class<T> entityClass, CriteriaSetup criteriaSetup);

  /**
   * Criteria分页查询
   * @param entityClass 实体对象对应的Class对象，Hibernate用于创建Criteria
   * @param criteriaSetup 用于设置查询条件.
   * @param pageNo 当前页码
   * @param pageSize 每页显示记录数.
   * @deprecated please use 
   * <code>Page pagedQuery(DetachedCriteria,int ,int)</code>
   * instead.
   * 
   */
  <T> Page pagedQuery(Class<T> entityClass, CriteriaSetup criteriaSetup, 
      int pageNo, int pageSize);
  /**
   * Criteria分页查询.子类类需要实现执行count查询的逻辑.
   * 
   * @param criteria <code>DetachedCriteria</code>对象.
   * @param pageNo 当前页码
   * @param pageSize 每页显示记录数.
   * @return Page object.
   */
  Page pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize);
  /**
   * HQL分页查询
   * @param hql 查询hql
   * @param pageNo 当前页码
   * @param pageSize 每页显示记录数.
   * @param args 查询参数
   * @return Page
   */
  Page pagedQuery(String hql, int pageNo, int pageSize,
      Object... args);
}
