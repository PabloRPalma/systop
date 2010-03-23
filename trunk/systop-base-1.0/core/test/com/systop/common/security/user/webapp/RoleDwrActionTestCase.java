/**
 * 
 */
package com.systop.common.security.user.webapp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

import com.systop.common.dao.Page;
import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.RoleManager;
import com.systop.common.security.user.service.UserManager;
import com.systop.common.test.BaseTestCase;

/**
 * TestCase of the {@link RoleDwrAction}
 * @author han
 *
 */
public class RoleDwrActionTestCase extends BaseTestCase {
	private RoleDwrAction getRoleDwrAction() {
		return (RoleDwrAction) applicationContext.getBean("roleDwrAction");
	}
	private UserManager getUserMgr() {
		return (UserManager) applicationContext.getBean("userManager");
	}
	private RoleManager getRoleMgr() {
		return (RoleManager) applicationContext.getBean("roleManager");
	}
	/**
	 * Test method for 
	 * {@link RoleDwrAction#getRolesOfUser(Integer, int, int, String)}.
	 */
	public void testGetRolesOfUser() {
		fail("因为被测方法涉及到servlet的操作，暂时不写测试"); // TODO
		//创建一个用户
		User me = new User();
		me.setLoginId("me");
		me.setPassword(DigestUtils.md5Hex("me"));
		me.setName("我");
		me.setStatus("0");
		me.setVersion(1);
		getUserMgr().save(me);
		
		//创建一堆角色
		final int num = 20;
		for (int i = 0; i < num; i++) {
			insertRole("角色".concat(String.valueOf(i)));
		}
		
		//给“我”分配几个角色
		Set<Role> rolesOfMe = new HashSet<Role>();
		List<Role> allRoles = getRoleMgr().get();
		final int assignNum = 10;
		// 用来记录已经分配的角色
		Map<Integer, Role> assignedRoles 
			= new HashMap<Integer, Role>(); 
		for (int i = 0; i < assignNum; i++) {
			rolesOfMe.add(allRoles.get(i));
			assignedRoles.put(allRoles.get(i).getId(), allRoles.get(i));
		}
		
		me.setRoles(rolesOfMe);
		
		//以下代码开始测试
		// 测试目标－－查到的角色列表中，角色的selected属性设置是否正确
		Page result = getRoleDwrAction().getRolesOfUser(me.getId(), 1, num, "");
		List resultLst = (List) result.getResult();

		for (Iterator itr = resultLst.iterator(); itr.hasNext();) {
			Role role = (Role) itr.next();
			if (role.isSelected()) {
				assertTrue("角色的selected属性设置不正确", assignedRoles
						.get(role.getId()) != null);
			} else {
				assertTrue("角色的selected属性设置不正确", assignedRoles
						.get(role.getId()) == null);
			}
		}
		
		//测试目标－－在执行方法返回的结果中，总记录数是否与初始化创建的角色数相同
		assertTrue("查到的结果与数据库中真实存在的角色数不一致", 
				result.getTotalCount() == num);
		//测试目标－－按角色名称模糊查询是否能查出正确的结果
		result = getRoleDwrAction().getRolesOfUser(me.getId(), 1, num, "20");
		assertTrue("数据库中只有一条角色名称包含'20'的记录,而查询结果中查到的个数却不是一个",
				result.getTotalCount() == 1);
	}

	/**
	 * Test method for {@link RoleDwrAction#isRoleNameInUse(String)}.
	 */
	public void testIsRoleNameInUse() {
		RoleDwrAction rda = (RoleDwrAction) applicationContext
				.getBean("roleDwrAction");
		String rolename = "单元测试创建的角色";
		assertTrue("初始化前的错误：创建角色的操作未执行，而要创建的角色已存在？", 
				!rda.isRoleNameInUse(rolename));
		Role role = new Role();
		role.setName(rolename);
		role.setVersion(1);
		getRoleMgr().save(role);
		assertTrue("初始化后的错误：创建的角色不存在", 
				rda.isRoleNameInUse(role.getName()));
	}

	/**
	 * Test method for
	 * {@link RoleDwrAction#selectRole(Integer, Integer, boolean)}.
	 */
	public void testSelectRole() {
		fail("因为被测方法涉及到对session的操作，暂时不写测试"); // TODO
		/*
		 * 测试目标
		 * 1.将一个role临时分配给一个用户，看看session中的待分配列表中是否有此role
		 * 2.将一个role正式分配给一个用户，再将此role设置为临时反选，看看session的
		 *   时间待反选列表中是否有此role
		 * 3.将一个role正式分配给一个用户，然后再将同一个role临时“选中”，看看
		 *   session中的待分配列表中是否有此role
		 * 4.将一个已经正式分配给用户的role临时反选，然后再看看session中的待“反选”
		 *   列表中是否有此role
		 */
		
	}

	/**
	 * Test method for {@link RoleDwrAction#saveUserRoles(Integer)}.
	 */
	public void testSaveUserRoles() {
		fail("因为被测方法涉及到对session的操作，暂时不写测试"); // TODO
		/*
		 * 测试目标
		 * 1.session中的临时“分配”列表和临时“反选”列表中的角色清单是否完全写入到数据，
		 *   即：正式分配给用户
		 */
	}

	/**
	 * Test method for {@link RoleDwrAction#cancelSaveUserRoles(Integer)}.
	 */
	public void testCancelSaveUserRoles() {
		fail("因为被测方法涉及到对session的操作，暂时不写测试"); // TODO
		/*
		 * 测试目标
		 * 1.session中的临时“分配”和临时“反选”列表是否清空成功
		 */
	}
	
	/**
	 * 创建角色
	 * @param name
	 */
	private void insertRole(String name) {
		Role role = new Role();
		role.setName(name);
		role.setVersion(1);
		getRoleMgr().save(role);
	}
}
