package com.systop.fsmis;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 事件常量类
 */
public final class CaseConstants {

	/** 事件状态：未派遣 */
	public static final String CASE_UN_RESOLVE = "0";

	/** 事件状态：处理中 */
	public static final String CASE_PROCESSING = "1";

	/** 事件状态：已处理 */
	public static final String CASE_PROCESSED = "2";

	/** 事件状态：退回 */
	public static final String CASE_RETURNED = "3";

	/** 事件状态：已核实结案 */
	public static final String CASE_CLOSED = "4";

	/** 事件状态：忽略 */
	public static final String CASE_IGNORED = "5";

	/** 市级 */
	public static final String CITY = "1";

	/** 区、县级 */
	public static final String COUNTY = "2";

	/**
	 * 配置类别
	 */
	public static final Map<String, String> CONFIGER_CATEGORY_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		CONFIGER_CATEGORY_MAP.put(CITY, "市级");
		CONFIGER_CATEGORY_MAP.put(COUNTY, "区县级");
	}

}
