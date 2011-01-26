package quake.seismic.station.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.station.model.Criteria;
import quake.seismic.station.model.InstrDic;

import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 测试台站数据查询dao
 * 
 * @author yj
 * 
 */
public class GridStationDaoTest extends BaseTransactionalTestCase {
  @Autowired
  GridStationDao gridStationDao;
  /**
   * 用于获取Schema
   */
  @Autowired
   DataSourceManager dataSourceManager;
  /**
   * 用于导出台站DAO
   */
  @Autowired
  @Qualifier("exportXmlDao")
   ExportXmlDao exportXmlDao;
  /**
   * 测试测震台站数据查询
   */
  public void testQueryStation(){
    Criteria model = new Criteria();
    model.setPage(new Page(Page.start(Page.FIRST_PAGE_INDEX, Constants.DEFAULT_PAGE_SIZE),
        Constants.DEFAULT_PAGE_SIZE));
    // 测震SCHEMA
    model.setSchema(dataSourceManager.getStationSchema());
    assertTrue(gridStationDao.queryStation(model).size()>0);
  }
  /**
   * 测试仪器数据查询
   */
  public void testGetInstrumentByType(){
    InstrDic instrDic = new InstrDic();
    instrDic.setSchema(dataSourceManager.getStationSchema());
    instrDic.setInstrType("Sensor");
    assertTrue(gridStationDao.getInstrumentByType(instrDic,AbstractStationDao.SQL_INSTR_TYPE).size()>0);
  }
}
