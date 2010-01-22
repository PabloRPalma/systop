package com.systop.fsmis.sms;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.model.SmsSend;
import com.systop.fsmis.sms.smproxy.SmsProxy;
import com.systop.fsmis.sms.util.MobileNumChecker;

/**
 * 短信管理类,短信模块的顶层类<br>
 * 本类依赖:<br>
 * <ul>
 * <li>SMSProxy完成短信的收发操作
 * <li>SmsSendManager完成发送短信的持久化操作
 * <li>SmsReceiveManager完成接收短信的持久化操作
 * </ul>
 * 
 * @author WorkShopers
 * 
 */
@Service("smsManager")
public class SmsManager {

  private static Logger logger = LoggerFactory.getLogger(SmsManager.class);
  /**
   * 依赖短信服务代理接口,用于完成特定的短信平台的短信发送/接收/查询操作
   */
  @Autowired
  private SmsProxy smsProxy;
  /**
   * 依赖发送短信Manager,用于进行发送短信的数据库操作
   */
  @Autowired
  private SmsSendManager smsSendManager;
  /**
   * 依赖接收短信Manager,用于进行接收短信的数据库操作
   */
  @Autowired
  private SmsReceiveManager smsReceiveManager;

  /**
   * 接收短信方法
   */
  public void receiveMessages() throws Exception {
    logger.info("SmsManager.receiveMessages()");
    try {
      SmsReceive smsReceive = smsProxy.receiveMessage();
      if (smsReceive != null) {
        smsReceiveManager.save(smsReceive);
      }
    } catch (Exception ex) {
      logger.error("SmsManager.receiveMessages()Error:{}", ex.getMessage());
      throw ex;
    }
  }

  /**
   * 发送短信方法
   */
  public void sendMessages() throws Exception {
    logger.info("SmsManager.sendMessages()");
    // 得到数据库中需要发送的短信列表
    List<SmsSend> smsSendList = smsSendManager.getNewSmsSends();
    // 遍历列表
    for (SmsSend smsSend : smsSendList) {
      if (smsSend == null) {
        logger.error("查询得到为null的短信");
      } else {
        if (!MobileNumChecker.checkMobilNumberDigit(smsSend.getMobileNum())) {
          logger.error("ID为:{}的短信,接收手机号[{}]有误,发送失败!", smsSend.getId(), smsSend
              .getMobileNum());
        } else {
          try {
            // 调用代理的发送功能,如果发送成功,则会得到该短信在Mas中的id
            int state = smsProxy.sendMessage(smsSend);

            // 更新数据库中短信的状态,不为新短信,短信发送时间
            smsSend.setIsNew(SmsConstants.SMS_SMS_SEND_IS_NOT_NEW);
            smsSend.setSendTime(new Date());
            smsSend.setMasid(state);
            smsSendManager.update(smsSend);

          } catch (Exception ex) {
            logger.error("SmsManager.sendMessages()Error:{}", ex.getMessage());
            throw ex;
          }
        }
      }
    }
  }

  /**
   * 得到已发送短信的接收状态
   */
  public void checkSmsSendState() throws Exception {
    logger.info("SmsManager.checkSmsSendState()");
    // 得到已经发送到Mas机的短信集合
    List<SmsSend> smsSends = smsSendManager.getSendedSmsSends();
    for (SmsSend smsSend : smsSends) {
      if (smsSend != null) {
        try {
          smsProxy.querySmsSendState(smsSend);
          smsSendManager.update(smsSend);
        } catch (Exception ex) {
          logger.error("SmsManager.checkSmsSendState()Error:{}", ex
              .getMessage());
          throw ex;
        }
      }
    }
  }

}
