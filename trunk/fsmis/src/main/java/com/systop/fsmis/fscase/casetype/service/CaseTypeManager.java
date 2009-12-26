package com.systop.fsmis.fscase.casetype.service;

import java.util.ArrayList;
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
		// 处理父类别
		if (caseType.getCaseType().getId() != null) { 
			 CaseType parent = get(caseType.getCaseType().getId());
			 parent.getCaseTypes().add(caseType);
			 caseType.setCaseType(parent); 
		} else {
			 caseType.setCaseType(null); // 处理parentId为null的情况 
	    }
		getDao().getHibernateTemplate().clear();
		super.save(caseType);
	}
	
	/**
	 * 获得二级单体类别根据一级Id list方法
	 */
	public List getLevelTwoList(Integer id) {
		List<CaseType> caseTypes = query(
				"from CaseType where caseType.id = ?", id);
		List caseTypesList = new ArrayList();
		for (CaseType caseType : caseTypes) {
			Map caseTypemap = new HashMap();
			caseTypemap.put("id", caseType.getId());
			caseTypemap.put("name", caseType.getName());
			caseTypesList.add(caseTypemap);
		}
		return caseTypesList;
	}
	
	/**
	 * 获得一级类别 List方法
	 */
	public List getLevelOneList() {
		List<CaseType> caseTypes = query(
				"from CaseType where caseType.id is null");
		List caseTypesList = new ArrayList();
		for (CaseType caseType : caseTypes) {
			Map caseTypemap = new HashMap();
			caseTypemap.put("id", caseType.getId());
			caseTypemap.put("name", caseType.getName());
			caseTypesList.add(caseTypemap);
		}
		return caseTypesList;
	}
	
	/**
	 * 获得父类别 Name  方法
	 */
	public String getParentName(Integer id) {
		String parentName=null;
		CaseType cType = this.getDao().get(
				CaseType.class, id);
		parentName=cType.getName();
		return parentName;
	}
}
