package com.systop.fsmis.video;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

public final class VideoConstants {
	/** 资源文件 */
	private static final String BUNDLE_KEY = "red5";

	/** 资源绑定对象 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_KEY);

	/** 视频应用上下文路径 */
	public static final String WEBAPP_CONTEXT_PATH = ResourceBundleUtil
			.getString(RESOURCE_BUNDLE, "webapp.contextPath", "/fsmis");
	/**
	 * 房间最多人员数量
	 */
	public static final Integer MAX_ROOM_MEMBERS = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "video.room.members.count", 15);
	/**
	 * 客户端用户名存在IClient对象中的属性的KEY名称
	 */
	public static final String USER_CLIENT_ATTR_KEY = "__user_id__";
	/**
	 * 视频流的publishedName存在IClient对象中的属性的KEY名称
	 */
	public static final String STREAM_CLIENT_ATTR_KEY = "__stream_published_name__";
	/**
	 * 視頻在房間中的index
	 */
	public static final String STREAM_CLIENT_ATTR_INDEX = "__stream_index__";
	/**
	 * room成员用SPLITER分隔各个用户id
	 */
	public static final String SPLITER = ",";
	/**
	 * 用户视频状态，空闲
	 */
	public static final String USER_IDLE = "0";
	/**
	 * 用户视频状态，在线等待（房间主人）
	 */
	public static final String USER_WAITING = "1";

	/**
	 * 用户视频状态，在线聊天
	 */
	public static final String USER_TALKING = "2";

	/**
	 * 客户端方法名：没有登录
	 */
	public static final String CLIENT_METHOD_NOT_LOGIN = "onNotLogin";
	/**
	 * 客户端方法:重复视频连接
	 */
	public static final String CLIENT_METHOD_DUPLE_CONNECT = "onDupleConnect";
	/**
	 * 客户端方法名：某人进入房间
	 */
	public static final String CLIENT_METHOD_ON_ROOM_JOIN = "onRoomJoin";
	
	/**
	 * 客户端方法名：房间已经满了
	 */
	public static final String CLIENT_METHOD_ON_ROOM_IS_FULL = "onRoomIsFull";
	/**
	 * 客户端方法名：当新的客户端到来的时候
	 */
	public static final String CLIENT_METHOD_ON_SUBSCRIBE_RESUME = "onSubscribeResume";
	/**
	 * 客户端方法名：视频预定结束
	 */
	public static final String CLIENT_METHOD_ON_SUBSCRIBE_OVER = "onSubscribeOver";
	/**
	 * 客户端方法名：某人离开房间
	 */
	public static final String CLIENT_METHOD_ON_ROOM_LEAVE = "onRoomLeave";
	/**
	 * 客户端方法名：收到视频
	 */
	public static final String CLIENT_METHOD_ON_STREAM_RECEIVED = "onStreamReceived";
	/**
	 * 客户端方法名：收到信息
	 */
	public static final String CLIENT_METHOD_RECEIVE_MSG = "onReceiveMsg";

	/**
	 * 客户端方法名: 接收本客户端连接状态
	 */
	public static final String CLIENT_METHOD_SHOW_CONN_STATUS = "showConnStatus";
	/**
	 * 客户端方法名,设置麦克风音量
	 */
	public static final String SWITCH_CLIENT_MIC = "onSwitchSpeaker";
	/**
	 * 客户端方法名,当有客户端修改房间或者新建房间时调用
	 */
	public static final String CLIENT_METHOD_ON_ROOM_CHANGE = "onRoomChange";
	
	/**
	 * 关闭房间
	 */
	public static final String CLIENT_METHOD_ON_CLOSE_ROOM = "onCloseRoom";
	/**
	 * 会议状态:未激活(未开始)
	 */
	public static final String ROOM_STATUS_NON_ACTIVED = "0";
	/**
	 * 会议状态:已激活(进行中)
	 */
	public static final String ROOM_STATUS_ACTIVED = "1";
	/**
	 * 会议状态:暂停
	 */
	public static final String ROOM_STATUS_HALT = "2";
	/**
	 * 会议状态:会议结束/关闭
	 */
	public static final String ROOM_STATUS_CLOSE = "3";
	
	
	
	  /** 会议状态常量Map  */
	  public static final Map<String, String> ROOM_MAP = Collections
	      .synchronizedMap(new LinkedHashMap<String, String>());
	  static {
		  ROOM_MAP.put(ROOM_STATUS_NON_ACTIVED, "未开始");
		  ROOM_MAP.put(ROOM_STATUS_ACTIVED, "进行中");
		  ROOM_MAP.put(ROOM_STATUS_HALT, "暂停");
		  ROOM_MAP.put(ROOM_STATUS_CLOSE, "已关闭");

	  }
}
