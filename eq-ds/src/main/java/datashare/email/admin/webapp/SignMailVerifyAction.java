package datashare.email.admin.webapp;

import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.ApplicationException;
import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.dao.hibernate.selector.NotEmptyStringPropertySelector;
import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;
import com.systop.core.webapp.struts2.action.BaseAction;

import datashare.email.sign.model.SignMail;
import datashare.email.sign.service.SignMailManager;
import datashare.sign.support.SignCommonDao;

/**
 * 前兆邮件审核Action
 * 
 * @author wbb
 */

@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SignMailVerifyAction extends BaseAction {
  /**
   * 用于访问MySQL数据库
   */
  @Autowired(required = true)
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao dao;
  
  @Autowired(required = true)
  private SignCommonDao commonDao;

  @SuppressWarnings("unchecked")
  private Collection<SignMail> items = Collections.EMPTY_LIST;
  /**
   * 前兆邮件订阅Manager
   */
  @Autowired(required = true)
  private SignMailManager signMailMgr;
  
  //@Autowired(required = true)
  //private SampleRateManager srManager;
  
  private SignMail signMail = new SignMail();
  /**
   * 被验证邮件项的ID
   */
  private Integer mailId;
  
  private String startDate;
  
  private String endDate;

  /**
   * 是否通过验证
   */
  private Boolean isPassed = true;

  /**
   * 查询前兆邮件订阅项的Action,可以根据订阅用户查询，也可以查询全部邮件。
   */
  public String list() {
    DetachedCriteria c = DetachedCriteria.forClass(SignMail.class)
    .add(Example.create(signMail)
        .setPropertySelector(new NotEmptyStringPropertySelector()))
    .addOrder(Order.desc("createDate"))
    .addOrder(Order.asc("state"));
    //加入订阅者查询条件
    if(signMail.getSubscriber() != null) {
      c.createCriteria("subscriber", "subscriber");
      if(signMail.getSubscriber().getId() != null) {        
        c.add(Restrictions.eq("subscriber.id", signMail.getSubscriber().getId()));
      }
      if(signMail.getSubscriber().getIndustry() != null) {
        c.add(Restrictions.eq("subscriber.industry", signMail.getSubscriber().getIndustry()));
      }
    }
    //加入订阅时间查询条件
    if(StringUtils.isNotBlank(startDate)) {
      try {
        c.add(Restrictions.ge("createDate", DateUtil.convertStringToDate("yyyy-MM", startDate)));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    if(StringUtils.isNotBlank(endDate)) {
      try {
        Date date = DateUtil.convertStringToDate("yyyy-MM", endDate);
        date = DateUtils.addMonths(date, 1);
        c.add(Restrictions.lt("createDate", date));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    items = dao.pagedQuery(new Page(0, 100000), c).getData();
    
    //items = dao.pagedQuery(new Page(0, 100000), c).getData();  
    return "list";
  }

  /**
   * 验证邮件订阅项,应使用Ajax查询
   */
  public String verify() {
    try {
      signMailMgr.verify(mailId, isPassed);
      render(getResponse(), "success", "plain/text");
    } catch (ApplicationException e) {
      render(getResponse(), e.getMessage(), "plain/text");
    }
    return null;
  }

  /**
   * 返回邮件订阅者列表
   */
  public List<User> getSubscribers() {
    return dao.query("select distinct m.subscriber from SignMail m");
  }
  
  /**
   * 返回已订阅的邮件中的测相分量
   */
  public Map<String, String> getItemIds() {
    List<String> list = dao.query("select distinct m.itemId from SignMail m order by m.itemId asc");
    Map<String, String> map = new LinkedHashMap();
    for(String itemId : list) {
      map.put(itemId, commonDao.getItemName(itemId));
    }
    
    return map;
  }
  
  /**
   * 返回已经订阅的台站
   */
  public Map<String, String> getStations() {
    List<String> list = dao.query("select distinct m.stationId from SignMail m order by m.stationId asc");
    Map<String, String> map = new LinkedHashMap();
    for(String stationId : list) {
      map.put(stationId, commonDao.getStationName(stationId));
    }
    
    return map;
  }
  /**
   * 返回已经订阅的采样率
   */
  public Map<String, String> getSampleRates() {
    List<String> list = dao.query("select distinct m.sampleRate from SignMail m order by m.sampleRate asc");
    Map<String, String> map = new LinkedHashMap();
    for(String sr : list) {
      map.put(sr, commonDao.getSampleRateName(sr));
    }
    return map;
  }
  
  /**
   * 行业内外
   */
  public Map<String, String> getIndustryMap() {
    return UserConstants.INDUSTRY_MAP;
  }

  /**
   * @return the items
   */
  public Collection<SignMail> getItems() {
    return items;
  }

  /**
   * @param items the items to set
   */
  public void setItems(Collection<SignMail> items) {
    this.items = items;
  }

  /**
   * @return the mailId
   */
  public Integer getMailId() {
    return mailId;
  }

  /**
   * @param mailId the mailId to set
   */
  public void setMailId(Integer mailId) {
    this.mailId = mailId;
  }

  /**
   * @return the isPassed
   */
  public Boolean getIsPassed() {
    return isPassed;
  }

  /**
   * @param isPassed the isPassed to set
   */
  public void setIsPassed(Boolean isPassed) {
    this.isPassed = isPassed;
  }

  public SignMail getSignMail() {
    return signMail;
  }

  public void setSignMail(SignMail signMail) {
    this.signMail = signMail;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
}
