package quake.seismic.data.catalog.dao.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.SeismicConstants;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.station.dao.GridStationDao;

import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.core.util.DateUtil;

/**
 * 测试导出地震目录数据DAO
 * 
 * @author yj
 * 
 */
public class ExportCatDaoTest extends BaseTransactionalTestCase {
  /**
   * 用于表格导出的测震DAO
   */
  @Autowired
  private ExportCatDao exportCatDao;
  /**
   * 查询条件
   */
  private Criteria model = new Criteria();
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  @Autowired
  GridStationDao gridStationDao;
  /**
   * 台站
   */
  @SuppressWarnings("unchecked")
  Map station;

  /**
   * 准备工作
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setUp() throws Exception {
    model.setTableName("catalog_w");
    model.setIsRoundQuery("1");
    model.setExpType(SeismicConstants.Bulletin_full);
    try {
      model.setStartDate(DateUtil.convertStringToDate("2008-10-10"));
      model.setEndDate(DateUtil.convertStringToDate("2008-10-15"));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    model.setSchema(dataSourceManager.getSeismicSchema());

    quake.seismic.station.model.Criteria m = new quake.seismic.station.model.Criteria();
    m.setPage(new Page(Page.start(Page.FIRST_PAGE_INDEX, Constants.DEFAULT_PAGE_SIZE),
        Constants.DEFAULT_PAGE_SIZE));
    // 测震SCHEMA
    m.setSchema(dataSourceManager.getStationSchema());
    List<Map> list = gridStationDao.queryStation(m);
    if (list.size() > 0) {
      station = list.get(0);
    }
    model.setQcId(station.get("ID").toString());
  }

  /**
   * 测试查询需要导出的数据 按圆形或矩形区域查询地震目录数据 并将地震目录查询出的数据按照指定的格式生成字符串
   */
  public void testQueryCriteria() {
    System.out.println(exportCatDao.query(model));
  }

  /**
   * 测试查询需要导出的数据(VLM) 将地震目录查询出的数据按照指定的格式生成字符串 导出的数据格式包括：基本目录数据格式、完全目录数据格式和观测报告 具体数据格式见测震数据规范相关文档
   */
  public void testQueryForVlm() {
    System.out.println(exportCatDao.queryForVlm(model));
  }

  /**
   * 测试查询单个震相数据(观测报告)
   * 
   */
  public void testQueryForSingleBulletin() {
    System.out.println(exportCatDao.queryForSingleBulletin(model));
  }

}
