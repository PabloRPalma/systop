package com.systop.modules.admin.security.rbac.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.systop.core.ApplicationException;
import com.systop.core.Constants;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.ReflectUtil;
import com.systop.modules.admin.security.rbac.UserConstants;
import com.systop.modules.admin.security.rbac.model.User;
import com.systop.modules.admin.security.rbac.service.listener.UserChangeListener;
import com.systop.modules.hr.employee.model.Employee;

/**
 * 用户管理类
 * @author Sam Lee
 * @version 3.0
 */
@Service()
@SuppressWarnings("unchecked")
public class UserManager extends BaseGenericsManager<User> {
  /**
   * 用于加密密码
   */
  private PasswordEncoder passwordEncoder;

  /**
   * <code>User</code>对象持久化操作监听器，可以直接注入UserListener的实例 或完整的类名。
   * @see {@link com.systop.common.security.user.service. listener.UserChangeListener}
   */
  private List<UserChangeListener> userChangeListeners;

  /**
   * JdbcTemplate，用户获取密码
   */
  private JdbcTemplate jdbcTemplate;

  /**
   * 根据登录名和密码得到<code>User</code>
   * @param loginId 登录名
   * @param password 密码
   * @return Instance of <code>User</code> or null.
   */
  public User getUser(String loginId, String password) {
    List<User> users = getDao().query(
        "from User user where user.loginId=? and user.password=?",
        new Object[] { loginId, password });
    if (users.isEmpty()) {
      return null;
    }
    return users.get(0);
  }

  /**
   * 根据用户登录名获得<code>User</code>对象
   * @param loginId 登录ID
   * @return Instance of <code>User</code> or null.
   */
  public User getUser(String loginId) {
    List<User> users = getDao().query("from User user where user.loginId=?",
        loginId);
    if (users.isEmpty()) {
      return null;
    }
    return users.get(0);
  }

  /**
   * 更新用户的登录时间（当前时间）和IP地址
   * @param user 给定用户
   * @param loginIp 给定用户登录所在IP
   */
  public void updateLoginInformation(User user, String loginIp) {
    if (user == null || user.getId() == null) {
      logger.error("No user login, return only.");
      return;
    }

    user.setLastLoginIp(loginIp);
    user.setLastLoginTime(new Date());
    if (user.getLoginTimes() == null) {
      user.setLoginTimes(1);
    } else {
      user.setLoginTimes(user.getLoginTimes().intValue() + 1);
    }

    update(user);
  }

  /**
   * @see BaseManager#save(java.lang.Object)
   */
  @Override
  public void save(User user) {
    save(user, true);
  }

