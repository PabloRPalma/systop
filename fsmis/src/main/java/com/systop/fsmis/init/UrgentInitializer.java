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
import com.systop.fsmis.init.utils.InitUtil;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentType;

/**
 * 应急模块数据初始化导入
 * 
 * @author Lunch
 * 
 */
public class UrgentInitializer {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	/**
	 * spring自动调用方法,用于数据初始化
	 */
	@PostConstruct
	@Transactional
	public void init() {
		Session session = sessionFactory.openSession();
		try {
			if (urgentTypeIsEmpty(session)) {// 应急案件类别为空,执行导入
				setupUrgentType(session);
				logger.debug("UrgentType init is complete.");
			}
			if (urgentGroupIsEmpty(session)) {// 应急指挥组为空,执行导入
				setupUrgentGroup(session);
				logger.debug("UrgentGroup init is complete.");
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 初始化应急派遣环节
	 * 
	 * @param session
	 */
	private void setupUrgentType(Session session) {
		for (Dept d : getDepts(session)) {
			for (UrgentType type : InitUtil.getUrgentSendType()) {
				// 为应急派遣类别设置区县
				type.setCounty(d);
				session.save(type);
			}
		}
	}

	/**
	 * 初始化应急指挥组
	 * 
	 * @param session
	 */
	private void setupUrgentGroup(Session session) {
		for (Dept d : getDepts(session)) {
			for (UrgentGroup group : InitUtil.getUrgentGroups()) {
				group.setCounty(d);
				session.save(group);
			}
		}
	}

	/**
	 * 得到系统初始化的有效单位部门,首先是单位类型,其次必须有下级部门
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Dept> getDepts(Session session) {
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
		return depts;
	}

	/**
	 * 判断应急类别是否为空
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean urgentTypeIsEmpty(Session session) {
		List<Long> list = session.createQuery(
				"select count(t.id) as count from UrgentType t").list();
		return list.get(0) == 0;
	}

	/**
	 * 应急指挥组是否为空
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean urgentGroupIsEmpty(Session session) {
		List<Long> list = session.createQuery(
				"select count(t.id) as count from UrgentGroup t").list();
		return list.get(0) == 0;
	}

}
