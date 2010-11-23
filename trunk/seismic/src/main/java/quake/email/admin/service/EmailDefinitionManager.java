package quake.email.admin.service;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import quake.base.service.Definable;
import quake.email.admin.model.EmailDefinition;

import com.systop.core.ApplicationException;
import com.systop.core.dao.hibernate.BaseHibernateDao;


/**
 * 管理数据订阅属性的Manager类。当邮件发送频率被修改之后，关闭原来的Quartz任务，并
 * 启动新的任务.
 * @author Sam
 *
 */
@Service
public class EmailDefinitionManager implements Definable {
  private static Logger logger = LoggerFactory.getLogger(EmailDefinitionManager.class);
  /**
   * 主键，因为只有一条记录，所以主键是固定的
   */
  public static final String PK = "emaildef";
  
  @Autowired(required = true)
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao baseHibernateDao;
  
  @Autowired(required = true)
  private ApplicationContext ctx;
  
  /**
   * 得到数据订阅属性定义对象
   */
  public EmailDefinition get() {
    return baseHibernateDao.get(EmailDefinition.class, PK);
  }
  
  @Transactional
  public void save(EmailDefinition entity) {
    Assert.notNull(entity);
    if (StringUtils.isBlank(entity.getId())) {
      entity.setId(PK);
    }
    if (get() != null) {
      logger.debug("修改邮件订阅管理属性.");
      baseHibernateDao.merge(entity);
    } else {
      logger.debug("新建邮件订阅管理属性.");
      baseHibernateDao.save(entity);
    }
    //重新启动Quartz任务
    restartJobs(entity.getFreqSeismic(), entity.getFreqSign());
  }
  
  /**
   * 如果前兆和测震任务中的一个cron改变，则重新启动Quartz任务。
   * @param seisCron 测震CRON
   * @param signCron 前兆CRON
   * @throws ApplicationException 如果CRON无法解析，或原来的任务无法关闭，以及无法启动新任务.
   */
  private void restartJobs(String seisCron, String signCron) {
    if(StringUtils.isBlank(seisCron)) {
      logger.warn("测震CRON未设定。");
      return;
    }
    //trim一下，难保数据库里面没有空格
    seisCron = seisCron.trim();
    //signCron = signCron.trim();
    //得到两个trigger
    CronTrigger seismicCronTrigger = (CronTrigger) ctx.getBean("seismicEmailCronTrigger", CronTrigger.class);
    //CronTrigger signCronTrigger = (CronTrigger) ctx.getBean("signEmailCronTrigger", CronTrigger.class);
    //如果测震和前兆发送频率都没有变，则不必重新启动.
    if(seisCron.equals(seismicCronTrigger.getCronExpression())) {
      logger.info("测震发送频率都未改变，Quartz不必重新启动.");
      return;
    }
    
    //得到SchedulerFactoryBean的实例，注意beanName前面的&符号
    SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean) ctx.getBean("&emailSchedulerFactory");
    try {
      //重新设定trigger
      seismicCronTrigger.setCronExpression(seisCron);
      //signCronTrigger.setCronExpression(signCron);
      schedulerFactory.destroy(); //关闭原来的任务
      schedulerFactory.afterPropertiesSet(); //启动新的任务
      logger.info("测震邮件发送任务启动成功.");
    } catch (ParseException e) {
      throw new ApplicationException("Cron表达式解析错误." + e.getMessage());
    } catch (SchedulerException e) {
      e.printStackTrace();
      throw new ApplicationException("关闭定时任务出现异常.");
    } catch (Exception e) {
      e.printStackTrace();
      throw new ApplicationException("启动定时任务出现异常.");
    }    
  }
  /**
   * 如果已经定义了属性，则返回<code>true</code>，否则返回<code>false</code>
   */
  @Override
  public boolean isDefined() {
    return get() != null;
  }
}
