package com.systop.modules.admin.security.rbac.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.test.BaseTransactionalTestCase;

/**
 * model测试类
 *
 */
public class ModelTest extends BaseTransactionalTestCase {

  private BaseHibernateDao dao;
  
  private BaseHibernateDao getDao() {
      return dao;
  }
  
  @Autowired
  public void setBaseHibernateDao(BaseHibernateDao dao) {
    this.dao = dao;
  }
  
  /**
   * test method for <code>User</code>
   */
  public void testUser() {
    getDao().query("from User u");
  }
  
  /**
   * test method for <code>Employee</code>
   */
  public void testEmployee() {
    getDao().query("from Employee e");
  }
  
  /**
   * test method for <code>Dept</code>
   */
  public void testDept() {
    getDao().query("from Dept d");
  }
  
  /**
   * test method for <code>Role</code>
   */
  public void testRole() {
    getDao().query("from Role r");
  }
  
  /**
   * test method for <code>Resource</code>
   */
  public void testResource() {
    getDao().query("from Resource r");
  }
}
