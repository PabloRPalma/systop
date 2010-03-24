package datashare.email.seismic.service;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.common.modules.mail.MailSender;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;
import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.DateUtil;
import com.systop.core.util.ReflectUtil;

import datashare.email.EmailConstants;
import datashare.email.seismic.model.Criteria;
import datashare.email.seismic.model.SeismicMail;

/**
 * 测震邮件订阅、审核Manager类
 * 
 * @author Sam
 * 
 */
@SuppressWarnings("unchecked")
@Service
public class SeismicMailManager extends BaseGenericsManager<SeismicMail> {
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

  @Autowired(required = true)
  private UserManager userManager;

  /**
   * 列出指定用户订阅的测震数据邮件，按照订阅时间<code>createDate</code>排序(DESC).
   * 
   * @param user 给出用户
   * @return List of {@link SeismicMail}, if there is no recodes, return <code>EMPTY_LIST</code>
   */

  public List<SeismicMail> queryByUser(User user) {
    List<SeismicMail> list = Collections.EMPTY_LIST;

    if (user == null || user.getId() == null) {
      // 执行查询
      list = getDao().query("from SeismicMail s order by s.state asc");
    } else {
      user = getDao().get(User.class, user.getId());
      list = getDao().query("from SeismicMail s where s.subscriber=? ", user);
    }

    return list;
  }

  /**
   * 根据指定查询条件列出订阅邮件（条件包含用户、订阅日期、行业内外）,无指定条件时查询所有邮件，按订阅时间<code>createDate</code>排序(ASC)
   * @param criteria 查询条件{@link Criteria}
   * @return List of {@link SeismicMail}, if there is no recodes, return <code>EMPTY_LIST</code>
   */
  public List<SeismicMail> queryByCriteria(Criteria criteria) {
    List<SeismicMail> list = Collections.EMPTY_LIST;
    StringBuffer hql = new StringBuffer("from SeismicMail s where 1 = 1 ");
    List<Object> args = new ArrayList<Object>();
    if (criteria.getSubscriber().getId() == null && StringUtils.isBlank(criteria.getStartTime())
        && StringUtils.isBlank(criteria.getEndTime()) && StringUtils.isBlank(criteria.getIndustry())) {//无指定条件时，查询全部
      hql.append("order by s.state asc");
      list = getDao().query(hql.toString());
    } else {//根据查询条件筛选邮件
      if (StringUtils.isNotBlank(criteria.getStartTime())
          && StringUtils.isNotBlank(criteria.getEndTime())) {
        hql.append("and (s.createDate between ? and ?) ");
        try {
          Date startTime = DateUtil.convertStringToDate(criteria.getStartTime() + "-1");
          Date endTime = DateUtil.convertStringToDate(criteria.getEndTime() + "-1");
          endTime = DateUtils.addMonths(endTime, 1);
          args.add(startTime);
          args.add(endTime);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      if (criteria.getSubscriber().getId() != null) {
        hql.append("and s.subscriber = ? ");
        User user = getDao().get(User.class, criteria.getSubscriber().getId());
        args.add(user);
      }
      if(StringUtils.isNotBlank(criteria.getIndustry())){
        hql.append("and s.subscriber.industry = ? ");
        args.add(criteria.getIndustry());
      }
    }
    list = getDao().query(hql.toString(), args.toArray());
    return list;
  }

  /**
   * 审核邮件，如果通过，则修改state属性为‘1’，否则，删除该订阅项目.
   * 
   * @param seisMailId 被审核邮件ID
   * @param isPassed <code>true</code>表示通过
   */
  @Transactional
  public void verify(Integer seisMailId, boolean isPassed) {
    SeismicMail mail = getDao().get(SeismicMail.class, seisMailId);
    User user = mail.getSubscriber();
    if (mail == null) {
      logger.warn("The seismic email is not exists.");
      throw new ApplicationException("被审核邮件不存在,可能已经撤销订阅.");
    }

    if (isPassed) {
      mail.setState(EmailConstants.VERIFIED);
      getDao().merge(mail);
    } else {
      getDao().delete(mail);
    }
    // 发送通知邮件
    sendNotify(user, mail, isPassed);
  }

  /**
   * 创建新的邮件订阅记录，设置审核状态为{@link EmailConstants#UNVERIFY}。
   */
  @Transactional
  public void create(SeismicMail seisMail, User user) {
    Assert.notNull(seisMail, "测震邮件为null,无法创建.");
    seisMail.setState(EmailConstants.UNVERIFY);
    seisMail.setCreateDate(new Date());
    seisMail.setSubscriber(user);
    user = getDao().get(User.class, user.getId());
    user.setDataEmail(seisMail.getEmailAddr());
    getDao().merge(user);
    getDao().save(seisMail);
  }

  /**
   * 修改邮件订阅记录，设置审核状态为{@link EmailConstants#UNVERIFY}。
   */
  @Transactional
  public void update(SeismicMail seisMail) {
    Assert.notNull(seisMail, "测震邮件为null,无法更新.");
    Assert.notNull(seisMail.getId(), "测震邮件为null,无法更新.");
    seisMail.setState(EmailConstants.UNVERIFY);
    seisMail.setCreateDate(new Date());
    getDao().merge(seisMail);
  }

  /**
   * 将邮件订阅项的“最后发送时间”字段更新为当前时间
   * 
   * @param seisMail 测震邮件
   * @param lastOTime 最后一次地震的发震时刻
   */
  @Transactional
  public void updateSendDate(SeismicMail seisMail, Date lastOTime) {
    if (seisMail == null || seisMail.getId() == null || lastOTime == null) {
      logger.warn("测震邮件订阅项为null,无法更新发送时间.");
      return;
    }
    seisMail.setLastSendDate(lastOTime);
    getDao().merge(seisMail);
  }

  /**
   * 发送订阅审核结果邮件
   */
  private void sendNotify(User user, SeismicMail mail, boolean passed) {
    // Send the new password to user's email.
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
    try {
      helper.setTo(user.getEmail());
      helper.setFrom(userManager.getAdmin().getEmail());
      helper.setSubject((passed) ? "恭喜您,测震邮件订阅审核通过" : "抱歉,测震邮件订阅审核未通过");
      helper.setText(getMailBody(user, mail, passed), true);
      helper.setSentDate(new Date());
    } catch (MessagingException e) {
      logger.error(e.getMessage());
      throw new ApplicationException("发送邮件失败." + e.getMessage());
    }
    mailSender.send(message);
  }

  /**
   * Retrieve the mail body.
   * 
   * @return the mail body or EMPTY string if any exception occurs.
   */
  private String getMailBody(User user, SeismicMail mail, boolean passed) {

    Template template = new Template("seisVerifyTemplate");
    TemplateContext templateCtx = new TemplateContext();
    templateCtx.setTemplate(template);
    // Set data that is contained in template .
    Map data = ReflectUtil.toMap(user, new String[] { "loginId", "email" }, false);
    data.put("mail", mail);
    data.put("passed", (passed) ? "审核通过" : "审核未通过");
    data.put("user", user);
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
