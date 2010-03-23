package com.systop.common.security.dept.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.systop.common.security.dept.model.Dept;
import com.systop.common.security.dept.service.DeptManager;
import com.systop.common.security.user.model.User;
import com.systop.common.service.BaseManager;

/**
 * /** <code>DeptManager</code>的实现类
 * @see {@link DeptManager}
 * @author qian.wang
 * 
 */
public class DeptManagerImpl extends BaseManager<Dept> implements DeptManager {

  /**
   * @see DeptManager#addUser(Dept, User)
   */
  public void addUser(Dept dept, User user) {
    user.setDept(dept);
    dept.getUsers().add(user);
    save(dept);

  }

  /**
   * @see DeptManager#findByParent(Dept)
   */
  @SuppressWarnings("unchecked")
  public List<Dept> findByParent(Dept parent) {
    String hsql = null;

    if (parent == null) {
      hsql = "from Dept d where d.parent is null";
      return find(hsql);
    } else {
      hsql = "from Dept d where d.parent=?";
      return find(hsql, parent);
    }
  }

  /**
   * @see DeptManager#findUsersByParent(Dept)
   */
  @SuppressWarnings("unchecked")
  public List<User> findUsersByDept(Dept dept) {

    return find("from User u where u.dept=?", dept);
  }

  /**
   * @see DeptManager#removeUsers(Dept)
   */
  public void removeUsers(Dept parent) {
    Set<User> users = parent.getUsers();
    for (User user : users) {
      parent.getUsers().remove(user);
      removeObject(user);
    }
  }

  /**
   * DeptManager#getDeptsByParentId(java.lang.Integer)
   */
  @SuppressWarnings("unchecked")
  public List<Dept> getDeptsByParentId(Integer parentId) {
    List<Dept> depts = null;
    if (parentId == null || parentId.intValue() <= -1) {
      depts = findByParent(null);
    } else {
      Dept parent = get(parentId);
      if (parent != null) {
        depts = findByParent(parent);
      }
    }

    if (depts == null) {
      return Collections.EMPTY_LIST;
    }
    return depts;
  }

  /**
   * @see DeptManager#saveDept(Integer, Dept)
   */
  public Integer saveDept(Integer parentId, Dept dept) {
    Dept parent = null;

    if (parentId != null && parentId.intValue() > 0) {
      parent = get(parentId);
    }
    if (parent != null) {
      parent.getDepts().add(dept);
      dept.setParent(parent);
    }
    save(dept);
    return dept.getId();
  }

  /**
   * @see DeptManager#updateDept(Dept)
   */
  public Integer updateDept(Dept deptForm) {
    if (deptForm.getId() == null) {
      throw new NullPointerException("id.null");
    }

    Dept dept = get(deptForm.getId());
    dept.setName(deptForm.getName());
    dept.setDescn(deptForm.getDescn());

    save(dept);

    return dept.getId();
  }

}
