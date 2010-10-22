package quake.email.admin.webapp;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.email.seismic.model.Criteria;
import quake.email.seismic.model.SeismicMail;
import quake.email.seismic.service.SeismicMailManager;

import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.ApplicationException;
import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.webapp.struts2.action.BaseAction;


/**
 * 测震邮件审核Action
 * @author Sam
 */
@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SeismicMailVerifyAction extends BaseAction {
  /**
   * 用于访问MySQL数据库
   */
  @Autowired(required = true)
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao dao;
  
  private List<SeismicMail> items = Collections.EMPTY_LIST;
  /**
   * 测震邮件订阅Manager
   */
  @Autowired(required = true)
  private SeismicMailManager siesmicMailMgr;
  
  private Criteria model = new Criteria();
  
  /**
   * 查询条件邮件订阅者
   */
//  private User subscriber = new User();
  /**
   * 被验证邮件项的ID
   */
  private Integer mailId;
  
  /**
   * 是否通过验证
   */
  private Boolean isPassed = true;
  
  
  /**
   * 查询测震邮件订阅项的Action,可以根据订阅用户查询，也可以查询全部邮件。
   */
  public String list() {
//    items = siesmicMailMgr.queryByUser(subscriber);
    items = siesmicMailMgr.queryByCriteria(model);
    return "list";
  }
  
  /**
   * 验证邮件订阅项,应使用Ajax查询
   */
  public String verify() {
    try {
      siesmicMailMgr.verify(mailId, isPassed);
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
    return dao.query("select distinct m.subscriber from SeismicMail m");
  }
  
  public Map<String, String> getIndustryMap(){
    return UserConstants.INDUSTRY_MAP;
  }
  
  /**
   * @return the subscriber
   */
//  public User getSubscriber() {
//    return subscriber;
//  }

  /**
   * @param subscriber the subscriber to set
   */
//  public void setSubscriber(User subscriber) {
//    this.subscriber = subscriber;
//  }
  /**
   * @return the items
   */
  public List<SeismicMail> getItems() {
    return items;
  }
  /**
   * @param items the items to set
   */
  public void setItems(List<SeismicMail> items) {
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

  public Criteria getModel() {
    return model;
  }

  public void setModel(Criteria model) {
    this.model = model;
  }
}
