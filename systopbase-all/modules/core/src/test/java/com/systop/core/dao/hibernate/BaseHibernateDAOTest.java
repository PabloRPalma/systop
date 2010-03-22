package com.systop.core.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.systop.core.BaseCoreTestCase;
import com.systop.core.dao.support.Page;
import com.systop.core.dao.testmodel.TestRole;
import com.systop.core.dao.testmodel.TestUser;

/**
 * @author Sam Lee
 *
 */

public class BaseHibernateDAOTest extends BaseCoreTestCase {
  /**
   * The BaseHibernateDao object to be tested.
   */
  private BaseHibernateDao dao;
  /**
   * @param dao the dao to set
   */
  @Autowired
  public void setDao(BaseHibernateDao dao) {
    this.dao = dao;
  }


  public BaseHibernateDao getDao() {
    return dao;
  }
  

  /**
   * Test method for {@link BaseHibernateDao#evict(Object)}.
   */
  public void testEvict() {
    TestUser user = getDao().load(TestUser.class, 1);
    getDao().evict(user);
  }

  /**
   * Test method for {@link BaseHibernateDao#query(String, Object[])}.
   */
  public void testFind() {
    assertTrue(getDao().query("from TestUser").size() > 0);
    assertTrue(getDao().query("from TestUser u where u.loginId=?", 
        "USER01").size() > 0);
    assertTrue(getDao().query("from TestUser u where u.loginId=? and u.email=?", 
        "USER01", "SAM@SYS.COM").size() > 0);
    assertTrue(getDao().query("from TestUser u where u.loginId like ?", 
        "USER%").size() > 0);
    //如果查询结果为空，则返回空List，而非null
    assertNotNull(getDao().query("from TestUser u where u.loginId = ?", 
    "USER").size() > 0);
  }

  /**
   * Test method for {@link BaseHibernateDao#findObject(String, Object[])}.
   */
  public void testFindObject() {
    TestUser u = (TestUser) getDao().findObject(
        "from TestUser u where u.loginId=? and u.email=?", "USER01",
        "SAM@SYS.COM");
    assertNotNull(u);
    u = (TestUser) getDao().findObject(
        "from TestUser u where u.loginId=? and u.email=?", "USERXX",
        "SAM@SYS.COM");
    assertNull(u);
  }

  /**
   * Test method for {@link BaseHibernateDao#get(Class)}.
   */
  public void testGetClassOfT() {
    List<TestUser> us = getDao().get(TestUser.class);
    assertTrue(us.size() > 0);
  }

  /**
   * Test method for {@link BaseHibernateDao#get(Class, java.io.Serializable)}.
   */
  public void testGetClassOfTSerializable() {
    TestUser u = getDao().get(TestUser.class, 1);
    assertNotNull(u);
    u = getDao().get(TestUser.class, 100);
    assertNull(u);
  }

  /**
   * Test method for {@link BaseHibernateDao#isAlreadyExists(Object, String[])}.
   */
  public void testIsAlreadyExists() {
    TestUser u = new TestUser();
    u.setLoginId("USER01");
    u.setEmail("SAM@SYS.COM");
    assertTrue(getDao().exists(u, new String[]{"loginId", "email"}));
    
    u = new TestUser();
    u.setLoginId("USER01");
    u.setLastLoginIp(null);
    assertTrue(getDao().exists(u, new String[]{"loginId", "lastLoginIp"}));
    
    u = new TestUser();
    u.setLoginId("USER");
    u.setEmail("SAM@SYS.COM");
    assertFalse(getDao().exists(u, new String[]{"loginId", "email"}));
  }

  /**
   * Test method for {@link BaseHibernateDao#load(Class, Serializable)}.
   */
  public void testLoad() {
    TestUser u = getDao().load(TestUser.class, 1);
    assertNotNull(u);
    try {
      TestUser ux = getDao().load(TestUser.class, 2000);
      assertNull(ux.getLoginId());
    } catch (Exception e) {
    }
  }

  /**
   * Test method for {@link BaseHibernateDao#merge(Object)}.
   */
  public void testMerge() {
    getDao().getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_AUTO);
    TestUser u = getDao().load(TestUser.class, 1);
    assertNotNull(u);
    assertEquals(u.getId(), Integer.valueOf(1));
    TestUser tu = new TestUser();
    tu.setId(Integer.valueOf(1));
    tu.setLoginId("TEST_LOGIN_ID");
    tu.setVersion(1);
    TestUser mergedU = getDao().merge(tu);
    getDao().flush();
    
