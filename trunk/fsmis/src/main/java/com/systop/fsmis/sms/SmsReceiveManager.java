package com.systop.fsmis.sms;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;
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
	
	/**
	 * 根据编号查询已核实单体事件的反馈信息数量
	 * 
	 * @param singleEvId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Long getCheckedMsgCountByFscaseId(Integer FscaseId){
		String hql = "select count(*) from SmsReceive s where s.fsCase.id = ? and  s.isReport = 2";
		List<Object> result = this.getDao().query(hql, new Object[]{FscaseId});		
		return (Long)result.get(0);
	}

}
