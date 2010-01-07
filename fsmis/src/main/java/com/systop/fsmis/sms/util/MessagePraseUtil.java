package com.systop.fsmis.sms.util;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.sms.SmsConstants;

/**
 * 短信内容解析工具类
 * 
 * @author WorkShopers
 * 
 */
public final class MessagePraseUtil {
	private static Logger logger = LoggerFactory
			.getLogger(MessagePraseUtil.class);

	public static SmsReceive praseContent(SmsReceive smsReceive) {
		// 分析后有结果的短信 IsParse字段为1，无结果的短信 IsParse字段为0 ，IsNew为1代表管理者没有读
		// 分析后的举报短信IsReport为1，反馈的短信为IsReport为2，是反馈的短信但是没有事件号码的IsReport为3
		HashMap<String, String> hm = stringArithmetic(smsReceive.getContent());
		String type = hm.get("type");

		if ("1".equals(type)) {

			smsReceive.setIsParsed(SmsConstants.Y);
			smsReceive.setIsReport("1");
		} else if ("2".equals(type)) {
			smsReceive.setIsParsed(SmsConstants.Y);
			smsReceive.setIsReport("2");
			String eventid = hm.get("eventId");
			if (eventid != null) {
				try {
					@SuppressWarnings("unused")
					Integer id = Integer.parseInt(eventid);
					// smsReceive.setEventId(id);
				} catch (NumberFormatException e) {
					// mesReceive.setIsReport("3");//已经解析，但是没有有效的事件ID
					// logger.error(e.getMessage());
				}
			} else {
				smsReceive.setIsParsed(SmsConstants.Y);
			}
		} else {
			smsReceive.setIsParsed(SmsConstants.N);
		}
		return smsReceive;
	}

	/***
	 * 判断短信的类型，上报/核实/其他
	 * 
	 * @param content
	 * @return
	 */
	private static HashMap<String, String> stringArithmetic(String content) {
		// 举报专用
		String[] quoteString = SmsConstants.QUOTE_STRING.split(",");

		// 反馈专用
		String[] feedbackString = SmsConstants.FEEDBACK_STRING.split(",");

		HashMap<String, String> hm = new HashMap<String, String>();
		content = content.trim();
		try {
			for (String s : quoteString) {
				if (content.startsWith(s)) {
					hm.put("type", "1");
					return hm;
				}
			}

			for (String s : feedbackString) {
				if (content.startsWith(s)) {
					content = content.substring(s.length(), content.length());
					if (content.startsWith(" ")) {
						int index = (content.trim()).indexOf(" ");
						if (index > 0) {
							String eventId = content.trim().substring(0, index);
							hm.put("eventId", eventId);
						}
					} else {
						hm.put("event", null);
					}
					hm.put("type", "2");
					return hm;
				}
			}
			hm.put("type", "3");
		} catch (Exception e) {
			hm.put("type", "3");
			logger.error(e.getMessage());
		}
		return hm;
	}
}
