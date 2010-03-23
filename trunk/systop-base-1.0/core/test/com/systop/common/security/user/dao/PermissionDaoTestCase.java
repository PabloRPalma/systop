/**
 * 
 */
package com.systop.common.security.user.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.systop.common.security.user.model.Permission;
import com.systop.common.test.BaseTestCase;

/**
 * JUnit Testcase for <code>PermissionDao</code>
 * @author Sam
 */
public class PermissionDaoTestCase extends BaseTestCase {
  /**
   * 从applicationContext中得到PermissionDao的实例，用于简化代码。
   */
  private PermissionDao getPermiDao() {
    return (PermissionDao) applicationContext.getBean("permissionDao");
  }
  /**
   * Test method for {@link PermissionDao#findBy(Map)}.
   */
  public void testFindByMap() {
    for (int i = 0; i < times; i++) {
      Permission permi = new Permission();
      permi.setName(RandomStringUtils.random(times) + "_PERMI");
      permi.setOperation(
          (i % 2 == 0) ? "all" : RandomStringUtils.random(times));
      getPermiDao().saveObject(permi);
    }
    
    Map filter = new HashMap();
    filter.put("name", "PERMI");
    
    assertTrue(getPermiDao().findBy(filter).size() == times);
    
    filter.put("operation", "all");
    assertTrue(getPermiDao().findBy(filter).size() == times);
  }

}
