package com.systop.fsmis.statistics.service;

import java.util.Date;
import java.util.List;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.FsCase;
/**
 * 事件统计管理
 * @author yj
 *
 */
public class FsCaseStatisticsManager extends BaseGenericsManager<FsCase> {
/**
 * 按事件状态进行统计
 * @param beginDate
 * @param endDate
 * @param county
 * @return
 */
	public List<Object[]> getFsCaseStatusStatistic(Date beginDate, Date endDate,
			Dept county) {
		
		return null;
	}


 
}
