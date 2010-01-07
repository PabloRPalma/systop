package com.systop.fsmis.init;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptSerialNoManager;
import com.systop.fsmis.init.utils.InitUtil;

/**
 * 部门数据初始化
 * 
 * @author Lunch
 * 
 */
public class DeptInitializer {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DeptSerialNoManager deptSerialNoManager;

	/**
	 * spring自动调用方法,用于数据初始化
	 */
	@PostConstruct
	@Transactional
	public void init() {
		Session session = sessionFactory.openSession();
		try {
			if (deptIsEmpty(session)) {
				setupDepts(session);
				logger.debug("Dept init is complete.");
			}
		} finally {
			deptSerialNoManager.updateAllSerialNo();
			session.flush();
			session.close();
		}
	}

	/**
	 * 导入区县及部门信息
	 * 
	 * @param session
	 */
	private void setupDepts(Session session) {
		List<Dept> countys = InitUtil.getCountys();
		Dept top = new Dept(DeptConstants.TOP_DEPT_NAME,
				DeptConstants.TYPE_COUNTY);
		session.save(top);
		for (Dept county : countys) {
			county.setParentDept(top);
			session.save(county);
			for (Dept dept : InitUtil.getDepts()) {
				dept.setParentDept(county);
				session.save(dept);
			}
		}
		for (Dept dept : InitUtil.getDepts()) {
			dept.setParentDept(top);
			session.save(dept);
		}
	}

	/**
	 * 判断部门数据是否为空
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean deptIsEmpty(Session session) {
		List<Long> list = session.createQuery(
				"select count(t.id) as count from Dept t").list();
		return list.get(0) == 0;
	}

}