  /**
   * 保存用户信息，并根据下面的情况决定是否加密密码:<br>
   * 
   * <pre>
   * &lt;ul&gt;
   *    &lt;li&gt;encodePassword属性不得为null.&lt;/li&gt;
   *    &lt;li&gt;如果是新建用户。&lt;/li&gt;
   *    &lt;li&gt;如果是修改用户，并且输入的密码为空字符串或UserConstants.DEFAULT_PASSWORD。
   *    此时使用原来的密码&lt;/li&gt;
   * &lt;/ul&gt;
   * </pre>
   * 
   * @param user <code>User</code> to save.
   * @param encodePassword true if encode password.
   */
  public void save(User user, boolean encodePassword) {
    if (user.getId() == null) { // 新建用户的状态为正常
      user.setStatus(Constants.STATUS_AVAILABLE);
    }
    // FIXME:防止identifier of an instance of Dept was altered from xxx to yyy
    getDao().getHibernateTemplate().getSessionFactory().getCurrentSession()
        .clear();
    // 验证重复的登录名
    validateDuplLoginId(user);
    // 验证重复的email
    vlidateDuplEmail(user);
    // 当修改loginId的时候，缓存中会存在脏数据，所以要保留原loginId用于同步缓存
    String originalLoginId = user.getLoginId();
    // 如果是修改，并且输入的密码为空或UserConstants.DEFAULT_PASSWORD，则表示使用原来的密码
    if (user.getId() != null
        && (user.getPassword().equals(UserConstants.DEFAULT_PASSWORD) || StringUtils
            .isBlank(user.getPassword()))) {
      String password = getOriginalPwd(user.getId()); // 用sql得到原ID
      logger.debug("Use old password '{}'", password);
      user.setPassword(password);
      getDao().getHibernateTemplate().update(user); // 一定是更新
    } else {
      if (passwordEncoder != null && encodePassword) { // 密码加密
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
            null));
      }
      getDao().saveOrUpdate(user); // 更新或新建（都需要加密密码）
    }

    if (userChangeListeners != null) { // 执行UserChangeListener，实现缓存同步等功能
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onSave(user, new User(user.getId(), originalLoginId));
      }
    }

  }

  private void validateDuplLoginId(User user) {
    for (Iterator<Employee> itr = user.getEmployees().iterator(); itr.hasNext();) {
      Employee emp = itr.next();
      getDao().evict(emp.getDept());
    }

    if (getDao().exists(user, new String[] { "loginId" })) {
      throw new ApplicationException("您输入的登录名'" + user.getLoginId() + "'已经存在.");
    }
  }

  private void vlidateDuplEmail(User user) {
    if (getDao().exists(user, new String[] { "email", "loginId" })) {
      throw new ApplicationException("您的E-mail地址已经注册了,请更换一个.");
    }
  }

  /**
   * 根据用户名更新用户的密码.
   * @throws ApplicationException 如果给定的emailAddr不存在.
   */
  public void updatePasswordByEmail(String emailAddr, String newPassword) {
    Assert.hasText(emailAddr, "E-Mail must not be empty.");

    User user = findObject("from User u where u.email=?", emailAddr);
    if (user == null) {
      throw new ApplicationException("电子邮件地址'" + emailAddr + "'不存在。");
    }
    user.setPassword(newPassword);
    save(user, true);
  }

  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(User user) {
    user.setStatus(Constants.STATUS_UNAVAILABLE);
    if (userChangeListeners != null) {
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onRemove(user);
      }
    }
  }

  /**
   * @param userChangeListeners the userChangeListeners to set
   */
  public void setUserChangeListeners(List userChangeListeners) {
    this.userChangeListeners = userChangeListeners;
  }

  /**
   * 返回指定UserId的密码
   */
  private String getOriginalPwd(Integer userId) {
    return (String) jdbcTemplate.query("select password from users where id=?",
        new Object[] { userId }, new ResultSetExtractor() {

          public Object extractData(ResultSet rs) throws SQLException {
            rs.next();
            return rs.getString(1);
          }

        });
  }

  /**
   * 修改密码
   * @param model 封装密码的User对象
   * @param oldPassword 旧的密码
   * @throws Exception
   */
  public void changePassword(User model, String oldPassword) {
    // 获得加密后的密码
    String encodePasswrod = passwordEncoder.encodePassword(oldPassword, null);
    // 判断输入的原密码和数据库中的原密码是否一致
    if (encodePasswrod.equals(getOriginalPwd(model.getId()))) {
      User user = getDao().get(User.class, model.getId());
      // 设置新密码
      user.setPassword(passwordEncoder
          .encodePassword(model.getPassword(), null));
      getDao().getHibernateTemplate().update(user);
      synchronousCache(user, user);
    } else {
      throw new ApplicationException("旧密码不正确!");
    }
  }

  /**
   * 同步缓存
   * @param user 新用户
   * @param oldUser 旧用户
   */
  private void synchronousCache(User user, User oldUser) {
    if (userChangeListeners != null) {
      // 执行UserChangeListener，实现缓存同步等功能
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onSave(user, oldUser);
      }
    }
  }

  /**
   * 执行监听器
   * @see UserListener
   */
  private UserChangeListener getListener(Object obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof String) {
      return (UserChangeListener) ReflectUtil.newInstance((String) obj);
    } else {
      return (UserChangeListener) obj;
    }
  }

  /**
   * @param passwordEncoder the passwordEncoder to set
   */
  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}
