package com.systop.fsmis.expert.service;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.ExpertCategory;

/**
 * 专家类别管理Manager
 * @author ShangHua
 *
 */
@Service
public class CategoryManager extends BaseGenericsManager<ExpertCategory>{
	
	/**
	 * 保存专家类别信息,并验证专家类别的唯一性
	 */
	@Transactional
	public void save(ExpertCategory expertCategory) {
		if (getDao().exists(expertCategory, "name")) {
			if (StringUtils.isNotEmpty(expertCategory.getName())) {
				throw new ApplicationException("专家类别【" + expertCategory.getName() + "】已存在。");
			}
		}
		super.save(expertCategory);
	}
}
