package datashare.email.sign.webapp;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.ApplicationException;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.admin.samplerate.model.SampleRate;
import datashare.admin.samplerate.service.SampleRateManager;
import datashare.admin.subject.model.Subject;
import datashare.admin.subject.service.SubjectManager;
import datashare.email.admin.service.EmailDefinitionManager;
import datashare.email.sign.model.SignMail;
import datashare.email.sign.service.SignMailManager;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SignMailAction extends DefaultCrudAction<SignMail, SignMailManager> {
  @Autowired(required = true)
  private SubjectManager subjectManager;
  
  @Autowired(required = true)
  private SampleRateManager sampleRateManager;
  
  @Autowired(required = true)
  private EmailDefinitionManager emailDefMgr;
  
  private Serializable[] selectItemIds;
  
  /**
   * 列出当前用户所订阅的测震邮件.
   */
  @Override
  public String index() {
    // 得到当前登录用户，如果为null，则要求重新登录
    User user = null;
    try {
      user = getUser();
    } catch (ApplicationException e) {
      return "login";
    }
    items = getManager().queryByUser(user);
    //代码转成名称，列表显示
    items = getManager().convertSignMail(items);
    return INDEX;
  }
  
  /**
   * 保存当前用户所订阅的测震邮件
   */
  @SkipValidation
  @Override
  public String save() {
    if (selectItemIds == null) {
      addActionError("请选择测项分量进行保存.");
      return INPUT;
    }

    // 得到当前登录用户，如果为null，则要求重新登录
    User user = null;
    try {
      user = getUser();
    } catch (ApplicationException e) {
      return "login";
    }
    
   Integer signMailMaxSize = getSignMailMaxSize();
    if ((getManager().queryByUser(user).size() + selectItemIds.length) > signMailMaxSize) {
      addActionError("你所订阅的邮件超出可订阅邮件的最大数 '" + signMailMaxSize + "'");
      return INPUT;
    }
    
    //解析出台站、测点、测项分量ID 
    for (Serializable str : selectItemIds) {
      String[] ids = str.toString().split("_");
      String sid = ids[0];//台站id
      String pid = ids[1];//测点id
      String iid = ids[2];//测项分量id
      SignMail signMail = new SignMail();
      BeanUtils.copyProperties(getModel(), signMail);
      signMail.setStationId(sid);
      signMail.setPointId(pid);
      signMail.setItemId(iid);
      getManager().create(signMail, user);
    }
    return SUCCESS;
  }
  
  /**
   * 返回学科列表，用于页面上的Select
   */
  public List<Subject> getSubjects() {
    return subjectManager.get();
  }

  /**
   * 返回采样率列表，用于页面上的Select
   * @return
   */
  public List<SampleRate> getSampleRates() {
    return sampleRateManager.getForMail();
  }

  public void setSelectItemIds(Serializable[] selectItemIds) {
    this.selectItemIds = selectItemIds;
  }
  
  public User getUser() {
    // 得到当前登录用户
     return UserUtil.getPrincipal(getRequest());
  }
  
  /**
   * 得到邮件最大订阅数
   */
  public Integer getSignMailMaxSize() {
    return emailDefMgr.get().getMaxItems();
  }
}
