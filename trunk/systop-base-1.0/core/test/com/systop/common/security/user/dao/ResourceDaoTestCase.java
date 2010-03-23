/**
 * 
 */
package com.systop.common.security.user.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.systop.common.security.user.model.Resource;
import com.systop.common.test.BaseTestCase;

/**
 * Junit test case of <code>ResourceDao</code>
 * @author Sam
 *
 */
public class ResourceDaoTestCase extends BaseTestCase {
  /**
   * 从applicationContext中获得ResourceDao的实例，简化代码
   */
  private ResourceDao getResDao() {
    return (ResourceDao) applicationContext.getBean("resourceDao");
  }
  /**
   * Test method for {@link ResourceDao#findBy(java.util.Map)}.
   */
  public void testFindByMap() {
    for (int i = 0; i < times; i++) {
      Resource res = new Resource();
      res.setName(RandomStringUtils.randomAscii(times) + "_RES");
      res.setResType(RandomStringUtils.randomAscii(1));
      getResDao().saveObject(res);
    }
    
    Map filter = new HashMap();
    filter.put("name", "_RES");
    
    assertTrue(getResDao().findBy(filter).size() == times);
    
    filter = new HashMap();
    filter.put("name", "_超人");
    assertTrue(getResDao().findBy(filter).size() == 0);
  }

}
