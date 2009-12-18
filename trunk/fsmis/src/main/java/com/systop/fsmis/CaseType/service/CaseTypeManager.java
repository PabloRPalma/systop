package com.systop.fsmis.CaseType.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.CaseType;


/**
 * 事件类别管理类
 * 
 * @author shaozhiyuan
 */
@Service
@SuppressWarnings("unchecked")
public class CaseTypeManager  extends
      BaseGenericsManager<CaseType>{

	/**
	 * 获得一级类别
	 * 
	 * @return
	 */
	public Map getLevelOneMap() {
		List<CaseType> caseTypes = query("from CaseType where caseType.id is null");
		Map levelOne = new HashMap();
		for (CaseType caseType : caseTypes) {
			levelOne.put(caseType.getId(), caseType.getName());
		}
		return levelOne;
	}
	@Transactional
	public void save(CaseType caseType) {
		Assert.notNull(caseType);
		
		getDao().getHibernateTemplate().clear();
		super.save(caseType);
	}
}
