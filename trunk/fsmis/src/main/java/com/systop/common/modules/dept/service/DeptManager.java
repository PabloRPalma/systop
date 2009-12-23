package com.systop.common.modules.dept.service;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.service.BaseGenericsManager;
/**
 * 部门管理Manager
 * @author Sam Lee
 *
 */
@Service
public class DeptManager extends BaseGenericsManager<Dept> {
  /**
   * 用于计算部门编号
   */
  private DeptSerialNoManager serialNoManager;
  @Autowired(required = true)
  public void setSerialNoManager(DeptSerialNoManager serialNoManager) {
    this.serialNoManager = serialNoManager;
  }

  /**
   * 保存部门信息
   * @see BaseGenericsManager#save(java.lang.Object)
   */
  @Override
  @Transactional
  public void save(Dept dept) {
    Assert.notNull(dept);
    logger.debug("Parent dept {}", dept.getParentDept());
    //查询上级部门并建立双向关联
    Dept parent = dept.getParentDept();
    getDao().evict(parent);
    if (parent != null && parent.getId() != null) {
      logger.debug("Parent dept Id {}", dept.getParentDept().getId());
      parent = get(dept.getParentDept().getId());
      if (parent != null) {
        parent.getChildDepts().add(dept);
        dept.setParentDept(parent);
      }
    } else {
      dept.setParentDept(null); //处理parentId为null的情况
    }
    if(dept.getId() == null) {//新建的部门设置部门编号
      dept.setSerialNo(serialNoManager.getSerialNo(dept));
    }
    //FIXME: Clear the session or it will throw an exception with the message: 
    //"dentifier of an instance was altered from xxx to xxx"
    getDao().getHibernateTemplate().clear();
    super.save(dept);
  }
  
  
  /**
   * 删除部门，解除关联关系
   */

  @Override
  @Transactional
  public void remove(Dept dept) {
    Assert.notNull(dept);
    /** 部门隐藏，与角色关系不存在
    //解除部门-角色关联
    dept.setRoles(Collections.EMPTY_SET);
    */
    // 解除父部门关联
    dept.setParentDept(null);
    //解除子部门关联
    Set<Dept> children = dept.getChildDepts();
    for (Dept child : children) {
      child.setParentDept(null);
    }
    dept.setChildDepts(Collections.EMPTY_SET);
    
    super.remove(dept);
  }  
  
  /**
   * 根据部门名称得到部门对应的实体
   * @return
   */
  public Dept getDeptByName(String name) {
    String hql = "from Dept d where d.name = ? ";
    Dept dept = (Dept)getDao().findObject(hql, name);
    return dept;
  }

}
