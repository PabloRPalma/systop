package com.systop.common.security.user.service;

import com.systop.common.security.user.model.User;
import com.systop.common.service.Manager;

/**
 * 用户管理
 * @author Sam
 */

public interface UserManager extends Manager<User> {
  /**
   * 判断指定的登录Id是否已经存在
   * @param loginId 登录id
   * @return 如果已经存在，返回<code>true</code>,否则<code>false</code>
   */
  boolean isLoginIdInUse(String loginId);
  

  
  /**
   * 根据用户名取得用户信息
   * @param loginId 用户名(登录名)
   * @return 如果存在，返回User实例，否则，返回<code>null</code>
   */
  User getUserByLoginId(String loginId);

  
  /**
   * 通过用户名和密码取得用户信息
   * @param username 用户名
   * @param password 密码
   * @return User对象，如果没有找到，返回<code>null</code>
   */
  User getUserByLoginIdAndPassword(String username, String password);
  
}
