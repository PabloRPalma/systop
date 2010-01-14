package com.systop.fsmis.fscase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.SmsSend;
import com.systop.fsmis.sms.SmsSendManager;

//FIXME:这个注释?...不再是一般的事件了。
/**
 * 一般事件
 * 
 * @author shaozhiyuan
 */
@Service
public class FsCaseManager extends BaseGenericsManager<FsCase> {

	@Autowired
	private SmsSendManager sesSendManager;

	/**
	 * 根据单体事件的编号查询事件的详情
	 * 
	 * @param code
	 *            单体事件编号
	 * @return 对应的单体事件
	 */
	public FsCase getGenericCaseByCode(String code) {
		return findObject("from FsCase g where g.code =?", code);
	}

	/**
	 * 给信息员发送信息核实事件
	 * 
	 * @param fsCase
	 *            事件信息
	 * @param name
	 *            接收人姓名
	 * @param moblie
	 *            接收人手机号
	 * @param msgContent
	 *            短信内容
	 */
	@Transactional
	public void sendMsg(FsCase fsCase, String name, String moblie,
			String msgContent) {
		SmsSend smsSend = new SmsSend();
		// 定义短信发送内容，组织短信发送内容
		StringBuffer content = new StringBuffer();
		content.append("ID:").append(fsCase.getId()).append("\\n");
		content.append(msgContent);
		smsSend.setName(name);
		smsSend.setMobileNum(moblie);
		smsSend.setContent(content.toString());
		sesSendManager.addMessage(smsSend);
	}
}
