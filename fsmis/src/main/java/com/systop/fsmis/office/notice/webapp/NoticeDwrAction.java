package com.systop.fsmis.office.notice.webapp;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.systop.core.webapp.dwr.BaseDwrAjaxAction;
import com.systop.fsmis.model.Notice;
import com.systop.fsmis.office.notice.service.NoticeManager;

@Component
public class NoticeDwrAction extends BaseDwrAjaxAction {
	
	@Autowired
	private NoticeManager noticeManager;
	/**
	 * 删除附件
	 * 
	 * @return
	 */
	public String removeAtt(Integer id) {
		logger.info("ID: " + id);
		Notice notice = noticeManager.get(id);
		String path = getServletContext().getRealPath(notice.getAtt());
		File file = new File(path);
		if (file.exists() && file.delete()) {
			return "success";
		} else {
			return "error";
		}
	}

}
