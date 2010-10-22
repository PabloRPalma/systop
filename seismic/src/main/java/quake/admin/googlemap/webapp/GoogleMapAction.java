package quake.admin.googlemap.webapp;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.googlemap.model.GoogleMap;
import quake.admin.googlemap.service.GoogleMapManager;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.core.webapp.struts2.action.BaseAction;


/**
 * GoogleMap密钥Action
 * @author dhm
 *
 */
@SuppressWarnings({"serial","unchecked"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GoogleMapAction extends BaseAction implements ModelDriven<GoogleMap> {
  
  private GoogleMap model = new GoogleMap();
  
  /**
   * googleMap密钥manager
   */
  @Autowired(required=true)
  private GoogleMapManager googleMapManager;

  /**
   * 进入密钥编辑页面
   * @return
   */
  public String edit() {
    model = googleMapManager.get();
    if(model == null) {
      model = new GoogleMap();
    }
    return "input";
  }
  
  /**
   * 保存密钥信息
   * @return
   */
  public String save() {
    if(StringUtils.isBlank(model.getGoogleMapId())) {
      addActionError("googleMap密钥不允许为空!");
      return "input";
    }
    try{
      googleMapManager.save(model);
      addActionMessage("配置成功！");
      return "success";
    }catch (Exception e) {
      addActionError(e.getMessage());
    }
    return "input";
  }
  public GoogleMap getModel() {
    return model;
  }
  public void setModel(GoogleMap model) {
    this.model = model;
  }
  
}
