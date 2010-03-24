package datashare.email.admin.jobs;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.systop.common.modules.mail.MailSender;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;
import com.systop.core.ApplicationException;
import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;

import datashare.GlobalConstants;
import datashare.admin.ds.service.DataSourceManager;
import datashare.email.EmailConstants;
import datashare.email.sign.model.SignMail;
import datashare.email.sign.service.SignMailManager;
import datashare.sign.data.dao.impl.MailSignDao;
import datashare.sign.data.model.Criteria;
import datashare.sign.data.model.DataSeries;

@Service("signEmailJob")
public class SignEmailJob {
  private static Logger logger = LoggerFactory.getLogger(SeismicEmailJob.class);
  /**
   * 前兆邮件Manager
   */
  @Autowired(required = true)
  private SignMailManager mailManager;
  /**
   * 用于表格查询的GridSignDao
   */
  @Autowired(required = true)
  private MailSignDao signDao;

  /**
   * 用于取得数据库SCHEMA
   */
  @Autowired(required = true)
  private DataSourceManager dsManager;

  /**
   * To retieve the SMTP configurations.
   */
  @Autowired(required = true)
  private MailSender mailSender;

  /**
   * Use freemarker as the mail body template.
   */
  @Autowired(required = true)
  @Qualifier("freeMarkerTemplateRender")
  private TemplateRender templateRender;

  /**
   * 用于获取“from”参数
   */
  @Autowired(required = true)
  private UserManager userManager;
  
  /**
   * 发送地震目录邮件并更新邮件的最后发送时间
   */
  public void send() {
    logger.info("前兆邮件发送任务启动.{}", DateUtil.getDateTime("yyyy-MM-dd HH:mm", new Date()));
    //找到所有订阅邮件的用户
    List<User> users = userManager.query("select distinct m.subscriber from SignMail m where m.state=?", EmailConstants.VERIFIED);
    for(User user : users) {
      List<SignMail> mails = mailManager.query(
          "from SignMail s where s.state=? and s.subscriber=?", EmailConstants.VERIFIED, user);
      StringBuffer buf = new StringBuffer(3000); //存放邮件内容
      
      for(SignMail mail : mails) { //遍历该用户的每一个邮件
        Criteria criteria = mail.getCriteria();
        Page page = new Page(0, GlobalConstants.MAX_RESULTS);
        criteria.setPage(page);
        criteria.setSchema(dsManager.getQzSchema()); // 这个Schema是数据库的“方案名称”
        List<DataSeries> data = signDao.query(criteria); // 数据查询
        if(CollectionUtils.isEmpty(data)) {
          continue; //如果没有数据，则继续循环
        } else {//如果有数据
          SignMail signMail = new SignMail();
          BeanUtils.copyProperties(mail, signMail, new String[] { "id" });
          mailManager.fillName(signMail);
          buf.append(getMailBody(signMail, data)).append("<BR>");
          mailManager.updateSendDate(mail, data); // 更新最后发送时间
        }
      }
      //发送邮件
      if(buf.length() > 0) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        try {
          helper.getMimeMessage().addHeader("Content-Type", "text/html; charset=UTF-8");
          if (StringUtils.isBlank(user.getDataEmail())) {
            helper.setTo(user.getEmail());
          } else {
            helper.setTo(user.getDataEmail());
          }
          helper.setFrom(userManager.getAdmin().getEmail());
          helper.setSubject("前兆邮件订阅");
          helper.setText(buf.toString(), true);
          helper.setSentDate(new Date());
        } catch (MessagingException e) {
          logger.error(e.getMessage());
          throw new ApplicationException("发送邮件失败.");
        }
        mailSender.send(message);
        logger.info("前兆数据邮件发送成功 {}", user.getEmail());
      }
    }
  }

  /**
   * 发送地震目录邮件并更新邮件的最后发送时间
   */
  @SuppressWarnings("unchecked")
  public void send1() {
    logger.info("前兆邮件发送任务启动.{}", DateUtil.getDateTime("yyyy-MM-dd HH:mm", new Date()));
    List<SignMail> list = mailManager.getDao().query(
        "from SignMail s where s.state=? order by s.subscriber", EmailConstants.VERIFIED);
    for (SignMail mail : list) {
      Criteria criteria = mail.getCriteria();
      Page page = new Page(0, GlobalConstants.MAX_RESULTS);
      criteria.setPage(page);
      criteria.setSchema(dsManager.getQzSchema()); // 这个Schema是数据库的“方案名称”
      List<DataSeries> data = signDao.query(criteria); // 数据查询
      //如果有数据就发送邮件
      if (!CollectionUtils.isEmpty(data)) {
        // 对象里代码转成汉字
        SignMail signMail = new SignMail();
        BeanUtils.copyProperties(mail, signMail, new String[] { "id" });
        mailManager.fillName(signMail);
        sendMail(signMail, data); // 发送邮件
        mailManager.updateSendDate(mail, data); // 更新最后发送时间
        logger.info("前兆数据邮件发送成功,地址：{}，台站：{}，测项分量{}，测点代码：{}，采样率：{}，数据类型：{}，共{}事件.", new String[] {
            signMail.getEmailAddr(), signMail.getStationId(), signMail.getItemId(),
            signMail.getPointId(), signMail.getSampleRate(), signMail.getDataType(),
            String.valueOf(data.size()) });
      } else {
        logger.info("前兆数据邮件发送失败，因为没有数据," +
        		"地址：{}，台站：{}，测项分量{}，测点代码：{}，采样率：{}，数据类型：{}.", new String[] {
            mail.getEmailAddr(), mail.getStationId(), mail.getItemId(),
            mail.getPointId(), mail.getSampleRate(), mail.getDataType()});
      }
    }
  }

  /**
   * 发送订阅审核结果邮件
   */
  @SuppressWarnings("unchecked")
  private void sendMail(SignMail mail, List events) {
    // Send the new password to user's email.
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
    try {
      helper.setTo(mail.getEmailAddr());
      helper.setFrom(userManager.getAdmin().getEmail());
      helper.setSubject("前兆邮件订阅");
      helper.setText(getMailBody(mail, events), true);
      helper.setSentDate(new Date());
    } catch (MessagingException e) {
      logger.error(e.getMessage());
      throw new ApplicationException("发送邮件失败.");
    }
    mailSender.send(message);
  }
  

  @SuppressWarnings("unchecked")
  private String getMailBody(SignMail mail, List events) {
    Template template = new Template("signEmailTemplate");
    TemplateContext templateCtx = new TemplateContext();
    templateCtx.setTemplate(template);
    // Set data that is contained in template .
    Map data = new HashMap();
    data.put("mail", mail);
    data.put("events", events);
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
    return writer.toString();
  }
}
