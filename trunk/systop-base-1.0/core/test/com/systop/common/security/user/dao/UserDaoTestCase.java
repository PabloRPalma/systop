/**
 * 
 */
package com.systop.common.security.user.dao;

import com.systop.common.Constants;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.model.User;
import com.systop.common.test.BaseTestCase;

/**
 * <code>UserDao</code>测试类
 * @author Sam
 * 
 */
public class UserDaoTestCase extends BaseTestCase {
  private UserDao getDao() {
    return (UserDao) applicationContext.getBean("userDao");
  }

  /**
   * test FindPermissionByUser
   * 
   */
  public void testFindPermissionByUser() {
    User user = new User();
    user.setLoginId("L");
    user.setPassword("");
    user.setStatus(Constants.STATUS_AVAILABLE);
    user.setName("S");
    this.getDao().saveObject(user);

    Permission perm = new Permission();
    perm.setName("AUTH_ADMIN");
    perm.setStatus(Constants.STATUS_AVAILABLE);
    this.getDao().saveObject(perm);

    Role role = new Role();
    role.setName("ROLE_USER");
    role.getUsers().add(role);
    user.getRoles().add(role);
    role.getPermissions().add(perm);
    perm.getRoles().add(role);
    this.getDao().saveObject(role);
    Role[] rs = (Role[]) user.getRoles().toArray(new Role[]{});
    assertEquals(rs[0].getPermissions().size(), 1);
    this.getDao().findPermissionByUser(user);
    this.getDao().findUsersByPermission(perm); 
  }
}
