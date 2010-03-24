package datashare.sign.station.webapp;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;

import datashare.ProvinceLatlng;
import datashare.admin.ds.service.DataSourceManager;
import datashare.admin.subject.model.Subject;
import datashare.admin.subject.service.SubjectManager;
import datashare.base.webapp.AbstractQueryAction;
import datashare.sign.prequery.model.Point;
import datashare.sign.prequery.model.Station;
import datashare.sign.station.dao.StationQueryDao;
import datashare.sign.station.model.SignStationCriteria;

/**
 * 前兆台站基本信息查询
 * @author DU
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@SuppressWarnings("unchecked")
public class SignStationAction extends AbstractQueryAction<SignStationCriteria> {
  
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  /**
   * 对应页面传来的查询条件，即model
   */
  private SignStationCriteria model = new SignStationCriteria();
  
  /**
   * Use freemarker as the mail body template.
   */
  @Autowired
  @Qualifier("freeMarkerTemplateRender")
  private TemplateRender templateRender;
  
  @Autowired(required = true)
  private SubjectManager subjectManager;
  
  @Autowired(required = true)
  private StationQueryDao stationQueryDao;
  
  /*@Autowired(required=true)
  private MethodManager methodManager;*/
  
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;
  
  /**
   * 用于获取当前省份
   * */
  @Autowired
  private ProvinceLatlng p;
  
  /**
   * 构建树型列表的JSON数据_学科-测项-仪器查询
   */
  private List jsonData;
  
  /**
   * 构建树型列表的JSON数据_台站查询
   */
  private List jsonDataForSta;
  
  /**
   * ajax得到学科下测项列表
   */
  private String subjectId;
  
  /**
   * 测项id
   */
  
  private String methodId;
  
  /**
   * 测项数据
   */
  private List jsonMethods;
  
  /**
   * 仪器数据
   */
  private List jsonInstruments;
  
  /**
   * 台站ID
   */
  private String stationId;
  
  /**
   * 台站测点ID
   */
  private String pointId;
  
  /**
   * 根据学科查询测项
   * json数据请求，在查询页面显示指定学科对应的测项
   */
  public String queryMethods() {
    Subject subject = subjectManager.get(subjectId);
    jsonMethods = new ArrayList();
    model.setSchema(dataSourceManager.getQzSchema());
    jsonMethods = stationQueryDao.queryMethodsByTwoCode(subject, model);
    return "methods";
  }

  /**
   * 根据学科查询仪器
   * json数据请求，在查询页面显示指定学科对应的仪器
   */
  public String queryInstruments() {
    Subject subject = subjectManager.get(subjectId);
    jsonInstruments = new ArrayList();
    model.setSchema(dataSourceManager.getQzSchema());
    jsonInstruments = stationQueryDao.queryInstrsByTwoCode(subject, model);
    return "instruments";
  }
  
  /**
   * 根据测项查询仪器
   * json数据请求，在查询页面显示指定测项对应的仪器
   */
  public String queryInstrumentsByMethod(){    
    jsonInstruments = new ArrayList();
    model.setSchema(dataSourceManager.getQzSchema());
    jsonInstruments = stationQueryDao.queryInstrsByMethod(methodId, model);
    return "instruments";
  }
  
  /**
   * 返回构建树型列表的JSON数据
   */
  public String staionsTree() {
    Subject subject = subjectManager.get(getModel().getSubjectId());
    getModel().setSchema(dataSourceManager.getQzSchema());
    jsonData = convertStation(stationQueryDao.buildStations(subject, getModel()));
    return "tree";
  }
  
  /**
   * 根据查询条件执行前兆台站数据查询，生成Xml格式数据_按学科-测项-仪器查询 
   */
  @Deprecated
  public String stationSettings() {
    Subject subject = subjectManager.get(getModel().getSubjectId());
    getModel().setSchema(dataSourceManager.getQzSchema());
    List data = stationQueryDao.queryStationForList(subject, getModel(), dataSourceManager.getQzSchema());
    render(getResponse(), parseFtl(data, "sign-station-settings"), "text/xml");
    
    return null;
  }
  
  /**
   * 按学科-测项-仪器查询前兆台站信息，生成Gis所需要的list数据
   */
  public String stationGis() {
    Subject subject = subjectManager.get(getModel().getSubjectId());
    getModel().setSchema(dataSourceManager.getQzSchema());
    List<Map> data = stationQueryDao.queryStationForList(subject, getModel(), dataSourceManager.getQzSchema());
    getRequest().setAttribute("items", data);
    //查找当前省份
    getRequest().setAttribute("currentProvince", p.getCurrentProvince());
    return "stagis";
  }
  
  /**
   * 根据查询条件执行前兆台站数据查询，生成Xml格式数据_按台站查询
   */
  @Deprecated
  public String stationForStaSettings() {
    getModel().setSchema(dataSourceManager.getQzSchema());
    List data = stationQueryDao.queryStationForStaList(getModel(), dataSourceManager.getQzSchema());
    render(getResponse(), parseFtl(data, "sign-station-settings"), "text/xml");
    
    return null;
  }
  
  /**
   * 按台站查询前兆台站信息，生成Gis所需要的list数据
   */
  public String stationForStaGis() {
    getModel().setSchema(dataSourceManager.getQzSchema());
    List<Map> data = stationQueryDao.queryStationForStaList(getModel(), dataSourceManager.getQzSchema());
    getRequest().setAttribute("items", data);
    //查找当前省份
    getRequest().setAttribute("currentProvince", p.getCurrentProvince());
    return "stagis";
  }
  
  /**
   * 使用给出的数据，渲染freemarker模板
   * 用于根据查询条件动态生成设置文件.
   * @param data 给出模板所需要的数据.
   * @return 由freemarker生成的String
   */
  private String parseFtl(List data, String template) {
    //TemplateContext包括了渲染模板所需的数据、模板名称和输出流对象
    TemplateContext templateCtx = new TemplateContext();
    //传入模板名称（包括路径）
    templateCtx.setTemplate(new Template(template));
    //传入输出流对象
    StringWriter writer = new StringWriter();
    templateCtx.setWriter(writer);
    //传入渲染模板所需数据
    templateCtx.addParameter("stations", data);
    //渲染模板
    templateRender.renderTemplate(templateCtx);

    return writer.toString();
  }
  
  /**
   * 把查询出的台站列表转换成符合TreeView控件要求的数据格式。
   * @param stations 台站list数据
   * @return List中以Map数据类型保存台站、测点信息。
   */
  private List convertStation(List<Station> stations) {
    if (stations.isEmpty()) {// 如果数据为空
      return emptyNodes();
    }
    
    List sData = new ArrayList();
    for (Station s : stations) { // 遍历台站信息
      Map sM = new HashMap();
      sM.put("text", "<a href='queryStationInfo.do?stationId=" + s.getId() 
          + "' target='pointInfo'>" + s.getName() + "</a>"); // 设置台站级节点名称
      sM.put("expanded", false); // 设置节点是否打开
      if (!s.getPoints().isEmpty()) {// 如果测点信息不为空
        List pData = new ArrayList();
        for (Point p : s.getPoints()) {// 遍历测点信息
          Map pM = new HashMap();
          pM.put("text", "<a href='queryPointInfo.do?stationId=" + s.getId() + "&pointId=" 
              + p.getId() + "' target='pointInfo'>" + p.getId() + "号测点_" + p.getName() + "</a>"); // 设置测点级节点名称
          pData.add(pM); // 测点列表增加测点信息
        }
        sM.put("children", pData); // 为台站级节点设置子节点：测点列表
      }
      sData.add(sM);// 台站列表增加台站信息
    }
    return sData;
  }
  
  /**
   * 返回提示没有可用数据的树型列表
   */
  private List emptyNodes() {
    List temps = new ArrayList();
    Map map = new HashMap();
    map.put("text", "没有可以访问的数据");
    map.put("expanded", false);
    temps.add(map);
    return temps;
  }
  
  /**
   * 前兆台站查询首页_按学科-仪器-测项查询
   */
  public String index() {
    List<Subject> subjects = subjectManager.get();
    //设置学科列表
    getRequest().setAttribute("subjects", subjects);
    List<Map<String, String>> instruments = stationQueryDao.queryInstruments();
    //设置仪器列表
    getRequest().setAttribute("instruments", instruments);
    return "index";
  }
  
  /**
   * 前兆台站查询首页_按台站查询
   */
  public String indexForSta() {
    return "indexForSta";
  }
  
  /**
   * 返回构建树型列表的JSON数据_按台站
   */
  public String staionsForStaTree() {
    getModel().setSchema(dataSourceManager.getQzSchema());
    jsonDataForSta = convertStation(stationQueryDao.buildStationsForSta(getModel()));
    return "treeForSta";
  }
  
  /**
   * 取得台站信息
   * 台站相关的洞体信息
   * 台站相关的井信息
   * 台站相关的泉信息
   * 台站相关的断层信息
   */
  public String queryStationInfo() {
    logger.debug("台站ID：" + stationId);
    model.setSchema(dataSourceManager.getQzSchema());
    model.setStationId(stationId);
    getRequest().setAttribute("station", stationQueryDao.queryStationInfo(model));
    
    //台站相关洞体信息
    List statCaveList = stationQueryDao.queryStatCaveInfo(model);
    getRequest().setAttribute("statCaveList", statCaveList);
    model.setStatCaveSize(statCaveList.size());
    logger.debug("台站相关洞体数量：" + statCaveList.size());
    //台站相关井信息
    List statWellList = stationQueryDao.queryStatWellInfo(model);
    getRequest().setAttribute("statWellList", statWellList);
    model.setStatWellSize(statWellList.size());
    logger.debug("台站相关井信息数量：" + statWellList.size());
    //台站相关泉信息
    List statSpringList = stationQueryDao.queryStatSpringInfo(model);
    getRequest().setAttribute("statSpringList", statSpringList);
    model.setStatSpringSize(statSpringList.size());
    logger.debug("台站相关泉信息数量：" + statSpringList.size());
    //台站相关断层信息
    List statFaultsList = stationQueryDao.queryStatFaultsInfo(model);
    getRequest().setAttribute("statFaultsList", statFaultsList);
    model.setFaultsSize(statFaultsList.size());
    logger.debug("台站相关断层信息数量：" + statFaultsList.size());
    
    return "staInfo";
  }
  
  /**
   * 取得台站测点及测点相关信息
   * 测点相关的仪器运行信息
   * 测点相关的地磁方位标信息
   * 测点相关的地磁墩差信息
   * 测点相关的测项分量信息
   * 测点相关的仪器参数信息
   * 测点相关的洞体信息
   * 测点相关的井泉信息
   */
  public String queryPointInfo() {
    logger.debug("台站ID：" + stationId + " 测点ID:" + pointId);
    model.setSchema(dataSourceManager.getQzSchema());
    model.setStationId(stationId);
    model.setPointId(pointId);
    //台站测点信息
    getRequest().setAttribute("point", stationQueryDao.queryPointInfo(model));
    //仪器运行
    /*
     * 根据中期修改意见，不关联测点相关的仪器运行数据 
    List instrList = stationQueryDao.queryInstrInfo(model);
    getRequest().setAttribute("staInstrList", instrList);
    model.setInstrSize(instrList.size());
    logger.debug("仪器运行数量：" + instrList.size());*/
    //地磁方位标
    List aziFgList = stationQueryDao.queryGeomagAziFlag(model);
    getRequest().setAttribute("aziFlagList", aziFgList);
    model.setAziFlagSize(aziFgList.size());
    logger.debug("地磁方位标数量：" + aziFgList.size());
    //地磁墩差
    List diffList = stationQueryDao.queryGeomagDiff(model);
    getRequest().setAttribute("diffList", diffList);
    model.setDiffSize(diffList.size());
    logger.debug("地磁墩差数量：" + diffList.size());
    //测项分量
    List itemsList = stationQueryDao.queryStaItems(model);
    getRequest().setAttribute("itemsList", itemsList);
    model.setItemsSize(itemsList.size());
    logger.debug("测项分量数量：" + itemsList.size());
    //仪器参数
    List instrParamList = stationQueryDao.queryInstrParams(model);
    getRequest().setAttribute("instrParamList", instrParamList);
    model.setInstrParamSize(instrParamList.size());
    logger.debug("仪器参数数量：" + instrParamList.size());
    //洞体
    List staCaveList = stationQueryDao.queryStaCave(model);
    getRequest().setAttribute("staCaveList", staCaveList);
    model.setStaCaveSize(staCaveList.size());
    logger.debug("洞体数量：" + staCaveList.size());
    //井泉
    List staWellList = stationQueryDao.queryStaWell(model);
    getRequest().setAttribute("staWellList", staWellList);
    model.setStaWellSize(staWellList.size());
    logger.debug("井泉信息数量：" + staWellList.size());
    
    return "pointInfo";
  }
  
  /**
   * 查询前兆台站，用于页面上台站选择
   */
  public List<Map> getStationsMap() {
    return stationQueryDao.getAllStations(dataSourceManager.getQzSchema());
  }
  
  public List getJsonData() {
    return jsonData;
  }
  
  public List getJsonMethods() {
    return jsonMethods;
  }

  public void setModel(SignStationCriteria model) {
    this.model = model;
  }
  
  public SignStationCriteria getModel() {
    return model;
  }

  public void setSubjectId(String subjectId) {
    this.subjectId = subjectId;
  }

  /**
   * @return the stationId
   */
  public String getStationId() {
    return stationId;
  }

  /**
   * @param stationId the stationId to set
   */
  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  /**
   * @return the pointId
   */
  public String getPointId() {
    return pointId;
  }

  /**
   * @param pointId the pointId to set
   */
  public void setPointId(String pointId) {
    this.pointId = pointId;
  }

  /**
   * @return the jsonInstruments
   */
  public List getJsonInstruments() {
    return jsonInstruments;
  }

  /**
   * @param jsonInstruments the jsonInstruments to set
   */
  public void setJsonInstruments(List jsonInstruments) {
    this.jsonInstruments = jsonInstruments;
  }

  /**
   * @return the jsonDataForSta
   */
  public List getJsonDataForSta() {
    return jsonDataForSta;
  }

  public String getMethodId() {
    return methodId;
  }

  public void setMethodId(String methodId) {
    this.methodId = methodId;
  }
}
