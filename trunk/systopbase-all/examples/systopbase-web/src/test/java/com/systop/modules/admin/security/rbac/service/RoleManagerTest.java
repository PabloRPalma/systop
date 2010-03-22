package com.systop.modules.admin.security.rbac.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.modules.admin.security.rbac.model.Role;

public class RoleManagerTest extends BaseTransactionalTestCase {

  private RoleManager roleManager;

  @Autowired
  public void setRoleManager(RoleManager roleManager) {
    this.roleManager = roleManager;
  }
  
  /**
   * Test method for {@link RoleManager#getRoleByName(String arg0, String arg1)}.
   */
  public void testGetRoleByName() {
    Role testRole = new Role();
    Role testRole1 = new Role();
    Role testRole2 = new Role();
    testRole.setName("ROLE_ADMIN");
    testRole.setDescn("Administrator");
    testRole1.setName("ROLE_ADMIN");
    testRole1.setDescn("Not the ADMIN");
    testRole2.setName("ROLE_MANAGER");
    testRole2.setDescn("MANAGER");
    roleManager.save(testRole);
    roleManager.save(testRole1);
    roleManager.save(testRole2);
    List<Role> list = Collections.emptyList();
    if (testRole.getName() == "ROLE_ADMIN") {
      list = roleManager.getRoleByName(testRole.getId().toString(), testRole.getName());
      for (Role role : list) {
        logger.debug("Role name is: " + role.getName() + " Role descn is: " + role.getDescn());
      }
    }
    if (testRole2.getName() == "ROLE_MANAGER") {
      list = roleManager.getRoleByName("", testRole2.getName());
      for (Role role : list) {
        logger.debug("Role name is: " + role.getName() + " Role descn is: " + role.getDescn());
      }
    }
  }
}
