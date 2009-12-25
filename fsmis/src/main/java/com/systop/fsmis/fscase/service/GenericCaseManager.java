package com.systop.fsmis.fscase.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.FsCase;


/**
 * 一般事件
 * @author shaozhiyuan
 *
 */
@Service
public class GenericCaseManager extends BaseGenericsManager<FsCase>{
	
	/**
	 * 保存一般事件信息
	 */
	@Transactional
	public void save(FsCase genericCase) {
		getDao().saveOrUpdate(genericCase);
	}

	/**
	 * 根据单体事件的编号查询事件的详情
	 * @param code 单体事件编号
	 * @return 对应的单体事件
	 */
	public FsCase getGenericCaseByCode(String code) {
		return findObject("from GenericCase g where g.code =?", code);
	}
}
