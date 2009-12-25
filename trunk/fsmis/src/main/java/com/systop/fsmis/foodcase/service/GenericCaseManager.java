package com.systop.fsmis.foodcase.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.FoodCase;


/**
 * 一般事件
 * @author shaozhiyuan
 *
 */
@Service
public class GenericCaseManager extends BaseGenericsManager<FoodCase>{
	
	/**
	 * 保存一般事件信息
	 */
	@Transactional
	public void save(FoodCase genericCase) {
		getDao().saveOrUpdate(genericCase);
	}

	/**
	 * 根据单体事件的编号查询事件的详情
	 * @param code 单体事件编号
	 * @return 对应的单体事件
	 */
	public FoodCase getGenericCaseByCode(String code) {
		return findObject("from GenericCase g where g.code =?", code);
	}
}
