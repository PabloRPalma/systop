package quake.instrument.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.instrument.dao.InstrumentDao;
import quake.seismic.instrument.model.Instrument;

import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 仪器dao测试
 * 
 * @author yj
 * 
 */
public class InstrumentDaoTest extends BaseTransactionalTestCase {
  @Autowired(required = true)
  @Qualifier("instrumentDao")
  InstrumentDao instrumentDao;
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  DataSourceManager dataSourceManager;
 
  /**
   * 测试仪器查询
   */
  public void testQuery() {
    Instrument instrument = new Instrument();
    instrument.setPage(new Page(Page.start(Page.FIRST_PAGE_INDEX, Constants.DEFAULT_PAGE_SIZE),
        Constants.DEFAULT_PAGE_SIZE));
    instrument.setSchema(dataSourceManager.getStationSchema());
    assertTrue(instrumentDao.query(instrument).getData().size() > 0);
  }
  
}
