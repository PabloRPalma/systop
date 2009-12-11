package com.systop.common.modules.security.user.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.mail.MailSender;
import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.model.RegMemo;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.listener.UserChangeListener;
import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.DateUtil;
import com.systop.core.util.ReflectUtil;

/**
 * 用户注册管理类
 * @author DU
 *
 */
@SuppressWarnings("unchecked")
@Service
public class RegistManager extends BaseGenericsManager<User> {
  
  /**
   * 用户管理Manager
   */
  private UserManager userManager;
  
  private RegMemoManager regMemoManager;
  
  @Autowired(required = true)
  private MailSender mailSender;
  
  //@Autowired(required = true)
  //private SmtpConfigDatabaseManager smtpConfigManager;
  /**
   * <code>User</code>对象持久化操作监听器，可以直接注入UserListener的实例
   * 或完整的类名。
   * @see {@link com.systop.common.security.user.service.
   * listener.UserChangeListener}
   */
  private List<UserChangeListener> userChangeListeners;
  
  /**
   * @see BaseManager#save(java.lang.Object)
   */
  @Override
  @Transactional
  public void save(User user) {
    userManager.save(user, true);
  }
  
  /**
   * 修改注册用户的状态
   * @param uId 用户ID
   */
  @Transactional
  public void checkupUser(String uId, String actUrl) {
    User user = get((uId == null) ? null : Integer.valueOf(uId.toString()));
    if(user != null) {
      try{
        user.setStatus(UserConstants.USER_STATUS_UNUSABLE.equals(user.getStatus())
            ? UserConstants.USER_STATUS_UNENABLED : UserConstants.USER_STATUS_UNUSABLE);
        update(user);
        //等待用户激活，向用户发送邮件
        if(UserConstants.USER_STATUS_UNENABLED.equals(user.getStatus())) {
          sendNotify(user, actUrl, true);
        }
        //禁用用户，向用户发送邮件
        if(UserConstants.USER_STATUS_UNUSABLE.equals(user.getStatus())) {
          sendNotify(user, actUrl, false);
        }
        synchronousCache(user, user);
      } catch(Exception e) {
        logger.error(e.getMessage());
        throw new ApplicationException("发送邮件失败，请检查SMTP配置。");
      }
    } else {
      logger.debug("试图修改null数据.");
    }
  }
  
  /**
   * 激活用户信息
   */
  @Transactional
  public void activateUser(String uId) {
    User user = get((uId == null) ? null : Integer.valueOf(uId.toString()));
    if(user != null) {
      //如果此时用户为待激活状态，该用户才能被激活
      if (UserConstants.USER_STATUS_UNENABLED.equals(user.getStatus())) {
        user.setStatus(UserConstants.USER_STATUS_USABLE);
        update(user);
        synchronousCache(user, user);
      }      
    } else {
      logger.debug("试图修改null数据.");
    }
  }
  
  /**
   * 发送邮件
   */
  private void sendNotify(User user, String actUrl, boolean passed) {
    // Send the new password to user's email.
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
    try {
      helper.setTo(user.getEmail());
      helper.setFrom(userManager.getAdmin().getEmail());
      //helper.getMimeMessage().addHeader("Content-Type", "text/html; charset=UTF-8");
      StringBuffer subject = new StringBuffer((passed) ? "恭喜您,您的账号信息审核通过" : "抱歉,您的账号信息已禁用")
      .append("[")
      .append(DateUtil.getDateTime("yyyy-MM-dd HH:mm", new Date()))
      .append("]");
      
      helper.setSubject(subject.toString());
      StringBuffer sbf = new StringBuffer();
      sbf.append("<font color='black'>点击以下链接激活您的账号。</font>")
      .append("<br/>")
      .append("<a href='")
      .append(actUrl).append(user.getId())
      .append("'>激活帐号</a>");
      if (passed) {
        helper.setText(sbf.toString(), true);
      } else {
        helper.setText("<font color='black'>抱歉,您的账号信息已禁用。</font>", true);
      }
      helper.setSentDate(new Date());
    } catch (MessagingException e) {
      logger.error(e.getMessage());
      throw new ApplicationException("发送邮件失败.");
    }
    mailSender.send(message);
  }
  
  /**
   * 批量修改注册用户的状态
   * @param selectedItems
   */
  @Transactional
  public void changeUserStatus(Serializable[] selectedItems) {
    if (selectedItems != null) {
      for (Serializable id : selectedItems) {
        if (id != null) {
          User user = get((id == null) ? null : Integer.valueOf(id.toString()));
          if(user != null) {
            logger.info("用户的状态是:"+user.getStatus());
            //user.setStatus(UserConstants.USER_STATUS_USABLE);
            user.setStatus(UserConstants.USER_STATUS_UNUSABLE.equals(user.getStatus())
                ? UserConstants.USER_STATUS_UNENABLED : UserConstants.USER_STATUS_UNUSABLE);
            update(user);
            synchronousCache(user, user);
          } else {
            logger.debug("试图修改null数据.");
          }
        }
      }    
    }
  }
  
  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  @Transactional
  public void remove(User user) {
    //确保LoginID存在
    if(StringUtils.isBlank(user.getLoginId())) {
      user = get(user.getId());
    }
    //确保不能删除（禁用）Admin账户.
    if("admin".equals(user.getLoginId())) {
      logger.warn("不能删除或禁用admin账户，这是一个危险的动作哦。");
      return;
    }
    //user.setStatus(Constants.STATUS_UNAVAILABLE);
    getDao().delete(user);
    if (userChangeListeners != null) {
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onRemove(user);
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
   * 取得注册说明信息
   */
  public RegMemo getRegMemoInfo() {
    return regMemoManager.get();
  }
  
  /**
   * @param userChangeListeners the userChangeListeners to set
   */
  @Autowired
  public void setUserChangeListeners(List<UserChangeListener> userChangeListeners) {
    this.userChangeListeners = userChangeListeners;
  }
  
  /**
   * @param userManager the userManager to set
   */
  @Autowired
  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

  /**
   * @param regMemoManager the regMemoManager to set
   */
  @Autowired
  public void setRegMemoManager(RegMemoManager regMemoManager) {
    this.regMemoManager = regMemoManager;
  }
}
