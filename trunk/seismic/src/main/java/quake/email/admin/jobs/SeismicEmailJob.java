package quake.email.admin.jobs;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import quake.GlobalConstants;
import quake.admin.czcatalog.model.QuakeCatalog;
import quake.admin.czcatalog.service.QuakeCatalogManager;
import quake.admin.ds.service.DataSourceManager;
import quake.email.EmailConstants;
import quake.email.seismic.model.SeismicMail;
import quake.email.seismic.service.SeismicMailManager;
import quake.seismic.data.catalog.dao.impl.GridCatDao;
import quake.seismic.data.catalog.model.Criteria;

import com.systop.common.modules.mail.MailSender;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;
import com.systop.core.ApplicationException;
import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;


@SuppressWarnings("unchecked")
@Service("seismicEmailJob")
public class SeismicEmailJob {
  private static Logger logger = LoggerFactory.getLogger(SeismicEmailJob.class);
  /**
   * 测震邮件Manager
   */
  @Autowired(required = true)
  private SeismicMailManager mailManager;
  /**
   * 用于表格查询的CatalogDao
   */
  @Autowired(required = true)
  private GridCatDao cataDao;

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
  
  @Autowired(required = true)
  private QuakeCatalogManager czCatalogManager;

  /**
   * 获取“from”参数
   */
  @Autowired(required = true)
  private UserManager userManager;

  /**
   * Use freemarker as the mail body template.
   */
  @Autowired(required = true)
  @Qualifier("freeMarkerTemplateRender")
  private TemplateRender templateRender;

  /**
   * 发送地震目录邮件并更新邮件的最后发送时间
   */
  public void send() {
    logger.info("测震邮件发送任务启动.{}", DateUtil.getDateTime("yyyy-MM-dd HH:mm", new Date()));

    List<SeismicMail> list = mailManager.getDao().query(
        "from SeismicMail s where s.state=? order by s.subscriber", EmailConstants.VERIFIED);

    for (SeismicMail mail : list) {
      Criteria criteria = mail.getCriteria();
      //关联震级
      QuakeCatalog catalog = czCatalogManager.findObject("from QuakeCatalog cc where cc.cltName=?", criteria.getTableName());
      criteria.setMagTname(catalog.getMagTname());
      
      Page page = new Page(0, GlobalConstants.MAX_RESULTS);
      criteria.setPage(page);
      criteria.setSchema(dsManager.getCzSchema()); // 这个Schema是数据库的“方案名称”
      page = cataDao.query(criteria); // 数据查询
      List data = page.getData();
      if (!CollectionUtils.isEmpty(data)) { //有数据的时候才发邮件        
        sendMail(mail, data); // 发送邮件
        //数据库O_TIME desc排序，所以第一个就是最后时刻
        Map last = (Map) data.get(0); 
        Date date = new Date(((Date) last.get("O_TIME")).getTime() + 1000L);
        mailManager.updateSendDate(mail, date); // 更新最后发送时间
        logger.info("地震目录数据邮件发送成功,地址：{}，目录:{}，共{}事件.", new String[] { mail.getEmailAddr(),
            mail.getCatalogName(), String.valueOf(page.getData().size()) });
      } else {
        logger.info("因为没有地震事件，所以未发送地震目录邮件。[{}]", mail.getEmailAddr());
      }
    }
  }

  /**
   * 发送订阅审核结果邮件
   */
  private void sendMail(SeismicMail mail, List events) {
    // Send the new password to user's email.
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
    try {
      helper.getMimeMessage().addHeader("Content-Type", "text/html; charset=UTF-8");
      helper.setTo(mail.getEmailAddr());      
      helper.setFrom(userManager.getAdmin().getEmail());
      helper.setSubject("测震数据订阅邮件：" + mail.getCatalogName());
      helper.setText(getMailBody(mail, events), true);
      helper.setSentDate(new Date());
    } catch (MessagingException e) {
      logger.error(e.getMessage());
      throw new ApplicationException("发送邮件失败.");
    }
    mailSender.send(message);
  }

  private String getMailBody(SeismicMail mail, List events) {
    Template template = new Template("seisEmailTemplate");
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
