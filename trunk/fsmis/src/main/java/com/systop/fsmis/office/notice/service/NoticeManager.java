package com.systop.fsmis.office.notice.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ReceiveRecordManager receiveRecordManager;
	/**
	 * 保存各部门通知记录
	 * @param notice
	 * @param deptsID
	 */
	@Transactional
	public void saveDeptRecord(Notice notice, List<Integer> deptIds) {
		Set<ReceiveRecord> receiveRecords = notice.getRecRecordses();
		if (CollectionUtils.isNotEmpty(receiveRecords)) {
			for (ReceiveRecord r : receiveRecords) {
				receiveRecordManager.remove(r);
			}
		}
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

	/**
	 * 删除通知信息，连同照片一起删除
	 * @param notice
	 * @param path
	 */
	@Transactional
	public void remove(Notice notice, String path) {
		if (notice != null) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
		super.remove(notice);
		
	}
}
