package com.systop.common.security.acegi;

import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.systop.common.security.user.dao.UserDao;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.UserManager;
import com.systop.common.security.user.service.impl.UserManagerImpl;

/**
 * 从<code>UserManager</code>中获的<code>UserDetail</code>信息.
 * @author Sam Lee
 * 
 */
public class UserDetailService implements UserDetailsService {
  /**
   * log for the class
   */
  private static Log log = LogFactory.getLog(UserDetailService.class);
  /**
   * Instance of {@link UserManager}
   */
  private UserManager userManager;
  /**
   * <code>UserCache</code>用于从缓存中取得UserDetails
   */
  private UserCache userCache;

  /**
   * @param userCache the userCache to set
   */
  public void setUserCache(UserCache userCache) {
    this.userCache = userCache;
  }

  /**
   * 根据用户名查询用户信息
   */
  public UserDetails loadUserByUsername(String username) {
    UserDetails userDetails = null;
    if (userCache != null) { // 从缓存中取得UserDetails
      userDetails = userCache.getUserFromCache(username);
      if (userDetails != null) {
        log.debug("Get user details from cache " + username 
            + "/" + userDetails.getPassword());
      }
      
    }
    if (userDetails == null) { // 如果缓存中没有，则查询数据库，然后putInCache
      User user = userManager.getUserByLoginId(username);
      if (user == null) {
        return null;
      }
      log.debug("password of user '" + user.getUsername()
          + "'" + user.getPassword());
      if (userManager instanceof UserManagerImpl) {
        UserDao userDao = ((UserManagerImpl) userManager).getUserDao();
        List<Permission> perms = userDao.findPermissionByUser(user);
        GrantedAuthority[] auths = new GrantedAuthority[perms.size()];
        for (int i = 0; i < perms.size(); i++) {
          auths[i] = new GrantedAuthorityImpl(perms.get(i).getName());
          log.debug("Auth of user '" + user.getUsername() 
              + "'" + auths[i].getAuthority());          
        }
        user.setAuthorities(auths);
      }
      userDetails = user;
      
      if (userDetails == null) {
        throw new UsernameNotFoundException(username);
      } else {
        if (userCache != null) {
          userCache.putUserInCache(userDetails);
          log.debug("Put user in cache." + username);
        }
      }
    }
    return userDetails;
  }

  /**
   * @return the userManager
   */
  public UserManager getUserManager() {
    return userManager;
  }

  /**
   * @param userManager the userManager to set
   */
  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

}
