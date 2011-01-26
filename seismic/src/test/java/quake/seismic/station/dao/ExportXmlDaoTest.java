package quake.seismic.station.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.station.model.Criteria;

import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.test.BaseTransactionalTestCase;

public class ExportXmlDaoTest extends BaseTransactionalTestCase {
  @Autowired
  GridStationDao gridStationDao;

  @Autowired
  ExportXmlDao exportXmlDao;
  /**
   * 台站
   */
  @SuppressWarnings("unchecked")
  Map station;
  /**
   * 数据源
   */
  String schema;
  /**
   * 用于获取Schema
   */
  @Autowired
  DataSourceManager dataSourceManager;

  @SuppressWarnings("unchecked")
  protected void setUp() throws Exception {
    Criteria model = new Criteria();
    model.setPage(new Page(Page.start(Page.FIRST_PAGE_INDEX, Constants.DEFAULT_PAGE_SIZE),
        Constants.DEFAULT_PAGE_SIZE));
    // 测震SCHEMA
    model.setSchema(dataSourceManager.getStationSchema());
    List<Map> list = gridStationDao.queryStation(model);
    if (list.size() > 0) {
      station = list.get(0);
    }
    schema = dataSourceManager.getStationSchema();
  }

  /**
   * 测试导出xml
   */
  public void testQueryForXml() {
    System.out.println(exportXmlDao.queryForXml(station.get("ID").toString(), schema).toString());
  }

  /**
   * 测试测震台站数据查询
   */
  public void testQueryStationById() {
    Criteria c = new Criteria();
    c.setId(station.get("ID").toString());
    c.setSchema(schema);
    assertTrue(exportXmlDao.queryStationById(c).size() > 0);
  }

  /**
   * 测试通道数据查询
   */
  @SuppressWarnings("unchecked")
  public void testQueryChannel() {
    Criteria model = new Criteria();
    model.setSchema(schema);
    model.setId(station.get("ID").toString());
    List<Map> stationList = exportXmlDao.queryStationById(model);
    if (stationList.size() == 1) {
      Map m = stationList.get(0);
      model.setStaCode(m.get("STA_CODE").toString());
      model.setNetCode(m.get("NET_CODE").toString());
    }
    assertTrue(exportXmlDao.queryChannel(model).size() > 0);
  }
}
