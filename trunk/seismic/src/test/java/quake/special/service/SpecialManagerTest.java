package quake.special.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import quake.special.model.Special;
import quake.special.service.SpecialManager;

import com.systop.core.test.BaseTransactionalTestCase;
/**
 * 地震专题管理测试
 * @author yj
 *
 */ 
public class SpecialManagerTest extends BaseTransactionalTestCase {
  @Autowired
  private SpecialManager sm;
  /**
   * 测试删除地震专题，及其包含的图片
   */
  @SuppressWarnings("unchecked")
  public void testRemoveSpecialString() {
    Special s = new Special();
    s.setTitle("测试地震专题");
    s.setArea("地震发生区域");
    s.setCreateDate(new Date());
    s.setDesn("测试地震专题描述");
    s.setFront_pic("d:\\1.jpg");
    s.setMagnitude("10");
    sm.save(s);
    
    sm.remove(sm.get(s.getId()), s.getFront_pic());
    List<Special> sl = sm.getDao().query("from Special s where s.magnitude= ? ","10");
    assertEquals(0, sl.size());
  }

}
