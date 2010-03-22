package com.systop.modules.hr.company.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.modules.hr.dept.model.Dept;

/**
 * 公司管理Manager测试
 */
public class CompanyManagerTest extends BaseTransactionalTestCase {
  private CompanyManager companyManager;
  
  @Autowired
  public void setCompanyManager(CompanyManager companyManager) {
    this.companyManager = companyManager;
  }
 
  /**
   * Test method for {@link CompanyManager#save(Dept)}.
   */
  public void testSave() {
    Dept dept = new Dept();
    dept.setName("systop");
    companyManager.save(dept);
    assertNotNull(dept.getId());
  }
}
