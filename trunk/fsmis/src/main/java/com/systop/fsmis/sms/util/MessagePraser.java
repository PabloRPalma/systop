package com.systop.fsmis.sms.util;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.sms.SmsConstants;

/**
 * 短信内容解析器
 * 
 * @author WorkShopers
 * 
 */
@Service
public class MessagePraser {
  private Logger logger = LoggerFactory.getLogger(MessagePraseUtil.class);
  @Autowired
  private FsCaseManager fsCaseManager;

  public SmsReceive praseContent(SmsReceive smsReceive) {
    // 分析后有结果的短信 IsParse字段为1，无结果的短信 IsParse字段为0 ，IsNew为1代表管理者没有读
    // 分析后的举报短信IsReport为1，反馈的短信为IsReport为2，是反馈的短信但是没有事件号码的IsReport为3
    HashMap<String, String> hm = stringArithmetic(smsReceive.getContent());
    String type = hm.get("type");

    // 已经分析
    smsReceive.setIsParsed(SmsConstants.Y);
    // 举报短信
    if ("report".equals(type)) {
      smsReceive.setIsReport(SmsConstants.Y);
    } else if ("verify".equals(type)) {// 核实短信
      smsReceive.setIsVerify(SmsConstants.Y);
      String fsCaseId = hm.get("fsCaseId");
      if (fsCaseId != null) {
        try {
          Integer id = Integer.parseInt(fsCaseId);
          FsCase fsCase = fsCaseManager.get(id);
          smsReceive.setFsCase(fsCase);
        } catch (NumberFormatException e) {
          logger.error("MessagePraseUtil.praseContent().error{}", e
              .getMessage());
        }
      } else {
        smsReceive.setIsParsed(SmsConstants.Y);
      }
    } else {// 其他短信
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
  private HashMap<String, String> stringArithmetic(String content) {
    // 举报专用
    String[] quoteString = SmsConstants.QUOTE_STRING.split(",");

    // 反馈专用
    String[] feedbackString = SmsConstants.FEEDBACK_STRING.split(",");

    HashMap<String, String> hm = new HashMap<String, String>();
    content = content.trim();
    try {
      // 举报短信类型,即以1,A,a打头的短信是举报短信
      for (String s : quoteString) {
        if (content.startsWith(s)) {
          hm.put("type", "report");
          return hm;
        }
      }
      // 返回核实短信类型
      for (String s : feedbackString) {
        if (content.startsWith(s)) {
          // 取得标示位后的字符串,去掉标示核实短信的字符以后的字符串
          content = content.substring(s.length(), content.length());
          //举报短信应该是2/B/b 空格   事件id组成,取完标示的字符串1/B/b后根据空格,取得后面的事件id
          if (content.startsWith(" ")) {
            int index = (content.trim()).indexOf(" ");
            if (index > 0) {
              String eventId = content.trim().substring(0, index);
              hm.put("fsCaseId", eventId);
            }
          } else {
            hm.put("fsCaseId", null);
          }
          hm.put("type", "verify");
          return hm;
        }
      }
      // 其他短信类型
      hm.put("type", "others");
    } catch (Exception e) {
      hm.put("type", "others");
      logger.error(e.getMessage());
    }
    return hm;
  }
}
