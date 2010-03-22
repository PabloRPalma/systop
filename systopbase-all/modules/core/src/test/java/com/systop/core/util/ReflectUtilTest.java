package com.systop.core.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.systop.core.dao.testmodel.TestDept;
import com.systop.core.dao.testmodel.TestEmployee;
import com.systop.core.dao.testmodel.TestRole;
import com.systop.core.dao.testmodel.TestUser;

/**
 * @author Sam Lee
 * 
 */
@SuppressWarnings("unchecked")
public class ReflectUtilTest extends TestCase {
  private TestEmployee testEmp;

  private static final String TEST_CLASSNAME = TestRole.class.getName();

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    TestUser testUser = new TestUser();
    testUser.setLoginId("Sam");
    TestDept dept = new TestDept();
    testEmp = new TestEmployee();
    testEmp.setUser(testUser);
    testUser.setEmployee(testEmp);
    dept.getEmployees().add(testEmp);
    testEmp.setDept(dept);

    testUser.setId(10);
    testEmp.setId(10);
    testEmp.setAddress("China");

    super.setUp();
  }

  /**
   * Test method for {@link ReflectUtil#classForName(String)}.
   */
  public void testClassForName() {
    assertNotNull(ReflectUtil.classForName(TEST_CLASSNAME));
    try {
      ReflectUtil.classForName("XX");
      fail("XX is class ???");
    } catch (Exception e) {
    }
    try {
      ReflectUtil.classForName(null);
      fail("Null is class ???");
    } catch (Exception e) {
    }
  }

    /**
   * Test method for {@link ReflectUtil#newInstance(String)}.
   */
  public void testNewInstanceString() {
    assertNotNull(ReflectUtil.newInstance(TEST_CLASSNAME));
    try {
      ReflectUtil.newInstance("XX");
      fail("XX can be instantiated ???");
    } catch (Exception e) {
    }
  }

  /**
   * Test method for {@link ReflectUtil#newInstance(Class)}.
   */
  public void testNewInstanceClassOfT() {
    TestRole r = ReflectUtil.newInstance(TestRole.class);
    assertNotNull(r);
  }

  /**
   * Test method for {@link ReflectUtil#findGetterByProperty(Class, String)}.
   */
  public void testFindGetterByProperty() {
    Method m = ReflectUtil.findGetterByProperty(TestUser.class, "LOGIN_ID");
    assertEquals(m.getName(), "getLoginId");
    m = ReflectUtil.findGetterByProperty(TestUser.class, "LoginId");
    assertEquals(m.getName(), "getLoginId");
    m = ReflectUtil.findGetterByProperty(TestUser.class, "loginId");
    assertEquals(m.getName(), "getLoginId");
    assertNull(ReflectUtil.findGetterByProperty(TestUser.class, "XX"));
  }

  /**
   * Test method for
   * {@link ReflectUtil#findSetterByProperty(Class, String, Object)}.
   */
  public void testFindSetterByProperty() {
    Method m = ReflectUtil.findSetterByProperty(TestUser.class, "LOGIN_ID",
        String.class);
    assertEquals(m.getName(), "setLoginId");
    m = ReflectUtil.findSetterByProperty(TestUser.class, "LoginId",
        String.class);
    assertEquals(m.getName(), "setLoginId");
    m = ReflectUtil.findSetterByProperty(TestUser.class, "loginId",
        String.class);
    assertEquals(m.getName(), "setLoginId");
    m = ReflectUtil.findSetterByProperty(TestUser.class, "loginId", null);
    assertNull(m);
    assertNull(ReflectUtil.findSetterByProperty(TestUser.class, "XX",
        String.class));
  }

  /**
   * Test method for {@link ReflectUtil#get(Object, String)}.
   */
  public void testGet() {
    assertEquals("China", ReflectUtil.get(testEmp, "address"));
    assertEquals("Sam", ReflectUtil.get(testEmp.getUser(), "LOGIN_ID"));
    assertEquals("Sam", ReflectUtil.get(testEmp.getUser(), "login_id"));
    assertEquals("Sam", ReflectUtil.get(testEmp.getUser(), "loginId"));
    /*
     * Date now = new Date(); for (int i = 0; i < 100000; i++) {
     * ReflectUtil.get(testEmp.getUser(), "LOGIN_ID"); }
     * System.out.println("10W次调用LOGIN_ID :" + (new Date().getTime() -
     * now.getTime())); now = new Date(); for (int i = 0; i < 100000; i++) {
     * ReflectUtil.get(testEmp.getUser(), "loginId"); }
     * System.out.println("10W次调用loginId :" + (new Date().getTime() -
     * now.getTime())); now = new Date(); for (int i = 0; i < 1000000; i++) {
     * testEmp.getUser().getLoginId(); } System.out.println("100W次正常调用getLoginId :" +
     * (new Date().getTime() - now.getTime()));
     */
  }

  /**
   * Test method for {@link ReflectUtil#set(Object, String, Object)}.
   */
  public void testSet() {
    ReflectUtil.set(testEmp.getUser(), "LAST_LOGIN_IP", "10");
    assertEquals("10", testEmp.getUser().getLastLoginIp());
    ReflectUtil.set(testEmp.getUser(), "LastLoginIp", "11");
    assertEquals("11", testEmp.getUser().getLastLoginIp());
    try {
      ReflectUtil.set(testEmp.getUser(), "xx", "11");
      fail("xx is a property???");
    } catch (Exception e) {

    }
  }

  /**
   * Test method for
   * {@link ReflectUtil#reportNoSuchMethodException(Class, String)}.
   */
  public void testReportNoSuchMethodException() {
    try {
      ReflectUtil.reportNoSuchMethodException(TestUser.class, "");
      fail("reportNoSuchMethodException can't work.");
    } catch (Exception e) {

    }
  }

  /**
   * Test method for {@link ReflectUtil#invoke(Object, String, Object[])}.
   */
  public void testInvokeObjectStringObjectArray() {
    ReflectUtil.invoke(testEmp.getUser(), "setLoginId", new String[] { "X" });
    assertEquals("X", testEmp.getUser().getLoginId());
    assertEquals("X", ReflectUtil.invoke(testEmp.getUser(), "getLoginId", null));
    try {
      ReflectUtil.invoke(testEmp.getUser(), "xxx", null);
      fail("xxx is a method???");
    } catch (Exception e) {

    }
  }

  /**
   * Test method for {@link ReflectUtil#nestedGet(Object, String)}.
   */
  public void testNestedGet() {
    testEmp.getUser().setLoginId("China");
    assertEquals("China", ReflectUtil.nestedGet(testEmp, "user.loginId"));
    assertEquals("China", ReflectUtil.nestedGet(testEmp, "user.LOGIN_ID"));

    try {
      ReflectUtil.nestedGet(testEmp, "user.XXX");
      fail("user.XXX is a method???");
    } catch (Exception e) {

    }
  }

  /**
   * Test method for {@link ReflectUtil#getPrivateProperty(Object, String)}.
   */
  public void testGetPrivateProperty() {
    assertEquals(TestEmployee.TEST_STRING, ReflectUtil.getPrivateProperty(
        testEmp, "testString"));
  }

  /**
   * Test method for
   * {@link ReflectUtil#setPrivateProperty(Object, String, Object)}.
   */
  public void testSetPrivateProperty() {
    ReflectUtil.setPrivateProperty(testEmp, "testString", "T");
    assertEquals("T", ReflectUtil.getPrivateProperty(testEmp, "testString"));
    ReflectUtil.setPrivateProperty(testEmp, "testString",
        TestEmployee.TEST_STRING);
  }

  /**
   * Test method for
   * {@link ReflectUtil#invokePrivateMethod(Object, String, Object[])}.
   */
  public void testInvokePrivateMethod() {
    ReflectUtil.invokePrivateMethod(testEmp, "setTestString",
        new Object[] { "" });
    assertEquals("", ReflectUtil.getPrivateProperty(testEmp, "testString"));
  }

  /**
   * Test method for {@link ReflectUtil#findDeclaredMethods(Class, String)}.
   */
  public void testFindDeclaredMethods() {
    Method[] ms = ReflectUtil.findDeclaredMethods(TestUser.class, "getLoginId");
    assertTrue(ms.length == 1);
    ms = ReflectUtil.findDeclaredMethods(TestUser.class, "XX");
    assertTrue(ms.length == 0);
  }

  /**
   * Test method for {@link ReflectUtil#findDeclaredMethod(Class, String)}.
   */
  public void testFindDeclaredMethod() {
    assertNotNull(ReflectUtil.findDeclaredMethod(TestUser.class, "getLoginId"));
    assertNull(ReflectUtil.findDeclaredMethod(TestUser.class, "xx"));
  }

  /**
   * Test method for {@link ReflectUtil#isTransientField(Class, String)}.
   */
  public void testIsTransientField() {
    assertTrue(ReflectUtil.isTransientField(TestUser.class, "changed"));
    assertFalse(ReflectUtil.isTransientField(TestUser.class, "id"));
  }

  /**
   * Test method for
   * {@link ReflectUtil#isAnnotatedMethod(reflect.Method, String)}.
   */
  public void testIsAnnotatedMethod() {
    assertTrue(ReflectUtil.isAnnotatedMethod(ReflectUtil.findDeclaredMethod(
        TestUser.class, "getId"), "javax.persistence.Id"));
    assertFalse(ReflectUtil.isAnnotatedMethod(ReflectUtil.findDeclaredMethod(
        TestUser.class, "setId"), "javax.persistence.Id"));
  }

  /**
   * Test method for {@link ReflectUtil#toMap(Object, String[], boolean)}.
   */
  public void testToMap() {
    TestUser tu = new TestUser();
    tu.setId(1);
    Map map = ReflectUtil.toMap(tu, new String[]{"id", "loginId"}, true);
    assertNotNull(map.get("id"));
    assertNull(map.get("loginId"));
    map = ReflectUtil.toMap(tu, new String[]{"id", "loginId"}, false);
    assertEquals(map.size(), 1);
    tu.setLoginId("X");
    map = ReflectUtil.toMap(tu, new String[]{"id", "login_id"}, true);
    assertEquals(map.get("login_id"), "X");
    map = ReflectUtil.toMap(tu, null, true);    
  }


  /**
   * Test method for {@link ReflectUtil#copyList(List, List, String[])}.
   */
  public void testCopyListListListStringArray() {
    List src = new ArrayList();
    TestUser u1 = new TestUser();
    u1.setId(1);
    TestUser u2 = new TestUser();
    u2.setId(2);
    src.add(u1);
    src.add(u2);
    List dest = new ArrayList();
    ReflectUtil.copyList(dest, src, new String[]{"loginId", "email"});
    assertTrue(dest.size() == 2);
    TestUser tu = (TestUser) dest.get(0);
    assertEquals(tu.getId(), Integer.valueOf(1));
  }

  /**
   * Test method for {@link ReflectUtil#copyList(List, String[])}.
   */
  public void testCopyListListStringArray() {
    List src = new ArrayList();
    TestUser u1 = new TestUser();
    u1.setId(1);
    TestUser u2 = new TestUser();
    u2.setId(2);
    src.add(u1);
    src.add(u2);
    List dest = ReflectUtil.copyList(src, new String[]{"loginId", "email"});
    assertTrue(dest.size() == 2);
    TestUser tu = (TestUser) dest.get(0);
    assertEquals(tu.getId(), Integer.valueOf(1));
  }

  /**
   * Test method for {@link ReflectUtil#getters(Class, boolean)}.
   */
  public void testGetters() {
    List<Method> getters = ReflectUtil.getters(TestUser.class, true);
    assertNotNull(getters);
    assertTrue(getters.size() > 0);
    List<Method> all = ReflectUtil.getters(TestUser.class, false);
    assertNotNull(all);
    assertTrue(all.size() > getters.size());
  }

  /**
   * Test method for {@link ReflectUtil#setters(Class, boolean)}.
   */
  public void testSetters() {
    List<Method> setters = ReflectUtil.setters(TestUser.class, true);
    assertNotNull(setters);
    assertTrue(setters.size() > 0);
    List<Method> all = ReflectUtil.setters(TestUser.class, false);
    assertNotNull(all);
    assertTrue(all.size() > setters.size());
  }

}
