package datashare.mainpage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.JsonCrudAction;

import datashare.admin.czcatalog.service.CzCatalogManager;
import datashare.admin.subject.model.Subject;
import datashare.admin.subject.service.SubjectManager;

/**
 * 数据共享首页Action类，主要功能是初始化首页右侧的菜单
 * @author Sam
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainPageAction extends JsonCrudAction<Subject, SubjectManager> {


  
  @Autowired
  private CzCatalogManager czCatalogManager;
  
  /**
   * 用于加载学科列表，地震目录列表，震相列表
   * 
   * @return
   */
  public String index() {
    
    return SUCCESS;
  }
  
  public List<Subject> getSubjects() {
    return getManager().get();
  }

  public List<Map<String, String>> getCzCatalogs() {
    return  czCatalogManager.getCat();
  }

  public List<Map<String, String>> getPhaseCats() {
    return czCatalogManager.getPhaseCat();
  }

  public List<Map<String, String>> getSeedCats() {
    return czCatalogManager.getSeedCat();
  }

}
