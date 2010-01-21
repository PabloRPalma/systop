package com.systop.fsmis.statistics.fscase.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 统计局部使用，日期操作
 * 
 * @author yj
 * 
 */
public final class Util {
	/**
	 * 获得月
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 获得年
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * /计算当月最后一天
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);// 设为当前月的1号
		c.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		c.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		return c.getTime();
	}

	/**
	 * 获取当月第一天
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);// 设为当前月的1号
		return c.getTime();
	}
}
