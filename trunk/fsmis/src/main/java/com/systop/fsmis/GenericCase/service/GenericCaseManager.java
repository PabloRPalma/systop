package com.systop.fsmis.GenericCase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.GenericCase;


/**
 * 一般事件
 * @author shaozhiyuan
 *
 */
@Service
public class GenericCaseManager extends BaseGenericsManager<GenericCase>{
	
	/**
	 * 用于操作信息员数据
	 */
	/*@Autowired
	private SupervisorManager supervisorManager;*/
	
	
	
	/**
	 * 保存单体事件信息
	 */
	@Transactional
	public void save(GenericCase genericCase) {
		/*Supervisor supervisor = supervisorManager
				.getSupervisorByMobile(genericCase.getEventPeoplePhone());
		if (supervisor.getId() != null) {
			singleEvent.setSupervisor(supervisor);
		}*/

		getDao().saveOrUpdate(genericCase);
	}

	/**
	 * 根据单体事件的编号查询事件的详情
	 * 
	 * @param code
	 *            单体事件编号
	 * @return 对应的单体事件
	 */
	public GenericCase getGenericCaseByCode(String code) {
		String hql = "from GenericCase genericCase where genericCase.code =?";
		List<GenericCase> li = super.query(hql, code);
		if (!li.isEmpty()) {
			return li.get(0);
		}
		return new GenericCase();
	}

}
