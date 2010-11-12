package quake.seismic.instrument.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.dao.support.Page;

import quake.admin.ds.service.DataSourceManager;
import quake.base.webapp.AbstractQueryAction;
import quake.seismic.instrument.dao.InstrumentDao;
import quake.seismic.instrument.model.Instrument;

/**
 * 仪器查询Action
 * @author DU
 *
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InstrumentAction extends AbstractQueryAction<Instrument> {

  /**
   * 用于仪器查询的DAO
   */
  @Autowired(required = true)
  @Qualifier("instrumentDao")
  private InstrumentDao instrumentDao;
  
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;
  
  private Instrument model = new Instrument();

  /**
   * 查询仪器
   */
  public String index() {
    try {
      model.setPage(getPage());
      model.setSortProperty(getSortProperty());
      model.setSortDir(getSortDir());
      model.setSchema(dataSourceManager.getStationSchema());
      Page page = instrumentDao.query(model);
      if (page != null) {
        getRequest().setAttribute("items", page.getData());
        restorePageData(page.getRows(), page.getPageSize());
      } else {
        clean(); //即使没有查到数据，也要设置页面上的分页信息，确保显示的结果正确
      }
    } catch (Exception e) {
      logger.error("数据查询错误{}", e.getMessage());
      e.printStackTrace();
      clean();
    }
    return "index";
  }
  
  public Instrument getModel() {
    return model;
  }

  public void setModel(Instrument model) {
    this.model = model;
  }


}
