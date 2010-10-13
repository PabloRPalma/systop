package quake.email.admin.jobs;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import quake.email.admin.model.EmailDefinition;
import quake.email.admin.service.EmailDefinitionManager;

/**
 * CronExpression表达式<code>FactoryBean</code>.<code>CronFactoryBean</code>可以从
 * 数据库EMAIL_DEF表中取得测震和前兆邮件的发送频率(Cron),并构建一个CronExpression对象。
 * <code>CronFactoryBean</code>通常作为<code>CronTrigger</code>的属性在Application 
 * Context中注入.
 * @author Sam
 *
 */
public class CronFactoryBean implements FactoryBean {
  private static Logger logger = LoggerFactory.getLogger(CronFactoryBean.class);
  @Autowired
  @Qualifier("defaultFreq")
  private String defaultCron;
  /**
   * 用于取得前兆和测震邮件发送CRON表达式.
   */
  @Autowired(required = true)
  private EmailDefinitionManager emailDefManager;
  /**
   * 如果<code>isForSign</code>，那么<code>CronFactoryBean</code>
   * 将创建用于前兆数据订阅的cron表达式，否则，创建测震数据订阅cron表达式.
   */
  private boolean isForSign = true;
  
  /**
   * 从数据库中取得CRON表达式，如果数据库中没有则取缺省值.
   * @see EmailConstants#DEFALUT_CRON
   */
  @Override
  public Object getObject() throws Exception {
    if(!emailDefManager.isDefined()) {
      logger.warn("数据共享邮件订阅属性尚未设置.");
      return new CronExpression(defaultCron);
    }
    EmailDefinition emailDef = emailDefManager.get();
    logger.debug(emailDef.toString());
    //根据前兆和测震的设定，取出CRON.
    String cronEx = (isForSign) ? emailDef.getFreqSign() : emailDef.getFreqSeismic();
    if(StringUtils.isBlank(cronEx)) {
      return new CronExpression(defaultCron);
    }
    return new CronExpression(cronEx.trim());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class getObjectType() {
    return CronExpression.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  /**
   * @param isForSign the isForSign to set
   */
  public void setForSign(boolean isForSign) {
    this.isForSign = isForSign;
  }

}
