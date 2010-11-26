package quake.seismic.station.webapp;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.ProvinceLatlng;
import quake.admin.ds.service.DataSourceManager;
import quake.base.webapp.AbstractQueryAction;
import quake.seismic.SeismicConstants;
import quake.seismic.data.seed.dao.impl.SeedDao;
import quake.seismic.instrument.InstrumentConstants;
import quake.seismic.station.StationConstants;
import quake.seismic.station.dao.AbstractStationDao;
import quake.seismic.station.dao.ExportRespDao;
import quake.seismic.station.dao.ExportXmlDao;
import quake.seismic.station.dao.GridStationDao;
import quake.seismic.station.model.Criteria;
import quake.seismic.station.model.InstrDic;

import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;
import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.util.DateUtil;

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
  @Qualifier("exportRespDao")
  private ExportRespDao exportRespDao;

  
  /**
   * 用于导出台站DAO
   */
  @Autowired(required = true)
  @Qualifier("exportXmlDao")
  private ExportXmlDao exportXmlDao;
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
  
  @Autowired
  private SeedDao seedDao;
  
  @Autowired
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao dao;

  /**
   * 测震台站查询条件类
   */
  private Criteria model = new Criteria();

  /**
   * 台站ID
   */
  private String stataionId;

  /**
   *通道ID
   */
  private String channelId;

  /**
   * 表格查询Action
   */
  public String list() {
    try {
      model.setPage(getPage());
      model.setOrder(getSortDir());
      // 测震SCHEMA
      model.setSchema(dataSourceManager.getStationSchema());
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
    model.setSchema(dataSourceManager.getSeismicSchema());
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
    model.setSchema(dataSourceManager.getStationSchema());
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
   * 查询台站信息，并加入台站连续波形信息
   * @return
   */
  public String stationSeeds() {
    list();
    List<Map> stations = (List<Map>) getRequest().getAttribute("items");
    if(CollectionUtils.isNotEmpty(stations)) {
      for(Map sta : stations) {
        if(MapUtils.isNotEmpty(sta) && sta.get("NET_CODE") != null
            && sta.get("STA_CODE") != null) {
          if(seedDao.isStationSeedExists(sta.get("NET_CODE").toString(), 
              sta.get("STA_CODE").toString())) {
            sta.put("HAS_SEED", Boolean.TRUE);
          } else {
            sta.put("HAS_SEED", Boolean.FALSE);
          }
        }
      }
    }
    getRequest().setAttribute("items", stations);
    return "stationSeeds";  
  }
  
  /**
   * 进入波形文件导出页面
   * @return
   */
  public String viewStaForSeed() {
    getModel().setSchema(dataSourceManager.getStationSchema());
    //用于输出当前台站信息
    List<Map> list = gridStationDao.queryStation(getModel());
    if(CollectionUtils.isNotEmpty(list)) {
      getRequest().setAttribute("sta", list.get(0));
    }
    // 查询当前台站的最大和最小时间
    Object obj = dao.findObject("select min(s.startTime), max(s.endTime)"
        + " from StationSeed s where s.net=? and s.sta=?",
        getModel().getNetCode(), 
        getModel().getStaCode());
    if(obj != null && obj.getClass().isArray()) {
      getRequest().setAttribute("startTime", ((Object[]) obj)[0]);
      getRequest().setAttribute("endTime", ((Object[]) obj)[1]);
    }
    //查询当前台站的通道
    List l = dao.query("select distinct(s.cha) from StationSeed s where s.net=? and s.sta=?",
        getModel().getNetCode(), 
        getModel().getStaCode());
    if(CollectionUtils.isNotEmpty(l)) {
      getRequest().setAttribute("cha", this.toJson(l));
    }
    
    return "viewStaForSeed";
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
   * 地震计map,页面上显示的下拉列表
   */
  public List<Map> getSensorMap() {
    return getInstrByType(InstrumentConstants.INSTR_TYPE_SENSOR);
  }

  /**
   * 数采map，页面上显示的下拉列表
   */
  public List<Map> getDigitizerMap() {
    return getInstrByType(InstrumentConstants.INSTR_TYPE_DIGITIZER);
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
    instrDic.setSchema(dataSourceManager.getStationSchema());
    if (StringUtils.isNotEmpty(instrType)) {
      instrDic.setInstrType(instrType);
    }
    list = gridStationDao.getInstrumentByType(instrDic, AbstractStationDao.SQL_INSTR_TYPE);
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
    c.setSchema(dataSourceManager.getStationSchema());
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
   * 台基类型
   * 
   * @return
   */
  public Map<String, String> getRockTypes() {
    return StationConstants.ROCK_TYPE;
  }

  /**
   * 台站类型
   * 
   * @return
   */
  public Map<String, String> getStaTypes() {
    return StationConstants.STA_TYPE;
  }

  /**
   * 导出resp格式数据
   * 
   * @return
   */
  public String resp() {
    String data = exportRespData();
    logger.debug("导出的数据：{}", data);
    String fileName = getFileName();
    getResponse().addHeader("Content-Disposition", fileName);
    render(getResponse(), data, "text/html");
    return null;

  }
  /**
   * 导出xml格式数据
   * 
   * @return
   */
  public String xml() {
    String data = exportXmlData();
    logger.debug("导出的数据：{}", data);
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"text.xml\"");
    renderXml(getResponse(), data);
    return null;
  }
  /**
   * 根据数据格式导出相应数据
   * 
   * @return
   */
  private String exportXmlData() {
    StringBuffer buf = exportXmlDao.queryForXml(stataionId, dataSourceManager
        .getStationSchema());
    return buf.toString();
  }

  /**
   * 下载文件命名
   * 
   * @return
   */
  private String getFileName() {
    Criteria c = new Criteria();
    c.setChannelId(channelId);
    c.setSchema(dataSourceManager.getStationSchema());
    List<Map> ChannelList = exportRespDao.queryChannelById(c);
    Map channel = null;
    if (ChannelList.size() == 1) {
      channel = ChannelList.get(0);
    }
    StringBuffer sb = new StringBuffer();
    if (channel != null) {
      sb.append("\"RESP.").append(channel.get("NET_CODE").toString()).append(".").append(
          channel.get("STA_CODE").toString()).append("..").append(
          channel.get("CHN_CODE").toString()).append("\"");
    }
    String fileName = "attachment;filename=" + sb.toString();
    return fileName;
  }

  /**
   * 根据数据格式导出相应数据
   * 
   * @return
   */
  private String exportRespData() {
    StringBuffer buf = exportRespDao.queryForResp(channelId, dataSourceManager
        .getStationSchema());
    return buf.toString();
  }

  /**
   * 查看台站下的通道
   * 
   * @return
   */
  public String viewChannels() {

    model.setSchema(dataSourceManager.getStationSchema());
    model.setId(stataionId);
    List<Map> stationList = exportXmlDao.queryStationById(model);
    if (stationList.size() == 1) {
      Map m = stationList.get(0);
      model.setStaCode(m.get("STA_CODE").toString());
      model.setNetCode(m.get("NET_CODE").toString());
      List<Map> channelList = exportXmlDao.queryChannel(model);
      getRequest().setAttribute("items", channelList);
      getRequest().setAttribute("staName", m.get("STA_CNAME"));
      getRequest().setAttribute("netName", SeismicConstants.NETWORK_INFO.get(m.get("NET_CODE")));
    }
    return "viewChannels";
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

  public String getStataionId() {
    return stataionId;
  }

  public void setStataionId(String stataionId) {
    this.stataionId = stataionId;
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }
}
