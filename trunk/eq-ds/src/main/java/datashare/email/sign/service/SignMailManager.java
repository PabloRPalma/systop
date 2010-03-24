package datashare.email.sign.service;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

import datashare.admin.samplerate.model.SampleRate;
import datashare.admin.samplerate.service.SampleRateManager;
import datashare.email.EmailConstants;
import datashare.email.sign.model.SignMail;
import datashare.sign.data.model.DataSeries;
import datashare.sign.support.SignCommonDao;

/**
 * 前兆邮件订阅
 * @author wbb
 */
@Service
public class SignMailManager extends BaseGenericsManager<SignMail> {
  @Autowired(required = true)
  private MailSender mailSender;

  @Autowired(required = true)
  @Qualifier("freeMarkerTemplateRender")
  private TemplateRender templateRender;
  
  @Autowired(required = true)
  private UserManager userManager;
  
  @Autowired(required = true)
  private SignCommonDao signCommonDao;
  
  @Autowired(required = true)
  private SampleRateManager sampleRateManager;
  
  /**
   * 列出指定用户订阅的前兆数据邮件，按照订阅时间<code>createDate</code>排序(DESC).
   * @param user 给出用户
   * @return List of {@link SignMail}, if there is no recodes, return <code>EMPTY_LIST</code>
   */
  @SuppressWarnings("unchecked")
  public List<SignMail> queryByUser(User user) {
    List<SignMail> list = Collections.EMPTY_LIST;

    if (user == null || user.getId() == null) {
      // 执行查询
      list = getDao().query("from SignMail s order by s.state asc");
    } else {
      user = getDao().get(User.class, user.getId());
      list = getDao().query("from SignMail s where s.subscriber=? ", user);
    }
    return list;
  }
  
  /**
   * 审核邮件，如果通过，则修改state属性为‘1’，否则，删除该订阅项目.
   * @param signMailId 被审核邮件ID
   * @param isPassed <code>true</code>表示通过
   */
  @Transactional
  public void verify(Integer signMailId, boolean isPassed) {
    SignMail mail = getDao().get(SignMail.class, signMailId);
    if (mail == null) {
      logger.warn("The sign email is not exists.");
      throw new ApplicationException("被审核邮件不存在,可能已经撤销订阅.");
    }
    
    User user = mail.getSubscriber();
    if (isPassed) {
      mail.setState(EmailConstants.VERIFIED);
      getDao().merge(mail);
    } else {
      getDao().delete(mail);
    }
    //防止之后evict方法
    getDao().flush();
    // 发送通知邮件
    sendNotify(user, mail, isPassed);
  }
  
  /**
   * 发送订阅审核结果邮件
   */
  private void sendNotify(User user, SignMail mail, boolean passed) {
    // Send the new password to user's email.
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
    try {
      helper.setTo(user.getEmail());
      helper.setFrom(userManager.getAdmin().getEmail());
      helper.setSubject((passed) ? "恭喜您,前兆邮件订阅审核通过" : "抱歉,前兆邮件订阅审核未通过");
      helper.setText(getMailBody(user, mail, passed), true);
      
      helper.setSentDate(new Date());
    } catch (MessagingException e) {
      logger.error(e.getMessage());
      throw new ApplicationException("发送邮件失败.");
    }
    mailSender.send(message);
  }
  
  /**
   * Retrieve the mail body.
   * @return the mail body or EMPTY string if any exception occurs.
   */
  @SuppressWarnings("unchecked")
  private String getMailBody(User user, SignMail mail, boolean passed) {
    Template template = new Template("signVerifyTemplate");
    TemplateContext templateCtx = new TemplateContext();
    templateCtx.setTemplate(template);
    // Set data that is contained in template .
    Map data = ReflectUtil.toMap(user, new String[] { "loginId", "email" }, false);
    //与数据断开
    getDao().evict(mail);
    //代码转成名称
    convert(mail);
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
  
  /**
   * 创建新的邮件订阅记录，设置审核状态为{@link EmailConstants#UNVERIFY}。
   */
  @Transactional
  public void create(SignMail signMail, User user) {
    Assert.notNull(signMail, "前兆邮件为null,无法创建.");
    signMail.setState(EmailConstants.UNVERIFY);
    signMail.setCreateDate(new Date());
    signMail.setSubscriber(user);
    user = getDao().get(User.class, user.getId());
    user.setDataEmail(signMail.getEmailAddr());
    getDao().merge(user);
    getDao().save(signMail);
  }
  
  
  /**
   * 将实例从session中清除，实例改变不与数据库同步，把实例中代码转成名称
   */
  public Collection<SignMail> convertSignMail(Collection<SignMail> list) {
    for (SignMail signMail : list) {
      getDao().evict(signMail);
      convert(signMail);
    }
    return list;
  }
  
  /**
   * 将实例中代码转成名称
   * @param signMail
   * @return
   */
  private SignMail convert(SignMail signMail) {
    //台站
    signMail.setStationId(signCommonDao.getStationName(signMail.getStationId()));
    //测项分量
    signMail.setItemId(signCommonDao.getItemName(signMail.getItemId()));
    SampleRate sr = sampleRateManager.get(signMail.getSampleRate());
    if (sr != null) {
      signMail.setSampleRate(sr.getName());
    }
    if ("DYU".equals(signMail.getDataType())) {
      signMail.setDataType("预处理数据");
    } else {
      signMail.setDataType("原始数据");
    }
    return signMail;
  }
  
  /**
   * 用于发送邮件，填充SignMail中的***Name字段
   */
  public SignMail fillName(SignMail signMail) {
    SignMail mail = new SignMail();
    BeanUtils.copyProperties(signMail, mail);
    mail = this.convert(mail);
    signMail.setStationName(mail.getStationId());
    signMail.setItemName(mail.getItemId());
    signMail.setDataTypeName(mail.getDataType());
    
    return signMail;
  }
  
  /**
   * 将邮件订阅项的“最后发送时间”字段更新为数据序列最后时间
   * @param signMail
   * @param list 
   */
  @Transactional
  public void updateSendDate(SignMail signMail, List<DataSeries> list) {
    if(signMail == null || signMail.getId() == null) {
      logger.warn("前兆邮件订阅项为null,无法更新发送时间.");
      return;
    }
    //找出数据序列中最后的数据
    Date last = list.get(0).getTime();
    for(DataSeries dataSeries : list) {
      if(dataSeries.getTime().compareTo(last) >= 0) {
        last = dataSeries.getTime();
      }
    }
    /*
     * 这个算法更快，不过不如实战中的保险，而且不好测试，所以注释掉了
     Date last = list.get(0).getTime();
     if(list.size() > 1) {
      //如果最大的不是第一个，那么就是最后一个
      if(last.compareTo(list.get(1).getTime()) < 0) { 
        last = list.get(list.size() - 1).getTime();
      } 
    } 
     */
    //查询SQL中用的是>=，所以需要加入1天，以保证数据不重复
    signMail.setLastSendDate(DateUtil.add(last, Calendar.DATE, 1)); 
    getDao().merge(signMail);
  }
}
