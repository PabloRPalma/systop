package com.systop.fsmis.init;

import java.util.List;

import javax.annotation.PostConstruct;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.model.Role;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.Constants;

/**
 * 初始化工具类，Spring在启动后会自动执行init方法
 * 
 */
@SuppressWarnings("unchecked")
public class UserInitializer {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 缺省的账号
	 */
	public static final String ADMIN = "admin";

	/**
	 * 缺省的密码
	 */
	public static final String DEFAULT_PWD = "manager";

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	/** 用于加密 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	private String adminPwd = DEFAULT_PWD;

	private String adminName;

	private String adminDesc;

	@PostConstruct
	@Transactional
	public void init() {
		logger.info("系统正在初始化...");
		Session session = sessionFactory.openSession();
		try {

			// 初始化系统管理员
			if (!hasAdmin(session)) {
				User admin = new User();
				admin.setLoginId(ADMIN);
				admin.setPassword(passwordEncoder
						.encodePassword(adminPwd, null));
				admin.setName(adminName);
				admin.setDescn(adminDesc);
				// 是否可用：可用
				admin.setStatus(Constants.STATUS_AVAILABLE);
				// 是否系统用户：是
				admin.setIsSys(Constants.STATUS_AVAILABLE);
				admin.setDept(getTopDept(session));
				// 初始化角色
				for (Role role : UserConstants.SYS_ROLES) {
					if (!hasSysRole(session, role.getName())) {
						session.save(role);
						admin.getRoles().add(role);
					}
				}
				session.save(admin);
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 获得顶级部门
	 * 
	 * @param session
	 * @return
	 */
	private Dept getTopDept(Session session) {
		List<Dept> list = session.createQuery(
				"from Dept d where d.parentDept = null").list();
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 是否已经有admin帐户
	 */
	private boolean hasAdmin(Session session) {
		List list = session.createQuery("from User u where u.loginId='admin'")
				.list();
		return list != null && list.size() > 0;
	}

	/**
	 * 是否已经有系统角色
	 */
	private boolean hasSysRole(Session session, String roleName) {
		List list = session.createQuery(
				"from Role r where r.isSys=? and r.name=?").setString(0,
				Constants.STATUS_AVAILABLE).setString(1, roleName).list();
		return list != null && list.size() > 0;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public void setAdminDesc(String adminDesc) {
		this.adminDesc = adminDesc;
	}

}
