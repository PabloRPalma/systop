package com.systop.fsmis.supervisor.service;

import java.io.File;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		throw new ApplicationException("添加的信息员编号已存在。");
		}
		if (!supervisor.getMobile().isEmpty() && getDao().exists(supervisor, "mobile")) {
			throw new ApplicationException("添加的手机号已存在。");
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

	/** 父类保存方法*/
	@Transactional
	public void superSave(Supervisor supervisor){
			super.save(supervisor);
		}
}
