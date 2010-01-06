package com.systop.fsmis.office.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.ReceiveRecord;

/**
 * 接收记录Struct2 Action。
 * @author ZW
 *
 */
@Service
public class ReceiveRecordManager extends BaseGenericsManager<ReceiveRecord> {

	/**
	 * 获得用户新接收的通知
	 * @param user
	 * @return
	 */
	public List<ReceiveRecord> getNewNotices(Dept dept) {
		String hql = "from ReceiveRecord r where r.isNew = ? and r.dept.id = ?";
		return this.query(hql, new Object[]{FsConstants.Y, dept.getId()});
	}

}
