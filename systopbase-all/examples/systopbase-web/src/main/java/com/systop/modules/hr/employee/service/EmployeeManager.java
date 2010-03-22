package com.systop.modules.hr.employee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.core.Constants;
import com.systop.core.service.BaseGenericsManager;
import com.systop.modules.admin.security.rbac.model.User;
import com.systop.modules.admin.security.rbac.service.UserManager;
import com.systop.modules.hr.dept.service.DeptManager;
import com.systop.modules.hr.employee.model.Employee;

/**
 * 员工管理类
 * @author WBB
 * @version 3.0
 */
@Service()
public class EmployeeManager extends BaseGenericsManager<Employee> {
  /**
   * 用户Manager
   */
  private UserManager userManager;

  /**
   * 部门Manager
   */
  private DeptManager deptManager;

  /**
   * 保存员工信息
   */
  @Override
  @Transactional
  public void save(Employee employee) {
    Assert.notNull(employee);
    Assert.notNull(employee.getUser());
    Set<Employee> employees = new HashSet<Employee>();
    employees.add(employee);
    employee.getUser().setEmployees(employees);
    userManager.save(employee.getUser());
  }
  
  /**
   * 更新用户信息
   * @param user
   */
  @Transactional
  public void updateUser(User user) {
    userManager.update(user);
  }

  /**
   * 根据部门ID查询员工
   * @param deptId 部门ID
   * @return
   */
  public List<Employee> queryEmployeeByDeptId(Integer deptId) {
    Assert.notNull(deptId);
    String hql = "from Employee e where e.user.status = ? and e.dept.id = ?";
    return query(hql, new Object[]{Constants.STATUS_AVAILABLE, deptId});
  }
  
  /**
   * 返回部门以及子部门下的员工信息
   * @return
   */
  @SuppressWarnings("unchecked")
  public Map getDeptEmpTree() {
    return getDeptEmpTree(deptManager.getDeptTree());
  }

  /**
   * 返回部门以及子部门下的员工信息
   * @param dept 部门
   * @return
   */
  @SuppressWarnings("unchecked")
  public Map getDeptEmpTree(Map dept) {
    if (dept.isEmpty()) {
      return dept;
    }
    // 设置部门类型
    dept.put("descn", "DEPT");
    // 得到部门ID
    Integer deptId = (Integer) dept.get("id");
    List<Employee> list = queryEmployeeByDeptId(deptId);
    // 添加本部门员工
    List children = new ArrayList();
    for (Employee emp : list) {
      Map map = new HashMap();
      map.put("id", emp.getId());
      map.put("text", emp.getName());
      map.put("descn", "EMP");
      map.put("sex", emp.getSex());
      map.put("leaf", true);
      children.add(map);
    }

    // 得到此部门下的所有子部门
    List<Map> childNodes = (List) dept.get("childNodes");
    if (childNodes != null) {
      for (Map map : childNodes) {
        map = getDeptEmpTree(map);
        children.add(map);
      }
    }
    if (!children.isEmpty()) {
      dept.put("children", children);
      dept.put("childNodes", children);
      dept.put("leaf", false);
    } else {
      dept.put("leaf", true);
    }
    return dept;
  }

  @Autowired
  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

  @Autowired
  public void setDeptManager(DeptManager deptManager) {
    this.deptManager = deptManager;
  }
}
