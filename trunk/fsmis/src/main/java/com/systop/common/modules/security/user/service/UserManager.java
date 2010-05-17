package com.systop.common.modules.security.user.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.dao.UserDao;
import com.systop.common.modules.security.user.model.Permission;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.model.UserLoginHistory;
import com.systop.common.modules.security.user.service.listener.UserChangeListener;
import com.systop.core.ApplicationException;
import com.systop.core.Constants;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.ReflectUtil;
import com.systop.core.util.ValidationUtil;
import com.systop.fsmis.video.VideoConstants;


/**
 * 用户管理类
 * @author Sam Lee
 *
 */
@SuppressWarnings("unchecked")
@Service
public class UserManager extends BaseGenericsManager<User> {
  /**
   * Admin账户，不能删除。
   */
  public static final String ADMIN_USER = "admin"; 
  /**
   * 用于加密密码
   */
  private PasswordEncoder passwordEncoder;
  /**
   * <code>User</code>对象持久化操作监听器，可以直接注入UserListener的实例
   * 或完整的类名。
   * @see {@link com.systop.common.security.user.service.
   * listener.UserChangeListener}
   */
  private List<UserChangeListener> userChangeListeners;
  /**
   * JdbcTemplate，用户获取密码
   */
  private JdbcTemplate jdbcTemplate;
  
