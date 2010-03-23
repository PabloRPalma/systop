package com.systop.common.security.user.service;

import org.apache.commons.codec.digest.DigestUtils;

import com.systop.common.security.user.model.User;
import com.systop.common.test.BaseTestCase;

/**
 * @author Sam
 * 
 */
public class UserManagerTestCase extends BaseTestCase {
  /**
   * @return the instance of the UserManager
   */
  private UserManager getUserMgr() {
    return (UserManager) applicationContext.getBean("userManager");
  }

  /**
   * Test method
   */
  public void testIsLoginIdInUse() {
    String loginId = "junitTestLoginID";
    assertTrue("初始化前的错误：创建用户的操作未执行，而要创建的用户登录ID已存在？"
        , !getUserMgr().isLoginIdInUse(loginId));
    User user = new User();
    user.setLoginId(loginId);
    user.setPassword(DigestUtils.md5Hex(loginId)); //登录ID与密码相同
    user.setName(loginId); //登录ID与用户名称相同
    user.setStatus("1");
    user.setVersion(1);
    getUserMgr().save(user);
    assertTrue("初始化后的错误：创建的用户不存在"
        , getUserMgr().isLoginIdInUse(user.getLoginId()));
  }
  
  /**
   * 测试save方法
   *
   */
  public void testSave() {
    String loginId = "junitTestLoginID";
    
    User user = new User();
    user.setLoginId(loginId);
    user.setPassword(DigestUtils.md5Hex(loginId)); //登录ID与密码相同
    user.setName(loginId); //登录ID与用户名称相同
    user.setStatus("1");
    getUserMgr().save(user);
    assertTrue(user.getId() != null);
  }
  
  /**
   * 测试remove方法
   */
  public void testRemove() {
    String loginId = "junitTestLoginID";
    
    User user = new User();
    user.setLoginId(loginId);
    user.setPassword(DigestUtils.md5Hex(loginId)); //登录ID与密码相同
    user.setName(loginId); //登录ID与用户名称相同
    user.setStatus("1");
    getUserMgr().save(user);
    assertTrue(user.getId() != null);
    getUserMgr().remove(user);
  }
}
