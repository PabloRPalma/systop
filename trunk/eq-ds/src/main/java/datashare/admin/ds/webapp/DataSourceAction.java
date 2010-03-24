package datashare.admin.ds.webapp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.core.webapp.struts2.action.BaseAction;

import datashare.admin.ds.model.DataSourceInfo;
import datashare.admin.ds.service.DataSourceManager;

/**
 * 数据源管理Action类
 * @author Sam
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DataSourceAction extends BaseAction implements ModelDriven<DataSourceInfo> {

  private DataSourceInfo model = new DataSourceInfo();
  /**
   * <code>DataSourceInfo</code>管理类
   */
  @Autowired(required = true)
  private DataSourceManager manager;
  
  /**
   * 编辑或者新建Action
   */
  public String edit() {
    model = manager.get();
    if(model == null) {
      model = new DataSourceInfo();
    }
    return INPUT;
  }
   
  /**
   * 保存Action
   */
  public String save() {
    try {
      model.setQzSchema(model.getQzSchema().toUpperCase());
      manager.save(model);
      addActionMessage("您已经成功设置了测震和前兆数据源.");
      return SUCCESS;
    } catch (Exception e) {
      addActionError(e.getMessage());
    }
    return INPUT;
  }
  
  /**
   * 用于页面显示
   */
  @SuppressWarnings("unchecked")
  public Map getCzTypes() {
    return manager.getCzTypes();
  }

  
  
  @Override
  public DataSourceInfo getModel() {
    return model;
  }
  
  
  /**
   * @param model the dataSourceInfo to set
   */
  public void setModel(DataSourceInfo model) {
    this.model = model;
  }
}
