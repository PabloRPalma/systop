package com.systop.modules.hr.employee.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.modules.admin.security.rbac.model.User;
import com.systop.modules.hr.employee.model.Employee;

public class EmployeeManagerTest extends BaseTransactionalTestCase {
  private EmployeeManager employeeManager;

  @Autowired
  public void setEmployeeManager(EmployeeManager employeeManager) {
    this.employeeManager = employeeManager;
  }

  public void testSave() {
    Employee employee = new Employee();
    User user = new User();
    user.setEmail("123Hello321@qq.com");
    user.setLoginId("321123987654ja");
    user.setPassword("123");
    user.setConfirmPwd("123");

    employee.setName("Hello");
    employee.setSex("F");

    employee.setUser(user);
    user.getEmployees().add(employee);

    employeeManager.save(employee);
    assertNotNull(user.getId());
    assertNotNull(employee.getId());
    logger.info("user id is " + user.getId());
    logger.info("Employee id is " + employee.getId());
  }
}
