package com.systop.common.security.user.service.impl;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import com.systop.common.exception.ApplicationException;
import com.systop.common.security.acegi.cache.AcegiCacheManager;
import com.systop.common.security.user.dao.UserDao;
import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.UserListener;
import com.systop.common.security.user.service.UserManager;
import com.systop.common.service.BaseManager;
import com.systop.common.util.ReflectUtil;

/**
 * <code>UserManager</code>的实现类
 * @see {@link UserManager}
 * @author Sam
 * 
 */
public class UserManagerImpl extends BaseManager<User> implements UserManager {
  /**
   * <code>User</code>对象持久化操作监听器，可以直接注入UserListener的实例
   * 或完整的类名。
   * @see {@link UserListener}
   */
  private List userListeners;
  
  /**
   * @see UserManager#getUserByLoginidAndPasswd(String, String)
   */
  public User getUserByLoginIdAndPassword(String username, String password) {
    List<User> users = find("from User u where u.loginId=? and u.password=?",
        username, password);
    if (users != null && users.size() > 0) {
      return users.get(0);
    }
    
    return null;
  }

  /**
   * Data access object of <code>User</code>.
   */
  private UserDao userDao;
  /**
   * <code>AcegiCacheManager</code>用于同步缓存
   */
  private AcegiCacheManager acegiCacheManager;
  
 
  
  /**
   * @return the userDao
   */
  public UserDao getUserDao() {
    return userDao;
  }

  /**
   * @param userDao the userDao to set
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
    // BaseManager可以和UserDao使用同一个SessionFactory，这样就避免了在
    // applicationContext中重复设置sessionFactory属性了
    if (getSessionFactory() == null) {
      setSessionFactory(userDao.getSessionFactory());
    }
  }

  /**
   * @see UserManager#isLoginIdInUse(java.lang.String)
   */
  public boolean isLoginIdInUse(String loginId) {
    User user = new User();
    user.setLoginId(loginId);
    if (log.isDebugEnabled()) {
      log.debug("check for login id '" + loginId + "'");
    }
    return exists(user, "loginId"); // 如果给定login id不唯一，则表示正在使用
  }

  /**
   * @see {@link UserManager#getUserByLoginId(String)}
   */
  public User getUserByLoginId(String loginId) {
    
    return userDao.findUniqueBy("loginId", loginId);
  }

  /**
   * @return the acegiCacheManager
   */
  public AcegiCacheManager getAcegiCacheManager() {
    return acegiCacheManager;
  }

  /**
   * @param acegiCacheManager the acegiCacheManager to set
   */
  public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
    this.acegiCacheManager = acegiCacheManager;
  }
  /**
   * 执行监听器
   * @see UserListener
   */
  private void executeListener(User user, String methodName) {
    if (userListeners != null && userListeners.size() > 0) {
      for (Iterator itr = userListeners.iterator(); itr.hasNext();) {
        Object obj = itr.next();
        UserListener userListener = null;
        if (obj instanceof UserListener) {
          userListener = (UserListener) obj;
        } else {
          userListener = 
            (UserListener) ReflectUtil.newInstance(obj.toString());
        }
        if (userListener != null) {
          try {
            Method method = userListener.getClass().
              getDeclaredMethod(methodName, new Class[]{User.class});
            ReflectUtil.invoke(userListener, method, new Object[]{user});
          } catch (SecurityException e) {
            e.printStackTrace();
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          } catch (Exception e) {
            e.printStackTrace();
          }
          
        }
      }
    }
  }
  /**
   * @see com.systop.common.service.BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(User user) {
    if (user == null || user.getId() == null) {
      return;
    }    
    //执行UserListener
    executeListener(user, "onBeforeRemove");
    
    String oldUsername = user.getUsername();
    super.remove(user);
    //同步缓存
    if (acegiCacheManager != null) {
      acegiCacheManager.onUserChanged(null, oldUsername);
    }
  }

  /**
   * 保存用户，密码不加密，调用者可以提供加密后的密码
   * @see com.systop.common.service.BaseManager#save(java.lang.Object)
   */
  @Override
  public void save(User user) {
    if (exists(user, "loginId")) {
      throw new ApplicationException("error.duplicate_loginid", 
          user.getLoginId());
    }
    executeListener(user, "onBeforeSave"); //执行UserListener
    String oldUsername = user.getUsername(); // 用于同步缓存
    super.save(user);
    if (acegiCacheManager != null) {
      acegiCacheManager.onUserChanged(user, oldUsername);
    }
  }

  /**
   * @return the userListeners
   */
  public List getUserListeners() {
    return userListeners;
  }

  /**
   * @param userListeners the userListeners to set
   */
  public void setUserListeners(List userListeners) {
    this.userListeners = userListeners;
  }
  
}
