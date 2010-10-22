package quake.email.admin.webapp;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import quake.email.admin.model.EmailDefinition;
import quake.email.admin.service.EmailDefinitionManager;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.core.webapp.struts2.action.BaseAction;


/**
 * 管理数据订阅属性的Action类
 * 
 * @author Sam
 * 
 */
@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmailDefinitionAction extends BaseAction implements ModelDriven<EmailDefinition> {
  
  @Autowired
  @Qualifier("freqMap")
  private LinkedHashMap freqs = new LinkedHashMap();
  
  private EmailDefinition model = new EmailDefinition();
  /**
   * 管理类
   */
  @Autowired(required = true)
  private EmailDefinitionManager manager;

  /**
   * 修改或新建{@link EmailDefinition}对象，定位到编辑页面
   */
  public String edit() {
    model = manager.get();
    if (model == null) {
      model = new EmailDefinition();
    }
    return INPUT;
  }

  /**
   * 保存订阅属性的Action
   */
  public String save() {
    Assert.notNull(model);
    try {
      manager.save(model);
    } catch (Exception e) {
      addActionError(e.getMessage());
      return INPUT;
    }
    addActionMessage("设置邮件订阅属性成功。");
    return SUCCESS;
  }

  /**
   * 发送频率，用于生成select列表
   */
  public Map<String, String> getFreqs() {
    return freqs;
  }

  @Override
  public EmailDefinition getModel() {
    return model;
  }
}
