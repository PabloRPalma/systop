package com.systop.fsmis.init;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;

public class DeptInitializer {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	@PostConstruct
	@Transactional
	public void init() {
		logger.debug("Fsmis system init is begin");
		Session session = sessionFactory.openSession();
		setupDepts(session);
		logger.debug("Fsmis system init is finish");
	}

	private void setupDepts(Session session) {
		if (deptIsEmpty(session)) {
			try {
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
			} finally {
				session.flush();
				session.close();
			}

			logger.debug("setup depts");
		}
	}

	private boolean deptIsEmpty(Session session) {
		return CollectionUtils.isEmpty(session.createQuery("from Dept").list());
	}

}
