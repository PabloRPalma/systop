/**
 * 
 */
package com.systop.common.security.user.service;

import com.systop.common.security.user.exception.RoleAlreadyExistsException;
import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.webapp.RoleDwrAction;
import com.systop.common.test.BaseTestCase;

/**
 * TestCase of the {@link RoleManager}
 * @author Administrator
 * 
 */
public class RoleManagerTestCase extends BaseTestCase {
  private RoleManager getRoleMgr() {
    return (RoleManager) applicationContext.getBean("roleManager");
  }

  /**
   * Test method
   */
  public void testIsRoleNameInUse() {
    RoleDwrAction rda = 
      (RoleDwrAction) applicationContext.getBean("roleDwrAction");
    String rolename = "单元测试创建的角色";
    assertTrue("初始化前的错误：创建角色的操作未执行，而要创建的角色已存在？",
        !rda.isRoleNameInUse(rolename));
    Role role = new Role();
    role.setName(rolename);
    role.setVersion(1);
    getRoleMgr().save(role);
    assertTrue("初始化后的错误：创建的角色不存在", rda.isRoleNameInUse(role.getName()));
  }
  /**
   * Test method
   */
  public void testSaveRole() {
    RoleDwrAction rda = 
      (RoleDwrAction) applicationContext.getBean("roleDwrAction");
    String rolename = "单元测试创建的角色";
    boolean isRoleNameInUse = rda.isRoleNameInUse(rolename);
    if (isRoleNameInUse) {
      assertTrue("初始化前的错误：创建角色的操作未执行，而要创建的角色已存在？"
          , isRoleNameInUse);
      return;
    }
    Role role = new Role();
    role.setName(rolename);
    role.setVersion(1);
    try {
      getRoleMgr().save(role);
    } catch (RoleAlreadyExistsException e) {
      assertTrue("错误：角色还未创建，却已经存在", false);
      e.printStackTrace();
    }
    //继续添加一个同名的角色，测试是否能添加成功，是否能触发“角色重复”的异常
    Role sameNameRole = new Role();
    sameNameRole.setName(rolename);
    try {
      getRoleMgr().save(sameNameRole);
      assertTrue("错误：角色已存在，仍然能创建该角色", false);
    } catch (RoleAlreadyExistsException e) {
      e.printStackTrace();
    }
    assertTrue("初始化后的错误：创建的角色不存在", rda.isRoleNameInUse(role.getName()));
  }
}
