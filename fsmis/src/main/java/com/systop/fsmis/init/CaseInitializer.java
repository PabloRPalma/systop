package com.systop.fsmis.init;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.systop.fsmis.init.utils.InitUtil;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.SendType;

/**
 * 案件管理部分数据的初始导入
 * 
 * @author Lunch
 */
public class CaseInitializer {

	/** 日志 */
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
			if (caseTypeIsEmpty(session)) {// 案件类别为空,导入案件类别
				setupCaseType(session);
				logger.debug("CaseType init is complete");
			}
			if (sendTypeIsEmpty(session)) {// 派遣类别为空,导入案件类别
				setupSendType(session);
				logger.debug("SendType init is complete");
			}
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 导入案件类别数据
	 * 
	 * @param session
	 */
	private void setupCaseType(Session session) {
		List<CaseType> caseTypes = InitUtil.getCaseTypes();
		for (CaseType caseType : caseTypes) {
			session.save(caseType);
		}

	}

	/**
	 * 导入派遣类别数据
	 * 
	 * @param session
	 */
	private void setupSendType(Session session) {
		List<SendType> sendTypes = InitUtil.getSendTypes();
		for (SendType sendType : sendTypes) {
			session.save(sendType);
		}
	}

	/**
	 * 判断案件类别是否为空
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean caseTypeIsEmpty(Session session) {
		List<Long> list = session.createQuery(
				"select count(t.id) as count from CaseType t").list();
		return list.get(0) == 0;
	}

	/**
	 * 判断派遣类别是否为空
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean sendTypeIsEmpty(Session session) {
		List<Long> list = session.createQuery(
				"select count(t.id) as count from SendType t").list();
		return list.get(0) == 0;
	}

}
