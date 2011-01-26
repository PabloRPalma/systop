package quake.special.webapp;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;

/**
 * 地震专题action测试
 * 
 * @author yj
 * 
 */
public class SpecialActionTest extends BaseTransactionalTestCase {
  @Autowired
  private SpecialAction sa;
/**
 * 测试获得地震目录
 */
  public void testQcOfSpecial() {
    sa.setTableName("CATALOG_W");
    sa.qcOfSpecial();
    assertTrue(sa.getPage().getData().size() > 0);
  }
  
}
