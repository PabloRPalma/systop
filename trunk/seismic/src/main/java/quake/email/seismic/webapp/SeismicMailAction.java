package quake.email.seismic.webapp;

import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import quake.admin.czcatalog.model.QuakeCatalog;
import quake.admin.czcatalog.service.QuakeCatalogManager;
import quake.email.admin.model.EmailDefinition;
import quake.email.admin.service.EmailDefinitionManager;
import quake.email.seismic.model.SeismicMail;
import quake.email.seismic.service.SeismicMailManager;

import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.ApplicationException;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;


/**
 * 测震邮件订阅Action类
 * 
 * @author Sam
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SeismicMailAction extends DefaultCrudAction<SeismicMail, SeismicMailManager> {

  @Autowired(required = true)
  private EmailDefinitionManager emailDefMgr;
  @Autowired(required = true)
  private QuakeCatalogManager cataMgr;
  
  /**
   * 列出当前用户所订阅的测震邮件.
   */
  @Override
  public String index() {
    // 得到当前登录用户，如果为null，则要求重新登录
    User user = null;
    try {
      user = UserUtil.getPrincipal(getRequest());
    } catch (ApplicationException e) {
      return "login";
    }
    //查询当前登录用户的测震邮件，不排序
    items = getManager().queryByUser(user);
    return INDEX;
  }

  /**
   * 保存当前用户所订阅的测震邮件
   */
  @SkipValidation
  @Override
  public String save() {
    Assert.notNull(getModel());
    QuakeCatalog catalog = cataMgr.queryByCltName(getModel().getCatalog());
    if(catalog != null) {
      getModel().setCatalogName(catalog.getClcName());
    }
    if (getModel().getId() == null) { // 新建
      // 得到当前登录用户，如果为null，则要求重新登录
      User user = null;
      try {
        user = UserUtil.getPrincipal(getRequest());
      } catch (ApplicationException e) {
        return "login";
      }
      getManager().create(getModel(), user);
      logger.debug("新建测震数据订阅项.");
    } else { // 修改
      getManager().update(getModel());
      logger.debug("修改测震数据订阅项." + getModel().getId());
    }
    return SUCCESS;
  }

  /**
   * 返回最小震级，用于页面验证.
   */
  public EmailDefinition getEmailDef() {
    return emailDefMgr.get();
  }

  /**
   * 返回地震目录列表，用于页面上的Select
   */
  public List<QuakeCatalog> getCatalogs() {
    return cataMgr.get();
  }
  
  public User getUser() {
   // 得到当前登录用户
    return UserUtil.getPrincipal(getRequest());
  }
}
