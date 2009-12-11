package com.systop.cms.advisory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 咨询常量
 * @author dailixia
 */

public final class AdvisoryConstants {

	/** 私有构造 */
	private AdvisoryConstants() {
	}

	/**
	 * 是否回复常量：Y-已回复
	 */
	public static final String ANSWER = "1";

	/**
	 * 是否回复常量：N-未回复
	 */
	public static final String UNANSWER = "0";

	/**
	 * 回复常量Map
	 */
	public static final Map<String, String> ANSWER_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		ANSWER_MAP.put(ANSWER, "已回复");
		ANSWER_MAP.put(UNANSWER, "未回复");
	}

}
