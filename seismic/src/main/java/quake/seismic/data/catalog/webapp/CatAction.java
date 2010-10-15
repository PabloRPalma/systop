package quake.seismic.data.catalog.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.ProvinceLatlng;
import quake.admin.catalog.model.QuakeCatalog;
import quake.admin.catalog.service.QuakeCatalogManager;
import quake.admin.ds.service.DataSourceManager;
import quake.base.webapp.AbstractQueryAction;
import quake.base.webapp.NumberFormatUtil;
import quake.seismic.data.catalog.dao.impl.ExportCatDao;
import quake.seismic.data.catalog.dao.impl.GridCatDao;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.seed.dao.impl.SeedDao;

import com.systop.core.dao.support.Page;


/**
 * 地震目录查询查
 * @author wbb
 *
 */
@SuppressWarnings({"serial","unchecked"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CatAction extends AbstractQueryAction<Criteria> {
  
  private Criteria model = new Criteria();

  /**
   * 用于表格查询的测震DAO
   */
  @Autowired(required = true)
  @Qualifier("gridCatDao")
  private GridCatDao gridCatDao;
  /**
   * 用于表格导出的测震DAO
   */
  @Autowired(required = true)
  @Qualifier("exportCatDao")
  private ExportCatDao exportCatDao;

  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  @Autowired(required = true)
  private QuakeCatalogManager czCatalogManager;
  
  @Autowired
  private ProvinceLatlng provinceLatlng;
  
  @Autowired(required=true)
  private SeedDao seedDao;

  private List<Map> cats = new ArrayList<Map>();
  /**
   * 进入地震目录查询页面
   * @return
   */
  public String index() {
    if (StringUtils.isNotBlank(model.getTableName())) {
      QuakeCatalog czCat = czCatalogManager.queryByCltName(model.getTableName());
      if (czCat == null) {
        addActionError("您选择的地震目录在本系统中不存在.");
      } else {
        model.setTableName(czCat.getCltName());
        model.setMagTname(czCat.getMagTname());
        model.setPhaseTname(czCat.getPhaseTname());
        model.setClcName(czCat.getClcName());
        model.setClDescn(czCat.getClDescn());
        model.setDisType(czCat.getDisType());
      }
    } else {
      addActionError("请选择要查询的地震目录，进行数据查询.");
    }
    return "index";
  }
  /**
   * 显示GIS信息
   * @return
   */
  @SuppressWarnings("unchecked")
  public String showGis(){
    if (StringUtils.isNotBlank(model.getTableName())) {
      try {
        model.setPage(getPage());
        //测震SCHEMA
        model.setSchema(dataSourceManager.getCzSchema());
        model.setSortProperty(getSortProperty());
        model.setSortDir(getSortDir());
        List<Map> list = gridCatDao.queryForGis(model);
        for(Map map : list) {
          map.put("EPI_LON", NumberFormatUtil.format(map.get("EPI_LON"), 2));
          map.put("EPI_LAT", NumberFormatUtil.format(map.get("EPI_LAT"), 2));
        }
        getRequest().setAttribute("items", list);
      } catch (Exception e) {
        logger.error("数据查询错误{}", e.getMessage());
        e.printStackTrace();
        clean();
      }
    }
    //查找当前省份
    getRequest().setAttribute("currentProvince", provinceLatlng.getCurrentProvince());
    return "quake";
  }
  
  /**
   * 进入震相查询页面，实际是有震相数据的地震目录查询页。此页没有EQT与WKF数据下载功能
   * @return
   */
  public String indexPhase() {
    //执行查询，与地震目录完全一样，只是返回的页面不同
    index();
    return "indexPhase";
  }
  
  /**
   * 地震目录查询返回结果
   * @return
   */
  public String listPhase() {
    //执行查询，与地震目录相同
    list();
    return "listPhase";
  }
  
  /**
   * 进入Seed查询页面
   */
  public String indexSeed(){
    index();
    return "indexSeed";
  }

  /**
   * 地震目录查询返回结果集
   * @return
   */
  public String listSeed(){
    list();
    seedDao.seedExists(cats);
    getRequest().setAttribute("items", cats);
    return "listSeed";
  }
  /**
   * 地震目录查询返回结果
   * @return
   */
  public String list() {
    if (StringUtils.isNotBlank(model.getTableName())) {
      try {
        model.setPage(getPage());
        //测震SCHEMA
        model.setSchema(dataSourceManager.getCzSchema());
        model.setSortProperty(getSortProperty());
        model.setSortDir(getSortDir());
        Page page = gridCatDao.query(model);
        cats = page.getData();
        getRequest().setAttribute("items", cats);
        restorePageData(page.getRows(), page.getPageSize());
      } catch (Exception e) {
        logger.error("数据查询错误{}", e.getMessage());
        e.printStackTrace();
        clean();
      }
    }
    return "list";
  }

  /**
   * 导出WKF格式数据
   * @return
   */
  public String exportWkf() {
    model.setExpType("WKF");
    String data = exportData();
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"WKF.txt\"");
    render(getResponse(), data, "text/html");
    return null;
  }

  /**
   * 导出EQT格式数据
   * @return
   */
  public String exportEqt() {
    model.setExpType("EQT");
    String data = exportData();
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"EQT.txt\"");
    render(getResponse(), data, "text/html");
    return null;
  }

  
  private String exportData() {
    if (StringUtils.isNotBlank(model.getTableName())) {
      //测震SCHEMA
      model.setSchema(dataSourceManager.getCzSchema());
      StringBuffer buf = exportCatDao.query(model);
      return buf.toString();
    } else {
      return null;
    }
  }

  @Override
  public Criteria getModel() {
    return model;
  }

  public void setModel(Criteria model) {
    this.model = model;
  }
}
