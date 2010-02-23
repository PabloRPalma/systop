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
import com.systop.fsmis.model.SmsReceive;

/**
 * SmsReceiveManager<br>
 * 用于完成接收短信持久化操作
 * 
 * @author WorkShopers
 * 
 */
@Service
public class SmsReceiveManager extends BaseGenericsManager<SmsReceive> {
  @Autowired
  private SmsCountManager smsCountManager;

  /**
   * 根据编号查询已核实单体事件的反馈信息数量
   * 
   * @param singleEvId
   * @return
   */
  @SuppressWarnings("unchecked")
  public Long getCheckedMsgCountByFscaseId(Integer FscaseId) {
    String hql = "select count(*) from SmsReceive s where s.fsCase.id = ? and  s.isVerify = 1";
    List<Object> result = this.getDao().query(hql, new Object[] { FscaseId });
    return (Long) result.get(0);
  }

  /**
   * 统计短信数量
   * 
   * @param mobileNum 手机号
   */
  @Transactional
  public void statisticsReceiveCount(String mobileNum) {
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
        "from SmsCount s where s.sendDate between ? and ?", DateUtil
            .firstSecondOfDate(new Date()), DateUtil
            .lastSecondOfDate(new Date()));
    // 存在当天的记录
    if (smsCount != null) {
      // 为移动短信
      if (isMobileNum) {
        if (smsCount.getMobileReceiveCount() == null) {
          smsCount.setMobileReceiveCount(0);
        }
        smsCount.setMobileReceiveCount(smsCount.getMobileReceiveCount() + 1);
      }
      // 为联通短信
      else if (isUnicomNum) {
        if (smsCount.getUnicomReceiveCount() == null) {
          smsCount.setUnicomReceiveCount(0);
        }
        smsCount.setUnicomReceiveCount(smsCount.getUnicomReceiveCount() + 1);
      }
      // 为其他供应商短信
      else {
        if (smsCount.getOtherReceiveCount() == null) {
          smsCount.setOtherReceiveCount(0);
        }
        smsCount.setOtherReceiveCount(smsCount.getOtherReceiveCount() + 1);
      }
      smsCountManager.save(smsCount);
    } else {// 当天记录不存在
      smsCount = new SmsCount();
      smsCount.setSmsDate(new Date());
      if (isMobileNum) {
        smsCount.setMobileReceiveCount(1);
      } else if (isUnicomNum) {
        smsCount.setUnicomReceiveCount(1);
      } else {
        smsCount.setOtherReceiveCount(1);
      }
      smsCountManager.save(smsCount);
    }
  }

  /**
   * 取得接收短信的具体内容
   * 
   * @param smsId 短信ID
   * @author shaozhiyuan
   */
  @SuppressWarnings("unchecked")
  public Map getSmsMapById(String smsId) {
    Map map = new HashMap();
    if (StringUtils.isNotBlank(smsId) && StringUtils.isNumeric(smsId)) {
      SmsReceive smsReceive = get(Integer.valueOf(smsId));
      if (smsReceive != null) {
        map.put("mobileNum", smsReceive.getMobileNum());
        map.put("receiveTime", smsReceive.getReceiveTime());
        map.put("content", smsReceive.getContent());
      }
    }
    return map;
  }

  /**
   * 更改短信的状态为：不是最新
   * 
   * @param smsId 短信ID
   * @author shaozhiyuan
   */
  @Transactional
  public void changeSmsReceive(String smsId) {
    if (StringUtils.isNotBlank(smsId) && StringUtils.isNumeric(smsId)) {
      SmsReceive smsReceive = get(Integer.valueOf(smsId));
      smsReceive.setIsNew(SmsConstants.N);
      save(smsReceive);
    }
  }
}
