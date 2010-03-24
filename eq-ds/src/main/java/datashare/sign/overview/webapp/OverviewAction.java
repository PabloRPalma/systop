package datashare.sign.overview.webapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.core.webapp.struts2.action.BaseAction;

import datashare.admin.ds.service.DataSourceManager;
import datashare.sign.data.model.Criteria;
import datashare.sign.overview.dao.OverviewDao;

/**
 * 数据总揽Action
 * @author Sam
 *
 */
@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OverviewAction extends BaseAction implements ModelDriven<Criteria>{
  @Autowired
  private OverviewDao overviewDao;
  @Autowired
  private DataSourceManager dsManager;
  private Criteria model = new Criteria();
  /**
   * 查询结果
   */
  private List items;
  
  /**
   * 列出QZ_DICT_STATIONITEMS表中的数据
   */
  public String index() {
    model.setSchema(dsManager.getQzSchema());
    items = overviewDao.query(model);
    return "index";
  }
  
  public List<Map> getStations() {
    return overviewDao.getStations(dsManager.getQzSchema());
  }
  
  /**
   * 是否数字化观测的Mapping，用于ECSide转换字段.
   */
  public Map getDigitalMapping() {
    Map map = new HashMap();
    map.put("0", "否");
    map.put("1", "是");
    return map;
  }
  /**
   * @return the items
   */
  
  public List getItems() {
    return items;
  }
  /**
   * @param items the items to set
   */
  public void setItems(List items) {
    this.items = items;
  }
  @Override
  public Criteria getModel() {
    return model;
  }
  

}
