package com.systop.fsmis.supervisor.service;

import java.io.File;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.Supervisor;
/**
 * 信息信息员管理
 * 
 * @author zhaozhg
 */

@Service
public class SupervisorManager extends BaseGenericsManager<Supervisor> {

	/**
	 * 保存信息员信息并验证信息员编号、手机号的唯一性
	 */
	@Transactional
	public void save(Supervisor supervisor) {
		getDao().getHibernateTemplate().clear();
		if (getDao().exists(supervisor, "code")) {
		throw new ApplicationException("添加的信息员编号【"+ supervisor.getCode()+"】已存在。");
		}
		if (!supervisor.getMobile().isEmpty() && getDao().exists(supervisor, "mobile")) {
			throw new ApplicationException("添加的手机号【"+ supervisor.getMobile()+"】已存在。");
		}
		super.save(supervisor);
	}

	/**
	 * 根据信息员编号取得该信息员实体信息
	 */
	public Supervisor getSupervisorByCode(String code) {
		String hql = "from Supervisor supervisor where supervisor.code = ?";
		List<Supervisor> li = super.query(hql, code);
		if (!li.isEmpty()) {
			return li.get(0);
		}
		return new Supervisor();
	}

	/**
	 * 根据信息员手机号取得该信息员实体信息
	 */
	public Supervisor getSupervisorByMobile(String mobile) {
		String hql = "from Supervisor s where s.mobile = ? ";
		List<Supervisor> li = query(hql, mobile);
		if (!li.isEmpty()) {
			return li.get(0);
		}
		return new Supervisor();
	}

		
	/** 删除信息员信息，如果有照片存在则删除照片*/
	@Transactional
	public void remove(Supervisor supervisor, String realPath){
		if (!supervisor.getPhotoUrl().isEmpty()) {
			File file = new File(realPath);
			if (file.exists()) {
				file.delete();
			}
		}
		super.remove(supervisor);
	}
	
	/**
	 * 得到当前登录用户所属部门下所有信息员
	 * @return 当前部门为空，返回null，否则返回所有信息员
	 */
	public List<Supervisor> getAllSupervisor(Dept dept){
    String hql = "";
		if (dept != null) {
			//顶级部门则查询全部
			if (dept.getName().equals(DeptConstants.TOP_DEPT_NAME)) {
				hql = hql + "from Supervisor s where 1=1";
			} else if (dept.getChildDepts().size() > 0) {//非顶级部门则查询本部门和所有下属部门
				hql = hql + "from Supervisor s where s.dept.id = "
					+ Integer.toString(dept.getId());
				for (Dept childen : dept.getChildDepts()) {
					hql = hql + " or s.dept.id = " + Integer.toString(childen.getId());
				}
			} else {
				hql = hql + "from Supervisor s where s.dept.id = "
						+ Integer.toString(dept.getId());
			}
		} else {
			return null;
		}
		return query(hql);
	}
	
  /**
   * 根据部门ID、监管员姓名以及身份证号获取监管员实体
   * @param deptId 部门ID
   * @param name  监管员姓名
   * @param idNumber 身份证号
   * @return Supervisor 监管员实体
   */
	public Supervisor getSupervisorInfo(Integer deptId, String code, String name) {
		String hql = "from Supervisor s where s.dept.id = ? and s.code = ? and s.name = ? ";
		Supervisor supervisor = (Supervisor) getDao().findObject(hql, 
				       new Object[] {deptId, code, name});
	  return supervisor;	
	}
	
	/**
	 * 保存从Excel表中导入的监管员信息
	 */
	@Transactional
	public void saveSupervisor(Supervisor supervisor) {
		super.save(supervisor);
	}
}
