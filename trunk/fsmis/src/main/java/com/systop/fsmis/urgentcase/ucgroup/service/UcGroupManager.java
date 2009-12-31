package com.systop.fsmis.urgentcase.ucgroup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentType;
import com.systop.fsmis.urgentcase.UcConstants;

/**
 * 应急指挥组管理
 * 
 * @author yj
 */
@Service
public class UcGroupManager extends BaseGenericsManager<UrgentGroup> {

	/**
	 * 保存组，类别下已经添加的事故处理组，善后处理组，禁止添加
	 */
	@Transactional
	public void save(UrgentGroup ug) {
		logger.info("组对应的类别{}", ug.getUrgentType());
		if (ug.getUrgentType() != null) {
			if (getDao().exists(ug, "county", "urgentType", "category")) {
				throw new ApplicationException(ug.getName() + "已在"
						+ ug.getUrgentType().getName() + "下添加！");
			}
		} else {
			if (getDao().exists(ug, "county", "category")) {
				throw new ApplicationException(ug.getName() + "已添加！");
			}
		}
		super.save(ug);
	}

	/**
	 *类别列表页面提示使用
	 */
	public String getUcGroupName(UrgentType ut) {
		UrgentGroup ugTemp;
		String msg = "";
		ugTemp = findObject(
				"from UrgentGroup ug where ug.urgentType.id=? and ug.category=?",
				ut.getId(), UcConstants.ACCIDENT_HANDLE);
		if (ugTemp == null) {
			msg = "事故调查处理组未添加 ";
		}
		ugTemp = findObject(
				"from UrgentGroup ug where ug.urgentType.id=? and ug.category=?",
				ut.getId(), UcConstants.AFTER_HANDLE);
		if (ugTemp == null) {
			msg += " 善后处理组未添加";
		}
		logger.info("msg{}", msg);
		return msg;
	}
}
