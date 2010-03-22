package com.systop.modules.hr.dept.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.modules.hr.company.service.CompanyManager;
import com.systop.modules.hr.dept.model.Dept;

public class DeptManagerTest extends BaseTransactionalTestCase {
  private DeptManager deptManager;
  
  @Autowired
  public void setDeptManager(DeptManager deptManager) {
    this.deptManager = deptManager;
  }
  
  private CompanyManager companyManager;
  
  @Autowired
  public void setCompanyManager(CompanyManager companyManager) {
    this.companyManager = companyManager;
  }
  
  /**
   * Test method for {@link DeptManager#save(Dept)}.
   */
  public void testSave() {
    Dept parent = companyManager.getCompany();
    if (parent == null) {
      parent = new Dept();
      companyManager.save(parent);
    }
    Dept dept = new Dept();
    dept.setName("NiHao Ya");
    dept.setParentDept(parent);
    parent.getChildDepts().add(dept);
    deptManager.save(dept);
    assertNotNull(dept.getId());
  }
  
  public void testFindChild() {
    Dept parent = companyManager.getCompany();
    if (parent == null) {
      return;
    }
    
    List<Dept> list = deptManager.getByParentId(parent.getId());
    logger.debug("dept size " + list.size());
    for (Dept dept : list) {
      logger.debug("dept name " + dept.getName());
    }
    
  }
}
