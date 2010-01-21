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
  private SmsCountManager smsCountManager;
	
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
	public void statisticsSendCount(String mobileNum) {
		if (mobileNum == null || StringUtils.isBlank(mobileNum)) {
			return;
		}
		// 移动号码段
		String strMobileNum = "134,135,136,137,138,139,150,151,157,158,159,187,188";
		// 联通号码段
		String strUnicomNum = "130,131,132,133,153,156";

		String[] mobileNums = strMobileNum.split(",");
		String[] unicomNums = strUnicomNum.split(",");

		Boolean isMobileNum = false;
		Boolean isUnicomNum = false;

		// 获取strFlag号码标志段，例如：131，132等
		String strFlag = "";
		if (StringUtils.indexOf(mobileNum, "+86") != -1) {
			strFlag = StringUtils.substring(mobileNum, 3, 6);
		} else {
			strFlag = StringUtils.substring(mobileNum, 0, 3);
		}

		// 根据标志号段，判断号码供应商
		for (int i = 0; i < mobileNums.length; i++) {
			if (mobileNums[i].equals(strFlag)) {
				isMobileNum = true;
				isUnicomNum = false;
				break;
			}
		}
		for (int i = 0; i < unicomNums.length; i++) {
			if (unicomNums[i].equals(strFlag)) {
				isUnicomNum = true;
				isMobileNum = false;
				break;
			}
		}
		// 获取当天的统计实体
		SmsCount smsCount = smsCountManager.findObject(
				"from SmsCount s where s.smsDate between ? and ?", DateUtil
						.firstSecondOfDate(new Date()), DateUtil
						.lastSecondOfDate(new Date()));
		// 存在当天的记录
		if (smsCount != null) {
			// 为移动短信
			if (isMobileNum) {
				if (smsCount.getMobileSendCount() == null) {
					smsCount.setMobileSendCount(0);
				}
				smsCount.setMobileSendCount(smsCount.getMobileSendCount() + 1);
			}
			// 为联通短信
			else if (isUnicomNum) {
				if (smsCount.getUnicomSendCount() == null) {
					smsCount.setUnicomSendCount(0);
				}
				smsCount.setUnicomSendCount(smsCount.getUnicomSendCount() + 1);
			}
			// 为其他供应商短信
			else {
				if (smsCount.getOtherSendCount() == null) {
					smsCount.setOtherSendCount(0);
				}
				smsCount.setOtherSendCount(smsCount.getOtherSendCount() + 1);
			}
			smsCountManager.save(smsCount);
		} else {// 当天记录不存在
			smsCount = new SmsCount();
			smsCount.setSmsDate(new Date());
			if (isMobileNum) {
				smsCount.setMobileSendCount(1);
			} else if (isUnicomNum) {
				smsCount.setUnicomSendCount(1);
			} else {
				smsCount.setOtherSendCount(1);
			}
			smsCountManager.save(smsCount);
		}
	}
}
