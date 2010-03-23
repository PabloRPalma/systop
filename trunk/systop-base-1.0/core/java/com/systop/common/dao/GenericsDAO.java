package com.systop.common.dao;

import java.io.Serializable;
import java.util.List;
/**
 * 通过泛型的方式，提供对单个Entity对象的管理.子类只需要声明
 * 父类的泛型对象，即可获得该对象的dao方法.
 * 
 * @author Sam
 * @see {@link DAO}
 * @param <T>
 */

public interface GenericsDAO<T> extends DAO {
  /**
   * 根据id，查询Dao所管理的对象.
   * @return instance of T, 如果不存在，返回null
   */
  T getObject(Serializable id);
  
  /**
   * @return 返回Dao所管理的全部对象. 如果不存在，返回Empty Collection.
   */
  List<T> getObjects();
  
  /**
   * 根据Id，删除单个对象.
   */
  void removeById(Serializable id);
  /**
   * 返回泛型所代表的单个实例. 
   * 
   * @see {@link DAO#findUniqueBy(String, Object)}
   */
  T findUniqueBy(String name, Object value);

  /**
   * 执行Criteria查询.Criteria对象根据泛型类型创建.
   * @see {@link DAO#findBy(Class, String, Object)}
   */
  List<T> findBy(String name, Object value);
  
  /**
   * 执行Criteria like查询.Criteria对象根据泛型类型创建.
   * @see {@link DAO#findByLike(Class, String, String)}
   */
  List<T> findByLike(String name, String value);
  
  /**
   * Criteria查询.Criteria对象根据泛型类型创建.并通过回调，调用
   * CriteriaSetup接口进行设置.
   * @see {@link DAO#findBy(Class, CriteriaSetup)}
   */
  List<T> findBy(CriteriaSetup criteriaSetup);
  
  /**
   *  执行Criteria分页查询.Criteria对象根据泛型类型创建.
   *  @see {@link DAO#pagedQuery(Class, CriteriaSetup, int, int)}
   */
  Page pagedQuery(CriteriaSetup criteriaSetup, int pageNo, int pageSize);
}
