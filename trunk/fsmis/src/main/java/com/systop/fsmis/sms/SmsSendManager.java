package com.systop.fsmis.sms;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.SmsSend;

/**
 * SmsSendManager<br>
 * 用于完成发送短信的持久化操作
 * 
 * @author WorkShopers
 * 
 */
@Service
public class SmsSendManager extends BaseGenericsManager<SmsSend> {
	/**
	 * 得到需要发送的短信方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmsSend> getNewSmsSends() {
		List<SmsSend> smsSendList = null;
		/*
		 * String hql = "from SmsSend ss where ss.isNew = ?"; // 查询新短信 List<SmsSend>
		 * smsSendList = query(hql, new Object[] { SMSConstants.SMS_SMS_SEND_IS_NEW
		 * });
		 */
		Session session = getDao().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(SmsSend.class);
		criteria.add(Restrictions.eq("isNew", SMSConstants.SMS_SMS_SEND_IS_NEW));
		criteria.addOrder(Order.asc("createTime"));
		criteria.setMaxResults(SMSConstants.SMS_SMS_SEND_COUNT);

		// getDao().getHibernateTemplate().findByCriteria(criteria);
		smsSendList = criteria.list();

		session.close();

		return smsSendList;
	}
}
