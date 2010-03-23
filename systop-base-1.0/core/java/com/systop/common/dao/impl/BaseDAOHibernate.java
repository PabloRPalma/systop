package com.systop.common.dao.impl;

/**
 * 实现基本的CRUD操作，通过泛型，子类无需扩展任何函数即拥有完整的CRUD操作.
 * @param <T> 所操作的Pojo
 * 
 * @author Sam
 * @version 1.3.0
 * @deprecated 考虑到兼容性才保留，不推荐使用，请直接使用{@link BaseGenericsDAO}
 */
public class BaseDAOHibernate<T> extends BaseGenericsDAO<T> {
}