    assertEquals(mergedU.getLoginId(), tu.getLoginId());
    assertTrue(mergedU.getVersion() != 1);
  }

  /**
   * Test method for {@link BaseHibernateDao#pagedQuery(DetachedCriteria, int, int)}.
   */
  public void testPagedQueryPageDetachedCriteria() {
    DetachedCriteria dc = DetachedCriteria.forClass(TestUser.class) 
    .add(Restrictions.like("loginId", "%"))
    .createCriteria("roles", "r")
    .add(Restrictions.like("name", "R%"));
    Page page = getDao().pagedQuery(new Page(Page.start(2, 5), 5), dc);
    assertTrue(page.getData().size() == 5);
    assertTrue(page.getPageNo() == 2);
    assertTrue(page.getHasNextPage());
  }

  /**
   * Test method for {@link BaseHibernateDao#pagedQuery(Criteria, int, int)}.
   */
  public void testPagedQueryPageCriteria() {
    Session s = getDao().getHibernateTemplate().getSessionFactory()
        .openSession();
    try {
      Criteria c = s.createCriteria(TestUser.class)
        .add(Restrictions.like("loginId", "%"))
        .createCriteria("roles", "r")
        .add(Restrictions.like("name", "R%"))
        .addOrder(Order.asc("name"));

      Page page = getDao().pagedQuery(new Page(Page.start(2, 5), 5), c);
      assertTrue(page.getData().size() == 5);
      assertTrue(page.getPageNo() == 2);
      assertTrue(page.getHasNextPage());
    } finally {
      s.close();
    }
  }

  /**
   * Test method for {@link BaseHibernateDao#pagedQuery(String, int, int, Object[])}.
   */
  public void testPagedQueryPageStringObjectArray() {
    Page page = getDao().pagedQuery(new Page(Page.start(2, 5), 5),
        "from TestUser tu inner join tu.roles r " +
    		"where tu.loginId like ? and r.name like ? order by tu.loginId",
    		new Object[]{"%", "%"});
    assertTrue(page.getData().size() == 5);
    assertTrue(page.getPageNo() == 2);
    assertTrue(page.getHasNextPage());
    
    page = getDao().pagedQuery(new Page(Page.start(2, 5), 5),
        "from TestUser tu where tu.loginId='cc'");
    assertTrue(page.getData().size() == 0);
    assertTrue(page.getPageNo() == 1);
    assertTrue(!page.getHasNextPage());     
    //Test for many-to-many
    TestUser tu = getDao().get(TestUser.class, 1);
    TestRole r = getDao().get(TestRole.class, 31);
    if(tu.getRoles().contains(r)) {
      tu.getRoles().remove(r);
    }
    getDao().merge(tu);
    getDao().flush();    
  }

  /**
   * Test method for {@link BaseHibernateDao#delete(Object)}.
   */
  public void testDelete() {
    TestUser tu = new TestUser();
    tu.setId(1);
    
    getDao().delete(tu);
    getDao().flush();
  }

  /**
   * Test method for {@link BaseHibernateDao#removeById(Class, Serializable)}.
   */
  public void testDeleteById() {
    getDao().delete(TestUser.class, Integer.valueOf(1));
    getDao().flush();
  }

  /**
   * Test method for {@link BaseHibernateDao#save(Object)}.
   */
  public void testSave() {
    TestUser tu = new TestUser();
    tu.setLoginId("TEST_U");
    getDao().save(tu);
    getDao().flush();
    assertNotNull(tu.getId());
    assertTrue(tu.getVersion() != null);
  }

  /**
   * Test method for {@link BaseHibernateDao#saveOrUpdate(Object)}.
   */
  public void testSaveOrUpdate() {
    //getDao().getSessionFactory().getCurrentSession().clear();
    TestUser forSave = new TestUser();
    forSave.setLoginId("TEST_U");
    getDao().saveOrUpdate(forSave);
    assertNotNull(forSave.getId());
    
    TestUser forUpdate = new TestUser();
    forUpdate.setId(10);
    forUpdate.setLoginId("TEST_U");
    forUpdate.setVersion(1);
    getDao().saveOrUpdate(forUpdate);
    getDao().flush();
    assertTrue(forUpdate.getVersion() != 1);
  }

  /**
   * Test method for {@link BaseHibernateDao#update(Object)}.
   */
  public void testUpdate() {
    TestUser forUpdate = getDao().load(TestUser.class, 12);
    forUpdate.setLoginId("TEST_U");
    getDao().update(forUpdate);    
    getDao().flush();
    assertTrue(forUpdate.getVersion() != 1);
  }
}
