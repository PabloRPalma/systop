package com.systop.modules.hr.dept.webapp.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTestCase;
import com.systop.modules.hr.dept.model.Dept;
import com.systop.modules.hr.dept.webapp.DeptAction;

public class DeptValidatorTest extends BaseTestCase {
  private DeptAction deptAction;

  @Autowired
  public void setDeptAction(DeptAction deptAction) {
    this.deptAction = deptAction;
  }

  public void testValidate() {
    Dept dept = new Dept();
    deptAction.setModel(dept);
    deptAction.validate();
    if (deptAction.hasActionErrors()) {
      for (String msg : deptAction.getActionErrors()) {
        logger.info(msg);
      }
    }
    
    if (deptAction.hasActionMessages()) {
      for (String msg : deptAction.getActionMessages()) {
        logger.info(msg);
      }
    }
    
    if (deptAction.hasFieldErrors()) {
      for (String key : deptAction.getFieldErrors().keySet()) {
        List<String> list = deptAction.getFieldErrors().get(key);
        for (String msg : list) {
          logger.info(msg);
        }
      }
    }
  }
}
