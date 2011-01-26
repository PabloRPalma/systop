package quake.wap;

import org.springframework.beans.factory.annotation.Autowired;

import quake.wap.webapp.WapAction;

import com.systop.core.test.BaseTransactionalTestCase;
/**
 * 手机action测试
 * @author yj
 *
 */
public class WapActionTest extends BaseTransactionalTestCase {
  @Autowired
  WapAction wa;
  /**
   * 测试获得地震目录
   */
  public void testIndex(){
    wa.setTableName("CATALOG_W");
    wa.index();
    assertTrue(wa.getCats().size() > 0);
  }
}
