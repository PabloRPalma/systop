package com.systop.fsmis.fscase.sendtype.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.SendType;

/**
 * 用于对SendType的查询和数据修改
 * @author Lunch
 */
@Service
public class SendTypeManager extends BaseGenericsManager<SendType> {

	/**
	 * 查询派遣环节,并根据sortId进行排序
	 * @return
	 */
	public List<SendType> orderSendType() {
		String hql = "from SendType s order by s.sortId";
		return query(hql);
	}

}
