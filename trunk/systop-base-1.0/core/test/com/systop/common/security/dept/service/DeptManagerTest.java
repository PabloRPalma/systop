package com.systop.common.security.dept.service;

import java.util.List;

import com.systop.common.security.dept.model.Dept;
import com.systop.common.security.user.model.User;
import com.systop.common.test.BaseTestCase;

/**
 * 
 * @author Administrator
 *
 */
public class DeptManagerTest extends BaseTestCase {

  /**
   * 测试用常量
   */
  private static final int TEST_TIMES = 6;

  /**
   * @return the instance of the DeptManager
   */
  private DeptManager getDeptMgr() {
    return (DeptManager) applicationContext.getBean("deptManager");
  }

  /**
   * test of the {@link DeptManager#findByParent(Dept)}
   *
   */
  public void testFindByParent() {
    Dept parent = new Dept();
    parent.setName("parentDept");
    getDeptMgr().save(parent);

    for (int i = 0; i < TEST_TIMES; i++) {
      Dept dept = new Dept();
      dept.setName("dept" + i);
      dept.setParent(parent);
      parent.getDepts().add(dept);
      getDeptMgr().save(dept);
    }
    // 对parent进行查询结果为>0
    List<Dept> depts = getDeptMgr().findByParent(parent);

    assertTrue(depts.size() == TEST_TIMES);

  }

  /**
   * test of the {@link DeptManager#getDeptsByParentId(Integer)}
   *
   */
  @SuppressWarnings("unchecked")
  public void testgetDeptsByParentId() {
    Dept parentDept = new Dept();
    parentDept.setName("parentDept");
    getDeptMgr().save(parentDept);

    for (int i = 0; i < TEST_TIMES; i++) {
      Dept subDept = new Dept();
      subDept.setName("subDept" + i);
      subDept.setParent(parentDept);
      parentDept.getDepts().add(subDept);
      getDeptMgr().save(subDept);
    }
    
    List<Dept> depts = getDeptMgr().getDeptsByParentId(parentDept.getId());
    for (Dept dept : depts) {
      logger.debug(dept.getName());
    }
    logger.debug(depts);
    assertTrue(depts.size() == TEST_TIMES);

  }

  /*
   * 暂不做 public void testFindUsersByDept() { Dept dept = new Dept();
   * dept.setName("parentDept");
   * 
   * for (int i = 0; i < TEST_TIMES; i++) { User user = new User();
   * user.setName("user" + TEST_TIMES); user.setDept(dept);
   * dept.getUsers().add(user); } getDeptMgr().save(dept);
   * 
   * List<User> users = getDeptMgr().findUsersByDept(dept);
   * logger.debug(users.size()); assertEquals(users.size(), TEST_TIMES); }
   */
  /**
   * test of the {@link DeptManager#removeUsers(Dept)}
   */
  public void testRemoveUsers() {
    Dept dept = new Dept();
    dept.setName("deptName");

    User user = new User();
    user.setName("user");
    getDeptMgr().addUser(dept, user);

    getDeptMgr().removeUsers(dept);

    assertTrue(dept.getUsers().size() == 0);
  }

  /**
   * test of the {@link DeptManager#addUser(Dept, User)}
   *
   */
  public void testAddUser() {
    User user = new User();
    user.setName("userName");

    Dept dept = new Dept();
    dept.setName("deptName");

    getDeptMgr().addUser(dept, user);

    assertTrue(dept.getUsers().size() == 1);
  }
  
  /**
   * test of the {@link DeptManager#saveDept(INteger, Dept)}
   *
   */
  public void testSaveDept() {
    Dept parent = new Dept();
    parent.setName("parent");
    getDeptMgr().save(parent);
    
    Dept dept = new Dept();
    dept.setName("childrenDept");
    getDeptMgr().saveDept(parent.getId(), dept);
    
    assertTrue(dept.getParent() != null);
        
  }

}
