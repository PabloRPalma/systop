package datashare.email.admin.jobs;

import org.quartz.CronExpression;
import org.springframework.scheduling.quartz.CronTriggerBean;
/**
 * FIXME:因为目前不知道如何实现重载方法的注入，而<code>CronTrigger</code>
 * 的<code>setCronExpression</code>既可以用<code>CronExpression</code>
 * 对象作为参数也可以用String作为参数，这就产生了不确定性。所以，我们extends了
 * <code>CronTriggerBean</code>,提供{@link #setCron(CronExpression)}
 * 方法，避免这种不确定性。
 * @author Sam
 *
 */
public class CronTriggerBeanEx extends CronTriggerBean {
  /**
   * 调用父类的{@link CronTriggerBean#setCronExpression(CronExpression)}
   * 方法.
   */
  public void setCron(CronExpression cronExpression) {
    this.setCronExpression(cronExpression);
  }
}
