package datashare.admin.czcatalog.webapp;

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.admin.czcatalog.CzConstants;
import datashare.admin.czcatalog.model.CzCatalog;
import datashare.admin.czcatalog.service.CzCatalogManager;

/**
 * 管理地震目录Action
 * @author wbb
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CzCatalogAction extends DefaultCrudAction<CzCatalog, CzCatalogManager> {

  /**
   * 保存地震目录
   * @see com.systop.core.webapp.struts2.action.DefaultCrudAction#save()
   */
  @Validations(requiredStrings = {
      @RequiredStringValidator(fieldName = "model.cltName", message = "地震目录表名是必须的."),
      @RequiredStringValidator(fieldName = "model.clcName", message = "地震目录名称是必须的.")
  })
  @Override
  public String save() {
    return super.save();
  }
  
  public Map<String, String> getDisTypeMap() {
    return CzConstants.DISTYPE_MAP;
  }

  public Map<String, String> getSeedDisMap() {
    return CzConstants.SEEDDIS_MAP;
  }
}
