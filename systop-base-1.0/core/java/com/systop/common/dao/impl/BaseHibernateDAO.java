package com.systop.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.systop.common.dao.CriteriaSetup;
import com.systop.common.dao.DAO;
import com.systop.common.dao.Page;
import com.systop.common.util.ReflectUtil;

/**
 * <code>DAO</code>的实现类，提供普通的CRUD操作.对返回值进行泛型处理.
 * @author Sam
 * @version 1.0
 */
@SuppressWarnings("hiding")
public class BaseHibernateDAO extends HibernateDaoSupport implements DAO {
  /**
   * Log for the class
   */
  protected Log log = LogFactory.getLog(getClass());
  /**
   * @see DAO#find(String, Object[])
   */
  public List find(String hql, Object... values) {
    if (values.length == 0) {
      return getHibernateTemplate().find(hql);
    } else {
      return getHibernateTemplate().find(hql, values);
    }
  }

  /**
   * @see DAO#findBy(Class, CriteriaSetup)
   * @deprecated
   */
  public <T> List<T> findBy(Class<T> entityClass, CriteriaSetup criteriaSetup) {
    Criteria criteria = getEntityCriteria(entityClass);
    if (criteriaSetup != null) {
      criteria = criteriaSetup.setupCriteria(criteria);
    }

    return criteria.list();
  }

  /**
   * @see DAO#findBy(Class, String, Object)
   */
  public <T> List<T> findBy(Class<T> entityClass, String name, Object value) {
    Assert.hasText(name);
    Criteria criteria = getEntityCriteria(entityClass);
    criteria.add(Restrictions.eq(name, value));
    return criteria.list();
  }

  /**
   * @see DAO#findByLike(Class, String, String)
   */
  public <T> List<T> findByLike(Class<T> entityClass, 
      String name, String value) {
    Assert.hasText(name);
    Criteria criteria = getEntityCriteria(entityClass);
    criteria.add(Restrictions.like(name, value, MatchMode.ANYWHERE));
    return criteria.list();
  }

  /**
   * @see DAO#findUniqueBy(Class, String, Object)
   */
  public <T> T findUniqueBy(Class<T> entityClass, String name, Object value) {
    Assert.hasText(name);
    Criteria criteria = getEntityCriteria(entityClass);
    criteria.add(Restrictions.eq(name, value));
    return (T) criteria.uniqueResult();
  }

  /**
   * @see DAO#pagedQuery(Class, CriteriaSetup, int, int)
   * @deprecated
   */
  public <T> Page pagedQuery(Class<T> entityClass, CriteriaSetup criteriaSetup,
      int pageNo, int pageSize) {
    Criteria criteria = getEntityCriteria(entityClass);
    if (criteriaSetup != null) {
      criteria = criteriaSetup.setupCriteria(criteria);
    }
    CriteriaImpl impl = (CriteriaImpl) criteria;
    
    return this.executePagedQuery(impl, pageNo, pageSize); 
  }
  
  /**
   * 执行Criteria分页查询.同时执行Count查询，并构建Page对象
   * @param criteria <code>Criteria</code>的实现类。
   * @param pageNo 页码
   * @param pageSize 每页的记录数
   * @return Instance of <code>Code</code>
   */
  protected Page executePagedQuery(CriteriaImpl criteria, int pageNo,
      int pageSize) {
    // 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
    Projection projection = criteria.getProjection();
    List<CriteriaImpl.OrderEntry> orderEntries;
    try {
      orderEntries = (List) ReflectUtil.getPrivateProperty(criteria,
          "orderEntries");
      ReflectUtil.setPrivateProperty(criteria, "orderEntries", new ArrayList());
    } catch (Exception e) {
      throw new InternalError(" Runtime Exception impossibility throw ");
    }

    // 执行查询
    int totalCount = (Integer) criteria.setProjection(Projections.rowCount())
        .uniqueResult();

    // 将之前的Projection和OrderBy条件重新设回去
    criteria.setProjection(projection);
    if (projection == null) {
      criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
    }
    try {
      ReflectUtil.setPrivateProperty(criteria, "orderEntries", orderEntries);
    } catch (Exception e) {
      throw new InternalError(" Runtime Exception impossibility throw ");
    }

    // 返回分页对象
    if (totalCount < 1) {
      return new Page();
    }

    int startIndex = Page.getStartOfPage(pageNo, pageSize);
    List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize)
        .list();

