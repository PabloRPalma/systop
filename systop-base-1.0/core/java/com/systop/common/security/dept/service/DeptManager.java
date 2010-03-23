package com.systop.common.security.dept.service;

import java.util.List;

import com.systop.common.security.dept.model.Dept;
import com.systop.common.security.user.model.User;
import com.systop.common.service.Manager;

/**
 * 部门管理
 * @author qian.wang
 */
public interface DeptManager extends Manager<Dept> {
 
  /**
   * 列出指定<code>Dept</code>下的所有Dept
   * @param parent <code>Dept</code>
   * @return 包含<code>Dept</code>实例的List，如果<code>Dept</code>
   *         下没有任何内容，返回空List
   */
  List<Dept> findByParent(Dept parent);
  /**
   * 删除指定的部门<code>Dept</code>下的用户
   * @param parent 指定的<code>Dept</code>
   */
  void removeUsers(Dept parent);

  /**
   * 为指定的<code>Dept</code>添加一个用户（<code>User</code>）
   * @param dept 指定的<code>Dept</code>
   * @param user 添加的用户
   */
  void addUser(Dept dept, User user);
  
  /**
   * 根据部门，列出下级部门
   * @param parentId 父部门Id
   * @return List of dept or empty list.
   */
  public List getDeptsByParentId(Integer parentId);
  
  /**
   * 列出指定<code>Dept</code>下的所有Users
   * @param dept <code>Dept</code>
   * @return 包含<code>User</code>实例的List，如果<code>Dept</code>
   *         下没有任何内容，返回空List
   */
  List<User> findUsersByDept(Dept dept);
  
  /**
   * 保存一个子部门
   * 
   * @param parentId 父目录Id
   * @param dept 即将保存的Dept
   */
  public Integer saveDept(Integer parentId, Dept dept);

  /**
   * 更新编辑好的部门
   * 
   * @param deptForm 从前台传来的编辑好的部门
   * @return
   */
  public Integer updateDept(Dept deptForm);

}
