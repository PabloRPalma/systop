package com.systop.fsmis.web.wap.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Complain;
import com.systop.fsmis.model.SmsSend;

/**
 * 投诉举报WAP管理类
 * @author DU
 *
 */
@Service
public class ComplainManager extends BaseGenericsManager<Complain> {

	/**
	 * 保存举报投诉内容，并发送短息
	 */
	@Transactional
	public void saveComplain(Complain complain) {
		// 设定isWap为“1”，即是Wap举报
		complain.setIsWap(FsConstants.Y);
		// 生成并设置secureStr信息
		String securityStr = getSecurity(8);
		complain.setSecureStr(securityStr);
		// 设置isConfirm信息，初始化为"0"
		complain.setIsConfirmed(FsConstants.N);
		// 设置isValidate信息，初始化为"0",即为验证
		complain.setIsValidated(FsConstants.N);
		//设置isNew信息，初始化为"1",即为新记录
		complain.setIsNew(FsConstants.Y);
		
		//向短息发送表中添加数据
		SmsSend smsSend = new SmsSend();
		StringBuffer content = new StringBuffer("请登陆页面，向系统提交字符串");
		content.append("'").append(securityStr).append("'")
		.append("进行投诉信息身份验证！");
		smsSend.setMobileNum(complain.getPhoneNo());
		smsSend.setName(complain.getReporter());
		smsSend.setContent(content.toString());
		smsSend.setCreateTime(new Date());
		smsSend.setIsNew(FsConstants.Y);
		//发送短信
		getDao().save(smsSend);
		//保存投诉内容
		save(complain);
	}
	
	/**
	 * 举报投诉身份确认
	 * @param secureStr 验证码
	 */
	@Transactional
	public void identitComp(String secureStr) {
		Complain complain = findObject(
				"from Complain cp where cp.secureStr = ?", secureStr);
		if (complain != null) {
			complain.setIsValidated(FsConstants.Y);
			save(complain);
		} else {
			throw new ApplicationException("身份验证失败，请检查您填写的验证号！");
		}
	}
	
	/**
	 * 获取指定长度的随机ASCII码字符串
	 * @param length
	 */
	private String getSecurity(int length) {
		String randomAscii = null;
		List<Complain> result = null;
		do {
			randomAscii = RandomStringUtils.randomAlphabetic(length)
					.toLowerCase();
			result = query("from Complain cp where cp.secureStr = ?",
					randomAscii);
		} while (result != null && result.size() > 0);
		return randomAscii;
	}
}
