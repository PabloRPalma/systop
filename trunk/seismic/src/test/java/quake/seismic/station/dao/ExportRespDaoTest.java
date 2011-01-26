package quake.seismic.station.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.station.model.Criteria;
import quake.seismic.station.model.Loc;

import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试导出台站数据respDAO
 * 
 * @author yj
 * 
 */
public class ExportRespDaoTest extends BaseTransactionalTestCase {
  @Autowired
  GridStationDao gridStationDao;

  @Autowired
  ExportRespDao exportRespDao;
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
   * 通道id
   */
  String channelId;
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
      List<Map> channelList = (List<Map>) station.get("staChannels");
      if (channelList.size() > 0) {
        channelId = (String) channelList.get(0).get("ID");
      }
    }
    schema = dataSourceManager.getStationSchema();
  }

  /**
   * 测试导出resp
   */
  @SuppressWarnings("unchecked")
  public void testQueryForResp() {
    System.out.println(exportRespDao.queryForResp(channelId, schema).toString());
  }

  /**
   * 测试通道数据查询
   */
  public void testQueryChannelById() {
    Criteria c = new Criteria();
    c.setChannelId(channelId);
    c.setSchema(schema);
    assertTrue(exportRespDao.queryChannelById(c).size() > 0);
  }

  /**
   * 测试位置信息查询
   */
  public void testQueryLoc() {
    Loc loc = new Loc();
    loc.setSchema(schema);
    loc.setNetCode(station.get("NET_CODE").toString());
    loc.setStaCode(station.get("STA_CODE").toString());

    assertTrue(exportRespDao.queryLoc(loc).size() > 0);
  }
}
