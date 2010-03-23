package com.systop.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 用于基本CRUD操作的DAO接口.使用者通过泛型获取基本的CRUD功能.
 * @author Sam
 * 
 */

public interface BasicCRUD {
  /**
   * 查询指定的类别
   * 
   */
  <T> List<T> getObjects(Class<T> entityClass);

 
  /**
   * 根据id(primary key)，查询指定类的某个实例
   * @param entityClass 指定被加载的实体类型. 
   * @param id 指定id
   * @return 该类别的一个实例，如果不存在返回null.
   */
  <T> T getObject(Class<T> entityClass, Serializable id);

  /**
   * 保存或更新一个对象
   * @param o 指定的对象
   */
  <T> void saveObject(Object o);


  /**
   * 删除指定的对象
   * @param o 被删除对象
   */
  <T> void removeObject(Object o);
  
  /**
   * 根据指定的主键删除对象
   * @param entityClass 指定被删除的实体类型.
   * @param id 指定id
   */
  <T> void removeById(Class<T> entityClass, Serializable id);
  
  /**
   * 判断对象某列的值在数据库中不存在重复
   *
   * @param names 在POJO里相对应的属性名。
   * @deprecated Use {@link #isAlreadyExists(Object,String...)} instead
   */
  <T> boolean isNotUnique(Object entity, String... names);


  /**
   * 判断对象某列的值在数据库中不存在重复
   *
   * @param names 在POJO里相对应的属性名。
   */
  <T> boolean isAlreadyExists(Object entity, String... names);
}
