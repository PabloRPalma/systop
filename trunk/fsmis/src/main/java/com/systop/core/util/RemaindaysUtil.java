package com.systop.core.util;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 用于解析任务剩余天数的工具类。
 * 
 * @author shaozhiyuan
 * 
 */
public final class RemaindaysUtil {

	private static double lastTime;

	private RemaindaysUtil() {
	}

	/**
	 * 根据传的开始时间和结束时间调用方法
	 * 
	 * @return返回计算出来的剩余天数
	 */
	public static double getRemainDays(Date beginDate, Date endDate) {
		return getRemainDayTime(beginDate, endDate);
	}

	/**
	 * 计算剩余天数的具体方法
	 * 
	 * @return 返回计算结果
	 */
	public static double getRemainDayTime(Date beginDate, Date endDate) {

		if (endDate != null) {
			lastTime = endDate.getTime() - new Date().getTime();
			lastTime = lastTime / 1000 / 60 / 60 / 24;
			DecimalFormat fnum = new DecimalFormat("##0.0");
			lastTime = Double.parseDouble(fnum.format(lastTime));
		} else {
			lastTime = 0;
		}
		return lastTime;
	}

	
	public static double getLastTime() {
		return lastTime;
	}

	public static void setLastTime(double lastTime) {
		RemaindaysUtil.lastTime = lastTime;
	}

}
