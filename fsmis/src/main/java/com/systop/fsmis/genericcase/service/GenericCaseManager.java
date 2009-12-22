package com.systop.fsmis.genericcase.service;

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
	 * 保存一般事件信息
	 */
	@Transactional
	public void save(GenericCase genericCase) {
		getDao().saveOrUpdate(genericCase);
	}

	/**
	 * 根据单体事件的编号查询事件的详情
	 * @param code 单体事件编号
	 * @return 对应的单体事件
	 */
	public GenericCase getGenericCaseByCode(String code) {
		return findObject("from GenericCase g where g.code =?", code);
	}
}
