package com.systop.fsmis.fscase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.CaseConstants;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.SmsSend;
import com.systop.fsmis.sms.SmsSendManager;


/**
 * 一般事件
 * @author shaozhiyuan
 *
 */
@Service
public class FsCaseManager extends BaseGenericsManager<FsCase>{
	
	//注入短信类用于发送短信
	@Autowired
	private SmsSendManager sesSendManager;
	
	/**
	 * 保存一般事件信息
	 */
	@Transactional
	public void save(FsCase genericCase) {
		getDao().saveOrUpdate(genericCase);
	}

	/**
	 * 根据单体事件的编号查询事件的详情
	 * @param code 单体事件编号
	 * @return 对应的单体事件
	 */
	public FsCase getGenericCaseByCode(String code) {
		return findObject("from FsCase g where g.code =?", code);
	}
	
	/**
	 * 给信息员发送信息核实事件
	 */
	@Transactional
	public void sendMsg(FsCase fsCase, String supervisorName,
			String supervisorMoblie, String msgContent) {
		SmsSend smsSend = new SmsSend();
		String content = CaseConstants.SEND_CONTENT + " "
				+ String.valueOf(fsCase.getId()) + " [" + msgContent + "]";
		smsSend.setMobileNum(supervisorMoblie);
		smsSend.setName(supervisorName);
		smsSend.setContent(content);
		sesSendManager.addMessage(smsSend);
		//设置标识，核实短信已发送
		fsCase.setIsSendInformMsg(CaseConstants.SENDED);
		getDao().save(fsCase);
	}
}
