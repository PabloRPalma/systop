package com.systop.common.modules.security.user.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.model.Role;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.Constants;

/**
 * 初始化工具类，Spring在启动后会自动执行init方法
 * 
 */
@SuppressWarnings("unchecked")
public class SysInitializer {

  protected Logger logger = LoggerFactory.getLogger(getClass());
  
  /**
   * 缺省的账号
   */
  public static final String ADMIN = "admin";
  
  /**
   * 缺省的密码
   */
  public static final String DEFAULT_PWD = "manager";

  @Autowired(required = true)
  private SessionFactory sessionFactory;

  private PasswordEncoder passwordEncoder;

  private String adminPwd = DEFAULT_PWD;

  private String adminName;
  
  private String adminDesc;

  private String adminEmail = getRandomEmail();
  
  public static final String DEFAULT_EMAIL_SUBFIX = "@gmail.com";
  
  @PostConstruct
  @Transactional
  public void init() {
    logger.info("系统正在初始化...");
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();
    try {
      // 初始化角色
      for (Role role : UserConstants.SYS_ROLES) {
        if(!hasSysRole(session, role.getName())){
          session.save(role);
        }
      }
      
      // 初始化系统管理员
      if (!hasAdmin(session)) {
        User admin = new User();
        admin.setLoginId(ADMIN);
        admin.setPassword(passwordEncoder.encodePassword(adminPwd, null));
        admin.setName(adminName);
        admin.setDescn(adminDesc);
        admin.setEmail(adminEmail);
        //是否可用：可用
        admin.setStatus(Constants.STATUS_AVAILABLE);
        //用户类型：后台用户
        admin.setUserType(UserConstants.USER_TYPE_SYS);
        //是否系统用户：是
        admin.setIsSys(Constants.STATUS_AVAILABLE);
        
        session.save(admin);
      }
    } finally {
      session.flush();
      tx.commit();
      session.close();
    }
  }
  
  /**
   * 是否已经有admin帐户
   */
  private boolean hasAdmin(Session session) {
    List list = session.createQuery("from User u where u.loginId='admin'")
        .list();
    return list != null && list.size() > 0;
  }
  /**
   * 是否已经有系统角色
   */
  private boolean hasSysRole(Session session, String roleName) {
    List list = session.createQuery("from Role r where r.isSys=? and r.name=?")
    .setString(0, Constants.STATUS_AVAILABLE)
    .setString(1, roleName).list();
    return list != null && list.size() > 0;
  }
  
  /**
   * 得到一个随机的电子邮件用户，用于初始化用户电子邮件，邮件后缀用{@link #DEFAULT_EMAIL_SUBFIX}
   */
  private static String getRandomEmail() {
    return new StringBuffer(20).append(RandomStringUtils.randomAscii(10)).append(
        DEFAULT_EMAIL_SUBFIX).toString();
  }
  
  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public void setAdminPwd(String adminPwd) {
    this.adminPwd = adminPwd;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }
  
  public void setAdminDesc(String adminDesc) {
    this.adminDesc = adminDesc;
  }
  
  public void setAdminEmail(String adminEmail) {
    this.adminEmail = adminEmail;
  }
}
