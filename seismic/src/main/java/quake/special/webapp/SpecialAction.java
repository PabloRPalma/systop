package quake.special.webapp;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.catalog.model.QuakeCatalog;
import quake.admin.catalog.service.QuakeCatalogManager;
import quake.admin.ds.service.DataSourceManager;
import quake.base.webapp.NumberFormatUtil;
import quake.seismic.data.catalog.dao.impl.GridCatDao;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.phase.dao.impl.GridPhaseDao;
import quake.seismic.data.phase.model.PhaseCriteria;
import quake.special.SpecialConstants;
import quake.special.dao.SpecialDao;
import quake.special.model.Special;
import quake.special.service.SpecialManager;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.model.Corp;

/**
 * 专题Action
 * 
 * @author yj
 * 
 */
@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SpecialAction extends ExtJsCrudAction<Special, SpecialManager> {
  /** 附件存放路径 */
  private static final String SPECIAL_FOLDER = "/uploadFiles/special/";

  private Criteria criteria = new Criteria();

  private PhaseCriteria phaseCriteria = new PhaseCriteria();

  private List<Map<String, String>> catalogs = null;

  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  @Autowired
  private QuakeCatalogManager catalogManager;

  private List<Map> cats = new ArrayList<Map>();

  private String specialId;

  private String tableName;

  private Map<String, String> jsonResult;

  @Autowired(required = true)
  @Qualifier("specialDao")
  private SpecialDao specialDao;

  /**
   * 用于表格查询的测震DAO
   */
  @Autowired(required = true)
  @Qualifier("gridPhaseDao")
  private GridPhaseDao gridPhaseDao;
  /**
   * /** 用于表格查询的测震DAO
   */
  @Autowired(required = true)
  @Qualifier("gridCatDao")
  private GridCatDao gridCatDao;

  @Autowired(required = true)
  private QuakeCatalogManager czCatalogManager;

  /** 显示图片 */
  private File pic;

  /** 首页显示图片名称 */
  private String picFileName;

  /**
   * 查询起始
   */
  private Date beginDate;

  /**
   * 查询截至
   */
  private Date endDate;

  /**
   * 地震目录列表
   * 
   * @return
   */
  public String qcOfSpecial() {
    if (StringUtils.isNotBlank(tableName)) {
      criteria.setTableName(tableName);
      criteria.setPage(new Page(Page.start(getPageNo(), getPageSize()), getPageSize()));
      // 测震SCHEMA
      criteria.setSchema(dataSourceManager.getSeismicSchema());
      criteria.setSortProperty(getSortProperty());
      criteria.setSortDir(getSortDir());
      if (criteria.getEndDate() == null) {
        if (criteria.getStartDate() == null) {
          try {
            criteria.setStartDate(DateUtil.add(DateUtil.convertStringToDate(SpecialConstants.TIME),
                Calendar.DATE, SpecialConstants.QUERY_QC_DAY));
          } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        try {
          criteria.setEndDate(DateUtil.convertStringToDate(SpecialConstants.TIME));
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      page = PageUtil.getPage(getPageNo(), getPageSize());

      int start = Page.start(getPageNo(), getPageSize());
      criteria.setStart(start);
      criteria.setSize(getPageSize());

      page = gridCatDao.query(criteria);
      cats = page.getData();
      logger.info("--------------cats {}" + cats.size());
      Special s = null;
      if (StringUtils.isNotBlank(specialId)) {
        s = getManager().get(Integer.valueOf(specialId));
      }
      for (int i = 0; i < cats.size(); i++) {
        Map map = cats.get(i);
        if (map.get("M") != null) {
          map.put("M", NumberFormatUtil.format(map.get("M"), 1));
        }
        if (map.get("EPI_LON") != null) {
          map.put("EPI_LON", NumberFormatUtil.format(map.get("EPI_LON"), 2));
        }
        if (map.get("EPI_LAT") != null) {
          map.put("EPI_LAT", NumberFormatUtil.format(map.get("EPI_LAT"), 2));
        }

        if (s != null) {
          if (map.get("ID").equals(s.getQc_id())) {
            map.put("changed", true);
          } else {
            map.put("changed", false);
          }
        } else {
          map.put("changed", false);
        }
      }
      page.setData(cats);
    }

    return JSON;
  }

  /**
   * 保存专题地震
   */
  @Override
  public String save() {
    getModel().setCreateDate(new Date());
    getModel().setState(SpecialConstants.UNVERIFY);

    String imgPath = UpLoadUtil.doUpload(pic, picFileName, SPECIAL_FOLDER, getServletContext());
    if (imgPath != null) {
      getModel().setFront_pic(imgPath);
    }
    return super.save();
  }

  /**
   *页面初始化
   */
  public String edit() {
    catalogs = catalogManager.getCat();
    return INPUT;
  }

  /**
   *查看地震专题
   */
  public String view() {
    Special s = getManager().get(getModel().getId());
    if (StringUtils.isNotBlank(s.getQc_id()) && StringUtils.isNotBlank(s.getTableName())) {
      phaseCriteria.setCatId(s.getQc_id());
      QuakeCatalog czCat = czCatalogManager.queryByCltName(s.getTableName());
      phaseCriteria.setTableName(czCat.getPhaseTname());
      phaseCriteria.setSchema(dataSourceManager.getSeismicSchema());
      List items = gridPhaseDao.query(phaseCriteria);
      getRequest().setAttribute("items", items);

      String catalogName = czCat.getClcName() + "地震目录";
      getRequest().setAttribute("catalogName", catalogName);
    }
    return VIEW;
  }

  /**
   * 审核专题地震
   * 
   * @return
   */
  public String verify() {
    getModel().setState(
        SpecialConstants.VERIFY.equals(getModel().getState()) ? SpecialConstants.UNVERIFY
            : SpecialConstants.VERIFY);
    getManager().save(getModel());
    return SUCCESS;
  }

  /**
   * 确认选择地震目录查询该地震目录，为地震专题页面显示信息做准备
   * 
   * @return
   */
  public String ensureQc() {

    jsonResult = Collections.synchronizedMap(new HashMap<String, String>());
    String qcId = getRequest().getParameter("qcId");
    String tableName = getRequest().getParameter("tableName");

    if (StringUtils.isNotBlank(qcId) && StringUtils.isNotBlank(tableName)) {
      criteria.setTableName(tableName);
      criteria.setPage(new Page(Page.start(getPageNo(), getPageSize()), getPageSize()));
      // 测震SCHEMA
      criteria.setSchema(dataSourceManager.getSeismicSchema());
      criteria.setSortProperty(getSortProperty());
      criteria.setSortDir(getSortDir());
      criteria.setQcId(qcId);

      page = PageUtil.getPage(getPageNo(), getPageSize());
      page = specialDao.queryQcForObeject(criteria);
      List<Map> qcList = new ArrayList<Map>();
      qcList = page.getData();
      if (page.getData() != null) {
        jsonResult = qcList.get(0);
        jsonResult.put("M", jsonResult.get("M") != null ? NumberFormatUtil.format(jsonResult
            .get("M"), 1) : "");
        jsonResult.put("EPI_LON", jsonResult.get("EPI_LON") != null ? NumberFormatUtil.format(
            jsonResult.get("EPI_LON"), 2) : "");
        jsonResult.put("EPI_LAT", jsonResult.get("EPI_LAT") != null ? NumberFormatUtil.format(
            jsonResult.get("EPI_LAT"), 2) : "");
        jsonResult.put("EPI_DEPTH", jsonResult.get("EPI_DEPTH") != null ? NumberFormatUtil.format(
            jsonResult.get("EPI_DEPTH"), 0) : "");
      }
    }
    return "jsonResult";
  }

  /**
   * 专题查询列表
   */
  @Override
  public String index() {
    Page page = PageUtil.getPage(getPageNo(), getPageSize());
    StringBuffer sql = new StringBuffer("from Special s where 1=1 ");
    List args = new ArrayList();
    if (StringUtils.isNotBlank(getModel().getTitle())) {
      sql.append(" and s.title like ?");
      args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));
    }
    if (beginDate != null && endDate != null) {
      sql.append("and s.createDate >= ? and s.createDate <= ? ");
      args.add(beginDate);
      args.add(endDate);
    }
    sql.append(" order by s.createDate desc");
    page = getManager().pageQuery(page, sql.toString(), args.toArray());
    restorePageData(page);

    return INDEX;
  }
  /**
   * 删除地震专题
   */
  @Override
  public String remove() {
    Special special = getManager().get(getModel().getId());
    // 检查是否与事件关联
    if (corp.getFsCases().size() != 0) {
      addActionError("该企业涉及食品安全事件，无法删除！");
      return INDEX;
    }
    // 如果存在照片，则连照片一起删除
    String Path = getModel().getPhotoUrl();
    if (StringUtils.isNotBlank(Path)) {
      getManager().remove(corp, getRealPath(Path));
      return SUCCESS;
    }
    return super.remove();
  }

  /**
   * 首页地震专题‘更多’列表
   * @return
   */
  public String indexMore() {
    index();
    return "indexMore";
  }
  
  public Criteria getCriteria() {
    return criteria;
  }

  public void setCriteria(Criteria criteria) {
    this.criteria = criteria;
  }

  public List<Map<String, String>> getCatalogs() {
    return catalogs;
  }

  public void setCatalogs(List<Map<String, String>> catalogs) {
    this.catalogs = catalogs;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public Map<String, String> getJsonResult() {
    return jsonResult;
  }

  public void setJsonResult(Map<String, String> jsonResult) {
    this.jsonResult = jsonResult;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public File getPic() {
    return pic;
  }

  public void setPic(File pic) {
    this.pic = pic;
  }

  public String getPicFileName() {
    return picFileName;
  }

  public void setPicFileName(String picFileName) {
    this.picFileName = picFileName;
  }

  public String getSpecialId() {
    return specialId;
  }

  public void setSpecialId(String specialId) {
    this.specialId = specialId;
  }
}
