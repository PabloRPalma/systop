package quake.seismic.data.catalog.dao.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.data.catalog.model.Criteria;

import com.systop.core.Constants;
import com.systop.core.dao.support.Page;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.core.util.DateUtil;

/**
 * 测试地震目录查询DAO
 * 
 * @author yj
 * 
 */
public class GridCatDaoTest extends BaseTransactionalTestCase {
  /**
   * 用于表格查询的测震DAO
   */
  @Autowired
  private GridCatDao gridCatDao;
  /**
   * 查询条件
   */
  private Criteria model = new Criteria();
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  /**
   * 准备工作
   */
  @Override
  protected void setUp() throws Exception {
    model.setTableName("catalog_w");
    model.setIsRoundQuery("1");
    try {
      model.setStartDate(DateUtil.convertStringToDate("2008-10-10"));
      model.setEndDate(DateUtil.convertStringToDate("2008-10-15"));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    model.setPage(new Page(Page.start(Page.FIRST_PAGE_INDEX, Constants.DEFAULT_PAGE_SIZE),
        Constants.DEFAULT_PAGE_SIZE));
    // 测震SCHEMA
    model.setSchema(dataSourceManager.getSeismicSchema());
    int start = Page.FIRST_PAGE_INDEX;
    model.setStart(start);
    model.setSize(Constants.DEFAULT_PAGE_SIZE);
  }

  /**
   * 测试查询地震目录 两种查询方式，按矩形区域查询和按圆形区域查询 根据地震目录配置情况判断是否关联震级表
   */
  public void testQueryCriteria() {
    Page page = gridCatDao.query(model);
    assertTrue(page.getData().size() > 0);
  }

  /**
   * 测试地震目录单表查询,获取GIS显示所需要数据，无分页查询 两种查询方式，按矩形区域查询和按圆形区域查询
   */
  public void testQueryForGis() {
    assertTrue(gridCatDao.queryForGis(model).size() > 0);
  }

  /**
   * 测试查询台网信息
   */
  public void testQueryNetwordInfo() {
    Criteria criteria = new Criteria();
    criteria.setSchema(dataSourceManager.getStationSchema());
    assertTrue(gridCatDao.queryNetwordInfo(criteria).size() > 0);
  }

}
