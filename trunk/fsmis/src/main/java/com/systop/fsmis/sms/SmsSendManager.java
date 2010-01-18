package com.systop.fsmis.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.DateUtil;
import com.systop.fsmis.model.SmsCount;
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
  @Autowired
  private SmsSendCountManager smsSendCountManager;
	
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

	/**
	 * 统计短信数量
	 * 
	 * @param mobileNum
	 *          手机号
	 */
	@Transactional
	public void statisticsSendConnt(String mobileNum) {

		String strMobileNum = "134,135,136,137,138,139,150,151,157,158,159,187,188";
		String[] strArray = strMobileNum.split(",");
		Boolean bIsMobileNum = false;

		if (mobileNum != null) {
			String strFlag = "";
			if (StringUtils.indexOf(mobileNum, "+86") != -1) {
				strFlag = StringUtils.substring(mobileNum, 3, 6);
			} else {
				strFlag = StringUtils.substring(mobileNum, 0, 3);
			}
			for (int i = 0; i < strArray.length; i++) {
				if (strArray[i].equals(strFlag)) {
					bIsMobileNum = true;
					SmsCount smsCount = null;
					List<SmsCount> listSC = smsSendCountManager.query(
							"from SmsCount s where s.sendDate between ? and ?", DateUtil
									.firstSecondOfDate(new Date()), DateUtil
									.lastSecondOfDate(new Date()));

					if (listSC.size() != 0) {
						smsCount = listSC.get(0);
						if (smsCount.getMobileCount() == null) {
							smsCount.setMobileCount(0);
						}
						smsCount.setMobileCount(smsCount.getMobileCount() + 1);
						smsSendCountManager.save(smsCount);
					} else {
						smsCount = new SmsCount();
						smsCount.setSendDate(new Date());
						smsCount.setMobileCount(1);
						smsSendCountManager.save(smsCount);
					}
					break;
				}
			}
			if (!bIsMobileNum) {
				SmsCount sendCount = null;
				List<SmsCount> listSC = smsSendCountManager.query(
						"from SmsCount s where s.sendDate between ? and ?", DateUtil
								.firstSecondOfDate(new Date()), DateUtil
								.lastSecondOfDate(new Date()));
				if (!listSC.isEmpty()) {
					sendCount = listSC.get(0);
					if (sendCount.getOtherCount() == null) {
						sendCount.setOtherCount(0);
					}
					sendCount.setOtherCount(sendCount.getOtherCount() + 1);
					smsSendCountManager.save(sendCount);
				} else {
					sendCount = new SmsCount();
					sendCount.setSendDate(new Date());
					sendCount.setOtherCount(1);
					smsSendCountManager.save(sendCount);
				}
			}
		}
	}
}
