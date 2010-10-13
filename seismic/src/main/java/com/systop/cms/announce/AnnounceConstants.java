package com.systop.cms.announce;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Announce常量
 * 
 * @author yj
 */
public final class AnnounceConstants {

	/** 私有构造 */
	private AnnounceConstants() {
	}

	/**
	 * 显示最新常量：1-最新
	 */
	public static final String NEW = "1";

	/**
	 * 显示最新常量：0-旧的
	 */
	public static final String OLD = "0";

	/**
	 * 显示类型常量：1-滚动
	 */
	public static final String SHOETYPE = "1";

	/**
	 * 显示类型常量：0-弹出
	 */
	public static final String UNSHOETYPE = "0";

	/**
	 * 显示类型常量Map
	 */
	public static final Map<String, String> SHOUTYPE_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		SHOUTYPE_MAP.put(SHOETYPE, "滚动");
		SHOUTYPE_MAP.put(UNSHOETYPE, "弹出");
	}

}
