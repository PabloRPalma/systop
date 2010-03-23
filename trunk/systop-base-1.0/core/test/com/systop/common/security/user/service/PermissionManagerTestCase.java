/**
 * 
 */
package com.systop.common.security.user.service;

import java.util.List;

import com.systop.common.security.user.exception
          .PermissionAlreadyExistsException;
import com.systop.common.security.user.exception.RoleAlreadyExistsException;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Role;
import com.systop.common.test.BaseTestCase;
import com.systop.common.util.CollectionUtil;

/**
 * TestCase of the {@link PermissionManager}
 * @author Administrator
 *
 */
public class PermissionManagerTestCase extends BaseTestCase {
  private RoleManager getRoleMgr() {
    return (RoleManager) applicationContext.getBean("roleManager");
  }

  private PermissionManager getPermMgr() {
    return (PermissionManager) applicationContext.getBean("permManager");
  }

  /**
   * Test method for {@link PermissionManager#isNameInUse(java.lang.String)}.
   */
  public void testIsNameInUse() {
    String name = "单元测试创建的权限";
    assertTrue("初始化前的错误：创建权限的操作未执行，而要创建的权限已存在？"
        , !getPermMgr().isNameInUse(name));
    Permission perm = new Permission();
    perm.setName(name);
    perm.setOperation("*");
    perm.setStatus("1");
    perm.setVersion(1);
    getPermMgr().save(perm);
    assertTrue("初始化后的错误：创建的权限不存在"
        , getPermMgr().isNameInUse(perm.getName()));
  }

  /**
   * Test method for {@link PermissionManager#getByRole(Role)}.
   */
  public void testGetByRole() {
    List<Permission> permlst = getPermMgr().get();
    int permNum = permlst.size();
    Role role = new Role();
    role.setPermissions(CollectionUtil.listToSet(permlst));
    role.setName("单元测试创建的角色");
    role.setVersion(1);
    try {
      getRoleMgr().save(role);
    } catch (RoleAlreadyExistsException e) {
      e.printStackTrace();
    }
    role = getRoleMgr().get(role.getId());
    if (role == null) {
      assertTrue("没有查到测试所创建的角色", role != null);
      return;
    }
    Permission[] perms = getPermMgr().getByRole(role);
    // 检查角色拥有的权限个数是不是创建用户时设置的权限的个数
    assertTrue(perms.length == permNum);
  }

  /**
   * Test method for {@link PermissionManager#savePerm(Permission)}.
   */
  public void testSavePerm() {
    String permname = "单元测试创建的权限";
    boolean isPermNameInUse = getPermMgr().isNameInUse(permname);
    if (isPermNameInUse) {
      assertTrue("初始化前的错误：创建权限的操作未执行，而要创建的权限已存在？"
          , isPermNameInUse);
      return;
    }
    Permission perm = new Permission();
    perm.setName(permname);
    perm.setOperation("*");
    perm.setStatus("1");
    perm.setVersion(1);
    try {
      getPermMgr().save(perm);
    } catch (PermissionAlreadyExistsException e) {
      assertTrue("错误：权限还未创建，却已经存在", false);
      e.printStackTrace();
    }
    //继续添加一个同名的权限，测试是否能添加成功，是否能触发“权限重复”的异常
    Permission sameNamePerm = new Permission();
    sameNamePerm.setName(permname);
    sameNamePerm.setOperation("*");
    sameNamePerm.setStatus("1");
    sameNamePerm.setVersion(1);
    try {
      getPermMgr().save(sameNamePerm);
      assertTrue("错误：权限已存在，仍然能创建该权限", false);
    } catch (PermissionAlreadyExistsException e) {
      e.printStackTrace();
    }
    assertTrue("初始化后的错误：创建的权限不存在"
        , getPermMgr().isNameInUse(perm.getName()));
  }
 

}
