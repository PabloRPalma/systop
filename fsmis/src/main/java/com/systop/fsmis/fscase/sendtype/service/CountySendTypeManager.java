package com.systop.fsmis.fscase.sendtype.service;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.CountySendType;

@Service
public class CountySendTypeManager extends BaseGenericsManager<CountySendType> {

	/**
	 * 根据派遣类别和区县得到对应的派遣方式
	 * 
	 * @param sendTypeId
	 * @param countyId
	 * @return
	 */
	public CountySendType getBySendTypeAndCounty(Integer sendTypeId,
			Integer countyId) {
		CountySendType cst = null;
		if (sendTypeId != null && countyId != null) {//两个参数都不为空时才有效
			String hql = "from CountySendType c where c.sendType.id = ? and c.county.id = ?";
			cst = findObject(hql, new Object[] { sendTypeId, countyId });
		}
		return cst;
	}
}
