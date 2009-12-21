package com.systop.fsmis.supervisor.service;

import java.io.File;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.Supervisor;
/**
 * 信息监管员管理
 * 
 * @author zhaozhg
 */

@Service
public class SupervisorManager extends BaseGenericsManager<Supervisor> {

	/**
	 * 保存监管员信息并验证监管员编号、手机号的唯一性
	 */
	@Transactional
	public void save(Supervisor supervisor) {
		getDao().getHibernateTemplate().clear();
		if (getDao().exists(supervisor, "code")) {
		throw new ApplicationException("添加的监管员编号已存在。");
		}
		if (!supervisor.getMobile().isEmpty() && getDao().exists(supervisor, "mobile")) {
			throw new ApplicationException("添加的手机号已存在。");
		}
		super.save(supervisor);
	}

	/**
	 * 根据监管员编号取得该监管员实体信息
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
	 * 根据监管员手机号取得该监管员实体信息
	 */
	public Supervisor getSupervisorByMobile(String mobile) {
		String hql = "from Supervisor s where s.mobile = ? ";
		List<Supervisor> li = query(hql, mobile);
		if (!li.isEmpty()) {
			return li.get(0);
		}
		return new Supervisor();
	}

		
	/** 删除监管员信息，如果有照片存在则删除照片*/
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	@Transactional
	public void superSave(Supervisor supervisor){
			super.save(supervisor);
		}
}
