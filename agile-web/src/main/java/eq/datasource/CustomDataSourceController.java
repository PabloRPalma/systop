package eq.datasource;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import agileweb.support.springmvc.ControllerContext;
import eq.datasource.factory.DataSourceFactory;

@SuppressWarnings("unchecked")
@Controller
public class CustomDataSourceController {
  private DataSourceFactory dataSourceFactory;
  
  @RequestMapping
  public DataSourceInfo edit(@RequestParam("type") String type) {
    HttpServletRequest request = ControllerContext.getInstance().getRequest();
    request.setAttribute("types", DataSourceConstants.DB_TYPES);
    
    Map dsi = dataSourceFactory.getDsInfo(type);
    
    if(MapUtils.isEmpty(dsi)) {
      return new DataSourceInfo() ;
    }
    DataSourceInfo dsInfo = new DataSourceInfo();
    dsInfo.setType((String) dsi.get("type"));
    dsInfo.setUsername((String) dsi.get("username"));
    dsInfo.setUrl((String) dsi.get("url"));
    dsInfo.setPassword((String) dsi.get("password"));
    
    return dsInfo;
  }
  
  @RequestMapping
  public String save(@ModelAttribute("dataSourceInfo") DataSourceInfo dsi) {
    dataSourceFactory.createDataSourceInfo(dsi);
    return "redirect:edit.do?type=" + dsi.getType();
  }
  /**
   * @param dataSourceFactory the dataSourceFactory to set
   */
  @Autowired
  public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }
}
