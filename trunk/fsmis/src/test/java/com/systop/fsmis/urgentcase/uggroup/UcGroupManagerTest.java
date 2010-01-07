package com.systop.fsmis.urgentcase.uggroup;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.urgentcase.ucgroup.service.UcGroupManager;

/**
 * 应急组测试
 * 
 * @author yj
 * 
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class UcGroupManagerTest extends BaseTransactionalTestCase {

	/**
	 * 应急组管理
	 */
	@Autowired
	private UcGroupManager ucGroupManager;
	/**
	 * 用户管理
	 */
	@Autowired
	private UserManager userManager;

	public void testSetUserUrgentGroup() {
		UrgentGroup ug = ucGroupManager.findObject(
				"from UrgentGroup ug where ug.name like ? ", MatchMode.ANYWHERE
						.toMatchString("指挥"));
		List<User> users = userManager.query("from User");
		int length = 0;
		String userIds = "";
		for (User u : users) {
			if (length == users.size() - 1) {
				userIds += u.getId();
				break;
			}
			userIds += u.getId() + ",";
			length++;
		}
		ucGroupManager.setUserUrgentGroup(userIds, ug);
		Set<User> us=ug.getUsers();
		for(User u: us){
			System.out.println(u.getName());
		}
		
	}

}