  @Autowired(required = true)
  private UserDao userDao;
   /**
     * 获得具有某一<code>Permission</code>的用户
     * @param perm 给定<code>Permission</code>
     * @return List of User or empty list.
     */
  public List<User> findUsersByPermission(Permission perm) {
    return userDao.findUsersByPermission(perm);
  }
   /**
     * 返回给定用户<code>User</code>所具有的权限（<code>Permission</code>）
     * @param user 给定用户
     * @return List of User or empty list.
     */
  public List<Permission> findPermissionsByUser(User user) {
    return userDao.findPermissionsByUser(user);
  }
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
   @Transactional
   public void updateLoginInformation(User user, String loginIp) {
     if (user == null || user.getId() == null) {
       logger.error("No user login, return only.");
       return;
     }
     
     user.setLastLoginIp(loginIp);
     user.setLastLoginTime(new Date());
     user.setOnline(Constants.YES);
     if (user.getLoginTimes() == null) {
       user.setLoginTimes(1);
     } else {
       user.setLoginTimes(user.getLoginTimes().intValue() + 1);
     }
     
     update(user);
   }   
   /**
    * 添加用户登录历史记录
    * @param user 给定用户
    * @param loginIp 给定用户登录所在IP
    */
   @Transactional
   public void addUserLoginHistory(User user, String loginIp){
	   if (user == null || user.getId() == null) {
	       logger.error("No user login, return only.");
	       return;
	     } 
	   if(loginIp.equals("0:0:0:0:0:0:0:1")){
		   loginIp="127.0.0.1";
	   }
	   UserLoginHistory userLoginHistory=new UserLoginHistory(user,new Date(),loginIp,user.getDept());
	   getDao().save(userLoginHistory);
   }
  /**
   * @see BaseManager#save(java.lang.Object)
   */
  @Override
  @Transactional
  public void save(User user) {
    save(user, true);
  }
  /**
   * 保存用户信息，并根据下面的情况决定是否加密密码:<br><pre>
   * <ul>
   *    <li>encodePassword属性不得为null.</li>
   *    <li>如果是新建用户。</li>
   *    <li>如果是修改用户，并且输入的密码为空字符串或UserConstants.DEFAULT_PASSWORD。
   *    此时使用原来的密码</li>
   * </ul>
   * </pre>
   * 
   * @param user <code>User</code> to save.
   * @param encodePassword true if encode password.
   */
  @Transactional
  public void save(User user, boolean encodePassword) {    
    /*if (user.getId() == null) { //新建用户的状态为正常
      user.setStatus(Constants.STATUS_AVAILABLE);
    }*/
    
    User oldUser = new User(); //用于同步缓存
    oldUser.setLoginId(user.getLoginId());
    oldUser.setId(user.getId());
    //验证重复的登录名
    if(getDao().exists(oldUser, new String[]{"loginId"})) {
      throw new ApplicationException("您输入的登录名'" 
          + oldUser.getLoginId() + "'已经存在.");
    }
    //验证重复的email.邮件重复验证暂时不需要,郭红亮2010-02-04
    //if(getDao().exists(user, new String[]{"email"})) {
    //  throw new ApplicationException("您的E-mail地址已经注册了,请更换一个.");
    //}
    //如果是修改，并且输入的密码为空或UserConstants.DEFAULT_PASSWORD，则表示使用原来的密码
    if (user.getId() != null
        && (user.getPassword().equals(UserConstants.DEFAULT_PASSWORD) 
            || StringUtils.isBlank(user.getPassword()))) {
      String password = getOldPassword(user.getId());
      logger.debug("Use old password '{}'", password);
      user.setPassword(password);
      //解决a different object with the same identifier value 
      //was already associated with the session的问题
      getDao().evict(get(user.getId()));
      getDao().getHibernateTemplate().update(user);
    } else {
      if (passwordEncoder != null && encodePassword) { // 密码加密
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
            null));
      }
      getDao().saveOrUpdate(user);
    }
    
    
    if (userChangeListeners != null) { //执行UserChangeListener，实现缓存同步等功能
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onSave(user, oldUser);
      }
    }    
  }
  
  /**
   * 根据用户名更新用户的密码.
   * @param newPwd 
   * @throws ApplicationException 如果给定的emailAddr不存在.
   */
  @Transactional
  public void updatePasswordByEmail(String emailAddr, String loginId, String newPwd) {
    Assert.hasText(emailAddr, "E-Mail must not be empty.");
    
    User user = findObject("from User u where u.loginId=?", loginId);
    if(user == null) {
      throw new ApplicationException("用户名''" + loginId + "不存在");
    }
    if(!StringUtils.equalsIgnoreCase(emailAddr, user.getEmail())) {
      throw new ApplicationException("注册邮箱地址不匹配.");
    }
    user.setPassword(newPwd);
    save(user, true);
  }
  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  @Transactional
  public void remove(User user) {
    if (Constants.STATUS_UNAVAILABLE.equals(user.getStatus())) {
      return;
    }
    //确保LoginID存在
    if(StringUtils.isBlank(user.getLoginId())) {
      user = get(user.getId());
    }
    //确保不能删除（禁用）Admin账户.
    if(ADMIN_USER.equals(user.getLoginId())) {
      logger.warn("不能删除或禁用admin账户，这是一个危险的动作哦。");
      return;
    }
    user.setStatus(Constants.STATUS_UNAVAILABLE);
    if (userChangeListeners != null) {
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onRemove(user);
      }
    }        
  }
  
  /**
   * 启用用户
   * @param user
   */
  @Transactional
  public void unsealUser(User user) {
    if (Constants.STATUS_AVAILABLE.equals(user.getStatus())) {
      return;
    }
    user.setStatus(Constants.STATUS_AVAILABLE);
    if (userChangeListeners != null) {
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onSave(user, user);
      }
    }
  }
  /**
   * @param userChangeListeners the userChangeListeners to set
   */
  @Autowired
  public void setUserChangeListeners(List<UserChangeListener> userChangeListeners) {
    this.userChangeListeners = userChangeListeners;
  }
  
  /**
   * 返回指定UserId的密码
   */
  private String getOldPassword(Integer userId) {
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
  @Transactional
  public void changePassword(User model, String oldPassword) { 
    //获得加密后的密码
    String encodePasswrod = passwordEncoder.encodePassword(oldPassword, null);
    //判断输入的原密码和数据库中的原密码是否一致
    if (encodePasswrod.equals(getOldPassword(model.getId()))) {
      User user = getDao().get(User.class, model.getId());
      //设置新密码
      user.setPassword(passwordEncoder.encodePassword(
        model.getPassword(), null));
      getDao().getHibernateTemplate().update(user);
      synchronousCache(user, user);
    } else {
      throw new ApplicationException("旧密码不正确!");
    }
  }
  
  /**
   * 前台注册用户修改个人信息
   * @see BaseManager#update(java.lang.Object)
   */
  @Override
  @Transactional
  public void update(User user) {
    if(user != null) {
      getDao().update(user);
      synchronousCache(user, user);
    }
  }
  
  /**
   * 得到loginId为{@link #ADMIN_USER}账户.
   * @return Instance of {@code User}
   * @throws NoSuchUserException 如果{@link #ADMIN_USER}账户不存在
   * @throws InvalidUserEmailException 如果{@link #ADMIN_USER}账户的email不合法
   */
  public User getAdmin() throws NoSuchUserException, InvalidUserEmailException {
    User admin = getUser(ADMIN_USER);
    if(admin == null) {
      throw new NoSuchUserException("admin 账户不存在.");
    }
    if(!ValidationUtil.isValidEmail(admin.getEmail())) {
      throw new InvalidUserEmailException("admin 账户电子邮件不合法-" + admin.getEmail());
    }
    
    return admin;
  }
  
  /**
   * 返回部门以及子部门下的员工信息
   * @param dept 部门树形列表
   */
  public Map getUserTree(Map dept, String roleName) {
    if (dept.isEmpty()) {
      return dept;
    }
    // 设置部门类型
    dept.put("type", dept.get("type"));
    // 得到部门ID
    Integer deptId = (Integer) dept.get("id");
    List<User> list;
    if(StringUtils.isBlank(roleName)) {
      list = query("from User u where u.dept.id=?", deptId);
    } else {
      list = query("select u from User u inner join u.roles r where u.dept.id=? and r.name=?", deptId, roleName);
    }
    // 添加本部门员工
    List children = new ArrayList();
    for (User user : list) {
      Map map = new HashMap();
      map.put("id", user.getId());
      map.put("text", user.getName());
      map.put("type", "user");
      map.put("sex", user.getSex());
      map.put("leaf", true);
      children.add(map);
    }

    // 得到此部门下的所有子部门
    List<Map> childNodes = (List) dept.get("childNodes");
    if (childNodes != null) {
      for (Map map : childNodes) {
        map = getUserTree(map, roleName);
        children.add(map);
      }
    }
    if (!children.isEmpty()) {
      dept.put("children", children);
      dept.put("childNodes", children);
      dept.put("leaf", false);
    } else {
      dept.put("leaf", true);
    }
    return dept;
  }
  
  /**
   * 同步缓存
   * @param user 新用户
   * @param oldUser 旧用户
   */
  private void synchronousCache(User user, User oldUser) {
    if (userChangeListeners != null) { 
      //执行UserChangeListener，实现缓存同步等功能
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onSave(user, oldUser);
      }
    }
  }
  
  /**
   * 根据登陆名称取得用户
   * @param uId
   * @param uName
   */
  public List<User> getUserByName(String uId, String uName) {
    List<User> list = Collections.EMPTY_LIST;
    StringBuffer hql = new StringBuffer("from User u where u.loginId = ?");
    if (StringUtils.isNotBlank(uId)) {
      hql.append(" and u.id != ?");
      list = query(hql.toString(), new Object[]{uName, Integer.valueOf(uId)});
    } else {
      list = query(hql.toString(), uName);
    }
    return list;
  }
  
  /**
   * 根据email取得用户
   * @param uId
   * @param email
   */
  public List<User> getUserByEmail(String uId, String email) {
    List<User> list = Collections.EMPTY_LIST;
    StringBuffer hql = new StringBuffer("from User u where u.email = ?");
    if (StringUtils.isNotBlank(uId)) {
      hql.append(" and u.id != ?");
      list = query(hql.toString(), new Object[]{email, Integer.valueOf(uId)});
    } else {
      list = query(hql.toString(), email);
    }
    return list;
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
  
  public static void main(String[] args) {
    PasswordEncoder pe = new org.acegisecurity.providers.encoding.Md5PasswordEncoder();
    System.out.println(pe.encodePassword("41467562", null));
  }
	/**
	 * 判断某用是否视频在线
	 */
	public boolean isVideoOnLine(User user) {
		if (!isUserOnline(user)) {
			return false;
		}
		user = get(user.getId());
		return StringUtils.equals(user.getVideoOnline(), Constants.YES);
	}
	/**
	 * 判断某个用户是否在线
	 * 
	 * @param user
	 *            用户实体
	 * @return 如果在线(online=1),返回true
	 */
	public boolean isUserOnline(User user) {
		if (user == null || user.getId() == null) {
			logger.warn("用户对象为null或者id为null，无法判断用户是否在线。");
			return false;
		}
		user = get(user.getId());
		return Constants.YES.equals(user.getOnline());
	}
	/**
	 * 更新用户的videoOnline字段，在用户登录视频或者退出的时候被调用
	 * 
	 * @param user
	 *            被更新的用户
	 * @param status
	 */
	@Transactional
	public void setVideoOnline(User user, String status) {
		if (!isUserOnline(user)) {
			throw new ApplicationException("用户{}没有登录，无法进入视频。", user
					.getLoginId());
		}
		user = get(user.getId());
		user
				.setVideoOnline((StringUtils.isBlank(status)) ? VideoConstants.USER_IDLE
						: status);
		logger
				.info("用户{}视频在线状态更新为{}", user.getLoginId(), user
						.getVideoOnline());
		getDao().merge(user);
	}

}
