package com.systop.common.modules.security.password;

import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.common.modules.mail.MailSender;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;
import com.systop.core.util.ReflectUtil;
import com.systop.core.webapp.struts2.action.BaseAction;

/**
 * Restore Password
 * 
 * @author Sam Lee
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RestorePasswordAction extends BaseAction {

  public static final int LENGTH_OF_NEWPWD = 8;
  /**
   * User email address, we will send a new password through the email.
   */
  private String emailAddr;

  private String loginId;

  /**
   * To retieve the SMTP configurations.
   */
  @Autowired
  private MailSender mailSender;

  /**
   * UserManager to handle the password.
   */
  @Autowired
  private UserManager userManager;
  /**
   * Subject of the password mail
   */
  private String subject = "密码确认信.";

  /**
   * Use freemarker as the mail body template.
   */
  @Autowired
  @Qualifier("freeMarkerTemplateRender")
  private TemplateRender templateRender;

  /**
   * Body of the password mail, {user} means username, and {pwd} means new password. Example:<br>
   * 
   * <pre>
   * Dear {user}:
   *    You password is {pwd}, please login with this password.
   * ...
   * </pre>
   * 
   * or a freemarker template file. and the file must be at the classpath:tempates/simple.
   * 
   */
  private String bodyTemplate = "RestorePwdMail";

  /**
   * 修改用户的密码并发送一个电子邮件.
   */
  @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "emailAddr", message = "请输入电子邮件.") }, emails = { @EmailValidator(fieldName = "emailAddr", message = "请输入正确的e-Mail.") })
  public String sendPasswordMail() {
    try {
      // Update user password with a random numeric.
      String newPwd = RandomStringUtils.randomNumeric(LENGTH_OF_NEWPWD);
      
      User user = (User) userManager.getDao().findObject(
          "from User u where u.email=? and u.loginId=?", emailAddr, loginId);
      if(user == null) {
        addActionError("邮件发送失败,用户名或Email地址错误.");
        return INPUT;
      }
      // Send the new password to user's email.
      MimeMessage message = mailSender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

      helper.setTo(emailAddr);
      helper.setFrom(userManager.getAdmin().getEmail());
      helper.setSubject(subject);
      helper.setText(getMailBody(user, newPwd), true);
      helper.setSentDate(new Date());
      mailSender.send(message);
      userManager.updatePasswordByEmail(emailAddr, loginId, newPwd);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      addActionError("邮件发送失败." + e.getMessage());
      return INPUT;
    }

    return "index";
  }

  /**
   * Retrieve the mail body. If the <code>templateRender</code> is not null, getMailBody method
   * will render the freemarker template as the body. Otherwise, Use <code>bodyTemplate</code> as
   * the template and replate the {user} and {pwd} sign in it.
   * 
   * @return the mail body or EMPTY string if any exception occurs.
   */
  private String getMailBody(User user, String password) {
    if (templateRender == null) {
      return bodyTemplate.replace("{user}", user.getLoginId()).replace("{pwd}", password);
    }

    Template template = new Template(bodyTemplate);
    TemplateContext templateCtx = new TemplateContext();
    templateCtx.setTemplate(template);
    // Set data that is contained in template .
    Map data = ReflectUtil.toMap(user, new String[] { "loginId", "email" }, false);
    data.put("password", password);
    templateCtx.addParameters(data);
    // The writer object that the template content will be writen in.
    StringWriter writer = new StringWriter();
    templateCtx.setWriter(writer);
    try {
      templateRender.renderTemplate(templateCtx);
    } catch (Exception e) {
      logger.error("An error has occurs. {}", e.getMessage());
      return StringUtils.EMPTY;
    }
    logger.debug("Password Mail Body:{}" + writer.toString());
    return writer.toString();
  }

  /**
   * 直接定向到密码输入页面.
   */
  public String edit() {
    return INPUT;
  }

  /**
   * @return the emailAddr
   */
  public String getEmailAddr() {
    return emailAddr;
  }

  /**
   * @param emailAddr the emailAddr to set
   */
  public void setEmailAddr(String emailAddr) {
    this.emailAddr = emailAddr;
  }

  /**
   * @param mailSender the mailSender to set
   */
  @Autowired
  public void setMailSender(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  /**
   * @param userManager the userManager to set
   */
  @Autowired
  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

  /**
   * @return the userManager
   */
  public UserManager getUserManager() {
    return userManager;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * @param bodyTemplate the bodyTemplate to set
   */
  public void setBodyTemplate(String bodyTemplate) {
    this.bodyTemplate = bodyTemplate;
  }

  /**
   * @param templateRender the templateRender to set
   */
  @Autowired
  public void setTemplateRender(TemplateRender templateRender) {
    this.templateRender = templateRender;
  }

  /**
   * @return the loginId
   */
  public String getLoginId() {
    return loginId;
  }

  /**
   * @param loginId the loginId to set
   */
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

}
