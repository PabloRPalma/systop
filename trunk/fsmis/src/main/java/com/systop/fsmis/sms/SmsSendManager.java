package com.systop.fsmis.sms;

import java.util.Date;
import java.util.List;

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
	 * 得到需要发送的短信方法(最大记录数由系统变量限定)
	 * 
	 * @return
	 */
	public List<SmsSend> getNewSmsSends() {
		List<SmsSend> smsSends = null;
		// 查询新短信并且以短信记录创建时间早晚排序
		String hql = "from SmsSend ss where ss.isNew = ? order by ss.createTime";
		smsSends = query(hql, SmsConstants.SMS_SMS_SEND_IS_NEW);
		// 如果得到的记录数大于系统所指定的一次发送记录数,则只取得指定的记录
		if (smsSends != null
				&& smsSends.size() > SmsConstants.SMS_SMS_SEND_COUNT) {
			return smsSends.subList(0, SmsConstants.SMS_SMS_SEND_COUNT - 1);
		}

		return smsSends;
	}

	public void addMessage(String mobileNum, String content) {
		SmsSend smsSend = new SmsSend();
		smsSend.setMobileNum(mobileNum);
		smsSend.setContent(content);
		smsSend.setCreateTime(new Date());
		smsSend.setIsNew(SmsConstants.SMS_SMS_SEND_IS_NEW);
		// 缺少统计发送数量逻辑
		save(smsSend);
	}

}
