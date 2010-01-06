package com.systop.fsmis.office.message.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Message;

/**
 * 内部消息管理类Manager
 * @author ZW
 *
 */
@Service
public class MessageManager extends BaseGenericsManager<Message> {

	
	/**
	 * 获得用户新接收的内部消息
	 * @return
	 */
	public List<Message> getNewMes(User user) {
		String hql = "from Message m where m.isNew = ? and m.receiver.id = ?";
		return query(hql, new Object[]{FsConstants.Y, user.getId()});
	}

}
