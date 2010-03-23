/**
 * 
 */
package com.systop.common.security.user.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.systop.common.security.user.model.Role;
import com.systop.common.test.BaseTestCase;

/**
 * @author Sam
 * 
 */
public class RoleDaoTestCase extends BaseTestCase {
  /**
   * 从applicationContext得到RoleDao对象。用于简化代码
   */
  private RoleDao getRoleDao() {
    return (RoleDao) applicationContext.getBean("roleDao");
  }

  /**
   * Test method for {@link RoleDao#findResourcesByRole(Role)}.
   */
  public void testFindResourcesByRole() {

  }

  /**
   * Test method for {@link RoleDao#findBy(java.util.Map)}.
   */
  public void testFindByMap() {
    //RoleManager rm = (RoleManager)applicationContext.getBean("roleManager");
    //RoleAction ra = (RoleAction) applicationContext.getBean("roleAction");
    //assertNotNull(rm);
    //assertNotNull(ra);
    // 准备测试数据
    for (int i = 0; i < times; i++) {
      Role role = new Role();
      role.setName(RandomStringUtils.randomAscii(times) + "_ROLE");
      getRoleDao().saveObject(role);
    }
    // 查询条件
    Map filter = new HashMap();
    filter.put("name", "ROLE");
    assertEquals(getRoleDao().findBy(filter).size(), times);

    // 查不到东西的查询条件
    filter = new HashMap();
    filter.put("name", "超级玛丽");
    assertEquals(getRoleDao().findBy(filter).size(), 0);
  }

}
