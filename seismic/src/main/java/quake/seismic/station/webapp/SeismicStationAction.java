package quake.seismic.station.webapp;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.DataType;
import quake.ProvinceLatlng;
import quake.admin.ds.service.DataSourceManager;
import quake.base.webapp.AbstractQueryAction;
import quake.seismic.SeismicConstants;
import quake.seismic.station.dao.AbstractStationDao;
import quake.seismic.station.dao.ExportStationDao;
import quake.seismic.station.dao.GridStationDao;
import quake.seismic.station.model.Criteria;
import quake.seismic.station.model.InstrDic;

import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;
import com.systop.core.Constants;
import com.systop.core.util.DateUtil;
import com.systop.core.util.ResourceBundleUtil;

/**
 * 测震台站查询Action
 * 
 * @author du
 * 
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@SuppressWarnings( { "unchecked", "serial" })
public class SeismicStationAction extends AbstractQueryAction<Criteria> {

  /**
   * 最大行数，用于设置limit
   */
  public static final int MAX_ROWS = 10000000;

  /**
   * 用于表格查询的测震台站DAO
   */
  @Autowired(required = true)
  @Qualifier("gridStationDao")
  private GridStationDao gridStationDao;

  /**
   * 用于导出台站DAO
   */
  @Autowired(required = true)
  @Qualifier("exportStationDao")
  private ExportStationDao exportStationDao;

  @Autowired
  private ProvinceLatlng provinceLatlng;

  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  /**
   * Use freemarker as the mail body template.
   */
  @Autowired
  @Qualifier("freeMarkerTemplateRender")
  private TemplateRender templateRender;

  /**
   * 测震台站查询条件类
   */
  private Criteria model = new Criteria();

  /**
   * 网站访问前缀
   */
  private String ctxUrl;

  /**
   * 表格查询Action
   */
  public String list() {
    try {
      model.setPage(getPage());
      model.setOrder(getSortDir());
      // 测震SCHEMA
      model.setSchema(dataSourceManager.getQzSchema());
      /*
       * 不同省份有不同的台网代码，暂时不考虑台网问题,即使考虑台网，如何取得有待确定....
       * model.setNetCode(SeismicConstants.NETWORK_INFO_HE);
       */
      if (StringUtils.isNotEmpty(model.getStartDate())
          && StringUtils.isNotEmpty(model.getEndDate())) {
        Date first = DateUtil.firstSecondOfDate(DateUtil.convertStringToDate(model.getStartDate()
            + "-1"));
        Date end = DateUtil.firstSecondOfDate(DateUtil.convertStringToDate(model.getEndDate()
            + "-31"));
        // logger.info("开始日期：" + first + " 结束日期：" + end);
        if (first != null && end != null) {
          model.setStartTimeOfTheYear(first);
          model.setEndTimeOfTheYear(end);
        }
      }
      List<Map> list = gridStationDao.queryStation(model);

      if (list != null) {
        getRequest().setAttribute("items", list);
      } else {
        clean(); // 即使没有查到数据，也要设置页面上的分页信息，确保显示的结果正确
      }
    } catch (Exception e) {
      logger.error("数据查询错误{}", e.getMessage());
      e.printStackTrace();
      clean();
    }
    return "list";
  }

  /**
   * 根据查询条件执行测震台站数据查询，生成Xml格式数据。
   */
  @Deprecated
  public String stationSettings() {
    // 测震SCHEMA
    model.setSchema(dataSourceManager.getCzSchema());
    /*
     * 不同省份有不同的台网代码，暂时不考虑台网问题,即使考虑台网，如何取得有待确定....
     * model.setNetCode(SeismicConstants.NETWORK_INFO_HE);
     */
    if (StringUtils.isNotEmpty(model.getStartDate()) && StringUtils.isNotEmpty(model.getEndDate())) {
      try {
        Date first = DateUtil.firstSecondOfDate(DateUtil.convertStringToDate(model.getStartDate()
            + "-1"));
        Date end = DateUtil.firstSecondOfDate(DateUtil.convertStringToDate(model.getEndDate()
            + "-31"));
        // logger.info("开始日期：" + first + " 结束日期：" + end);
        if (first != null && end != null) {
          model.setStartTimeOfTheYear(first);
          model.setEndTimeOfTheYear(end);
        }
      } catch (ParseException e) {
        logger.error("日期转换错误{}", e.getMessage());
        e.printStackTrace();
      }
    }
    List data = gridStationDao.queryStation(model);
    render(getResponse(), parseFtl(data, "station-settings"), "text/xml");
    return null;
  }

  /**
   * 根据条件查询台站信息，为GIS提供list
   */
  public String stationGis() {
    // 测震SCHEMA
    model.setSchema(dataSourceManager.getQzSchema());
    /*
     * 不同省份有不同的台网代码，暂时不考虑台网问题,即使考虑台网，如何取得有待确定....
     * model.setNetCode(SeismicConstants.NETWORK_INFO_HE);
     */
    if (StringUtils.isNotEmpty(model.getStartDate()) && StringUtils.isNotEmpty(model.getEndDate())) {
      try {
        Date first = DateUtil.firstSecondOfDate(DateUtil.convertStringToDate(model.getStartDate()
            + "-1"));
        Date end = DateUtil.firstSecondOfDate(DateUtil.convertStringToDate(model.getEndDate()
            + "-31"));
        // logger.info("开始日期：" + first + " 结束日期：" + end);
        if (first != null && end != null) {
          model.setStartTimeOfTheYear(first);
          model.setEndTimeOfTheYear(end);
        }
      } catch (ParseException e) {
        logger.error("日期转换错误{}", e.getMessage());
        e.printStackTrace();
      }
    }
    List data = gridStationDao.queryStation(model);
    getRequest().setAttribute("items", data);
    if (StringUtils.isNotBlank(model.getNetCode())) {
      getRequest().setAttribute("currentProvince",
          provinceLatlng.getProvinceByCode(model.getNetCode()));
    } else {
      getRequest().setAttribute("currentProvince", provinceLatlng.getCurrentProvince());
    }
    return "stationmap";
  }

  /**
   * 使用给出的数据，渲染freemarker模板，用于根据查询条件动态生成amcharts 设置文件.
   * 
   * @param data 给出模板所需要的数据.
   * @return 由freemarker生成的String
   */
  private String parseFtl(List data, String template) {
    // TemplateContext包括了渲染模板所需的数据、模板名称和输出流对象
    TemplateContext templateCtx = new TemplateContext();
    // 传入模板名称（包括路径）
    templateCtx.setTemplate(new Template(template));
    // 传入输出流对象
    StringWriter writer = new StringWriter();
    templateCtx.setWriter(writer);
    // 传入渲染模板所需数据
    templateCtx.addParameter("stations", data);
    // 渲染模板
    templateRender.renderTemplate(templateCtx);

    return writer.toString();
  }

  /**
   * 提供测震台站查询访问地址，并重定向到第三方提供的JSP页面。
   */
  public String xmlUrlJsp() {
    StringBuffer staUrl = new StringBuffer();
    staUrl.append(ctxUrl).append(SeismicConstants.STATION_INFO_URL).append("?model.startDate=")
        .append(model.getStartDate()).append("&model.endDate=").append(model.getEndDate()).append(
            "&model.instrumentId=").append(model.getInstrumentId()).append("&model.datarecordId=")
        .append(model.getDatarecordId());
    // logger.info("访问地址：" + staUrl.toString());
    getRequest().setAttribute("xmlUrl", staUrl.toString());
    return "xmlUrl";
  }

  /**
   * 地震计map,页面上显示的下拉列表
   */
  public List<Map> getInstrumentsMap() {
    String instrType = ResourceBundleUtil.getString(Constants.RESOURCE_BUNDLE,
        "quake.instrtype.seismometer", "地震计");
    return getInstrByType(instrType);
  }

  /**
   * 数采map，页面上显示的下拉列表
   */
  public List<Map> getDatarecordsMap() {
    String instrType = ResourceBundleUtil.getString(Constants.RESOURCE_BUNDLE,
        "quake.instrtype.das", "数采");
    return getInstrByType(instrType);
  }

  /**
   * 根据不同的仪器类型取得对应的仪器
   * 
   * @param instrType 仪器类型
   * @return 仪器列表
   */
  private List<Map> getInstrByType(String instrType) {
    List list = Collections.EMPTY_LIST;
    InstrDic instrDic = new InstrDic();
    instrDic.setSchema(dataSourceManager.getCzSchema());
    if (StringUtils.isNotEmpty(instrType)) {
      instrDic.setTypeLen(instrType.length());
      instrDic.setInstrType(instrType);
    }
    // 分别处理Oracle和Mysql
    if (dataSourceManager.isOracle(DataType.SEISMIC)) {
      list = gridStationDao.getAllInstrument(instrDic, AbstractStationDao.SQL_INSTR_ID_ORACLE);
    } else {
      list = gridStationDao.getAllInstrument(instrDic, AbstractStationDao.SQL_INSTR_ID_MYSQL);
    }

    return list;
  }

  /**
   * 台网代码名称Map
   */
  public Map<String, String> getNetworkInfoMap() {
    return SeismicConstants.NETWORK_INFO;
  }

  /**
   * 返回本省StationInfo表中台网代码名称对应Map，用于页面查询条件的显示。
   */
  public Map<String, String> getNetCodes() {
    Criteria c = new Criteria();
    c.setSchema(dataSourceManager.getQzSchema());
    List<String> list = gridStationDao.getTemplate().queryForList("cz.queryNetCode", c);
    Map map = new LinkedHashMap();
    for (String netCode : list) {
      if (StringUtils.isNotBlank(netCode) && SeismicConstants.NETWORK_INFO.containsKey(netCode)) {
        map.put(netCode, SeismicConstants.NETWORK_INFO.get(netCode));
      }
    }
    return map;
  }

  /**
   * 导出EQT格式数据
   * 
   * @return
   */
  public String eqt() {
    String data = exportEqtData();
    logger.debug("导出的数据：{}", data);
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"EQT.txt\"");
    render(getResponse(), data, "text/html");
    return null;

  }

  /**
   * 根据数据格式导出相应数据
   * 
   * @return
   */
  private String exportEqtData() {
    model.setSchema(dataSourceManager.getQzSchema());
    StringBuffer buf = exportStationDao.queryForEqt(model);
    return buf.toString();
  }

  /**
   * @return the model
   */
  @Override
  public Criteria getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(Criteria model) {
    this.model = model;
  }

  /**
   * @return the ctxUrl
   */
  public String getCtxUrl() {
    return ctxUrl;
  }

  /**
   * @param ctxUrl the ctxUrl to set
   */
  public void setCtxUrl(String ctxUrl) {
    this.ctxUrl = ctxUrl;
  }
}
