package datashare.admin.method.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.common.modules.security.user.model.Role;

import datashare.admin.method.model.Method;
import datashare.admin.method.service.MethodManager;
import datashare.base.test.BaseTestCase;


public class MethodManagerTest extends BaseTestCase {
  @Autowired
  private MethodManager methodManager;
 
  /**
   * 保存测项
   */
  public void testSave() {
    Method  ti = new Method();
    ti.setName("测项");
    ti.setId("21");
    methodManager.save(ti);
    assertTrue(ti.getId() != null);
  }
  
  /**
   * 关联测试
   */
  public void testSaveRole() {
    Role role = new Role();
    role.setName("ROLE_ADMIN");
    role.setDescn("管理员用户");
    methodManager.getDao().save(role);
    assertTrue(role.getId() != null);
    Method ti = new Method();
    ti.setId("21");
    ti.setName("关联测试");
    ti.getRoles().add(role);
    methodManager.save(ti);
    assertTrue(ti.getId() != null);    
  }
}
