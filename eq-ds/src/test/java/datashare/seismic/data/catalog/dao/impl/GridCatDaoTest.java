package datashare.seismic.data.catalog.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.dao.support.Page;

import datashare.GlobalConstants;
import datashare.base.test.BaseTestCase;
import datashare.email.EmailConstants;
import datashare.email.seismic.model.SeismicMail;
import datashare.seismic.data.catalog.model.Criteria;


public class GridCatDaoTest  extends BaseTestCase {
  @Autowired(required = true)
  private GridCatDao gridCatDao;
  
  @Autowired(required = true)
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao hDao;
  
  @SuppressWarnings("unchecked")
  public void testQuery() {
    Criteria cti = new Criteria();
    cti.setSchema("CZDATA");
    cti.setTableName("CATALOG_C");
    cti.setMagTname("MAG_C");
    Page page = new Page(0,20);
    cti.setPage(page);
    page = gridCatDao.query(cti);
    for (int i = 0; i < page.getData().size(); i++) {
      Map row = (Map)page.getData().get(i);
      logger.info("ID:" + (String)row.get("ID"));
    }
    
    List<SeismicMail> list = hDao.query(
        "from SeismicMail s where s.state=? order by s.subscriber", EmailConstants.VERIFIED);
    for (SeismicMail mail : list) {
      Criteria criteria = mail.getCriteria();
      page = new Page(0, GlobalConstants.MAX_RESULTS);
      criteria.setPage(page);
      criteria.setSchema("CZDATA"); // 这个Schema是数据库的“方案名称”
      page = gridCatDao.query(criteria); // 数据查询
     
      logger.info("地震目录数据邮件发送成功,地址：{}，目录:{}，共{}事件.", new String[] { mail.getEmailAddr(),
          mail.getCatalogName(), String.valueOf(page.getData().size()) });
    }
  }
  
  
}
