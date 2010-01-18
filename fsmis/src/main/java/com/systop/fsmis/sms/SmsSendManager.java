package com.systop.fsmis.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
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
    // 查询新短信并且以短信记录创建时间早晚排序
    StringBuffer buf = new StringBuffer("from SmsSend ss where ss.isNew = ");
    buf.append(SmsConstants.SMS_SMS_SEND_IS_NEW);
    buf.append(" order by ss.createTime");

    List<SmsSend> smsSends = query(buf.toString());
    // 如果得到的记录数大于系统所指定的一次发送记录数,则只取得指定的记录
    if (smsSends.size() > SmsConstants.SMS_SMS_SEND_COUNT) {
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

  public void addMessage(SmsSend smsSend) {
    if (smsSend != null) {
      smsSend.setCreateTime(new Date());
      smsSend.setIsNew(SmsConstants.SMS_SMS_SEND_IS_NEW);
      // 缺少统计发送数量逻辑
      save(smsSend);
    }
  }

  /**
   * 取得发送短信的具体内容
   * @param smsId 短信ID
   * @author DU
   */
  @SuppressWarnings("unchecked")
  public Map getSmsMapById(String smsId) {
  	Map map = new HashMap();
  	if (StringUtils.isNotBlank(smsId) && StringUtils.isNumeric(smsId)) {
  		SmsSend smsSend = get(Integer.valueOf(smsId));
  		if (smsSend != null) {
  			map.put("mobileNum", smsSend.getMobileNum());
  			map.put("name", smsSend.getName());
  			map.put("sendTime", smsSend.getSendTime());
  			map.put("content", smsSend.getContent());
  		}
  	}
  	
  	return map;
  }
}