    return new Page(startIndex, totalCount, pageSize, list);
  }
  
  /**
   * @see {@link DAO#pagedQuery(DetachedCriteria, int, int)}
   */
  public Page pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize) {
    Criteria c = criteria.getExecutableCriteria(getSession());
    CriteriaImpl impl = (CriteriaImpl) c;
    
    return this.executePagedQuery(impl, pageNo, pageSize); 
  }

  /**
   * @see DAO#pagedQuery(String, int, int, Object[])
   */
  public Page pagedQuery(String hql, int pageNo, int pageSize, Object... args) {
    Assert.hasText(hql);
    //创建查询
    Query query = getSession().createQuery(hql);
    for (int i = 0; i < args.length; i++) {
        query.setParameter(i, args[i]);
    }
    String countQueryString = " select count (*) " 
      + removeSelect(removeOrders(removeFetchs(hql)));
    List countlist = getHibernateTemplate().find(countQueryString, args);
    int totalCount = ((Long) countlist.get(0)).intValue();

    if (totalCount < 1) {
      return new Page();
    }
    int startIndex = Page.getStartOfPage(pageNo, pageSize);
    List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

    return new Page(startIndex, totalCount, pageSize, list);
  }

  // Next section is basic CRUD methods.

  /**
   * @see BasicCRUD#getObject(Class, java.io.Serializable)
   */
  public <T> T getObject(Class<T> entityClass, Serializable id) {
    return (T) getHibernateTemplate().get(entityClass, id);
  }

  /**
   * @see BasicCRUD#getObjects()
   */
  public <T> List<T> getObjects(Class<T> entityClass) {
    return getHibernateTemplate().loadAll(entityClass);
  }

  /**
   * 取得Entity的Criteria.
   */
  protected <T> Criteria getEntityCriteria(Class<T> entityClass) {
    return getSession().createCriteria(entityClass);
  }

  /**
   * 判断一个对象中的某些字段是否存在重复，通常用于在save之前对某些字段进行判断，例如，
   * email，username等.已经区分了update和insert的情况,也就是说如果是update，则 排除自身.
   * @see BasicCRUD#isNotUnique(Object, String)
   * @deprecated Use {@link #isAlreadyExists(Object,String...)} instead
   */
  public <T> boolean isNotUnique(Object entity, String... names) {
    return isAlreadyExists(entity, names);
  }

  /**
   * 判断一个对象中的某些字段是否存在重复，通常用于在save之前对某些字段进行判断，例如，
   * email，username等.已经区分了update和insert的情况,也就是说如果是update，则 排除自身.
   * @see BasicCRUD#exists(Object, String)
   */
  public <T> boolean isAlreadyExists(Object entity, String... names) {
    Assert.notEmpty(names);
    Criteria criteria = getEntityCriteria(entity.getClass()).setProjection(
        Projections.rowCount());

    try {
      // 循环加入
      for (String name : names) {
        criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity,
            name)));
      }

      // 以下代码为了如果是update的情况,排除entity自身.
      // 通过Hibernate的MetaData接口取得主键名
      String idPropertyName = getSessionFactory().getClassMetadata(
          entity.getClass()).getIdentifierPropertyName();
      if (idPropertyName != null) {
        // 通过反射取得entity的主键值
        Object id = PropertyUtils.getProperty(entity, idPropertyName);
        // 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
        if (id != null) {
          criteria.add(Restrictions.not(Restrictions.eq(idPropertyName, id)));
        }
      }

    } catch (IllegalAccessException e) {
      logger.error("Error when reflection on entity," + e.getMessage());
      return false;
    } catch (InvocationTargetException e) {
      logger.error("Error when reflection on entity," + e.getMessage());
      return false;
    } catch (NoSuchMethodException e) {
      logger.error("Error when reflection on entity," + e.getMessage());
      return false;
    }
    return (Integer) criteria.uniqueResult() > 0;
  }

  /**
   * @see BasicCRUD#removeById(Class, java.io.Serializable)
   */
  public <T> void removeById(Class<T> entityClass, Serializable id) {
    removeObject(getObject(entityClass, id));
  }

  /**
   * @see BasicCRUD#removeObject(Object)
   */
  public <T> void removeObject(Object o) {
    getHibernateTemplate().delete(o);
  }

  /**
   * @see BasicCRUD#saveObject(Object)
   */
  public <T> void saveObject(Object o) {
    getHibernateTemplate().saveOrUpdate(o);
  }

  /**
   * 去除hql的select 子句，未考虑union的情况
   */
  private static String removeSelect(String hql) {
      Assert.hasText(hql);
      int beginPos = hql.toLowerCase().indexOf("from");
      Assert.isTrue(beginPos != -1, " hql : " 
          + hql + " must has a keyword 'from'");
      return hql.substring(beginPos);
  }

  /**
   * 去除hql的orderby 子句
   */
  private static String removeOrders(String hql) {
      Assert.hasText(hql);
      Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 
          Pattern.CASE_INSENSITIVE);
      Matcher m = p.matcher(hql);
      StringBuffer sb = new StringBuffer();
      while (m.find()) {
          m.appendReplacement(sb, "");
      }
      m.appendTail(sb);
      return sb.toString();
  }
  /**
   * 删除HSQL中的fetch
   */
  private static String removeFetchs(String hql) {
    Assert.hasText(hql);
    return hql.replaceAll("fetch ", "");
  }
}
