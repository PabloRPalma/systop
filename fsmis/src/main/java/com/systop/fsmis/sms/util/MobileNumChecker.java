package com.systop.fsmis.sms.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.systop.fsmis.sms.SmsConstants;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 手机号码验证工具类
 * 
 * @author Workshopers
 * 
 */
public final class MobileNumChecker {
	/**
	 * 验证手机号码位数方法
	 * 
	 * @param mobileNum
	 *          要验证的手机号码
	 * @return 验证成功与否
	 */
	public static boolean checkMobilNumberDigit(String mobileNum) {
		if (StringUtils.isBlank(mobileNum) || !StringUtils.isNumeric(mobileNum)) {
			return false;
		}
		// 取得常量类从配置文件中的手机号码位数数组,转换为List
		List<String> allowMobilNumberDigits = new ArrayList<String>(
				SmsConstants.ALLOW_MOBILE_NUM_DIGIT.length);
		// 如果要验证的手机号码的位数在允许序列，则验证成功
		Collections.addAll(allowMobilNumberDigits,
				SmsConstants.ALLOW_MOBILE_NUM_DIGIT);
		if (allowMobilNumberDigits.contains(String.valueOf(mobileNum.length()))) {
			return true;
		} else {
			return false;
		}
	}
}
