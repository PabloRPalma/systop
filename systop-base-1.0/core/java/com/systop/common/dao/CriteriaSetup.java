package com.systop.common.dao;

import org.hibernate.Criteria;

/**
 * 设置Criteria的接口。
 * @author Sam
 *
 */
public interface CriteriaSetup {
  /**
   * 对指定的<code>Criteria</code>进行设置.实现者通常使用内部匿名类，根据
   * 查询、排序、统计等要求对<code>Criteria</code>进行操作。调用者采用回调
   * 的方式对<code>setupCriteria</code>调用。调用之前应该创建一个基本的
   * <code>Criteria</code>对象。
   * @param criteria <code>Criteria</code> object to be setup.
   * @return The <code>Criteria</code>.
   * @deprecated
   */
  Criteria setupCriteria(Criteria criteria);
}
