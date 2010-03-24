package datashare.admin.samplerate.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.core.ApplicationException;
import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.service.BaseGenericsManager;

import datashare.admin.samplerate.model.SampleRate;
import datashare.base.service.Definable;

@Service
public class SampleRateManager extends BaseGenericsManager<SampleRate> implements Definable{
  private static Logger logger = LoggerFactory.getLogger(SampleRateManager.class);
  
  @Autowired(required = true)
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao baseHibernateDao;
  
  /**
   * 返回全部的SampleRate
   */
  @SuppressWarnings("unchecked")
  public List<SampleRate> get() {
    return baseHibernateDao.query("from SampleRate s order by s.sort asc");
  }
  /**
   * 根据采样率代码（主键），返回采样率；如果给定采样率代码对应的
   * 数据不存在，则抛出{@link ApplicationException}
   */
  public SampleRate get(String id) {
    SampleRate rate = baseHibernateDao.get(SampleRate.class, id);
    return rate;
  }
  /**
   * 保存或更新一个采样率
   */
  @Transactional
  public void save(SampleRate entity) {
    Assert.notNull(entity);
    Assert.notNull(entity.getId());
    SampleRate rate = get(entity.getId());
    if(rate != null) {
      baseHibernateDao.merge(entity);
      logger.info("更新采样率{}", entity.getId());
    } else {
      baseHibernateDao.save(entity);
      logger.info("新建采样率{}", entity.getId());
    }
  }
  /**
   * 根据id，删除采样率
   */
  @Transactional
  public void remove(String id) {
    baseHibernateDao.delete(SampleRate.class, id);
  }
  /**
   * 返回所有可用的，并且可以订阅的采样率
   */
  @SuppressWarnings("unchecked")
  public List<SampleRate> getForMail() {
    return baseHibernateDao.query("from SampleRate sr where sr.forMail='1' and sr.enabled='1' order by sort asc");
  }
  /**
   * 返回所有可用的采样率
   */
  @SuppressWarnings("unchecked")
  public List<SampleRate> getEnabled() {
    return baseHibernateDao.query("from SampleRate sr where sr.enabled='1' order by sort asc");
  }
  @Override
  public boolean isDefined() {
    return get().size() > 0;
  }
}
