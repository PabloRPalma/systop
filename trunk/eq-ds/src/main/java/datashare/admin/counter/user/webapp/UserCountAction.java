package datashare.admin.counter.user.webapp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.admin.counter.user.service.UserCountManager;

@SuppressWarnings({"unchecked","serial"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserCountAction extends DefaultCrudAction<User,UserCountManager> {

  public String index(){
    List<Map> list = getManager().queryRoles();
    getRequest().setAttribute("items", list);
    return "index";
  }
}
