package datashare.sign.data.webapp;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.ecside.core.ECSideConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ibatis.common.io.ReaderInputStream;
import com.systop.common.modules.template.Template;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.TemplateRender;
import com.systop.core.ApplicationException;
import com.systop.core.Constants;
import com.systop.core.util.DateUtil;
import com.systop.core.util.ReflectUtil;

import datashare.GlobalConstants;
import datashare.admin.ds.service.DataSourceManager;
import datashare.admin.samplerate.model.SampleRate;
import datashare.admin.samplerate.service.SampleRateManager;
import datashare.base.webapp.AbstractQueryAction;
import datashare.sign.data.SignDataConstants;
import datashare.sign.data.dao.impl.CsvSignDao;
import datashare.sign.data.dao.impl.EasyChartSignDao;
import datashare.sign.data.dao.impl.GridSignDao;
import datashare.sign.data.model.Criteria;
import datashare.sign.data.model.EasyChartModel;
import datashare.sign.data.model.GridResult;
import datashare.sign.data.util.SignDataUtil;
import datashare.sign.support.SignCommonDao;

@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SignAction extends AbstractQueryAction<Criteria> {
  /**
   * 最大行数，用于设置limit
   */
  public static final int MAX_ROWS = 10000000;

  /**
   * 用于表格查询的前兆DAO
   */
  @Autowired(required = true)
  @Qualifier("gridSignDao")
  private GridSignDao gridDao;

  /**
   * 用于曲线查询的前兆DAO
   */
  @Autowired(required = true)
  @Qualifier("csvSignDao")
  private CsvSignDao csvDao;
  
  /**
   * 用于曲线查询的前兆DAO
   */
  @Autowired(required = true)
  @Qualifier("easyChartSignDao")
  private EasyChartSignDao ecDao;

  /**
   * 用于查询台站名称，测点名称，测项分量名称等
   */
  @Autowired(required = true)
  private SignCommonDao signCommonDao;

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

  @Autowired(required = true)
  private SampleRateManager srManager;

  /**
   * 对应页面传来的查询条件，至于命名...地球人都知道
   */
  private Criteria model = new Criteria();

  /**
   * 表格查询的查询结果，页面上可以用它取得台站、测点等信息
   */
  private GridResult gridResult = new GridResult();

  /**
   * 用于接收被选中的测项分量代码,其实此代码为组合代码。即：[stationId_pointId_itemId]
   */
  private String[] selectItemIds;

  /**
   * 显示多条曲线对应的条件list，因为JSP要用，所以做成只读属性.
   */
  private List<Criteria> criteriaList;

  /**
   * 以表格的形式显示前兆数据查询结果，支持：
   * <ul>
   * <li>数据分页、排序</li>
   * <li>同时显示多个测项分量时，动态调整表格列数。</li>
   * <li>表头跨列显示.</li>
   * </ul>
   */
  public String list() {
    // 当查询数据为空的时候，提示信息尚不正确，应提示〔查询结果为空〕
    if (selectItemIds == null || model == null) {
      throw new ApplicationException("查询条件错误，selectItemIds is null or mode(Criteria) is null");
    }
    // 前兆SCHEMA
    model.setSchema(dataSourceManager.getQzSchema());
    model.setPage(getPage());
    model.setOrder(getSortDir());
    List<GridResult> itemsList = new ArrayList();
    for (String selectItemId : selectItemIds) {
      // selectItemId是符合id包含stationId,pointId,itemId.所以要拆分
      model = buildModel(model, selectItemId);
      // 查询加工之后的结果，此结果不会为null
      GridResult gridResult = gridDao.query(model);
      itemsList.add(gridResult);
      logger.debug("GridResult's dataSeries size is:" + gridResult.getDataSeries().size());
    }

    // 为request Set各种数据，为了页面显示数据,同时设置分页信息
    buildData(itemsList);
    return "list";
  }

  /**
   * 曲线查询，直接定位到line.jsp
   */
  public String line() {
    return "line";
  }

  /**
   * stock曲线查询,用于处理数据量较大的曲线。
   */
  public String stock() {
    // 设置多曲线条件
    initCriteriaList();
    return "stock";
  }

  /**
   * Applet显示多条数据曲线，用于处理秒值或时间跨度很大的分钟值。
   */
  public String applet() {
    // 设置多曲线条件的Criteria对象
    initCriteriaList();
    // 循环遍历得到多条曲线的title.以stationId_pointId_itemId组合区分
    for (Criteria c : criteriaList) {
      getRequest().setAttribute(buildLineName(c), buildLineTitle(c));
    }
    getRequest().setAttribute("start", DateUtil.getDateTime("yyyy-MM-dd", model.getStartDate()));
    getRequest().setAttribute("end", DateUtil.getDateTime("yyyy-MM-dd", model.getEndDate()));
    return "applet";
  }

  /**
   * 查询前兆数据表，并将查询结果生成csv格式的数据。用于各种曲线显示的时候获取数据： <br>
   * 在Stock曲线查询的时候，曲线的setting文件中通过这个方法取得数据；<br>
   * 在Applect曲线查询的时候，Applect通过URL调用这个方法取得数据；<br>
   * 普通Flash曲线查询的时候，通过"data_file"参数调用这个方法取得数据。
   */
  public String csv() {
    if (model != null && model.getMethodId() != null) {
      logger.debug("Query for csv");
      // 前兆SCHEMA
      model.setSchema(dataSourceManager.getQzSchema());
      StringBuffer buf = csvDao.query(model);
      render(getResponse(), buf.toString(), "text/csv");
      return null;
    }
    return "line";
  }
  
  /**
   * 使用D版easycharts作为图形组件，满足用户的要求。
   */
  public String easyChart() {
    // 设置多曲线条件的Criteria对象
    initCriteriaList();
    if(CollectionUtils.isEmpty(criteriaList)) {
      return null;
    }
    // 循环遍历得到多条曲线的title.以stationId_pointId_itemId组合区分
    List<EasyChartModel> models = new ArrayList<EasyChartModel>(criteriaList.size());
    for (Criteria c : criteriaList) {
      c.setSchema(dataSourceManager.getQzSchema());
      EasyChartModel ecModel = ecDao.query(c);
      ecModel.setTitle(buildLineTitle(c));
      ecModel.setItemId(c.getItemId());
      ecModel.setPointId(c.getPointId());
      ecModel.setStationId(c.getStationId());
      models.add(ecModel);
      logger.debug("曲线图测相分量{}", c.getItemId());
    }
    getRequest().setAttribute("models", models);
    getRequest().setAttribute("start", DateUtil.getDateTime("yyyy-MM-dd", model.getStartDate()));
    getRequest().setAttribute("end", DateUtil.getDateTime("yyyy-MM-dd", model.getEndDate()));

    return "easyChart";
  }
  
  /**
   * 配合easyCharts,弥补其不能导出图片的缺陷
   */
  public String exportAll() {
   // 设置多曲线条件的Criteria对象
    initCriteriaList();
    if(CollectionUtils.isEmpty(criteriaList)) {
      return null;
    }
    getResponse().setContentType("application/x-download");
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"qzimages.zip\"");
    ZipOutputStream out = null;
    try {
      out = new ZipOutputStream(getResponse().getOutputStream());
      // 每一个entry是一个文件
      for (Criteria c : criteriaList) {
        c.setSchema(dataSourceManager.getQzSchema());
        StringBuffer csv = csvDao.query(c);
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        String dateFormat = srManager.get(c.getSampleRate()).getDateFormat();
        new JfreeChartHelper().getImage(buildLineTitle(c), csv, dateFormat, byteOut);
        // 建立一个Zip文件的Entry
        ZipEntry zipEntry = new ZipEntry(buildLineName(c) + ".jpg");
        out.putNextEntry(zipEntry);
        out.write(byteOut.toByteArray());        
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        out.flush();
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * 生成amcharts line设置文件，在使用普通的Flash曲线的时候通过 "settings_file"参数调用这个方法，以获取曲线的设置文件。
   */

  public String lineSettings() {
    Map data = ReflectUtil.toMap(model, new String[] {}, false);
    extraInfo(data, model);
    render(getResponse(), parseFtl(data, "line-settings"), "text/xml");
    return null;
  }

  /**
   * 生成amcharts stock line设置文件，在使用stock曲线的时候，通过settings_file参数
   * 调用这个方法，获取配置文件，并读取数据(stock的数据URL是写在配置文件中的)。
   */

  public String stockSettings() {
    Map data = ReflectUtil.toMap(model, new String[] {}, false);
    extraInfo(data, model);
    SampleRate sr = srManager.get(model.getSampleRate());
    data.put("sampleName", sr.getName());
    data.put("periods", sr.getStockPeriod());
    data.put("periodName", sr.getStockPeriodName());
    StringBuffer url = new StringBuffer().append(getServletContext().getContextPath()).append(
        "/datashare/sign/data/csv.do").append(model.toString());
    logger.debug(url.toString());
    data.put("url", url.toString());
    // 选一个合适的时间格式
    data.put("dateFormat", sr.getStockDateFormat());
    render(getResponse(), parseFtl(data, "stock-settings"), "text/xml");
    return null;
  }

  /**
   * 前兆数据下载。将多个测项分量的数据（csv格式）打包成一个zip文件，以供下载使用.
   */
  public String zip() {
    getResponse().setContentType("application/x-download");
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"qzdata.zip\"");
    initCriteriaList();
    ZipOutputStream out = null;
    try {
      out = new ZipOutputStream(getResponse().getOutputStream());
      // 每一个entry是一个文件
      for (Criteria c : criteriaList) {
        c.setSchema(dataSourceManager.getQzSchema());
        // 查询CSV数据，并在CSV文件前面加入Title
        StringBuffer buf = new StringBuffer(100000).append(buildLineTitle(c))
            .append("\n").append(csvDao.query(c));
        // 建立一个Zip文件的Entry
        ZipEntry zipEntry = new ZipEntry(buildLineName(c) + ".txt");
        out.putNextEntry(zipEntry);
        Reader reader = new StringReader(buf.toString());
        ReaderInputStream in = new ReaderInputStream(reader); // IBitis提供的ReaderInputStream
        final int BUFFER = 4096;
        byte[] dataBlock = new byte[BUFFER];
        int count;
        while ((count = in.read(dataBlock, 0, BUFFER)) != -1) {
          out.write(dataBlock, 0, count);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        out.flush();
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * Amcharts导出图片，在amcharts的设置文件中的"&lt;export_as_image&gt;"节点 中设置设个action的url可以实现图片导出。
   */
  public String export() {
    HttpServletRequest request = getRequest();
    HttpServletResponse response = getResponse();
    int width = Integer.parseInt(request.getParameter("width"));
    int height = Integer.parseInt(request.getParameter("height"));

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // 逐个像素处理上传的数据
    for (int y = 0; y < height; y++) {
      int x = 0;
      String[] row = request.getParameter("r" + y).split(",");
      for (int c = 0; c < row.length; c++) {
        String[] pixel = row[c].split(":"); // 十六进制颜色数组
        int repeat = pixel.length > 1 ? Integer.parseInt(pixel[1]) : 1;
        for (int l = 0; l < repeat; l++) {
          bufferedImage.setRGB(x, y, Integer.parseInt(pixel[0], 16));
          x++;
        }
      }
    }
    // image type
    Graphics2D graph = bufferedImage.createGraphics();
    graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graph.drawImage(bufferedImage, 0, 0, width, height, null);
    graph.dispose();
    // 将图片数据写入response
    response.setContentType("image/jpeg");
    response.addHeader("Content-Disposition", "attachment; filename=\"amchart.jpg\"");
    try {
      ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将selectItemId拆分，并将对应的信息添加到model中，并将model返回
   * 
   * @param model
   * @param selectItemId
   * @return
   */
  private Criteria buildModel(Criteria model, String selectItemId) {
    String[] ids = selectItemId.split(GlobalConstants.SPLITTER);
    model.setStationId(ids[0]);
    model.setPointId(ids[1]);
    model.setItemId(ids[2]);
    model.setMethodId(model.getItemId().substring(0, 3));
    return model;
  }

  /**
   * 根据给定的Criteria对象，创建曲线的Title
   */
  private String buildLineTitle(Criteria c) {
    Map data = extraInfo(null, c);
    return new StringBuffer(200).append(data.get("stationName")).append("  测点").append(
        data.get("pointId")).append("  ").append(data.get("itemName")).append("  ").append(
        "(" + data.get("sampleRateName") + ")").toString();
  }

  /**
   * 根据给定的Criteria对象，创建曲线的"Name",这个"Name"通常作为 Request的Attribute Name或者曲线导出文件的文件名。
   */
  private String buildLineName(Criteria c) {
    return new StringBuffer(c.getStationId()).append("_").append(c.getPointId()).append("_")
        .append(c.getItemId()).append("_title").toString();
  }

  /**
   * 向request添加数据，包括页面显示数据，Ecside动态列所需属性等，以及分页信息等
   * 
   * @param itemsList
   */
  private void buildData(List<GridResult> itemsList) {

    // 将数据横向组合,定义变量，用于接收组合后的数据
    List<Map> mapDatas = SignDataUtil.convertData(itemsList);
    getRequest().setAttribute("items", mapDatas);
    // 获得Ecside动态列包含的信息[titles,widths,propertys]
    Map<String, String[]> columnInfo = SignDataUtil.getColumnInfo(mapDatas);
    String[] titles = columnInfo.get(SignDataConstants.COL_INFO_TITLES);
    String[] propertys = columnInfo.get(SignDataConstants.COL_INFO_PROPERTYS);
    //String[] widths = columnInfo.get(SignDataConstants.COL_INFO_WIDTHS);
    //logger.info(Arrays.toString(widths));
    getRequest().setAttribute(ECSideConstants.TABLE_TITLES_KEY, titles);
    getRequest().setAttribute(ECSideConstants.TABLE_FILEDS_KEY, propertys);
    //getRequest().setAttribute(ECSideConstants.TABLE_WIDTHS_KEY, widths);
    getRequest().setAttribute("colSize", titles.length);
    if (titles.length > 1) {
      // itemsList.size()表达了有几组数据
      String[] compositeTitles = new String[itemsList.size()];
      for (int i = 0; i < itemsList.size(); i++) {
        compositeTitles[i] = itemsList.get(i).getStationName() + "<br>"
            + itemsList.get(i).getItemName();
      }
      getRequest().setAttribute("comTitles", compositeTitles);
      GridResult maxResult = SignDataUtil.getMaxGridResult(itemsList);
      // titles.length大于1代表有数据，设置正确分页信息。
      logger.debug("row count is:" + maxResult.getPage().getRows());
      // 为了从页面获取查询结果记录总数
      getRequest().setAttribute("ROW_COUNTS", maxResult.getPage().getRows());
      restorePageData(maxResult.getPage().getRows(), maxResult.getPage().getPageSize());
    } else {// 当没有数据时，也需要设置分页信息，确保页面显示正确
      restorePageData(0, Constants.DEFAULT_PAGE_SIZE);
    }
    if(StringUtils.isNotBlank(model.getSampleRate())) {
      setWidths(titles.length, model.getSampleRate());
    }
  }
  
  /**
   * 计算各个列的宽度
   */
  private void setWidths(int titles, String sampleRate) {
    String dateFmt = srManager.get(sampleRate).getDateFormat();
    String timeWidth = new Integer(dateFmt.length() * 8).toString();
    String valueWidth = "80";
    StringBuffer widths = new StringBuffer(100);
    int len =  titles / 2;
    for(int i = 0; i < len; i++) {
      widths.append(timeWidth).append(",").append(valueWidth);
      if(i != len -1) {
        widths.append(",");
      }
    }
    getRequest().setAttribute(SignDataConstants.COL_INFO_WIDTHS, widths.toString());
  }

  /**
   * 为多条曲线显示，构建对应个数的查询条件对象，并封装到{@link #criteriaList}属性中。
   */
  private void initCriteriaList() {
    String[] ids = getCompositeIds();
    criteriaList = new ArrayList();
    for (String id : ids) {
      Criteria criteria = new Criteria();
      BeanUtils.copyProperties(model, criteria);
      criteriaList.add(buildModel(criteria, id));
    }
  }

  /**
   * 将所有组合id封装入一个数组，此数组内的组合id表现形式为[stationId_pointId_itemId]
   * 
   * @return
   */
  private String[] getCompositeIds() {
    String[] comIds = null;
    // ids：statioinId_pointId_itemId, statioinId_pointId_itemId
    String ids = getRequest().getParameter("ids");
    if (StringUtils.isNotBlank(ids)) {
      // 用", "分割字符串。逗号+英文半角空格。因为selectItemIds属性在java code中是数组，到页面显示时候元素之间用", "分割了。
      comIds = ids.split(", ");
    }
    if (comIds == null || comIds.length == 0) {
      throw new ApplicationException("查询条件错误。没有给出台站，测点，测项分量等信息");
    }
    return comIds;
  }

  /**
   * 将台站名称，测点名称，测项分量名称等信息装入给定的Map对象中，如果给定对象为null 则重新创建一个。
   * 
   * @return 返回已经装入所需数据的Map对象.
   */
  private Map extraInfo(Map data, Criteria model) {
    if (data == null) {
      data = new HashMap();
    }
    data.put("stationName", signCommonDao.getStationName(model.getStationId()));
    data.put("itemName", signCommonDao.getItemName(model.getItemId()));
    data.put("pointId", model.getPointId());
    data.put("pointName", signCommonDao.getPointName(model.getStationId(), model.getPointId()));
    data.put("sampleRateName", srManager.get(model.getSampleRate()).getName());
    return data;
  }

  /**
   * 使用给出的数据，渲染freemarker模板，用于根据查询条件动态生成amcharts 设置文件.
   * 
   * @param data 给出模板所需要的数据.
   * @return 由freemarker生成的String
   */
  private String parseFtl(Map data, String template) {
    // TemplateContext包括了渲染模板所需的数据、模板名称和输出流对象
    TemplateContext templateCtx = new TemplateContext();
    // 传入模板名称（包括路径）
    templateCtx.setTemplate(new Template(template));
    // 传入输出流对象
    StringWriter writer = new StringWriter();
    templateCtx.setWriter(writer);
    // 传入渲染模板所需数据
    templateCtx.addParameters(data);
    // 渲染模板
    templateRender.renderTemplate(templateCtx);
    return writer.toString();
  }

  @Override
  public Criteria getModel() {
    return model;
  }

  /**
   * @return the queryResult
   */
  public GridResult getGridResult() {
    return gridResult;
  }

  /**
   * @param queryResult the queryResult to set
   */
  public void setGridResult(GridResult queryResult) {
    this.gridResult = queryResult;
  }

  /**
   * @param model the model to set
   */
  public void setModel(Criteria model) {
    this.model = model;
  }

  /**
   * @return the selectItemIds
   */
  public String[] getSelectItemIds() {
    return selectItemIds;
  }

  /**
   * @param selectItemIds the selectItemIds to set
   */
  public void setSelectItemIds(String[] selectItemIds) {
    this.selectItemIds = selectItemIds;
  }

  /**
   * @return the criteriaList
   */
  public List<Criteria> getCriteriaList() {
    return criteriaList;
  }

  /**
   * @return the signCommonDao
   */
  public SignCommonDao getSignCommonDao() {
    return signCommonDao;
  }

  /**
   * @param signCommonDao the signCommonDao to set
   */
  public void setSignCommonDao(SignCommonDao signCommonDao) {
    this.signCommonDao = signCommonDao;
  }

}
