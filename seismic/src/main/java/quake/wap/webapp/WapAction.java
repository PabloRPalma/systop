package quake.wap.webapp;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.catalog.service.QuakeCatalogManager;
import quake.admin.ds.service.DataSourceManager;
import quake.base.webapp.AbstractQueryAction;
import quake.base.webapp.NumberFormatUtil;
import quake.seismic.data.catalog.dao.impl.GridCatDao;
import quake.seismic.data.catalog.model.Criteria;
import quake.special.dao.SpecialDao;
import quake.wap.WapConstants;
import quake.wap.dao.WapDao;

import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;

@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WapAction extends AbstractQueryAction<Criteria> {
  private Criteria model = new Criteria();
  /** pageCurrent 地震目录列表当前页索引 ,初始化为0 */
  private int pageCurrent = 1;

  /** pageSize 页容量 */
  private static final int pageSize = 5;

  private List<Map> cats = new ArrayList<Map>();

  /** pageCount 数据页总数 */
  private int pageCount = 0;

  /** beginIndex 起始索引 */
  private int beginIndex = 0;

  private String tableName;

  private String catalogId;

  /**
   * /** 用于表格查询的测震DAO
   */
  @Autowired(required = true)
  @Qualifier("gridCatDao")
  private GridCatDao gridCatDao;

  @Autowired(required = true)
  @Qualifier("wapDao")
  private WapDao wapDao;

  @Autowired(required = true)
  @Qualifier("specialDao")
  private SpecialDao specialDao;
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  @Autowired
  private QuakeCatalogManager catalogManager;

  /** 地震目录 静态化，避免重复查询 */
  private static List<Map<String, String>> catalogs = null;

  public String index() {
    if (StringUtils.isNotBlank(tableName)) {
      model.setTableName(tableName);
      model.setPage(new Page(Page.start(pageCurrent, pageSize), pageSize));
      // 测震SCHEMA
      model.setSchema(dataSourceManager.getSeismicSchema());
      model.setSortProperty(getSortProperty());
      model.setSortDir(getSortDir());

      model.setStart(Page.start(pageCurrent, pageSize));
      model.setSize(pageSize);
      try {
        model.setStartDate(DateUtil.add(DateUtil.convertStringToDate(WapConstants.TIME),
            Calendar.DATE, WapConstants.QUERY_QC_DAY));
        model.setEndDate(DateUtil.convertStringToDate(WapConstants.TIME));
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      Page page = gridCatDao.query(model);
      cats = page.getData();
      for (int i = 0; i < cats.size(); i++) {
        Map map = cats.get(i);
        if (map.get("M") != null) {
          map.put("M", NumberFormatUtil.format(map.get("M"), 1));
        }
        if (map.get("EPI_LON") != null) {
          map.put("EPI_LON", NumberFormatUtil.format(map.get("EPI_LON"), 2));
          map.put("Map_LON", NumberFormatUtil.format(map.get("EPI_LON"), 6));

        }
        if (map.get("EPI_LAT") != null) {
          map.put("EPI_LAT", NumberFormatUtil.format(map.get("EPI_LAT"), 2));
          map.put("Map_LAT", NumberFormatUtil.format(map.get("EPI_LAT"), 6));
        }
      }
      int count = wapDao.getCatalogCount(model);
      pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;// 计算总页数
      beginIndex = pageSize * (pageCurrent - 1) + 1;// 计算起始索引
    }
    return "index";
  }

  /**
   * 显示GIS信息
   * 
   * @return
   */
  public String showGis() {
    if (StringUtils.isNotBlank(tableName)) {

      try {
        model.setPage(new Page(Page.start(pageCurrent, pageSize), pageSize));
        // 测震SCHEMA
        model.setSchema(dataSourceManager.getSeismicSchema());
        model.setSortProperty(getSortProperty());
        model.setSortDir(getSortDir());
        model.setQcId(catalogId);

        Page page = specialDao.queryQcForObeject(model);
        List<Map> qcList = new ArrayList<Map>();
        qcList = page.getData();
        if (qcList.size() == 1) {
          Map map = qcList.get(0);
          map.put("EPI_LON", NumberFormatUtil.format(map.get("EPI_LON"), 2));
          map.put("EPI_LAT", NumberFormatUtil.format(map.get("EPI_LAT"), 2));
          getRequest().setAttribute("cMap", map);
        }
      } catch (Exception e) {
        logger.error("数据查询错误{}", e.getMessage());
        e.printStackTrace();
        clean();
      }
    }
    return "quake";
  }

  public String wap() {
    if (catalogs == null) { // 查询地震目录
      catalogs = catalogManager.getCat();
    }
    return "index";
  }

  public int getPageCurrent() {
    return pageCurrent;
  }

  public void setPageCurrent(int pageCurrent) {
    this.pageCurrent = pageCurrent;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public int getBeginIndex() {
    return beginIndex;
  }

  public void setBeginIndex(int beginIndex) {
    this.beginIndex = beginIndex;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public static int getPagesize() {
    return pageSize;
  }

  public Criteria getModel() {
    return model;
  }

  public void setModel(Criteria model) {
    this.model = model;
  }

  public static List<Map<String, String>> getCatalogs() {
    return catalogs;
  }

  public static void setCatalogs(List<Map<String, String>> catalogs) {
    WapAction.catalogs = catalogs;
  }

  public List<Map> getCats() {
    return cats;
  }

  public void setCats(List<Map> cats) {
    this.cats = cats;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }
}
