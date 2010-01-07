package com.systop.fsmis.urgentcase.ucgroup.service;

import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.security.user.model.User;
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
	 * 
	 * @param ug
	 *          应急组
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
	 * 
	 * @param ut
	 *          应急类别
	 */
	public String getUcGroupName(UrgentType ut) {
		UrgentGroup ugTemp;
		String msg = "";
		ugTemp = findObject(
				"from UrgentGroup ug where ug.urgentType.id=? and ug.category=?", ut
						.getId(), UcConstants.ACCIDENT_HANDLE);
		if (ugTemp == null) {
			msg = "事故调查处理组未添加 ";
		}
		ugTemp = findObject(
				"from UrgentGroup ug where ug.urgentType.id=? and ug.category=?", ut
						.getId(), UcConstants.AFTER_HANDLE);
		if (ugTemp == null) {
			msg += " 善后处理组未添加";
		}
		logger.info("msg{}", msg);
		return msg;
	}

	/**
	 * 保存应急组和人员之间的关系
	 * 
	 * @param userIds
	 *          前台操作人员字符串，类似"张三,李四,王五"
	 * @param ug
	 *          应急组
	 */
	@Transactional
	public void setUserUrgentGroup(String userIds, UrgentGroup ug) {
		// 删除关系
		Set<User> users = ug.getUsers();
		for (User user : users) {
			user.getUrgentGroups().remove(ug);
			this.getDao().save(user);
		}
		// 组清空用户
		ug.getUsers().clear();
		if (StringUtils.isNotBlank(userIds)) {
			// 添加关系
			for (String userId : userIds.split(",")) {
				User user = this.getDao().get(User.class, Integer.valueOf(userId));
				user.getUrgentGroups().add(ug);
				ug.getUsers().add(user);
				this.getDao().save(user);
			}
		}
	}
}
