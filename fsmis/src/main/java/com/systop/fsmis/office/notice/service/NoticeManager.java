package com.systop.fsmis.office.notice.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Notice;
import com.systop.fsmis.model.ReceiveRecord;

/**
 * 通知管理类
 * 
 * @author ZW
 */
@Service
public class NoticeManager extends BaseGenericsManager<Notice> {

	/**
	 * 保存各部门通知记录
	 * @param notice
	 * @param deptsID
	 */
	@Transactional
	public void saveDeptRecord(Notice notice, List<Integer> deptIds) {
		save(notice);
		if (CollectionUtils.isNotEmpty(deptIds)) {
			for (Integer deptId : deptIds) {
				ReceiveRecord receiveRecord = new ReceiveRecord();
				receiveRecord.setDept(getDao().get(Dept.class, deptId));
				receiveRecord.setIsNew(FsConstants.Y);
				receiveRecord.setNotice(notice);
				getDao().save(receiveRecord);
			}
		}
	}
}
