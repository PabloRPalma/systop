package datashare.admin.subject.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.admin.method.model.Method;
import datashare.admin.method.service.MethodManager;
import datashare.admin.subject.model.Subject;
import datashare.admin.subject.service.SubjectManager;

/**
 * 学科管理Action
 * @author wbb
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SubjectAction extends DefaultCrudAction<Subject, SubjectManager> {
  private MethodManager methodManager;

  /**
   * 得到所有测项列表
   * @return
   */
  public List<Method> getMethodAll() {
    return methodManager.get();
  }

  @Autowired
  public void setMethodManager(MethodManager methodManager) {
    this.methodManager = methodManager;
  }
}
