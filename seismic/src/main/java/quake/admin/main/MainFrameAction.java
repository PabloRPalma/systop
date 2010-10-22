package quake.admin.main;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;

import quake.base.service.Definable;

import com.googlecode.jsonplugin.annotations.JSON;
import com.systop.core.webapp.struts2.action.BaseAction;


/**
 * 后台管理Main frame对应的Action类.
 * @author Sam
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainFrameAction extends BaseAction {
  
  @Autowired
  private List<Definable> definableItems;
  
  /**
   * 将“没有配置”的信息都存入这里
   */
  private List<String> undefineMessages = new ArrayList<String>();;
  
  /**
   * “所有配置项都已配置了。”
   */
  @Value("所有配置项均已定义。")
  private String allDefineMessage;
  
  @SuppressWarnings("unchecked")
  private LinkedHashMap configMessages = new LinkedHashMap();
  /**
   * 定位到/pages/main.jsp
   */
  public String index() {
    return "index";
  }
  
  /**
   * 验证各种配置是否完成.
   * @return
   */
  public String validateConfigurations() {
    if(definableItems == null || definableItems.size() == 0) {
      logger.warn("There is no Definable implementations, check your configuration.");
      return "json";
    }
    for(Definable definable : definableItems) {
      if(!definable.isDefined()) {
        String msg = (String) configMessages.get(ClassUtils.getShortName(definable.getClass()));
        if(StringUtils.isNotBlank(msg)) {
          msg = MessageFormat.format(msg, new Object[] {getRequest().getContextPath()});
          undefineMessages.add(msg);
        } else {
          undefineMessages.add(definable.getClass() + " has not defined.");
        }
      }
    }
    
    return "json";
  }
  
 
  /**
   * @return the undefineMessages
   */
  @JSON(serialize = true)
  public List<String> getUndefineMessages() {
    return undefineMessages;
  }

  /**
   * @return the allDefineMessage
   */
  @JSON(serialize = true)
  public String getAllDefineMessage() {
    return allDefineMessage;
  }
  
}
