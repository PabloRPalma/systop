package com.systop.fsmis.office.message.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.Message;

/**
 * 内部消息管理类Manager
 * @author ZW
 *
 */
@Service
public class MessageManager extends BaseGenericsManager<Message> {

	/**
	 * 得到所有用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllUser() {
		List<User> userList = this.getDao().query("from User");
		List allUser = new ArrayList();
		for (User user : userList) {
			Map map = new HashMap();
			map.put("id", user.getId());
			map.put("name", user.getName());
			allUser.add(map);
		}
		return userList;
	}

}
