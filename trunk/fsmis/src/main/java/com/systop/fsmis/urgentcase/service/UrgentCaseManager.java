package com.systop.fsmis.urgentcase.service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.UrgentCase;

/**
 * 应急事件管理类
 * @author DU
 *
 */
@Service
public class UrgentCaseManager extends BaseGenericsManager<UrgentCase> {

	/**
	 * 保存应急事件审核结果
	 * @param caseId 应急事件ID
	 * @param isAgree 是否同意
	 * @param reason 具体意见
	 */
	@Transactional
	public void saveCheckResult(String caseId, String isAgree, String reason, User checker) {
		if (StringUtils.isNotEmpty(caseId)) {
			if (isNumeric(caseId)) {
				UrgentCase urgentCase = getDao().get(UrgentCase.class, Integer.valueOf(caseId));
				urgentCase.setStatus(isAgree);
				urgentCase.setIsAgree(isAgree);
				CheckResult checkResult = new CheckResult();
				checkResult.setChecker(checker);
				checkResult.setCheckTime(new Date());
				checkResult.setIsAgree(isAgree);
				checkResult.setResult(reason);
				checkResult.setUrgentCase(urgentCase);
				getDao().save(urgentCase);
				getDao().save(checkResult);
			}
		}
	}
	
	/**
	 * 判断字符串是否由数字组成
	 * @param str
	 */
	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()){
			return false;
		}
		return true;
	} 
}
