package datashare.sign.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import datashare.base.test.BaseTestCase;
import datashare.sign.data.dao.impl.GridSignDao;
import datashare.sign.data.model.Criteria;

/**
 * @author Sam
 *
 */
public class GridSignDaoTest extends BaseTestCase {
  @Autowired(required = true)
  private GridSignDao dao;
  /**
   * Test method for {@link GridSignDao#query(Criteria)}.
   */
  public void testQuery() {
    dao.query(null);
  }

}
