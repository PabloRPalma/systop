package quake.seismic.instrument.webapp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.dao.support.Page;

import quake.admin.ds.service.DataSourceManager;
import quake.base.webapp.AbstractQueryAction;
import quake.seismic.SeismicConstants;
import quake.seismic.instrument.InstrumentConstants;
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
  
  /**
   * 台网代码名称Map
   */
  public Map<String, String> getNetworkInfoMap() {
    return SeismicConstants.NETWORK_INFO;
  }

  /**
   * 返回台网代码名称对应Map，用于页面查询条件的显示。
   */
  @SuppressWarnings("unchecked")
  public Map<String, String> getNetCodes() {
    Instrument instr = new Instrument();
    instr.setSchema(dataSourceManager.getStationSchema());
    List<String> list = instrumentDao.getTemplate().queryForList("cz.queryNetCodeAtInstr", instr);
    Map map = new LinkedHashMap();
    for (String netCode : list) {
      if (StringUtils.isNotBlank(netCode) && SeismicConstants.NETWORK_INFO.containsKey(netCode)) {
        map.put(netCode, SeismicConstants.NETWORK_INFO.get(netCode));
      }
    }
    return map;
  }
  
  /**
   * 仪器用途
   */
  public Map<String, String> getUseTypes() {
    return InstrumentConstants.USE_TYPE;
  }
  /**
   * 仪器类型
   */
  public Map<String, String> getInstrTypes() {
    return InstrumentConstants.INSTR_TYPE;
  }
  public Instrument getModel() {
    return model;
  }

  public void setModel(Instrument model) {
    this.model = model;
  }


}
