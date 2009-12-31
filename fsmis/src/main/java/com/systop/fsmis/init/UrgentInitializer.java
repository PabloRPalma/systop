package com.systop.fsmis.init;

import java.util.ArrayList;
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
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentType;

public class UrgentInitializer {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	@PostConstruct
	@Transactional
	public void init() {
		Session session = sessionFactory.openSession();
		setupUrgentType(session);
		setupUrgentGroup(session);
	}

	/**
	 * 初始化应急派遣环节
	 * 
	 * @param session
	 */
	private void setupUrgentType(Session session) {
		try {
			for (Dept d : getDepts()) {
				for (UrgentType type : InitUtil.getUrgentType()) {
					type.setCounty(d);
					session.save(type);
				}
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 初始化应急指挥组
	 * 
	 * @param session
	 */
	private void setupUrgentGroup(Session session) {
		try {
			for (Dept d : getDepts()) {
				for (UrgentGroup group : InitUtil.getUrgentGroups()) {
					group.setCounty(d);
					session.save(group);
				}
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 得到系统初始化的有效单位部门
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Dept> getDepts() {
		Session session = sessionFactory.openSession();
		List<Dept> list = session.createQuery("from Dept d where d.type = ?")
				.setString(0, DeptConstants.TYPE_COUNTY).list();
		List<Dept> depts = new ArrayList();
		if (!CollectionUtils.isEmpty(list)) {
			for (Dept d : list) {
				if (d.getChildDepts().size() > 0) {
					depts.add(d);
				}
			}
		}
		session.close();
		return depts;
	}

}
